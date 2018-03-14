package com.yatang.sc.sorder.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Strings;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.staticvalue.Constants;
import com.yatang.sc.fulfillment.dto.OrderReturnOperateMessageDto;
import com.yatang.sc.fulfillment.dubboservice.OrderReturnRequestWriteDubboService;
import com.yatang.sc.operation.util.ImageDomainJoinUrlUtil;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.ChangeRequestListVo;
import com.yatang.sc.operation.vo.ChangeRequestQueryParamVo;
import com.yatang.sc.operation.vo.ReturnRequestDescriptionVo;
import com.yatang.sc.operation.vo.ReturnRequestListVo;
import com.yatang.sc.operation.vo.ReturnRequestQueryParamVo;
import com.yatang.sc.operation.vo.prod.BranchCompanyInfoVo;
import com.yatang.sc.operation.web.BaseAction;
import com.yatang.sc.order.dubboservice.OrderRefundDubboService;
import com.yatang.sc.order.dubboservice.WebOrderDubboService;
import com.yatang.sc.order.dubboservice.WebReturnRequestDubboService;
import com.yatang.sc.payment.dto.request.ApplyRefundRequestDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundReason;
import com.yatang.sc.purchase.dto.OrderManageDetailDto;
import com.yatang.sc.purchase.dto.OrderRefundDto;
import com.yatang.sc.purchase.dto.OrderRefundPriceDto;
import com.yatang.sc.purchase.dto.ReturnRequestDescriptionDto;
import com.yatang.sc.purchase.dto.ReturnRequestDetailDto;
import com.yatang.sc.purchase.dto.ReturnRequestListDto;
import com.yatang.sc.purchase.dto.ReturnRequestProductDto;
import com.yatang.sc.purchase.dto.ReturnRequestQueryParamDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;
import com.yatang.sc.purchase.dubboservice.ReturnRequestWriteDubboService;
import com.yatang.sc.sorder.res.ActionResponse;
import com.yatang.sc.sorder.vo.returnedOrder.OrderReturnOperateMessageVo;
import com.yatang.sc.sorder.vo.returnedOrder.ReturnRequestReceiptVo;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;
import static com.yatang.sc.order.states.OrderReturnRequestTypes.NORMAL_EXCHANGE;
import static com.yatang.sc.order.states.OrderReturnRequestTypes.NORMAL_RETURN;
import static com.yatang.sc.order.states.OrderReturnRequestTypes.REJECT_RETURN;

/**
 * @描述: Web端退货单Action
 * @类名: ReturnRequestAction
 * @作者: kangdong
 * @创建时间: 2017/10/17 14:22
 * @版本: v1.0
 */

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/sc/webReturnRequest")
public class ReturnRequestAction extends BaseAction {

    private final ReturnRequestWriteDubboService writeDubboService;

    private final WebReturnRequestDubboService dubboService;

    private static final Logger log= LoggerFactory.getLogger(ReturnRequestAction.class);

    @Autowired
    private ImageDomainJoinUrlUtil imageDomainJoinUrlUtil;

    private final OrderReturnRequestWriteDubboService orderReturnRequestWriteDubboService;

    private final RefundDubboService refundDubboService;
    private final OrderRefundDubboService orderRefundDubboService;

    @Autowired
    private WebOrderDubboService webOrderDubboService;

