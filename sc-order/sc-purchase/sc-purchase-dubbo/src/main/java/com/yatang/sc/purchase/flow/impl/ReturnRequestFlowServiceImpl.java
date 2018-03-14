package com.yatang.sc.purchase.flow.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.DateUtil;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.ItemPrice;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.domain.returned.OrderRefundPricePo;
import com.yatang.sc.order.domain.returned.ReturnRequestItemPo;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.domain.returned.ReturnRequestReceiptPo;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.ReturnRequestService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.OrderReturnRequestTypes;
import com.yatang.sc.order.states.OrderReturnedItemStates;
import com.yatang.sc.order.states.OrderReturnedProductSates;
import com.yatang.sc.order.states.OrderReturnedStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.ReturnedOrderConstants;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.purchase.dto.returned.ReturnRequestDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestItemDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;
import com.yatang.sc.purchase.flow.ReturnRequestFlowService;
import com.yatang.sc.purchase.service.PurchaseHelper;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @描述: 退换货的flow服务实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/18 13:22
 * @版本: v1.0
 */
@Service
public class ReturnRequestFlowServiceImpl implements ReturnRequestFlowService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ReturnRequestService returnRequestService;


    @Autowired
    private OrderService orderService;

    @Autowired
    private ShippingGroupService shippingGroupService;


    @Autowired
    private PurchaseHelper mPurchaseHelper;

    @Autowired
    private CommerceItemService commerceItemService;


    @Autowired
    private ProductHelper productHelper;



    @Override
    public Response<ReturnRequestReceiptPo> addOrderReturnReceiptDetail(ReturnRequestReceiptDto receiptDto) {

        Response<ReturnRequestReceiptPo> response = new Response<>();
        logger.info("flow---addOrderReturnReceiptDetail>>填充退换货单参数 start.");

        //1.调用检测是否已经有退换货单存在(退货中和已完成的)== 目前只支持一次退货
        ReturnRequestDto returnRequest = receiptDto.getReturnRequest();
        String orderId = returnRequest.getOrderId();

        Order order = orderService.selectByPrimaryKey(orderId);
        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());

        if (ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())) {
            logger.info("订单为直配订单，不允许退换货操作：{}", order.getId());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_20104.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_20104.getName() + " 供应商直送订单不允许该操作");
            return response;
        }

        Integer receiptCount = returnRequestService.getReturnReceiptCountByOrderId(orderId);
        if (receiptCount > 0) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_20104.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_20104.getName() + " 已经执行过有效的退换货操作");
            return response;
        }
        String returnRequestType = returnRequest.getReturnRequestType();
        OrderReturnRequestTypes orderReturnRequestType = OrderReturnRequestTypes.parse(returnRequestType);//获取退换货单类型

        if (0 != receiptDto.getSource()) {//退货单来源不是管理后台（0：为后台）
            //2.校验退换货单类型
            Response<Void> checkResponse = checkOrderReturnedReceipt(order);
            if (!checkResponse.isSuccess()) {
                return BeanConvertUtils.convert(checkResponse, Response.class);
            }
        }
        Integer receiptNum = returnRequestService.getReturnReceiptCountByOrderIdAndReturnRequestType(orderId, returnRequestType);
        receiptNum++;
        switch (OrderReturnRequestTypes.parse(returnRequestType)) {//设置退换货单主键
            case NORMAL_EXCHANGE://换货

                returnRequest.setId(orderId + "H" + String.format("%02d", receiptNum));
                //设置商品状态为待取货
                returnRequest.setProductState(OrderReturnedProductSates.UN_PICKED.getStateValue());
                returnRequest.setProductStateDetail(OrderReturnedProductSates.UN_PICKED.getDescription());
                //设置订单状态为待确认
                returnRequest.setState(OrderReturnedStates.UNCONFIRMED.getStateValue());
                returnRequest.setStateDetail(OrderReturnedStates.UNCONFIRMED.getDescription());
                break;
            case NORMAL_RETURN://正常退货//适用范围:直营店
            case REJECT_RETURN://拒收退货//加盟商和直营店

                returnRequest.setId(orderId + "T" + String.format("%02d", receiptNum));
                //设置收货状态为待收货
                returnRequest.setShippingState(OrderReturnedItemStates.UN_COMPLETE.getStateValue());
                returnRequest.setShippingStateDetail(OrderReturnedItemStates.UN_COMPLETE.getDescription());
                //设置订单状态为待确认
                returnRequest.setState(OrderReturnedStates.UNCONFIRMED.getStateValue());
                returnRequest.setStateDetail(OrderReturnedStates.UNCONFIRMED.getDescription());
                break;

            default:
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20104.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20104.getName() + " 当前请求类型: " + OrderReturnRequestTypes.parse(returnRequestType).getDescription());
                return response;

        }


        //3.拼接退换货单信息
        //退换货单主体
        returnRequest.setBranchCompanyArehouse(order.getBranchCompanyArehouse());
        returnRequest.setFranchiseeStoreId(order.getFranchiseeStoreId());
        returnRequest.setBranchCompanyId(order.getBranchCompanyId());
        returnRequest.setProfileId(order.getProfileId());
        returnRequest.setFranchiseeId(order.getFranchiseeId());

        //退货单商品信息
        List<ReturnRequestItemDto> requestItems = receiptDto.getRequestItems();
        List<CommerceItem> items = commerceItemService.getCommerceItemForOrderId(orderId);
        double amount = 0.0;//退换货单总金额
        for (CommerceItem commerceItem : items) {
            for (ReturnRequestItemDto requestItem : requestItems) {
                if (commerceItem.getProductId().equals(requestItem.getProductId())) {
                    //校验退货数量小于等于(签收数量-已经退货数量）

                    Long quantity = 0l;
                    //根据正常退换货或者拒签
                    if (OrderReturnRequestTypes.NORMAL_RETURN.getStateValue().equals(orderReturnRequestType.getStateValue()) || OrderReturnRequestTypes.NORMAL_EXCHANGE.getStateValue().equals(orderReturnRequestType.getStateValue())) {
                        quantity = commerceItem.getCompletedQuantity();//订单实际签收数量
                    } else if (OrderReturnRequestTypes.REJECT_RETURN.getStateValue().equals(orderReturnRequestType.getStateValue())) {//拒签退货
                        quantity = commerceItem.getQuantity();

                    }
                    Long orderReturnQuantity = requestItem.getReturnQuantity() == null ? 0 : requestItem.getReturnQuantity();//将要退货的数量
                    if (receiptDto.getSource() != 0) {//退货单来源不是管理后台(0:为后台)
                        if (!Objects.equals(commerceItem.getSellFullCase(), null) && Objects.equals(commerceItem.getSellFullCase(), 1)) {
                            orderReturnQuantity = orderReturnQuantity * commerceItem.getUnitQuantity();
                            requestItem.setReturnQuantity(orderReturnQuantity);
                        }
                    }

                    Long alreadyReturnedQuantity = commerceItem.getReturnQuantity();//已经退货数量
                    if (orderReturnQuantity <= (quantity - alreadyReturnedQuantity)) {
                        ProductIndexDto productDto = productHelper.findProductById(commerceItem.getProductId());
                        requestItem.setCatalogId(commerceItem.getCatalogId());
                        requestItem.setQuantity(commerceItem.getQuantity());
                        requestItem.setShippedQuantity(commerceItem.getShippedQuantity());
                        requestItem.setCompletedQuantity(commerceItem.getCompletedQuantity());
                        requestItem.setProductCode(productDto.getProductCode());
                        requestItem.setProductName(productDto.getSaleName());
                        requestItem.setProductId(commerceItem.getProductId());
                        requestItem.setSaleQuantity(commerceItem.getSaleQuantity());
                        requestItem.setUnitQuantity(commerceItem.getUnitQuantity());
                        requestItem.setItemPriceInfo(commerceItem.getItemPriceInfo());

                        //价格
                        ItemPrice itemPrice = commerceItem.getItemPrice();//获取销售价格
                        Double salePrice = itemPrice.getSalePrice();
                        BigDecimal bigDecimal = new BigDecimal(salePrice * orderReturnQuantity).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
                        requestItem.setRawTotalPrice(bigDecimal.doubleValue());//设置单品销售价格
                        amount += bigDecimal.doubleValue();
                    } else {//校验失败
                        logger.error("退换货校验:订单编号:{}中的商品id:[{}]的退换货数量:{}大于订单的签收数量:{}减去已经退货数量:{}", receiptDto.getReturnRequest().getOrderId(), commerceItem.getProductId(), orderReturnQuantity, quantity, alreadyReturnedQuantity);
                        response.setCode(CommonsEnum.RESPONSE_20103.getCode());
                        response.setErrorMessage("订单id为" + receiptDto.getReturnRequest().getOrderId() + CommonsEnum.RESPONSE_20103.getName());
                        response.setSuccess(false);
                        return response;
                    }
                }
            }
        }
        returnRequest.setAmount(amount);//退换货单总金额

        OrderRefundPricePo orderRefundPrice = returnRequestService.refundPrice(amount, orderId);
        returnRequest.setRefundAmount(orderRefundPrice.getAmount());//退换货单退款总金额

        //记录订单配送信息
        mPurchaseHelper.saveShippingGroup(shippingGroup);
        returnRequest.setShippingGroup(shippingGroup.getId());
        returnRequest.setDescription(shippingGroup.getShippingMethod());//获取快递编码
        //转换
        ReturnRequestReceiptPo receiptPo = new ReturnRequestReceiptPo();
        ReturnRequestPo requestPo = BeanConvertUtils.convert(receiptDto.getReturnRequest(), ReturnRequestPo.class);
        receiptPo.setReturnRequest(requestPo);
        List<ReturnRequestItemPo> returnRequestItemPos = BeanConvertUtils.convertList(requestItems, ReturnRequestItemPo.class);
        receiptPo.setRequestItems(returnRequestItemPos);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        response.setResultObject(receiptPo);
        response.setSuccess(true);
        return response;
    }


    /**
     * 校验退换货单 能否执行退货操作
     *
     * @param order
     * @return
     */
    private Response<Void> checkOrderReturnedReceipt(Order order) {
        String orderId = order.getId();
        Response<Void> response = new Response<>();
        if (null == order) {
            logger.error("flow--addOrderReturnReceiptDetail>>订单号【{}】无法查询到订单信息", orderId);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_20101.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_20101.getName());
            return response;
        }
        //订单类型判断
        if ((!OrderTypes.NORMAL_SALE.equals(order.getOrderType()) && (!OrderTypes.DIRECT_STORE.equals(order.getOrderType())))) {//或者直营店,正常商品才能执行退换货操作
            logger.error("flow--addOrderReturnReceiptDetail>>订单号【{}】的订单类型为:{},不能执行退换货操作", orderId, order.getOrderType());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_20118.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_20118.getName());
            return response;
        }
        response.setSuccess(true);
        return response;
    }

    /**
     * 根据退换货单类型进行校验
     *
     * @param order
     * @param orderReturnRequestType
     * @param returnRequestType
     * @param storeType
     * @return
     */
    private Response<Void> checkReturnRequestType(Order order, OrderReturnRequestTypes orderReturnRequestType, String returnRequestType, String storeType) {
        Response<Void> response = new Response<>();
        String orderId = order.getId();

        //正常退换货判定
        if (OrderReturnRequestTypes.NORMAL_RETURN.getStateValue().equals(orderReturnRequestType.getStateValue()) || OrderReturnRequestTypes.NORMAL_EXCHANGE.getStateValue().equals(orderReturnRequestType.getStateValue())) {
            if (!ShippingStates.COMPLETED.equals(order.getShippingState())) {//判定订单是否已完成
                logger.error("flow--addOrderReturnReceiptDetail>>订单号【{}】处于:{}状态,不能执行退换货操作", orderId, order.getShippingState());
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20114.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20114.getName());
                return response;
            }


            if (OrderTypes.NORMAL_SALE.equals(storeType)) {//正常退货,加盟商判定
                logger.error("flow--addOrderReturnReceiptDetail>>订单号为【{}】的订单属于不属于直营店正常退货类型,门店类型:{}", orderId, storeType);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20121.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20121.getName());
                return response;
            }
            //订单时间判断
            Date completedTime = order.getCompletedTime();//订单完成时间
            if (null == completedTime) {
                logger.error("flow--addOrderReturnReceiptDetail>>订单号【{}】的完成时间为空,不能执行退换货操作", orderId);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20119.getCode());
                response.setErrorMessage("订单没有签收不能执行退换货操作");
                return response;
            }
            //订单已完成<=7天的可以退货判定
            boolean deadLine = DateUtil.isDeadLine(completedTime, ReturnedOrderConstants.VALID_DAYS);//判定
            if (!deadLine) {//超过截止时间
                logger.error("flow--addOrderReturnReceiptDetail>>订单号【{}】的完成时间超过7天,不能执行退换货操作", orderId);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20120.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20120.getName());
                return response;
            }
        } else {//拒收退货
            if (!ShippingStates.PENDING_RECEIVE.equals(order.getShippingState())) {//待收货
                logger.error("flow--addOrderReturnReceiptDetail>>订单号【{}】不处于待收货状态,不能执行换货操作", orderId, order.getShippingState());
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20122.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20122.getName());
                return response;
            }
        }
        response.setSuccess(true);
        return response;
    }
    

}
