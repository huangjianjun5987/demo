package com.yatang.sc.purchase.dubboservice.impl;

import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.states.OrderReturnedStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.purchase.dubboservice.QueryOrderDetailDubboService;
import com.yatang.sc.order.domain.*;
import com.yatang.sc.order.service.*;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.purchase.dto.*;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.busi.common.utils.BeanConvertUtils;
import org.springframework.stereotype.Service;
import com.busi.common.resp.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/10 14:44
 * @版本:v1.0
 */

@Service("queryOrderDetailDubboService")
public class QueryOrderDetailDubboServiceImpl implements QueryOrderDetailDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceSerive;

    @Autowired
    private PaymentGroupService  paymentGroupService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Autowired
    private OrderPriceService orderPriceService;

    @Autowired
    private  OrderItemsService orderItemsService;

    @Autowired
    private CommerceItemService commerceItemService;

    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private ItemPriceAdjService itemPriceAdjService;

    @Autowired
    private PriceAdjustmentService priceAdjustmentService;

    @Autowired
    private OrderPriceAdjService orderPriceAdjService;

    @Autowired
    private ShippingPriceAdjService shippingPriceAdjService;

    @Autowired
    private OrderPaymentsService orderPaymentsService;

    @Autowired
    private ShippingPriceService shippingPriceService;

    @Autowired
    private ReturnRequestService returnRequestService;

    @Override
    public Response<OrderDto> queryOrderDetaiById(String id) {
        log.info("App端 queryOrderDetaiById=>orderId:{}", id);
        Response<OrderDto> response = new Response<OrderDto>();
        try {
            Order order = orderService.selectByPrimaryKey( id );
            //log.info("App端 queryOrderDetaiById=>order:{}",JSON.toJSONString(order) );
            OrderDto orderDto = BeanConvertUtils.convert( order, OrderDto.class );
            if(orderDto != null){
                ReturnRequestPo returnRequestPo = returnRequestService.queryReturnRequestByOrderId(order.getId());
                if(returnRequestPo != null){
                    orderDto.setReturnState(returnRequestPo.getState());
                    orderDto.setReturnStateDesc(OrderReturnedStates.parse(orderDto.getReturnState()).getDescription());
                }else{
                    orderDto.setReturnState((short)0);
                    orderDto.setReturnStateDesc(OrderReturnedStates.parse(orderDto.getReturnState()).getDescription());
                }
                orderDto.setOrderType(OrderTypes.orderTypeDesc.get(orderDto.getOrderType()));
                //发票
                Long invoiceId = order.getInvoiceInfo();
                Invoice invoice = invoiceSerive.getInvoiceForId( invoiceId );
                if (invoice != null) {
                    InvoiceInfoDto invoiceInfoDto = BeanConvertUtils.convert( invoice, InvoiceInfoDto.class );
                    orderDto.setInvoiceInfoDto( invoiceInfoDto );
                }
                //支付方式
                List<OrderPayments> orderPayments = orderPaymentsService.selectByOrderId(id);
                List<PaymentGroupDto> paymentGroupdtos = new ArrayList<PaymentGroupDto>();
                for (OrderPayments orderPayment : orderPayments){
                    PaymentGroup paymentGroup = paymentGroupService.getPaymentGroupForId(orderPayment.getPaymentGroupId());
                    if (paymentGroup != null) {
                        PaymentGroupDto paymentGroupDto = BeanConvertUtils.convert( paymentGroup, PaymentGroupDto.class );
                        paymentGroupdtos.add(paymentGroupDto);
                    }
                }
                orderDto.setPaymentGroupDtos(paymentGroupdtos);

                //配送
                String shippingGroupId = order.getShippingGroup();
                ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey( shippingGroupId );
                if (shippingGroup != null){
                    ShippingPrice shippingPrice = shippingPriceService.selectByPrimaryKey(shippingGroup.getShippingPriceInfo());
                    List<ShippingPriceAdj> shippingPriceAdjs=shippingPriceAdjService.selectByShippingPriceId( shippingGroup.getShippingPriceInfo() );
                    List<PriceAdjustment> shippingPriceAdjustments=new ArrayList<PriceAdjustment>(  );
                    for (ShippingPriceAdj shippingPriceAdj :shippingPriceAdjs){
                        PriceAdjustment priceAdjustment = new PriceAdjustment();
                        priceAdjustment=priceAdjustmentService.selectByPrimaryKey( shippingPriceAdj.getAdjustmentId() );
                        shippingPriceAdjustments.add( priceAdjustment );
                    }
                    if (shippingPrice !=null){
                        shippingGroup.setShippingPriceInfoDto(shippingPrice);
                        shippingGroup.getShippingPriceInfoDto().setAdjustments(shippingPriceAdjustments);
                    }
                    ShippingGroupDto shippingGroupDto = BeanConvertUtils.convert( shippingGroup, ShippingGroupDto.class );
//                    if(ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
//                        shippingGroupDto.setShippingModes("供应商"+ShippingModes.PROVIDER_SHIPPING_MODE.getDesc());
//                    }else if(ShippingModes.UNIFIED_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
//                        shippingGroupDto.setShippingModes("雅堂"+ShippingModes.UNIFIED_SHIPPING_MODE.getDesc());
//                    }
                    orderDto.setShippingGroupDto( shippingGroupDto );
                }

                //价格
                Long priceInfoId = order.getPriceInfo();
                OrderPrice orderPrice = orderPriceService.selectByPrimaryKey( priceInfoId );
                if (orderPrice != null){
                    List<OrderPriceAdj>   orderPriceAdjs = orderPriceAdjService.selectByOrderPriceId( orderPrice.getId() );
                    List<PriceAdjustment> orderPriceAdjustments=new ArrayList<PriceAdjustment>(  );
                    for (OrderPriceAdj orderPriceAdj :orderPriceAdjs){
                        PriceAdjustment priceAdjustment=new PriceAdjustment();
                        priceAdjustment=priceAdjustmentService.selectByPrimaryKey( orderPriceAdj.getAdjustmentId() );
                        orderPriceAdjustments.add( priceAdjustment );
                    }
                    orderPrice.setAdjustments(orderPriceAdjustments  );
                    OrderPriceInfoDto orderPriceInfoDto = BeanConvertUtils.convert( orderPrice, OrderPriceInfoDto.class );
                    orderDto.setPriceInfoDto( orderPriceInfoDto );
                }

                //商品列表
                if (id != null) {
                    List<OrderItems> itemsIds = orderItemsService.getOrderItemsForOrderId( id );
                    //这是找到商品的id了 然后通过商品的id找到商品
                    List<CommerceItem> items = new ArrayList<CommerceItem>();
                    for (OrderItems itemsId : itemsIds) {
                        if (itemsId != null){
                            //通过商品的id 找到商品
                            CommerceItem commerceItem = commerceItemService.getCommerceItemForId( itemsId.getCommerceItemId() );
                            ProductIndexDto productDto = productHelper.findProductById( commerceItem.getProductId() );
                            if (commerceItem !=null && productDto != null){
                                commerceItem.setProductName(productDto.getSaleName());
                                commerceItem.setProductImg(productDto.getThumbnailImage());
                                Map<String, String> property = new HashMap<String, String>();
                                property.put("minUnit",productDto.getMinUnit());  // 最小销售单位
                                property.put("unitExplanation",productDto.getUnitExplanation());  // 标准包装单位参考值，例如：箱*盒*个'
                                property.put("saleName",productDto.getSaleName());  //// 销售名称
                                property.put("packingSpecifications",productDto.getPackingSpecifications());  // 标准包装规格参考值，例如：1*2*6',
                                property.put("fullCaseUnit",productDto.getFullCaseUnit());
                                commerceItem.setProperties(property);

                                ItemPrice itemPrice = itemPriceService.getItemPriceForId( commerceItem.getItemPriceInfo() );
                                //通过商品价格找到对应的调价记录
                                if (itemPrice != null){
                                    List<ItemPriceAdj> adjustmentIds=itemPriceAdjService.selectByItemPriceId(itemPrice.getId()  );
                                    List<PriceAdjustment> priceAdjustments=new ArrayList<PriceAdjustment>(  );
                                    for (ItemPriceAdj itemPriceAdj :adjustmentIds){
                                        PriceAdjustment priceAdjustment=new PriceAdjustment();
                                        priceAdjustment=priceAdjustmentService.selectByPrimaryKey( itemPriceAdj.getAdjustmentId() );
                                        priceAdjustments.add( priceAdjustment );
                                    }
                                    itemPrice.setAdjustments( priceAdjustments );
                                }
                                commerceItem.setItemPrice( itemPrice );
                                items.add( commerceItem );
                            }
                        }

                    }
                    List<CommerceItemDto> commerceItemDtos = BeanConvertUtils.convertList( items, CommerceItemDto.class );
                    orderDto.setItems( commerceItemDtos );
                }
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setSuccess(true);
                response.setResultObject(orderDto);
                response.setCode( CommonsEnum.RESPONSE_200.getCode() );
            }
        }catch (Exception e){
            response.setErrorMessage( CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error( ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Map<String,Object>> queryPaymentTotal(String id) {
        log.info("queryPaymentTotal=>orderId:{}", id);
        Response<Map<String,Object>> mapResponse = new Response<Map<String,Object>>();
        try {
            Order order = orderService.selectByPrimaryKey(id);
            Map<String, Object> map = new HashMap<String, Object>();
            if (order == null) {
                mapResponse.setSuccess(false);
                mapResponse.setCode(CommonsEnum.RESPONSE_10006.getCode());
                mapResponse.setErrorMessage("订单不存在:" + id);
                mapResponse.setResultObject(map);
                return mapResponse;
            }
            //价格
            Long priceInfoId = order.getPriceInfo();
            OrderPrice orderPrice = orderPriceService.selectByPrimaryKey(priceInfoId);
            if (orderPrice != null) {
                map.put("total", orderPrice.getTotal());
            }
            List<OrderItems> itemsIds = orderItemsService.getOrderItemsForOrderId(id);
            if (CollectionUtils.isEmpty(itemsIds)) {
                log.info("订单不存在:{} 无商品信息", id);
                mapResponse.setSuccess(false);
                mapResponse.setCode("5001");
                mapResponse.setErrorMessage("订单不存在:" + id + " 无商品信息");
                mapResponse.setResultObject(map);
                return mapResponse;
            }
            //这是找到商品的id了 然后通过商品的id找到商品
            boolean  b= false;
            for (OrderItems itemsId : itemsIds) {
                if (itemsId != null) {
                    //通过商品的id 找到商品
                    CommerceItem commerceItem = commerceItemService.getCommerceItemForId(itemsId.getCommerceItemId());
                    ProductIndexDto productDto = productHelper.findProductById( commerceItem.getProductId() );
                    if (productDto.getSaleName() != null) {
                        map.put("item", productDto.getSaleName() + "...");
                        b=true;
                        break;
                    }
                }
            }
            if (!b){
                map.put("item", "饼干等...");
            }
            mapResponse.setSuccess(true);
            mapResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
            mapResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            mapResponse.setResultObject(map);
            log.info("queryPaymentTotal=>map:{}", map);
        } catch (Exception e) {
            log.error("queryPaymentTotal：", e);
            mapResponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            mapResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            mapResponse.setResultObject(null);
            mapResponse.setSuccess(false);
            log.error( ExceptionUtils.getFullStackTrace(e));
        }
        return mapResponse;
    }

    @Override
    public Response<String> queryUserIdByOrderId(String id) {
        Response<String> userIdResponse = new Response<String>();
        String userId = null;
        try {
            Order order = orderService.selectByPrimaryKey( id );
            if (order != null){
                userId = order.getProfileId();
                userIdResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                userIdResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
                userIdResponse.setResultObject(userId);
                userIdResponse.setSuccess(true);
            }
        }catch (Exception e){
            userIdResponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            userIdResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            userIdResponse.setResultObject(null);
            userIdResponse.setSuccess(false);
            log.error( ExceptionUtils.getFullStackTrace(e));
        }
        return userIdResponse;
    }


}