    /**
     * 根据条件查询退货单列表
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/queryReturnRequestList", method = RequestMethod.GET)
    public Response<PageResult<ReturnRequestListVo>> queryReturnRequestList(ReturnRequestQueryParamVo param, HttpServletRequest req) {
        LoginInfoVO currentUser = (LoginInfoVO)req.getSession().getAttribute(CURRENT_USER);
        if (null==currentUser){
            log.error("session中用户信息为空");
            return new Response<>();
        }
        ReturnRequestQueryParamDto paramDto = BeanConvertUtils.convert(param, ReturnRequestQueryParamDto.class);
        //获取子公司
        List<String> branchCompanyIds=new ArrayList<>();
        if (Strings.isNullOrEmpty(param.getBranchCompanyId())){
            branchCompanyIds=this.queryUserCompanyIds(currentUser.getUserId());
        }
        List list = new ArrayList();
        list.add(NORMAL_RETURN.getStateValue());//正常退货,ZCTH
        list.add(REJECT_RETURN.getStateValue());//拒收退货,JSTH
        paramDto.setReturnRequestTypes(list);
        paramDto.setBranchCompanyIds(branchCompanyIds);
        Response<PageResult<ReturnRequestListDto>> response = dubboService.queryReturnRequestList(paramDto);
        PageResult<ReturnRequestListDto> pageResult = response.getResultObject();
        List<ReturnRequestListDto> returnRequestListDto = pageResult.getData();
        List<ReturnRequestListVo> requestListDtoList = BeanConvertUtils.convertList(returnRequestListDto, ReturnRequestListVo.class);
        PageResult<ReturnRequestListVo> results = BeanConvertUtils.convert(pageResult, PageResult.class);
        results.setData(requestListDtoList);
        Response convert = BeanConvertUtils.convert(response, Response.class);
        convert.setResultObject(results);
        return convert;
    }

    /**
     * 根据条件查询换货单列表
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/queryChangeRequestList", method = RequestMethod.GET)
    public Response<PageResult<ChangeRequestListVo>> queryChangeRequestList(ChangeRequestQueryParamVo param, HttpServletRequest req) {
        LoginInfoVO currentUser = (LoginInfoVO)req.getSession().getAttribute(CURRENT_USER);
        if (null==currentUser){
            log.error("session中用户信息为空");
            return new Response<>();
        }

        ReturnRequestQueryParamDto paramDto = BeanConvertUtils.convert(param, ReturnRequestQueryParamDto.class);
        //获取子公司
        List<String> branchCompanyIds=new ArrayList<>();
        if (Strings.isNullOrEmpty(param.getBranchCompanyId())){
            branchCompanyIds=this.queryUserCompanyIds(currentUser.getUserId());
        }

        List list = new ArrayList();
        list.add(NORMAL_EXCHANGE.getStateValue());//正常换货,ZCHH
        paramDto.setReturnRequestTypes(list);
        paramDto.setBranchCompanyIds(branchCompanyIds);
        Response<PageResult<ReturnRequestListDto>> response = dubboService.queryReturnRequestList(paramDto);
        PageResult<ReturnRequestListDto> pageResult = response.getResultObject();
        List<ReturnRequestListDto> returnRequestListDto = pageResult.getData();
        List<ChangeRequestListVo> requestListDtoList = BeanConvertUtils.convertList(returnRequestListDto, ChangeRequestListVo.class);
        PageResult<ChangeRequestListVo> results = BeanConvertUtils.convert(pageResult, PageResult.class);
        results.setData(requestListDtoList);
        Response convert = BeanConvertUtils.convert(response, Response.class);
        convert.setResultObject(results);
        return convert;
    }

    /**
     * 退换货单详情
     *
     * @param id
     * @return
     */
    @ParamValid
    @RequestMapping(value = "returnRequestDetail", method = RequestMethod.GET)
    public Response<ReturnRequestDetailDto> returnRequestDetail(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String id) {
        log.debug("/returnRequestDetail:request param,id:" + id);
        Response<ReturnRequestDetailDto> returnRequestDetailDtoResponse = dubboService.returnRequestDetail(id);
        ReturnRequestDetailDto returnRequestDetailDto = returnRequestDetailDtoResponse.getResultObject();
        if (returnRequestDetailDto == null) {
            logger.warn("退货单详情查询为空:{}", id);
            Response response = new Response();
            response.setSuccess(false);
            response.setErrorMessage("退货单详情查询为空");
            return ActionResponse.wrap(response);
        }

        List<ReturnRequestProductDto> returnRequestProduct = returnRequestDetailDto.getItems();
        if (returnRequestProduct != null && returnRequestProduct.size() != 0) {
            for (ReturnRequestProductDto returnRequestProducts : returnRequestProduct) {
                if (returnRequestProducts != null) {
                    returnRequestProducts.setProductImg(returnRequestProducts.getProductImg() == null ? null : imageDomainJoinUrlUtil.getImageUrl(returnRequestProducts.getProductImg()));
                }
            }
        }
        return ActionResponse.wrap(returnRequestDetailDtoResponse);
    }

    /**
     * 保存备注和退货/拒收数量
     * @param descriptionVo
     * @return
     */
    @RequestMapping(value = "returnDescription", method = RequestMethod.POST)
    Response<Boolean> returnDescription(@RequestBody @Validated ReturnRequestDescriptionVo descriptionVo) {
        log.debug("/returnDescription: request param,"+ JSONObject.toJSONString(descriptionVo));
        ReturnRequestDescriptionDto descriptionDto = BeanConvertUtils.convert(descriptionVo,ReturnRequestDescriptionDto.class);
        LoginInfoVO loginInfoVo = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        if(loginInfoVo != null) {
            userId = loginInfoVo.getUserId();
        }
        descriptionDto.setUserId(userId);
        return ActionResponse.wrap(dubboService.updateDescriptionAndQuantity(descriptionDto));
    }

