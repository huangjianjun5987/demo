package com.yatang.sc.purchase.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.order.domain.OrderRefundPo;
import com.yatang.sc.order.domain.returned.*;
import com.yatang.sc.order.service.*;
import com.yatang.sc.order.states.OrderReturnRequestTypes;
import com.yatang.sc.order.states.ReturnReasonTypes;
import com.yatang.sc.payment.dto.RefundDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.RefundStatus;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.purchase.dto.OrderProductListDto;
import com.yatang.sc.purchase.dto.ProductAttributeDto;
import com.yatang.sc.purchase.dto.returned.*;
import com.yatang.sc.purchase.dubboservice.ReturnRequestQueryDubboService;

import com.yatang.xc.mbd.pi.es.dto.AttributeValueIndexDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @描述: 退换货dubboQuery服务实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/17 13:51
 * @版本: v1.0
 */
@Service("returnRequestQueryDubboService")
public class ReturnRequestQueryDubboServiceImpl implements ReturnRequestQueryDubboService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReturnRequestService returnRequestService;

    @Autowired
    private ReturnRequestItemService returnRequestItemService;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private RefundDubboService refundDubboService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Override
    public Response<PageResult<ReturnRequestListDto>> queryReturnRequestList(ReturnRequestQueryParamDto param) {
        log.info("App端 queryReturnRequestList=>param:{}", JSON.toJSONString(param));
        ReturnRequestQueryParamPo po = BeanConvertUtils.convert(param,ReturnRequestQueryParamPo.class);
        Response<PageResult<ReturnRequestListDto>> response = new Response<PageResult<ReturnRequestListDto>>();
        PageResult<ReturnRequestListDto> pageResult = new PageResult<ReturnRequestListDto>();
        List<ReturnRequestListDto> list = new ArrayList<ReturnRequestListDto>();
        try{
            int page = po.getPageNum();
            int pageSize = po.getPageSize();
            PageInfo<ReturnRequestListPo> pageInfo = returnRequestService.queryReturnRequestList(po);
            List<ReturnRequestListDto> listDtos = BeanConvertUtils.convertList(pageInfo.getList(),ReturnRequestListDto.class);
            for(ReturnRequestListDto lists:listDtos){
                ReturnRequestListDto requestListDto = new ReturnRequestListDto();
                List<AppQueryReturnRequestItemPo> itemsPosList = returnRequestItemService.queryReturnRequestItemByReturnId(lists.getId());
                List<OrderProductListDto> productList = BeanConvertUtils.convertList(itemsPosList,OrderProductListDto.class);
                for (OrderProductListDto orderProductListDto:productList){
                    ProductIndexDto productDtozsj = productHelper.findProductById(orderProductListDto.getProduct_id());
                    if(!Objects.equals(productDtozsj,null)){
                        orderProductListDto.setIconUrl(productDtozsj.getThumbnailImage());
                        orderProductListDto.setName(productDtozsj.getName());
                        orderProductListDto.setSaleName(productDtozsj.getSaleName());
                        orderProductListDto.setAttributeDtoList(getProductAttributes(productDtozsj.getAttributeValuleList()));
                    }
                }
                requestListDto.setProductList(productList);
                requestListDto.setId(lists.getId());
                requestListDto.setProductCount(productList.size());
                requestListDto.setState(lists.getState());
                requestListDto.setStateDetail(lists.getStateDetail());
                requestListDto.setShipping(0.00);//运费
                requestListDto.setAmount(lists.getAmount());
                requestListDto.setReturnRequestType(lists.getReturnRequestType());
                list.add(requestListDto);
            }
            pageResult.setData(list);
            pageResult.setPageNum(page);
            pageResult.setPageSize(pageSize);
            pageResult.setTotal((long) listDtos.size());
            response.setSuccess(true);
            response.setResultObject(pageResult);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            log.info("App端 queryReturnRequestList end.");
        }catch (Exception e) {
            response.setSuccess(false);
            response.setResultObject(null);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    public List<ProductAttributeDto> getProductAttributes(List<AttributeValueIndexDto> attributeValueDtoList){
        List<ProductAttributeDto> list = new ArrayList<ProductAttributeDto>();
        if(!CollectionUtils.isEmpty(attributeValueDtoList)){
            Map<String,ProductAttributeDto> map = new HashMap<String, ProductAttributeDto>();
            for (AttributeValueIndexDto attributeValueDto:attributeValueDtoList){
                if(!map.containsKey(attributeValueDto.getAttributeName())){
                    ProductAttributeDto productAttributeDto = new ProductAttributeDto();
                    productAttributeDto.setName(attributeValueDto.getAttributeName());
                    productAttributeDto.setValue(attributeValueDto.getValue());
                    map.put(attributeValueDto.getAttributeName(), productAttributeDto);
                }else{
                    ProductAttributeDto productAttributeDto = map.get(attributeValueDto.getAttributeName());
                    productAttributeDto.setValue(productAttributeDto.getValue()+","+attributeValueDto.getValue());
                }
            }
            for (String attributeName: map.keySet()){
                list.add(map.get(attributeName));
            }
        }
        return list;
    }

    @Override
    public Response<ReturnRequestDetailDto> queryReturnRequestDetailById(String id,String userId){
        log.info("App端 queryReturnRequestDetailById=>id:{},userId:{}", id,userId);
        Response<ReturnRequestDetailDto> response = new Response<ReturnRequestDetailDto>();
        try {
            ReturnRequestDetailDto returnRequestDetailDto = new ReturnRequestDetailDto();
            ReturnRequestPo dtoResponse = returnRequestService.selectByPrimaryKey(id);
            log.debug("查询到退货单：{}", JSON.toJSONString(response));
            if (!Objects.equals(dtoResponse,null)) {
                if (!dtoResponse.getProfileId().equals(userId)) {
                    response.setErrorMessage("退货单所属用户信息不同于当前用户");
                    response.setCode(CommonsEnum.RESPONSE_500.getCode());
                    response.setSuccess(false);
                    return response;
                }
                List<AppQueryReturnRequestItemPo> itemsPosList = returnRequestItemService.queryReturnRequestItemByReturnId(id);
                List<ReturnRequestOrderProductListDto> productList = BeanConvertUtils.convertList(itemsPosList, ReturnRequestOrderProductListDto.class);
                for (ReturnRequestOrderProductListDto orderProductListDto : productList) {
                    ProductIndexDto productDto = productHelper.findProductById(orderProductListDto.getProduct_id());
                    if (!Objects.equals(productDto,null)) {
                        orderProductListDto.setIconUrl(productDto.getThumbnailImage());
                        orderProductListDto.setName(productDto.getSaleName());
                        orderProductListDto.setSaleName(productDto.getSaleName());
                        // 最小销售单位
                        orderProductListDto.setMinUnit(productDto.getMinUnit());
                        // 标准包装单位参考值，例如：箱*盒*个'
                        orderProductListDto.setUnitExplanation(productDto.getUnitExplanation());
                        // 标准包装规格参考值，例如：1*2*6',
                        orderProductListDto.setPackingSpecifications(productDto.getPackingSpecifications());
                        orderProductListDto.setFullCaseUnit(productDto.getFullCaseUnit());
                        orderProductListDto.setSellFullCase(productDto.getSellFullCase());
                        orderProductListDto.setAttributeDtoList(getProductAttributes(productDto.getAttributeValuleList()));
                        //判断是否按箱销售
                        if(!Objects.equals(productDto.getSellFullCase(),null) && productDto.getSellFullCase() == 1) {
                            long quantity = orderProductListDto.getNum() / orderProductListDto.getUnitQuantity();
                            double price = orderProductListDto.getUnitQuantity() * orderProductListDto.getPrice();
                            orderProductListDto.setPrice(price);
                            orderProductListDto.setNum(quantity);
                        }
                    }
                }
                returnRequestDetailDto.setProductList(productList);
                returnRequestDetailDto.setId(dtoResponse.getId());
                returnRequestDetailDto.setState(dtoResponse.getState());
                returnRequestDetailDto.setStateDetail(dtoResponse.getStateDetail());
                returnRequestDetailDto.setCreationTime(dtoResponse.getCreationTime());
                returnRequestDetailDto.setAmount(dtoResponse.getAmount());//退货总额
                //returnRequestDetailDto.setTelephone();
                returnRequestDetailDto.setReturnRequestType(OrderReturnRequestTypes.parse(dtoResponse.getReturnRequestType()).getDescription());
                //配送
                returnRequestDetailDto.setPaymentStateDetail("未退款");
                OrderRefundPo orderRefundPo = orderRefundService.queryByReturnId(id);
                if(!Objects.equals(orderRefundPo,null)) {
                    Response<RefundDto> refundResponseDto = refundDubboService.refundById(Long.parseLong(orderRefundPo.getRefundId()));
                    if(refundResponseDto.isSuccess() && !Objects.equals(refundResponseDto.getResultObject(),null)) {
                        RefundDto refundDto = refundResponseDto.getResultObject();
                        if(refundDto.getStatus().equals(RefundStatus.REFUNDED_CONFIRM)) {
                            returnRequestDetailDto.setPaymentStateDetail("已退款");
                            returnRequestDetailDto.setActualRefundAmount(refundDto.getRefundAmount());//实际退换货总额
                        }
                    }
                }
                returnRequestDetailDto.setProductStateDetail(dtoResponse.getProductStateDetail());
                returnRequestDetailDto.setReturnReason(dtoResponse.getReturnReason());
                returnRequestDetailDto.setReturnReasonType(ReturnReasonTypes.returnReasonTypeDesc.get(dtoResponse.getReturnReasonType()));
                returnRequestDetailDto.setRefundAmount(dtoResponse.getRefundAmount());//退款金额
            }
            response.setSuccess(true);
            response.setResultObject(returnRequestDetailDto);
            response.setCode( CommonsEnum.RESPONSE_200.getCode() );
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e) {
            response.setSuccess(false);
            log.error( ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }
}
