package com.yatang.sc.timedtask;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.dubboservice.WebReturnRequestDubboService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.purchase.dto.ReturnRequestDetailDto;
import com.yatang.xc.oc.b.member.biz.core.dto.LevelPrivilegeDto;
import com.yatang.xc.oc.b.member.biz.core.dto.PrivilegeInfoDto;
import com.yatang.xc.oc.b.member.biz.core.dubboservice.PrivilegeInfoDubboService;

/**
 * Created by liusongjie on 2017/7/27.
 * <p>
 * 13、	由“订单自动审核服务”定时检查订单付款状态为“已付款”，库存状态为“已签收”，
 * 则判定收货数量与订单行数量是否相等，相等则变更 订单状态为“已完成”；
 * 如果收货数量与订单行数量不相等，则判断当前订单是否子订单，如果不是则生成该订单的两张子订单；
 */
@Component("autoCompleteOrderScheduler")
@Deprecated
public class AutoCompleteOrderScheduler implements Scheduler {
    private Logger log = LoggerFactory.getLogger( this.getClass() );

    @Autowired
    OrderService orderService;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    private PrivilegeInfoDubboService privilegeInfoDubboService;

    @Autowired
    private WebReturnRequestDubboService webReturnRequestDubboService;

    public void execute() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put( "orderState", OrderStates.APPROVED );
        condition.put( "shippingState", ShippingStates.COMPLETED );
//        condition.put("shippingState", ShippingStates.USER_REJECT);
        log.info( "depend on order state as " + ShippingStates.COMPLETED + " to get order" );
        List<Order> signedOrder = orderService.getOrdersByState( condition );
        log.info( "On " + ShippingStates.COMPLETED + " state, order quantities are " + signedOrder.size() );

        if (CollectionUtils.isEmpty( signedOrder )) {
            return;
        }
        for (Order order : signedOrder) {
            updateOrderToCompleted( order );
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOrderToCompleted(Order order) {
        order.setState( OrderTotalStates.COMPLETED.getStateValue() );
        order.setStateDetail( OrderTotalStates.COMPLETED.getDescription() );
        order.setOrderState( OrderStates.COMPLETED );
        order.setLastModifiedTime( new Date() );
        orderService.updateOrderSelective( order );
        orderLogService.saveOrderLog( order.getId(), order.getState(), "自动审核订单为已完成！", "系统" );
        log.info( "auto complete, order:{}", JSON.toJSONString( order ) );
        //订单返券
        // giveCoupons( order );

    }

    private void giveCoupons(Order order) {
        Response<LevelPrivilegeDto> privilegeInfo = privilegeInfoDubboService.getPrivilegeInfoByMemberCode( order.getProfileId(), "QY_system_gyl" );
        if(privilegeInfo==null||privilegeInfo.getResultObject() == null||privilegeInfo.getResultObject().getPrivilegeInfoDtoList()==null||privilegeInfo.getResultObject().getPrivilegeInfoDtoList().isEmpty()){
        	return;
        }
        PrivilegeInfoDto privilegeInfoDto = privilegeInfo.getResultObject().getPrivilegeInfoDtoList().get(0);
        if (privilegeInfoDto == null) {
                return;
        }
        Response<ReturnRequestDetailDto> returnRequestDetailDto = webReturnRequestDubboService.queryReturnRequestByOrderId( order.getId() );
        double refuseAmount = 0d;
        if (returnRequestDetailDto != null && returnRequestDetailDto.getResultObject() != null&&returnRequestDetailDto.getResultObject().getRefundAmount()!=null) {
            refuseAmount = returnRequestDetailDto.getResultObject().getRefundAmount();
        }
        //会员等级返券
        Response<String> giveCouponToStore = orderService.giveCouponToStore( order.getFranchiseeStoreId(), refuseAmount, order.getPriceInfo() );
        if (giveCouponToStore != null) {
            log.debug( "订单确认返券： order Id-->:" + order.getId() + " give coupons->: " + giveCouponToStore.getResultObject() );
        }
    }
}
