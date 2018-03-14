package com.yatang.sc.order.handler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.CouponPurchaseRecord;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.dubboservice.WebReturnRequestDubboService;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.CouponActivityService;
import com.yatang.sc.order.service.CouponPurchaseRecordService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.purchase.dto.ReturnRequestDetailDto;
import com.yatang.sc.service.ProductHelper;
import com.yatang.sc.vo.UpdateOrderVO;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.oc.b.member.biz.core.dto.LevelPrivilegeDto;
import com.yatang.xc.oc.b.member.biz.core.dto.PrivilegeInfoDto;
import com.yatang.xc.oc.b.member.biz.core.dubboservice.PrivilegeInfoDubboService;

/**
 * 发放优惠券
 * Created by qiugang on 7/26/2017.
 */
@Service("deliveryElectronicOrderListener")
public class DeliveryElectronicOrderListener extends OrderMsgListener {
    protected Logger log = LoggerFactory.getLogger(DeliveryElectronicOrderListener.class);

    @Autowired
    OrderService orderService;

    @Autowired
    PaymentGroupService paymentGroupService;

    @Autowired
    ShippingGroupService shippingGroupService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    ProductDubboService productDubboService;

    @Autowired
    WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    @Autowired
    CouponActivityService couponActivityService;

    @Autowired
    CouponPurchaseRecordService couponPurchaseRecordService;

    @Autowired
    ProductHelper productHelper;
    
    @Autowired
    private PrivilegeInfoDubboService privilegeInfoDubboService;

	@Autowired
	private WebReturnRequestDubboService webReturnRequestDubboService;

    @Override
    @Transactional
    public void handle(OrderMessage msg) {
        log.info("handle msg order id: " + msg.getOrderId());
        Order order = orderService.selectByPrimaryKey(msg.getOrderId());
        if (order == null) {
            log.error("can't find order by id: " + msg.getOrderId());
            return;
        }
        if(!OrderTypes.ELECTRONIC_ORDER.equals(order.getOrderType())){
            log.error("order {} type is {}, not corrent! ", msg.getOrderId(), order.getOrderType());
            return;
        }
        String currentShippingState = order.getShippingState();
        if (ShippingStates.PENDING_PROCESS.equals(currentShippingState)
                && OrderStates.APPROVED.equals(order.getOrderState())
                && (PaymentStates.DEBITED.equals(order.getPaymentState()) || PaymentStates.GSN.equals(order.getPaymentState()))) {
            List<CommerceItem> commerceItemList = commerceItemService.getCommerceItemAndPriceForOrderId(order.getId());
            for (CommerceItem commerceItem : commerceItemList) {
                List<CouponPurchaseRecord> records = couponPurchaseRecordService.queryByCommerceItemId(commerceItem.getId());
                if(!CollectionUtils.isEmpty(records)){
                    log.warn("record is exiting, commerceItemId:" + commerceItem.getId());
                    continue;
                }
                String productId = commerceItem.getProductId();
                long qty = commerceItem.getQuantity();
                String couponId = null;
                ProductDto product = productHelper.findProductById(productId);
                if(product != null){
                    couponId = product.getCouponId();
                }
                if(StringUtils.isEmpty(couponId)){
                    throw new RuntimeException("coupon id is null,product:" + productId);
                }
                couponActivityService.grantMutiQtyCoupon(order.getFranchiseeStoreId(), couponId, qty);
                couponPurchaseRecordService.saveCouponPurchaseRecord(order.getId(), commerceItem.getId(), qty);
            }
            order.setShippingState(ShippingStates.COMPLETED);
            order.setState(OrderTotalStates.COMPLETED.getStateValue());
            order.setStateDetail(OrderTotalStates.COMPLETED.getDescription());
            UpdateOrderVO updateOrderVO = new UpdateOrderVO();
            updateOrderVO.setOperator("系统");
            updateOrderVO.setDescription("订单已传送到GLink,等待GLink Accept 通知");
            updateOrderVO.setPreOrderState(order.getState());
            updateOrderVO.setOrder(order);
            orderService.updateAndSaveLog(updateOrderVO);
            log.info("发送订单成功！ order id: " + msg.getOrderId());
            //订单返券
            //giveCoupons(order); 
        }
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
    
    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.DeliveryElectronicOrder.equals(msg.getMssageType());
    }
}
