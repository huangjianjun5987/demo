package com.yatang.sc.timedtask;

import com.yatang.sc.common.CommonEnum;
import com.yatang.sc.common.utils.DateUtil;
import com.yatang.sc.flow.OrderFulfillerDubboServiceFlow;
import com.yatang.sc.fulfillment.dto.OrderReceiveDto;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.ReturnRequestService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.OrderReturnedStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.ShippingModes;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 订单自动收货定时任务
 * <p>
 * Created by jianghuajun on 2017/11/20.
 */
@Component("AutoReceiveOrderScheduler")
public class AutoReceiveOrderScheduler implements Scheduler {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private OrderFulfillerDubboServiceFlow mOrderFulfillerDubboServiceFlow;

    @Autowired
    private ReturnRequestService returnRequestService;

    @Autowired
    private ShippingGroupService shippingGroupService;


    /**
     * 自动收货期限 7天
     */
    private static final Integer AUTO_RECEIVE_DATE = 15;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute() {

        log.info("自动批量收货任务执行 start: " + DateUtil.formatDateYMDhms(new Date()));

        // 查询所有需要自动收货订单

        //TODO 生产
          List<String> orderIds = orderService.queryReadyToAutoReceiveOrderId(getLimitDate());
        //TODO 测试
        //List<String> orderIds = orderService.queryReadyToAutoReceiveOrderId(getLimitDateTest());

        // 批量执行收货
        for (String orderId : orderIds) {
            // 已创建退货单且未被驳回的订单不进行自动收货
            ReturnRequestPo returnRequestPo = returnRequestService.queryReturnRequestByOrderId(orderId);
            if (returnRequestPo != null && returnRequestPo.getState() != OrderReturnedStates.CANCELLED.getStateValue()) {
                log.info("订单存在拒收退货申请，不自动签收:{}", orderId);
                continue;
            }

            Order order = orderService.selectByPrimaryKey(orderId);
            ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
            if(shippingGroup != null && ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
                log.info("直送的订单，不自动签收:{}", orderId);
                continue;
            }


            // 自动收货
            OrderReceiveDto orderReceiveDto = new OrderReceiveDto();
            orderReceiveDto.setOperatorId("自动收货");
            orderReceiveDto.setOrderId(orderId);
            try {
                mOrderFulfillerDubboServiceFlow.orderReceive(orderReceiveDto);

                orderLogService.saveOrderLog(orderId, OrderTotalStates.COMPLETED.getStateValue(), "自动收货成功", "系统");
                log.info("AutoReceiveOrderScheduler 订单 {} 已被自动收货", orderId);
            } catch (Exception e) {
                orderLogService.saveOrderLog(orderId, OrderTotalStates.PENDING_RECEIVING.getStateValue(), "自动收货失败", "系统");
                log.error("AutoReceiveOrderScheduler 订单 {} 自动收货失败:{}", orderId, ExceptionUtils.getFullStackTrace(e));
            }
        }
        log.info("自动批量收货任务执行 end: " + DateUtil.formatDateYMDhms(new Date()));
    }

    /**
     * 获取收货时间节点
     * @return
     */
    private Date getLimitDate(){
        return DateUtil.subDay2Calendar(new Date(), AUTO_RECEIVE_DATE).getTime();
    }

    /**
     * 获取收货时间节点(测试用)
     * @return
     */
    private Date getLimitDateTest(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, -10);
        return c.getTime();
    }


}
