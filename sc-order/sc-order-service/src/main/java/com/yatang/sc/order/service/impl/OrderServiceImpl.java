package com.yatang.sc.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yatang.sc.order.domain.*;
import com.yatang.sc.order.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.DateUtil;
import com.yatang.sc.common.utils.PricingUtil;
import com.yatang.sc.order.dao.OrderDao;
import com.yatang.sc.order.domain.orderIndex.OrderItemIndex;
import com.yatang.sc.order.domain.orderIndex.OrderDetailIndex;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.vo.UpdateOrderVO;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderDao dao;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    OrderPriceService orderPriceService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    DataSourceTransactionManager transationManager;

    @Autowired
    PaymentGroupService paymentGroupService;
    
    @Autowired
    CouponActivityService couponActivityService;
    
    @Autowired
	private CouponsService couponsService;

	@Autowired
    private OrderGiveCouponToStoreLogService couponToStoreLogService;
	
	@Autowired
	private CouponRecordService couponRecordService;

    @Autowired
    InvoiceInfoService invoiceInfoService;

    @Autowired
    ShippingGroupService shippingGroupService;

    @Autowired
    ProviderShippingGroupService providerShippingGroupService;

    @Override
    public List<Order> getOrder(Map<String, Object> map) {
        return dao.getOrder(map);
    }

    @Override
    public Order selectByPrimaryKey(String id) {

        return dao.selectByPrimaryKey( id );
    }

    @Override
    public Long getOrderCount(Map<String, Object> map) {
        return dao.getOrderCount(map);
    }

    @Override
    public int updateOrderSelective(Order order) {
        int n = dao.updateByPrimaryKeySelective(order);
        if(n>0){
            logger.info("send indexOrderMessage,orderId:{}",order.getId());
            sendIndexMsg(order.getId());
        }
        return n;
    }

    private void sendIndexMsg(final String id) {

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void suspend() {

            }

            @Override
            public void resume() {

            }

            @Override
            public void flush() {

            }

            @Override
            public void beforeCommit(boolean b) {

            }

            @Override
            public void beforeCompletion() {

            }

            @Override
            public void afterCommit() {
                OrderMessage indexMsg = new OrderMessage();
                indexMsg.setOrderId(id);
                orderMessageSender.sendOrderUpdateMsg(indexMsg);

            }

            @Override
            public void afterCompletion(int i) {

            }
        });

    }

    @Override
    public void save(Order order) {
        int n = dao.insert(order);
        if(n>0){
            sendIndexMsg(order.getId());
        }

    }

    @Override
    public Long getOrderPageListCount(Map<String, Object> map) {
        return dao.getOrderPageListCount(map);
    }

    @Override
    public List<Order> getOrderPageList(Map<String, Object> condition) {
        return dao.getOrderPageList(condition);
    }

    @Override
    public void update(Order order){
        int result = dao.updateByPrimaryKey(order);
        if(result <=0){
            throw new RuntimeException("更新失败，版本改变请重新更新。");
        }
        logger.info("send indexOrderMessage update,orderId:{}--》{}",order.getId(),order.getOrderState());
        sendIndexMsg(order.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAndSaveLog(UpdateOrderVO pUpdateOrderVO) {
        update(pUpdateOrderVO.getOrder());
        orderLogService.saveOrderLog(pUpdateOrderVO.getOrder().getId(), pUpdateOrderVO.getPreOrderState(), getSubError(pUpdateOrderVO.getDescription()), pUpdateOrderVO.getOperator());
    }

    protected String getSubError(String errorMsg) {
        if (StringUtils.isEmpty(errorMsg)) {
            return "";
        }
        if (errorMsg.length() > 200) {
            return errorMsg.substring(0, 200);
        }
        return errorMsg;
    }

    @Override
    public boolean updateOrderDes(Map<String, String> map) {
        return dao.updateOrderDes(map) >=1 ;
    }

    @Override
    public List<Order> getOrdersByState(Map<String, Object> condition) {
        return dao.getOrdersByState(condition);
    }

    @Override
    public Boolean updateOrderStateByNewState(List<Order> orders) {
        return dao.updateOrderStateByNewState(orders) != 0;
    }

    @Override
    public List<Order> getSubOrders(String parentId) {
        return dao.getSubOrders(parentId);
    }

    @Override
    public OrderDetailIndex loadOrderDetail(String orderId) {

        Order order = dao.selectByPrimaryKey(orderId);
        if (order == null) {
            return null;
        }
        OrderDetailIndex orderDetailIndex = BeanConvertUtils.convert(order, OrderDetailIndex.class);
        orderDetailIndex.setSubmitTime(order.getSubmitTime() == null ? null : DateUtil.formatDateYMDhms(order.getSubmitTime()));
        orderDetailIndex.setCompletedTime(order.getCompletedTime() == null ? null : DateUtil.formatDateYMDhms(order.getCompletedTime()));
        orderDetailIndex.setBranchCompanyArehouseCode(order.getBranchCompanyArehouse());
        //订单价格信息
        encapOrderPrice(orderDetailIndex, order.getPriceInfo());
        //发票信息
        encapInvoiceInfo(orderDetailIndex);
        //物流信息
        encapShippingGroup(orderDetailIndex, order.getShippingGroup());
        //供应商配送信息
        encapShippingGroupProvider(orderDetailIndex,order.getShippingGroup());
        //商品信息
        List<OrderItemIndex> orderItemToScreenList = orderItemsService.getOrderItemToScreenPoList(order.getId());
        orderDetailIndex.setOrderItemPoList(orderItemToScreenList);
        return orderDetailIndex;
    }

    private void encapShippingGroupProvider(OrderDetailIndex orderDetailIndex, String shippingGroupId) {
        ProviderShippingGroup providerShippingGroup = providerShippingGroupService.selectByShippingGroupId(shippingGroupId);
        if (providerShippingGroup == null) {
            orderDetailIndex.setSpName("雅堂");
            orderDetailIndex.setSpAdrName("雅堂");
            return;
        }
        orderDetailIndex.setSpId(providerShippingGroup.getSpId());
        orderDetailIndex.setSpNo(providerShippingGroup.getSpNo());
        orderDetailIndex.setSpName(providerShippingGroup.getSpName());
        orderDetailIndex.setSpAdrId(providerShippingGroup.getSpAdrId());
        orderDetailIndex.setSpAdrNo(providerShippingGroup.getSpAdrNo());
        orderDetailIndex.setSpAdrName(providerShippingGroup.getSpAdrName());
    }

    private void encapOrderPrice(OrderDetailIndex orderDetailIndex, Long priceInfo) {
        OrderPrice orderPrice = orderPriceService.selectByPrimaryKey(priceInfo);
        if (orderPrice == null) {
            return;
        }
        orderDetailIndex.setAmount(orderPrice.getAmount());
        orderDetailIndex.setRawSubtotal(orderPrice.getRawSubtotal());
        orderDetailIndex.setUserDiscountAmount(orderPrice.getUserDiscountAmount());
        orderDetailIndex.setCouponDiscountAmount(orderPrice.getCouponDiscountAmount());
        orderDetailIndex.setManualAdjustmentTotal(orderPrice.getManualAdjustmentTotal());
        orderDetailIndex.setShipping(orderPrice.getShipping());
        orderDetailIndex.setDiscountAmount(orderPrice.getDiscountAmount());
        orderDetailIndex.setTotal(orderPrice.getTotal());
        double userDiscountAmount = orderPrice.getUserDiscountAmount()==null?0:orderPrice.getUserDiscountAmount();
        double couponDiscountAmount = orderPrice.getCouponDiscountAmount()==null?0:orderPrice.getCouponDiscountAmount();
        double discountAmount = orderPrice.getDiscountAmount()==null?0:orderPrice.getDiscountAmount();
        orderDetailIndex.setDiscountTotalAmount(new BigDecimal(userDiscountAmount).add(new BigDecimal(couponDiscountAmount)).add(new BigDecimal(discountAmount)).doubleValue());
        orderDetailIndex.setActualTotal(new BigDecimal(orderDetailIndex.getTotal()).subtract(new BigDecimal(orderDetailIndex.getDiscountTotalAmount())).doubleValue());
    }

    private void encapInvoiceInfo(OrderDetailIndex orderDetailIndex) {
        UserInvoiceInfo userInvoiceInfo = invoiceInfoService.getInvoiceByProfileId(orderDetailIndex.getFranchiseeId());
        if (userInvoiceInfo != null) {
            orderDetailIndex.setInvoiceType(userInvoiceInfo.getInvoiceType());
            orderDetailIndex.setInvoiceTitle(userInvoiceInfo.getInvoiceTitle());
            orderDetailIndex.setCompanyName(userInvoiceInfo.getCompanyName());
        } else {
            orderDetailIndex.setInvoiceType("none");
            orderDetailIndex.setInvoiceTitle("");
            orderDetailIndex.setCompanyName("");
            logger.info("Invoice Info is null:{}", orderDetailIndex.getFranchiseeId());
        }
    }

    private void encapShippingGroup(OrderDetailIndex orderDetailIndex, String shippingGroupId) {
        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(shippingGroupId);
        orderDetailIndex.setActualShipDate(shippingGroup.getActualShipDate() == null ? null : DateUtil.formatDateYMDhms(shippingGroup.getActualShipDate()));
        orderDetailIndex.setShippingMethod(shippingGroup.getShippingMethod());
        orderDetailIndex.setShipOnDate(shippingGroup.getShipOnDate() == null ? null : DateUtil.formatDateYMDhms(shippingGroup.getShipOnDate()));
        orderDetailIndex.setProvince(shippingGroup.getProvince());
        orderDetailIndex.setCity(shippingGroup.getCity());
        orderDetailIndex.setDistrict(shippingGroup.getDistrict());
        orderDetailIndex.setDetailAddress(shippingGroup.getDetailAddress());
        orderDetailIndex.setConsigneeName(shippingGroup.getConsigneeName());
        orderDetailIndex.setPostcode(shippingGroup.getPostcode());
        orderDetailIndex.setCellphone(shippingGroup.getCellphone());
        orderDetailIndex.setShippingNo(shippingGroup.getShippingNo());
        orderDetailIndex.setEstimatedArrivalDate(shippingGroup.getEstimatedArrivalDate() == null ? null : DateUtil.formatDateYMDhms(shippingGroup.getEstimatedArrivalDate()));
        orderDetailIndex.setDeliveryer(shippingGroup.getDeliveryer());
        orderDetailIndex.setDeliveryerPhone(shippingGroup.getDeliveryerPhone());
        orderDetailIndex.setShippingModes(shippingGroup.getShippingModes());
        orderDetailIndex.setSingedCertImg(shippingGroup.getSingedCertImg());
        orderDetailIndex.setShippingRemarks(shippingGroup.getRemarks());
    }

    /**
	 * 调用会员系统，实现会员等级相应的返券功能。 调用接口获取拒收信息，
	 * 计算出实际确认收货金额【需要考虑订单优惠券使用】调用小超人返券配置信息返券。
	 * 
	 * @param franchiseeStoreId 加盟商门店
     * @param refuseAmount
     * @param priceInfoId 价格信息
	 */
	@Override
	public Response<String> giveCouponToStore(String franchiseeStoreId, double refuseAmount,Long priceInfoId) {
		OrderGiveCouponToStoreLog log = couponToStoreLogService.queryByPriceInfoId(priceInfoId);
		if(log==null){
			logger.error("no discount info found! priceInfoId ->{}",priceInfoId);
			return null;
		}
        double discountNumeric = log.getDiscount();
		OrderGiveCouponToStoreLog orderLog=new OrderGiveCouponToStoreLog();
		Response<String> response = new Response<String>();
		Date creationTime = log.getCreationTime();
		// 判断是否使用过优惠券
		OrderPrice orderPrice = orderPriceService.getOrderPriceForId(priceInfoId);
		if (orderPrice.getDiscountAmount()==0d) {
			// 获取拒收金额
			Double autualAomunt = PricingUtil.sub(orderPrice.getAmount(), refuseAmount);
			// 通过实际金额计算返券
			String[] couponIds = calcGiveCouples(discountNumeric,autualAomunt,creationTime,orderLog);
			
			couponActivityService.grantCoupon(new String[] { franchiseeStoreId },couponIds,null);
			orderLog.setGiveCouponInfo(StringUtils.join(couponIds));
			orderLog.setPriceInfoId(priceInfoId);
			orderLog.setSuccess(true);
			
			response.setResultObject(StringUtils.join(couponIds));
		}else{
			orderLog.setSuccess(false);
		}
		couponToStoreLogService.update(orderLog);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setSuccess(true);
		return response;
	}

	/**
	 * 通过实际金额计算返券列表
	 * @param discountNumeric 加盟商
	 * @param autualAomunt 实际金额
	 * @return
	 */
	private String[] calcGiveCouples(double discountNumeric,Double autualAomunt,Date decisionGiveTime,OrderGiveCouponToStoreLog orderLog) {
		//获取实际返券金额
		Double giveCouponAmount = autualAomunt*discountNumeric/100;
		List<CouponsPo> queryToGiveCoupons = couponsService.queryToGiveCoupons();
		
		if(CollectionUtils.isEmpty(queryToGiveCoupons)){
			return new String[]{};
		}
		
		List<String> couponIds=new ArrayList<String>();
		double actualAmount=0d;
		for(CouponsPo cd:queryToGiveCoupons){
			Date createDate = cd.getCreateDate();
			//在支付时决定赠券时间后新建的券不能够赠送
			if(createDate!=null&&createDate.after(decisionGiveTime)){
				continue;
			}
			while(giveCouponAmount>=cd.getDiscount().doubleValue()){
				giveCouponAmount=giveCouponAmount-cd.getDiscount().doubleValue();
				couponIds.add(cd.getId());
				actualAmount=actualAmount+cd.getDiscount().doubleValue();
			}
		}
		orderLog.setGiveAmount(actualAmount);
		return asArray(couponIds);
	}

	private String[] asArray(List<String> couponIds) {
		if(CollectionUtils.isEmpty(couponIds)){
			return new String[]{};
		}
		String[] cIds=new String[couponIds.size()];
		for(int i=0;i<couponIds.size();i++){
			cIds[i]=couponIds.get(i);
		}
		return cIds;
	}
	/**
	 * 优惠券回滚
	 */
	@Override
	public Response<String>  revertCoupons(String orderId,Long priceInfoId){
		Response<String> response = new Response<String>();
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setSuccess(true);

		OrderPrice orderPrice = orderPriceService.getOrderPriceForId(priceInfoId);
		if (orderPrice==null || orderPrice.getAdjustments() == null || orderPrice.getAdjustments().isEmpty()) {
			return response;
		}
		if(orderPrice.isCouponActivityReverted()){
			logger.info("repeat revert coupons priceInfoId:{}",priceInfoId);
			return response;
		}
		List<PriceAdjustment> adjustments = orderPrice.getAdjustments();
		List<String> couponActivityIds = new ArrayList<String>();
		List<String> couponIds = new ArrayList<String>();
		if(adjustments==null){
			return response;
		}
		if(CollectionUtils.isEmpty(adjustments)){
			return response;
		}
		
		for (int i = 0; i < adjustments.size(); i++) {  
				//如果是优惠券则加入到回滚列表
				PriceAdjustment priceAdjustment = adjustments.get(i);
				if(priceAdjustment.getPricingModelType()==null){
					continue;
				}
				if(priceAdjustment.getPricingModelType()==1&&StringUtils.isNotEmpty(priceAdjustment.getActivityIds())){
					couponActivityIds.addAll(Arrays.asList(priceAdjustment.getActivityIds().split(",")));
					couponIds.add(priceAdjustment.getPricingModel());
				}
		}
		
		if(couponActivityIds.isEmpty()||couponIds.isEmpty()){
			return response;
		}
		couponActivityService.updateStateToActive(couponActivityIds);
		for(String couponId:couponIds){
			couponsService.revertUsedQty(couponId);
			//couponsService.revertGrantQty(couponId);
		}
		couponRecordService.deleteByOrderId(orderId);
		orderPriceService.updateOrderPriceCouponActivityReverted(priceInfoId);
		response.setResultObject(StringUtils.join(couponActivityIds));
		return response;
	}

    @Override
    public String queryProfileIdByOrderId(String orderId) {
        return dao.queryProfileIdByOrderId(orderId);
    }

    @Override
    public List<String> getAllOrderId(Map<String,Object> condition) {
        return dao.getAllOrderId(condition);
    }

    @Override
    public long getAllOrderCount() {
        return dao.getAllOrderCount();
    }

    @Override
    public List<OrderEnhancedPo> getEnhancedOrderPageList(Map<String, Object> map) {
        return dao.getEnhancedOrderPageList(map);
    }

    @Override
    public Long getEnhancedOrderPageCount(Map<String, Object> map) {
        return dao.getEnhancedOrderPageCount(map);
    }

    @Override
    public Order queryThirdOrderByThirdOrderNo(String thirdPartOrderNo) {
        return dao.queryThirdOrderByThirdOrderNo(thirdPartOrderNo);
    }

    @Override
    public List<String> queryReadyToAutoReceiveOrderId(Date time) {
	    return dao.getReadyToAutoReceiveOrderId(time);
    }

    @Override
    public List<Order> getOrdersForCheckInventory() {
        return dao.getOrdersForCheckInventory();
    }

    @Override
    public Order getOrderByReceiptNo(String receiptNo) {
        return dao.getOrderByReceiptNo(receiptNo);
    }

}
