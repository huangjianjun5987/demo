package com.yatang.sc.product;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Objects;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.order.domain.*;
import com.yatang.sc.order.domain.returned.*;
import com.yatang.sc.order.dubboservice.WebReturnRequestDubboService;
import com.yatang.sc.order.service.*;
import com.yatang.sc.order.states.OrderReturnRequestTypes;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.payment.dto.RefundDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.RefundStatus;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.service.ProductHelper;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.InternationalCodeDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @描述: Web退换货DubboService实现类
 * @类名: WebReturnRequestDubboServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/10/23 14:56
 * @版本: v1.0
 */
@Service("webReturnRequestDubboService")
public class WebReturnRequestDubboServiceImpl implements WebReturnRequestDubboService{
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReturnRequestService returnRequestService;

    @Autowired
    private ReturnRequestItemService returnRequestItemService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private RefundDubboService refundDubboService;


    /**
     * 根据条件查询退换货管理列表
     *
     * @param paramDto
     * @return
     */
    @Override
    public Response<PageResult<ReturnRequestListDto>> queryReturnRequestList(ReturnRequestQueryParamDto paramDto) {
        Response response = new Response();
        try {
            log.info("start----queryReturnRequestList---paramDto:{}", JSON.toJSONString(paramDto));
            ReturnRequestQueryParamPo convert = BeanConvertUtils.convert(paramDto,ReturnRequestQueryParamPo.class);
            PageInfo<ReturnRequestListPo> pageInfo = returnRequestService.queryReturnRequestList(convert);
            log.info("db queryReturnRequestList:{}",JSON.toJSONString(pageInfo));
            List<ReturnRequestListDto> returnRequestListDto = BeanConvertUtils.convertList(pageInfo.getList(),ReturnRequestListDto.class);

            for(ReturnRequestListDto list:returnRequestListDto){
                try {
                    Response<BranchCompanyDto> companyRep = organizationService.querySimpleByBranchCompanyId(list.getBranchCompanyId());
                    if (companyRep.isSuccess() && !Objects.equal(companyRep.getResultObject(),null)) {
                        list.setBranchCompanyName(companyRep.getResultObject().getName());
                    } else {
                        log.warn("子公司信息未查询到:{}", list.getBranchCompanyId());
                    }
                    Response<FranchiseeDto> franchiseeDto = organizationService.querySimpleById(list.getFranchiseeId());
                    if (franchiseeDto.isSuccess() && !Objects.equal(franchiseeDto.getResultObject(),null)) {
                        list.setFranchiseeName(franchiseeDto.getResultObject().getName());
                    } else {
                        log.warn("加盟商信息未查询到:{}", list.getFranchiseeId());
                    }
                } catch (Exception ex) {
                    log.error(list.getFranchiseeId() + "查询加盟商信息异常" + list.getBranchCompanyId() + "查询子公司信息异常", ex);
                }

                list.setReturnRequestType(OrderReturnRequestTypes.parse(list.getReturnRequestType()).getDescription());
                if(Objects.equal(OrderTypes.NORMAL_SALE, list.getOrderType())) {
                    list.setPaymentStateDetail("未退款");
                    list.setPaymentState("WTK");
                    OrderRefundPo orderRefundPo = orderRefundService.queryByReturnId(list.getId());
                    if(!Objects.equal(orderRefundPo,null)) {
                        Response<RefundDto> refundResponseDto = refundDubboService.refundById(Long.parseLong(orderRefundPo.getRefundId()));
                        if(refundResponseDto.isSuccess() && !Objects.equal(refundResponseDto.getResultObject(),null)) {
                            RefundDto refundDto = refundResponseDto.getResultObject();
                            if(refundDto.getStatus().equals(RefundStatus.REFUNDED_CONFIRM)){
                                list.setPaymentStateDetail("已退款");
                                list.setPaymentState("YTK");
                            }else if(refundDto.getStatus().equals(RefundStatus.REQUEST)) {
                                list.setPaymentStateDetail("已申请");
                                list.setPaymentState("YSQ");
                            }
                        }
                    }
                }
            }
            log.info("post queryReturnRequestList:{}",JSON.toJSONString(returnRequestListDto));
            PageResult pageResult = new PageResult();
            pageResult.setData(returnRequestListDto);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPageSize(pageInfo.getPageSize());
            pageResult.setPageNum(pageInfo.getPageNum());
            response.setResultObject(pageResult);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            log.info("end----queryReturnRequestList");
        }catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 查询退换货单详情
     * @param id
     * @return
     */
    @Override
    public Response<ReturnRequestDetailDto> returnRequestDetail(String id) {
        log.debug(getClass()+",method:returnRequestDetail,param:id:"+id);
        Response<ReturnRequestDetailDto> response = new Response<ReturnRequestDetailDto>();
        try{
            ReturnRequestPo returnRequestPo = returnRequestService.selectByPrimaryKey(id);
            ReturnRequestDetailDto returnRequestDetailDto = new ReturnRequestDetailDto();
            //查询收货信息
            if(Objects.equal(returnRequestPo,null)){
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }
            //配送
            Order order = orderService.selectByPrimaryKey(returnRequestPo.getOrderId());
            if (Objects.equal(order,null)) {
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }
            String shippingGroupId = returnRequestPo.getShippingGroup();
            ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(shippingGroupId);
            //商品列表
            if (!Objects.equal(returnRequestPo.getOrderId(),null)) {
                //这是找到商品的id了 然后通过商品的id找到商品
                List<ReturnRequestItemPo> returnRequestItemPos = returnRequestItemService.queryReturnRequestItem(id);
                List<ReturnRequestProductDto> items = new ArrayList<ReturnRequestProductDto>();
                for (ReturnRequestItemPo requestItem: returnRequestItemPos) {
                    //查价格
                    ItemPrice itemPrice = itemPriceService.getItemPriceForId(requestItem.getItemPriceInfo());
                    ItemPriceInfoDto itemPriceInfoDto = BeanConvertUtils.convert(itemPrice,ItemPriceInfoDto.class);
                    ReturnRequestProductDto returnRequestProductDto = new ReturnRequestProductDto();
                    ProductIndexDto productDto = productHelper.findProductIndexById(requestItem.getProductId());
                    if(!Objects.equal(productDto,null)){
                        returnRequestProductDto.setProductImg(productDto.getMainImage());
                        returnRequestProductDto.setProductCode(productDto.getProductCode());returnRequestProductDto.setProductName(productDto.getSaleName());
                        returnRequestProductDto.setSecondLevelCategoryName(productDto.getSecondLevelCategoryName());
                        returnRequestProductDto.setThirdLevelCategoryName(productDto.getThirdLevelCategoryName());
                        List<InternationalCodeDto> internationalCodes = BeanConvertUtils.convertList(productDto.getInternationalCodes(),InternationalCodeDto.class);
                        returnRequestProductDto.setInternationalCodes(internationalCodes);
                        returnRequestProductDto.setSellFullCase(productDto.getSellFullCase());
                    }
                    returnRequestProductDto.setId(requestItem.getId());
                    returnRequestProductDto.setProductId(requestItem.getProductId());
                    returnRequestProductDto.setQuantity(requestItem.getReturnQuantity());
                    returnRequestProductDto.setActualReturnQuantity(requestItem.getActualReturnQuantity());
                    returnRequestProductDto.setRawTotalPrice(requestItem.getRawTotalPrice());
                    returnRequestProductDto.setListPrice(itemPriceInfoDto.getListPrice());
                    returnRequestProductDto.setSalePrice(itemPriceInfoDto.getSalePrice());
                    returnRequestProductDto.setShippedQuantity(requestItem.getShippedQuantity());
                    returnRequestProductDto.setUnitQuantity(requestItem.getUnitQuantity());
                    items.add(returnRequestProductDto);
                }
                //退货单商品列表信息
                returnRequestDetailDto.setItems(items);
                //订单商品总数量
                int count = 0;
                for (ReturnRequestProductDto commerceItemDto:items){
                    count+=commerceItemDto.getQuantity();
                }
                returnRequestDetailDto.setCommodityTotal(count);
            }
            Response<BranchCompanyDto> branchCompanyDtoResponse = organizationService.querySimpleByBranchCompanyId(returnRequestPo.getBranchCompanyId());
            if (branchCompanyDtoResponse.isSuccess()){
                if (!Objects.equal(branchCompanyDtoResponse.getResultObject(),null)){
                    returnRequestDetailDto.setBranchCompanyName(branchCompanyDtoResponse.getResultObject().getName());
                }
            }
            Response<FranchiseeDto> franchiseeDto = organizationService.queryFranchiseeById(returnRequestPo.getFranchiseeId());
            if (franchiseeDto.isSuccess() && !Objects.equal(franchiseeDto.getResultObject(),null)) {
                returnRequestDetailDto.setFranchiseeName(franchiseeDto.getResultObject().getName());
            }
            //查询退货单基本信息
            returnRequestDetailDto.setId(returnRequestPo.getId());
            returnRequestDetailDto.setOrderId(returnRequestPo.getOrderId());
            returnRequestDetailDto.setCreationTime(returnRequestPo.getCreationTime());
            returnRequestDetailDto.setStateDetail(returnRequestPo.getStateDetail());
            returnRequestDetailDto.setProductStateDetail(returnRequestPo.getProductStateDetail());
            returnRequestDetailDto.setShippingStateDetail(returnRequestPo.getShippingStateDetail());
            returnRequestDetailDto.setReturnReasonType(returnRequestPo.getReturnReasonType());
            returnRequestDetailDto.setReturnRequestType(OrderReturnRequestTypes.parse(returnRequestPo.getReturnRequestType()).getDescription());
            OrderRefundPo orderRefundPo = orderRefundService.queryByReturnId(id);
            returnRequestDetailDto.setPaymentStateDetail("未退款");
            if(!Objects.equal(orderRefundPo,null)) {
                Response<RefundDto> refundResponseDto = refundDubboService.refundById(Long.parseLong(orderRefundPo.getRefundId()));
                if(refundResponseDto.isSuccess() && !Objects.equal(refundResponseDto.getResultObject(),null)) {
                    RefundDto refundDto = refundResponseDto.getResultObject();
                    if(refundDto.getStatus().equals(RefundStatus.REFUNDED_CONFIRM)){//TKD
                        returnRequestDetailDto.setPaymentStateDetail("已退款");
                        returnRequestDetailDto.setRefundAmount(refundDto.getRefundAmount());//取实退金額
                    }else if(refundDto.getStatus().equals(RefundStatus.REQUEST)) {
                        returnRequestDetailDto.setPaymentStateDetail("已申请");
                    }
                }
            }
            //收货地址
            returnRequestDetailDto.setProvince(shippingGroup.getProvince());
            returnRequestDetailDto.setCity(shippingGroup.getCity());
            returnRequestDetailDto.setDistrict(shippingGroup.getDistrict());
            returnRequestDetailDto.setDetailAddress(shippingGroup.getDetailAddress());
            returnRequestDetailDto.setPostcode(shippingGroup.getPostcode());
            returnRequestDetailDto.setConsigneeName(shippingGroup.getConsigneeName());
            returnRequestDetailDto.setCellphone(shippingGroup.getCellphone());
            //订单总金额
            returnRequestDetailDto.setAmount(returnRequestPo.getAmount());
            returnRequestDetailDto.setReturnReason(returnRequestPo.getReturnReason());
            returnRequestDetailDto.setDescription(returnRequestPo.getDescription());
            response.setSuccess(true);
            response.setResultObject(returnRequestDetailDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 保存退货单备注
     * @param descriptionDto
     * @return
     */
    @Override
    public Response<Boolean> updateDescriptionAndQuantity(ReturnRequestDescriptionDto descriptionDto){
        log.info("start----updateDescriptionAndQuantity---descriptionDto:{}", JSON.toJSONString(descriptionDto));
        Response<Boolean> response = new Response<Boolean>();
        try {
            OrderLog orderLog = new OrderLog();
            ReturnRequestPo returnRequest = returnRequestService.selectByPrimaryKey(descriptionDto.getReturnId());
            List<ReturnQuantityPo> returnQuantityPos = null;
            if (!Objects.equal(returnRequest,null) && !Objects.equal(descriptionDto.getDescription(),null)) {
                returnRequest.setDescription(descriptionDto.getDescription());
                returnRequest.setReturnReason(descriptionDto.getReturnReason());
                returnRequest.setReturnReasonType(descriptionDto.getReturnReasonType());
                orderLog.setDescription("web端退货单备注成功");
            }
            if(!CollectionUtils.isEmpty(descriptionDto.getItems())) {
                //更新实际退货/拒收数量
                returnQuantityPos = BeanConvertUtils.convertList(descriptionDto.getItems(),ReturnQuantityPo.class);
                double total = 0d;
                for(ReturnQuantityPo list: returnQuantityPos) {
                    ReturnRequestItemPo requestItem = returnRequestItemService.queryById(list.getId());
                    if(!Objects.equal(requestItem.getItemPriceInfo(),null) && list.getReturnQuantity() <= requestItem.getShippedQuantity()) {
                        //查价格
                        ItemPrice itemPrice = itemPriceService.getItemPriceForId(requestItem.getItemPriceInfo());
                        ItemPriceInfoDto itemPriceInfoDto = BeanConvertUtils.convert(itemPrice,ItemPriceInfoDto.class);
                        if(!Objects.equal(itemPriceInfoDto,null)) {
                            double price = itemPriceInfoDto.getSalePrice();
                            //(scp_return_request_item)行总价raw_total_price
                            BigDecimal bigDecimal = BigDecimal.valueOf(price*list.getReturnQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
                            list.setRawTotalPrice(bigDecimal.doubleValue());
                            total += bigDecimal.doubleValue();//总价
                        }
                    }else {
                        response.setSuccess(false);
                        response.setResultObject(false);
                        response.setCode(CommonsEnum.RESPONSE_400.getCode());
                        response.setErrorMessage("商品编码为"+requestItem.getProductCode()+"的退货数量不能大于配送数量");
                        return response;
                    }
                }
                BigDecimal bigDecimal = BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
                OrderRefundPricePo orderRefundPrice = returnRequestService.refundPrice(bigDecimal.doubleValue(), returnRequest.getOrderId());
                //(scp_return_request)退款金额refund_amount
                returnRequest.setRefundAmount(orderRefundPrice.getAmount());//退换货单退款总金额
                //(scp_return_request)退货总额amount
                returnRequest.setAmount(total);
                orderLog.setDescription("web端退货/拒收数量修改成功");
            }
            returnRequestService.updateDescriptionAndQuantity(returnRequest,returnQuantityPos);
            response.setSuccess(true);
            response.setResultObject(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());

            orderLog.setOrderId(returnRequest.getId());
            orderLog.setOrderState(returnRequest.getState());
            orderLog.setCreateDate(new Date());
            orderLog.setOperater(descriptionDto.getUserId());
            orderLogService.save(orderLog);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    /**
     * 通过订单ID查询退货单信息
     * @param orderId
     * @return
     */
    @Override
    public Response<ReturnRequestDetailDto> queryReturnRequestByOrderId(String orderId) {
        log.debug(getClass()+",method:queryReturnRequestByOrderId,param:orderId:"+orderId);
        Response response = new Response();
        try{
            ReturnRequestPo returnRequestPo = returnRequestService.queryReturnRequestByOrderId(orderId);
            if(returnRequestPo != null) {
                return returnRequestDetail(returnRequestPo.getId());
            }else {
                response.setSuccess(false);
                response.setResultObject(null);
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
            }
        }catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    /**
     * 根据退货单号查询退款记录条数
     *
     * @param returnOrderId
     * @return
     */
    @Override
    public Response<Integer> selectByReturnOrderId(String returnOrderId) {
        log.info("查询退货单退款记录参数 {}",returnOrderId);
        Response response = new Response();
        try {
            int i = orderRefundService.selectByReturnOrderId(returnOrderId);
            log.info("查询退货单退款记录返回结果 {}",i);
            response.setResultObject(i);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e){
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    /**
     * 插入退款退货单关系记录
     *
     * @param orderRefundDto
     * @return
     */
    @Override
    public Response<Boolean> insert(OrderRefundDto orderRefundDto) {
        log.info("新增退货单退款记录参数 {}", JSON.toJSONString(orderRefundDto));
        Response response = new Response();
        try {
            OrderRefundPo orderRefundPo = BeanConvertUtils.convert(orderRefundDto, OrderRefundPo.class);
            orderRefundService.insert(orderRefundPo);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e){
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }
}
