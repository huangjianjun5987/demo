package com.yatang.sc.purchase.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;

import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.order.domain.*;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.service.*;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.OrderClassificationDto;

import com.yatang.sc.purchase.dto.OrderProductListDto;
import com.yatang.sc.purchase.dto.OrderSummaryDto;
import com.yatang.sc.purchase.dto.ProductAttributeDto;
import com.yatang.sc.purchase.dubboservice.QueryOrderDubboService;
import com.yatang.sc.purchase.dubboservice.util.MyBeanUtils;
import com.yatang.xc.mbd.pi.es.dto.AttributeValueIndexDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/7/10.
 */

@Service("queryOrderDubboService")
public class QueryOrderDubboServiceImpl implements QueryOrderDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderPriceService orderPriceService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private ReturnRequestService returnRequestService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Override
    public Response<PageResult<OrderSummaryDto>> queryOrder(OrderClassificationDto orderClassificationDto) {
        log.info("App端 queryOrder=>orderClassificationDto:{}", JSON.toJSONString(orderClassificationDto));
        Response<PageResult<OrderSummaryDto>> response = new Response<PageResult<OrderSummaryDto>>();
        PageResult<OrderSummaryDto> result = new PageResult<OrderSummaryDto>();
        List<OrderSummaryDto> orderListDtoList = new ArrayList<OrderSummaryDto>();
        Map<String, Object> condition = MyBeanUtils.beanToMap(orderClassificationDto);
        try {
            int page = Integer.valueOf(orderClassificationDto.getPage());
            int pageSize = Integer.valueOf(orderClassificationDto.getPageSize());
            condition.put("start",(page-1)*pageSize);
            condition.put("end",pageSize);
            long total = orderService.getOrderCount(condition);
            List<Order> list = orderService.getOrder(condition);
            for(int i=0;i<list.size();i++){
                OrderSummaryDto orderListDto = new OrderSummaryDto();
                Order order = list.get(i);
                orderListDto.setId(order.getId());
                orderListDto.setProfileId(order.getProfileId());
                orderListDto.setState(order.getState());
                orderListDto.setPaymentState(order.getPaymentState());
                orderListDto.setPaymentStateDetail(PaymentStates.paymentStateDesc.get(order.getPaymentState()));
                orderListDto.setCreationTime(order.getCreationTime());
                orderListDto.setOrderType(order.getOrderType());
                ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
                if(shippingGroup != null){
                    orderListDto.setShippingModes(shippingGroup.getShippingModes());
//                    if(ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
//                        orderListDto.setShippingModes("供应商"+ShippingModes.PROVIDER_SHIPPING_MODE.getDesc());
//                    }else if(ShippingModes.UNIFIED_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
//                        orderListDto.setShippingModes("雅堂"+ShippingModes.UNIFIED_SHIPPING_MODE.getDesc());
//                    }
                }
                ReturnRequestPo returnRequestPo = returnRequestService.queryReturnRequestByOrderId(order.getId());
                if(returnRequestPo != null){
                    orderListDto.setReturnState(returnRequestPo.getState());
                }else{
                    orderListDto.setReturnState((short)0);
                }
                OrderPrice orderPrice = orderPriceService.getOrderPriceForId(order.getPriceInfo());
                if(orderPrice !=null){
                    orderListDto.setPost(orderPrice.getShipping());
                    orderListDto.setTotalPrice(orderPrice.getTotal());
                }
                List<QuerOrderItemsPo> itemsPosList = orderItemsService.queryOrderItemsByOrderId(order.getId());
                List<OrderProductListDto> productList = BeanConvertUtils.convertList(itemsPosList,OrderProductListDto.class);
                for (OrderProductListDto orderProductListDto:productList){
                    ProductIndexDto productDtozsj = productHelper.findProductById(orderProductListDto.getProduct_id());
                    if(productDtozsj != null){
                        orderProductListDto.setIconUrl(productDtozsj.getThumbnailImage());
                        orderProductListDto.setName(productDtozsj.getName());
                        orderProductListDto.setSaleName(productDtozsj.getSaleName());
                        orderProductListDto.setAttributeDtoList(getProductAttributes(productDtozsj.getAttributeValuleList()));
                    }
                }

                orderListDto.setProductTotal(productList.size());
                orderListDto.setProductList(productList);
                orderListDtoList.add(orderListDto);
            }
            result.setData(orderListDtoList);
            result.setPageNum(page);
            result.setPageSize(pageSize);
            result.setTotal(total);
            result.setData(orderListDtoList);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(result);
            log.info("App端 queryOrder end.");
        }catch (Exception e){
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setResultObject(null);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }

        return response;
    }

    public List<ProductAttributeDto> getProductAttributes( List<AttributeValueIndexDto> attributeValueDtoList){

        List<ProductAttributeDto> list = new ArrayList<ProductAttributeDto>();
        if(!CollectionUtils.isEmpty(attributeValueDtoList)){
            Map<String,ProductAttributeDto> map = new HashMap<String, ProductAttributeDto>();
            for (AttributeValueIndexDto attributeValueDto:attributeValueDtoList){
                if(!map.containsKey(attributeValueDto.getAttributeName())){
                    ProductAttributeDto productAttributeDto = new ProductAttributeDto();
                    productAttributeDto.setName(attributeValueDto.getAttributeName());
                    productAttributeDto.setValue(attributeValueDto.getValue());
                }else{
                    ProductAttributeDto productAttributeDto = map.get(attributeValueDto.getAttributeName());
                    productAttributeDto.setValue(productAttributeDto.getValue()+","+attributeValueDto.getValue());
                }
            }
            for (String  attributeName : map.keySet()){
                list.add(map.get(attributeName));
            }
        }

        return list;
    }

}