    /**
     * 退货单操作(取消,确认)
     *
     * @return
     */
    @RequestMapping(value = "operateOrderReturnedReceipt", method = RequestMethod.POST)
    public Response<Void> operateOrderReturnedReceipt(@RequestBody @Validated OrderReturnOperateMessageVo operateMessageVo,HttpServletRequest request) {
        log.info("action--createOrderReturnedReceipt>>退货单操作请求操作:{}", JSON.toJSONString(operateMessageVo));
        LoginInfoVO	userInfo = (LoginInfoVO)request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        log.info("action--createOrderReturnedReceipt>>退货单操作请求操作单号:{},获取当前登录用户信息 userInfo{}",operateMessageVo.getReturnId(),JSON.toJSON(userInfo));
        if (userInfo==null){
            Response response = new Response();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        //vo2dto
        OrderReturnOperateMessageDto operateMessageDto = BeanConvertUtils.convert(operateMessageVo, OrderReturnOperateMessageDto.class);
        operateMessageDto.setUserId(userInfo.getUserId());//设置当前用户id
        return orderReturnRequestWriteDubboService.operateOrderReturnedReceipt(operateMessageDto);

    }

    /**
     * 退款（加盟商+仓库收货已完成才执行退款操作）
     * @param returnId
     * @return
     */
    @RequestMapping(value = "insertRefund",method = RequestMethod.GET)
    public Response<Void> insertRefund(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String returnId,HttpSession session) {
        log.info("action--insertRefund>>退款请求操作:{}", returnId);
        Response response = new Response();
        Response<ReturnRequestDetailDto> returnRequestDetailDtoResponse = dubboService.returnRequestDetail(returnId);
        ReturnRequestDetailDto returnRequestDetailDto = returnRequestDetailDtoResponse.getResultObject();
        log.info("查询拒单单据信息 returnRequestDetailDto ",JSON.toJSONString(returnRequestDetailDto));
        if (returnRequestDetailDto == null) {
            response.setCode(CommonsEnum.RESPONSE_20117.getCode());
            response.setSuccess(false);
            response.setErrorMessage("退货单详情查询为空");
            return response;
        }else {
            //判断是否已经有申请退款记录
            Response<Integer> integerResponse = dubboService.selectByReturnOrderId(returnRequestDetailDto.getId());
            if (!integerResponse.isSuccess()|| null==integerResponse.getResultObject()){
                response.setCode(CommonsEnum.RESPONSE_20117.getCode());
                response.setSuccess(false);
                response.setErrorMessage("订单支付记录为空");
                return response;
            }
            Integer integer = integerResponse.getResultObject();
                if (integer>0){//已经有拒收退款记录
                    response.setCode(CommonsEnum.RESPONSE_20127.getCode());
                    response.setSuccess(false);
                    response.setErrorMessage(CommonsEnum.RESPONSE_20127.getName());
                    return response;
                }
            Response<OrderRefundPriceDto> orderRefundPriceDtoResponse = orderRefundDubboService.refundPrice(returnRequestDetailDto.getAmount(), returnRequestDetailDto.getOrderId());
            log.info("计算折算优惠卷之后的实际退货金额 orderRefundPriceDtoResponse {}",orderRefundPriceDtoResponse);
            if (!orderRefundPriceDtoResponse.isSuccess()||null==orderRefundPriceDtoResponse.getResultObject()){
                Response convert = BeanConvertUtils.convert(orderRefundPriceDtoResponse, Response.class);
                return convert;
            }
            OrderRefundPriceDto resultObject = orderRefundPriceDtoResponse.getResultObject();
            ApplyRefundRequestDto applyRefundRequestDto = new ApplyRefundRequestDto();
            String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            applyRefundRequestDto.setNonceStr(uuid);//生成32位随机数
            applyRefundRequestDto.setRefundAmount(resultObject.getAmount());//设置退款金额
            applyRefundRequestDto.setPayNo(Constants.RUNFUND_PAYNO);//设置退款交易流水号
            applyRefundRequestDto.setPayTradeNo(resultObject.getPayTradeNo());//设置原订单交易流水号
            applyRefundRequestDto.setPayType(PayType.parse(resultObject.getPaytype()));//设置支付渠道
            applyRefundRequestDto.setOrderNo(returnRequestDetailDto.getOrderId());//设置退款id
            applyRefundRequestDto.setTotalAmount(resultObject.getTotalAmount());//设置原订单实际交易总额
            // 获取当前用户信息
            LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
            log.info("获取当前用户信息 LoginInfoVO: {}",JSON.toJSONString(attribute));
            if (attribute == null || attribute.getUserId() == null) {
                response.setCode(com.yatang.sc.facade.common.CommonsEnum.RESPONSE_401.getCode());
                response.setSuccess(false);
                response.setErrorMessage(com.yatang.sc.facade.common.CommonsEnum.RESPONSE_401.getName());
                return response;
            }
            applyRefundRequestDto.setRefundUserId(attribute.getUserId());//设置退款操作人id
            applyRefundRequestDto.setRefundUserName(attribute.getLoginName());//设置退款操作人名称
            applyRefundRequestDto.setRefundReason(RefundReason.REJECTION_REFUND);//设置退款原因
            log.info("新增退款单参数 addRefundRecordDto {}",JSON.toJSONString(applyRefundRequestDto));
            Response<Long> refund = refundDubboService.applyRefund(applyRefundRequestDto);
            if (null !=refund.getResultObject()||refund.isSuccess()){
                OrderRefundDto orderRefundDto = new OrderRefundDto();
                orderRefundDto.setRefundId(String.valueOf(refund.getResultObject()));
                orderRefundDto.setReturnOrderId(returnRequestDetailDto.getId());
                log.info("新增拒收单退款关联记录信息 {}",JSON.toJSONString(orderRefundDto));
                Response<Boolean> insert = dubboService.insert(orderRefundDto);
                log.info("插入拒收退款记录返回结果 {}",insert);
                return BeanConvertUtils.convert(insert,Response.class);
            }
            return BeanConvertUtils.convert(refund,Response.class);
        }

    }

    /**
     * 新增退换货（拒收接口）
     * @param requestReceiptVo
     */

    @RequestMapping(value = "/insertReturn", method = RequestMethod.POST)
    public Response<String> insert(@RequestBody @Validated(GroupOne.class) ReturnRequestReceiptVo requestReceiptVo){

        log.info( "action--createRejectOrderReturnedReceipt>>新增(拒收)退换货单:{}", JSON.toJSONString( requestReceiptVo ) );
        //vo2dto
        ReturnRequestReceiptDto receiptDto = BeanConvertUtils.convert( requestReceiptVo, ReturnRequestReceiptDto.class );
        ReturnRequestDto returnRequest = receiptDto.getReturnRequest();
        Response<OrderManageDetailDto>  orderManageDetailDtoResponse = webOrderDubboService.queryOrderDeatil(returnRequest.getOrderId());
        OrderManageDetailDto orderManageDetailDto = orderManageDetailDtoResponse.getResultObject();
        if (orderManageDetailDto == null) {
            logger.warn("订单详情查询为空:{}", receiptDto.getReturnRequest().getOrderId());
            Response response = new Response();
            response.setSuccess(false);
            response.setErrorMessage("订单详情查询为空");
            return ActionResponse.wrap(response);
        }
        receiptDto.setSource(0);//后台生成退换货标识字段
        if ("C".equals(orderManageDetailDto.getOrderState())){//订单已完成只能进行退货流程
            returnRequest.setReturnRequestType("ZCTH");//正常退货
            return writeDubboService.createOrderReturnedReceipt( receiptDto );
        }else {//订单未完成走拒收流程
            return writeDubboService.createRejectOrderReturnedReceipt( receiptDto );
        }
    }

    /**
     * 获取子公司
     * @param currentUser
     * @param paramDto
     * @return
     */
    private ReturnRequestQueryParamDto getBranchCompanyIds(LoginInfoVO currentUser, ReturnRequestQueryParamDto paramDto) {
        List<BranchCompanyInfoVo> branchCompanyInfoVos = queryUserCompanyInfosByParam(currentUser.getUserId(),null,null);
        if(CollectionUtils.isNotEmpty(branchCompanyInfoVos)) {
            List<String> list = new ArrayList<>();
            for(BranchCompanyInfoVo vos:branchCompanyInfoVos) {
                list.add(vos.getId());
            }
            paramDto.setBranchCompanyIds(list);
        }else{
            if(Objects.equals(currentUser.getCompanyType(),0)){
                if(!StringUtils.isEmpty(currentUser.getCompanyId())){
                    paramDto.setBranchCompanyId(currentUser.getCompanyId());
                }
            }
        }
        return paramDto;
    }
}
