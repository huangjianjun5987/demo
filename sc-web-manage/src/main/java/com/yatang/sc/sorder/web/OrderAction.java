package com.yatang.sc.sorder.web;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yatang.sc.fulfillment.dto.OrderReceiveDto;
import com.yatang.sc.fulfillment.dubboservice.OrderFulfillerDubboService;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.sorder.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Strings;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.CancelOrderRequestDto;
import com.yatang.sc.dto.DeliverInfoDto;
import com.yatang.sc.dto.ShippingMethodDto;
import com.yatang.sc.dto.WebShippingGroupDto;
import com.yatang.sc.operation.util.BeanConvertExpandUtils;
import com.yatang.sc.operation.util.ExcelUtil;
import com.yatang.sc.operation.util.ImageDomainJoinUrlUtil;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.util.excel.ExcelUtil2;
import com.yatang.sc.operation.web.BaseAction;
import com.yatang.sc.order.dubboservice.CancelOrderDubboService;
import com.yatang.sc.order.dubboservice.ShippingMethodDubboService;
import com.yatang.sc.order.dubboservice.WebOrderDubboService;
import com.yatang.sc.payment.dto.request.AuditRefundRequestDto;
import com.yatang.sc.payment.dto.request.RefundConfirmDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.purchase.dubboservice.AddPaymentInfoDubboService;
import com.yatang.sc.sorder.res.ActionResponse;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
@RestController
@RequestMapping("/sc/sorder")
public class OrderAction extends BaseAction{
    @Autowired
    private WebOrderDubboService webOrderDubboService;

    @Autowired
    private CancelOrderDubboService mCancelOrderDubboService;

    @Autowired
    private RefundDubboService mRefundDubboService;

    @Autowired
    private ShippingMethodDubboService shippingMethodDubboService;

    @Autowired
    private OrganizationSCService organizationSCService  ;

    @Autowired
    private ImageDomainJoinUrlUtil imageDomainJoinUrlUtil;

    @Autowired
    private AddPaymentInfoDubboService addPaymentInfoDubboService;

    @Autowired
    private OrderFulfillerDubboService orderFulfillerDubboService;


    @RequestMapping(value="queryOrder",method= RequestMethod.GET)
    public Response<PageResult<WebQueryOrderDto>> queryOrder(WebQueryOrderConditionVo condition) throws Exception {
        List<String> resultCompanyIds = new ArrayList<>();
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (null==currentUser){
            logger.error("从session中获取用户信息失败");
            return this.getFailResponse();
        }
       if (Strings.isNullOrEmpty(condition.getBranchCompanyId())){
           resultCompanyIds=this.queryUserCompanyIds(currentUser.getUserId());
       }else {
           resultCompanyIds.add(condition.getBranchCompanyId());
       }
        logger.debug("/queryOrder:request param：{}", JSONObject.toJSONString(condition));

        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sb1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sb2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        WebQueryOrderConditionDto webQueryOrderConditionDto = BeanConvertUtils.convert(condition,WebQueryOrderConditionDto.class);
        webQueryOrderConditionDto.setBranchCompanyIds(resultCompanyIds);
        if(webQueryOrderConditionDto.getSubmitStartTime()!=null ){
            webQueryOrderConditionDto.setSubmitStartTime(sb.parse(sb1.format(webQueryOrderConditionDto.getSubmitStartTime())));
        }
        if(webQueryOrderConditionDto.getSubmitEndTime() != null){
            webQueryOrderConditionDto.setSubmitEndTime(sb.parse(sb2.format(webQueryOrderConditionDto.getSubmitEndTime())));
        }

        return ActionResponse.wrap(webOrderDubboService.queryOrder(webQueryOrderConditionDto));
    }
    /**
     * 查询订单的详情
     * @param id
     * @return
     */
    @RequestMapping(value="orderDetail",method= RequestMethod.GET)
    @ParamValid
    public Response<OrderManageDetailDto> orderDetail(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY)  String id){
        logger.debug("/orderDetail:request param,id:"+id);
        Response<OrderManageDetailDto>  orderManageDetailDtoResponse = webOrderDubboService.queryOrderDeatil(id);
        OrderManageDetailDto orderManageDetailDto = orderManageDetailDtoResponse.getResultObject();
        if (orderManageDetailDto == null) {
            logger.warn("订单详情查询为空:{}", id);
            Response response = new Response();
            response.setSuccess(false);
            response.setErrorMessage("订单详情查询为空");
            return ActionResponse.wrap(response);
        }
        orderManageDetailDto.setSingedCertImg(orderManageDetailDto.getSingedCertImg() == null ? null : imageDomainJoinUrlUtil.getImageUrl(orderManageDetailDto.getSingedCertImg()));
        List<OMSCommerceItemDto> commerceItemDtos = orderManageDetailDto.getItems();
        if (commerceItemDtos != null && commerceItemDtos.size()!=0){
            for (OMSCommerceItemDto commerceItemDto:commerceItemDtos){
                if (commerceItemDto != null){
                    commerceItemDto.setProductImg(commerceItemDto.getProductImg() == null ? null : imageDomainJoinUrlUtil.getImageUrl(commerceItemDto.getProductImg()));
                }
            }
        }
        return ActionResponse.wrap(orderManageDetailDtoResponse);

    }

    /**
     * 发货
     * @param deliverInfoVo
     * @return
     */
    @RequestMapping(value="deliverGoods",method= RequestMethod.POST)
    public Response<Boolean> deliverGoods(@RequestBody @Validated({GroupOne.class}) DeliverInfoVo deliverInfoVo){
        logger.debug("/deliverGoods:request param,"+JSONObject.toJSONString(deliverInfoVo));
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        DeliverInfoDto deliverInfoDto = BeanConvertExpandUtils.convertMoreParam(DeliverInfoDto.class, deliverInfoVo);
        return ActionResponse.wrap(webOrderDubboService.deliverGoods(deliverInfoDto, userId));
    }

    /**
     * 打印订单商品
     * @param id
     * @return
     */
    @RequestMapping(value="printProduct",method = RequestMethod.GET)
    @ParamValid
    public Response<PrintOrderDto> printProduct(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String id){
        logger.debug("/printProduct:request param,id:"+id);
        return ActionResponse.wrap(webOrderDubboService.getPrintInfo(id));
    }

    /**
     * 订单备注
     * @param orderDescriptionVo
     * @return
     */
    @RequestMapping(value="orderDescription",method= RequestMethod.POST)
    public Response<Boolean> orderDescription(@RequestBody @Validated OrderDescriptionVo orderDescriptionVo){
        logger.debug("/orderDescription:request param,"+JSONObject.toJSONString(orderDescriptionVo));
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        return ActionResponse.wrap(webOrderDubboService.orderDescription(orderDescriptionVo.getOrderId(), orderDescriptionVo.getDescription(), userId));
    }

    /**
     * 导出excel
     * @param conditionVo
     * @param response
     */
    @RequestMapping(value="toExcel",method= RequestMethod.GET)
    public void toExcel(WebQueryOrderConditionVo conditionVo,HttpServletResponse response,HttpServletRequest req) throws ParseException {

        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (null==currentUser){
            logger.error("从session中获取用户信息失败");
        }
        List<String> companyIds = new ArrayList<>();
        if (Strings.isNullOrEmpty(conditionVo.getBranchCompanyId())){
            companyIds=this.queryUserCompanyIds(currentUser.getUserId());
        }else {
            companyIds.add(conditionVo.getBranchCompanyId());
        }
        conditionVo.setBranchCompanyIds(companyIds);


        logger.debug("/toExcel:request param：{}->{}", JSONObject.toJSONString(conditionVo), JSON.toJSONString(companyIds));

        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sb1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sb2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        WebQueryOrderConditionDto condition = BeanConvertUtils.convert(conditionVo,WebQueryOrderConditionDto.class);
        if(condition.getSubmitStartTime()!=null ){
            condition.setSubmitStartTime(sb.parse(sb1.format(condition.getSubmitStartTime())));
        }
        if(condition.getSubmitEndTime() != null){
            condition.setSubmitEndTime(sb.parse(sb2.format(condition.getSubmitEndTime())));
        }
        condition.setPage(1);
        condition.setPageSize(Integer.MAX_VALUE);
        Response<PageResult<WebQueryOrderDto>> list = webOrderDubboService.queryOrder(condition);
        if(list.getResultObject().getData() != null){
            String[] title ={"id-订单编号","createdByOrderId-父订单编号","orderTypeDesc-订单类型","franchiseeId-加盟商编号","branchCompanyName-所属子公司","submitTime-订单日期","total-订单金额","orderStateDesc-订单状态","paymentStateDesc-支付状态","shippingStateDesc-物流状态"};
            SimpleDateFormat df = new SimpleDateFormat("yyyymmddHHmmss");
            String name = "订单导出"+df.format(new Date());
            try{
                logger.debug("==== 订单导出datas === " + JSON.toJSON(list.getResultObject().getData()));
                ExcelUtil.listToExcel(name,title,list.getResultObject().getData(),response);
            }catch (Exception e){
                logger.error("导出异常", e);
            }
        }
    }

    /**
     * 批量审核
     * @param ids
     * @param req
     * @return
     */
    @RequestMapping(value = "batchApproval",method = RequestMethod.POST)
    public Response<Boolean> batchApproval(@RequestBody @Validated String[] ids, HttpServletRequest req){
        logger.debug("/batchApproval:request param,"+JSONObject.toJSONString(ids));
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        return ActionResponse.wrap(webOrderDubboService.batchApproval(ids,userId));
    }


    /**
     * 单个订单的审批
     * @param vo
     * @return
     */
    @RequestMapping(value = "approvalOrder", method = RequestMethod.POST)
    public Response<Boolean> approvalOrder(@RequestBody @Validated CommonVo vo,HttpServletRequest req) {
        logger.debug("/approvalOrder:request param,", JSONObject.toJSONString(vo));
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        return webOrderDubboService.approvalOrder(vo.getId(),userId);
    }

    /**
     * 批量取消
     * @param pBatchCancelOrderVo
     * @param req
     * @return
     */
    @RequestMapping(value = "batchCancel", method = RequestMethod.POST)
    public Response<Map<String, String>> batchCancel(@RequestBody @Validated BatchCancelOrderVo pBatchCancelOrderVo, HttpServletRequest req) {
        logger.debug("request params:{}", pBatchCancelOrderVo);
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        String realName = "";
        if(loginInfoVO != null){
            userId = loginInfoVO.getUserId();
            realName=loginInfoVO.getRealName();
        }
        List<CancelOrderRequestDto> cancelOrderRequestDtos = new ArrayList<>();
        CancelOrderRequestDto cancelOrderRequestDto;
        for (String orderId : pBatchCancelOrderVo.getIds()) {

            if(orderId !=null && !orderId.equals("")){
                cancelOrderRequestDto = new CancelOrderRequestDto();
                cancelOrderRequestDto.setOrderId(orderId);
                cancelOrderRequestDto.setOperatorId(userId);
                cancelOrderRequestDto.setOperatorName(realName);
                cancelOrderRequestDto.setRemark(pBatchCancelOrderVo.getRemark());
                cancelOrderRequestDtos.add(cancelOrderRequestDto);
            }
        }
        return ActionResponse.wrap(mCancelOrderDubboService.batchCancelOrder(cancelOrderRequestDtos));
    }

    /**
     * 取消单个订单
     * @param cancelOrderRequestVo
     * @param req
     * @return
     */
    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    public Response<String> cancelOrder(@RequestBody @Validated CancelOrderRequestVo cancelOrderRequestVo, HttpServletRequest req) {
        logger.debug("request param:", cancelOrderRequestVo);
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        String realName = "";
        if(loginInfoVO != null){
            userId = loginInfoVO.getUserId();
            realName=loginInfoVO.getRealName();
        }
        CancelOrderRequestDto cancelOrderRequestDto = new CancelOrderRequestDto();
        cancelOrderRequestDto.setOrderId(cancelOrderRequestVo.getId());
        cancelOrderRequestDto.setRemark(cancelOrderRequestVo.getRemark());
        cancelOrderRequestDto.setOperatorName(realName);
        cancelOrderRequestDto.setOperatorId(userId);
        return ActionResponse.wrap(mCancelOrderDubboService.cancelOrder(cancelOrderRequestDto));
    }

    /**
     * 获取物流方式
     * @return
     */
    @RequestMapping(value="shippingMethod",method = RequestMethod.GET)
    public Response<List<ShippingMethodDto>> shippingMethod( @ModelAttribute("companyId") String companyId){
        logger.debug("/shippingMethod");
        return ActionResponse.wrap(shippingMethodDubboService.getAllShippingMethod());
    }

    /**
     * 获取物流信息
     * @param id
     * @return
     */
    @RequestMapping(value = "shippingGroupInfo", method = RequestMethod.GET)
    public Response<WebShippingGroupInfoVo> shippingGroupInfo(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String id) {
        logger.debug("/shippingGroupInfo:request param,id:" + id);
        Response<WebShippingGroupDto> response = webOrderDubboService.shippingGroupInfo(id);

        Response<WebShippingGroupInfoVo> voResponse = new Response<WebShippingGroupInfoVo>();
        voResponse.setSuccess(response.isSuccess());
        voResponse.setCode(response.getCode());
        voResponse.setErrorMessage(response.getErrorMessage());
        voResponse.setResultObject(BeanConvertUtils.convert(response.getResultObject(), WebShippingGroupInfoVo.class));
        return voResponse;
    }


    /**
     * 获取订单支付信息
     * @param pOrderId
     * @return
     */
    @RequestMapping(value = "paymentInfo", method = RequestMethod.GET)
    public Response<WebQueryPaymentInfoDto> paymentInfo(@RequestParam("orderId") String pOrderId) {
        logger.info("request param:{}", pOrderId);
        return ActionResponse.wrap(webOrderDubboService.getOrderPaymentInfo(pOrderId));
    }

    /**
     * 审核退款
     *
     * @param pAuditRefundRequestVo
     * @param req
     * @return
     */
    @RequestMapping(value = "auditRefund", method = RequestMethod.POST)
    public Response<Boolean> auditRefund(@RequestBody @Validated AuditRefundRequestVo pAuditRefundRequestVo, HttpServletRequest req) {
        logger.info("request param:{}", pAuditRefundRequestVo);
        if(!pAuditRefundRequestVo.getPassed() && StringUtils.isEmpty(pAuditRefundRequestVo.getRemark())){
            Response<Boolean> response = new Response<>();
            response.setErrorMessage("备注信息不能为空!");
            response.setSuccess(false);
            return ActionResponse.wrap(response);
        }
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        String userName="";
        if(loginInfoVO != null){
            userId = loginInfoVO.getUserId();
            userName = loginInfoVO.getRealName();
        }
        AuditRefundRequestDto auditRefundRequestDto = new AuditRefundRequestDto();
        auditRefundRequestDto.setAuditorId(userId);
        auditRefundRequestDto.setAuditorName(userName);
        auditRefundRequestDto.setPassed(pAuditRefundRequestVo.getPassed());
        auditRefundRequestDto.setRefundNo(pAuditRefundRequestVo.getRefundNo());
        auditRefundRequestDto.setRemark(pAuditRefundRequestVo.getRemark());
        auditRefundRequestDto.setFinalRefundAmount(pAuditRefundRequestVo.getAmount());
        auditRefundRequestDto.setNonceStr(RandomStringUtils.randomAlphanumeric(32));
        return ActionResponse.wrap(mRefundDubboService.auditRefund(auditRefundRequestDto));
    }

    /**
     * 确认退款
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "confirmRefund", method = RequestMethod.POST)
    public Response<Boolean> confirmRefund(@RequestBody @Validated CompleteRefundVo pCompleteRefundVo, HttpServletRequest req) {
        logger.info("request param:{}", pCompleteRefundVo);
        if(!pCompleteRefundVo.getPassed() && StringUtils.isEmpty(pCompleteRefundVo.getRemark())){
            Response<Boolean> response = new Response<>();
            response.setErrorMessage("备注信息不能为空!");
            response.setSuccess(false);
            return ActionResponse.wrap(response);
        }
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        String userName="";
        if(loginInfoVO != null){
            userId = loginInfoVO.getUserId();
            userName = loginInfoVO.getRealName();
        }
        RefundConfirmDto refundConfirmDto = new RefundConfirmDto();
        refundConfirmDto.setRemark(pCompleteRefundVo.getRemark());
        refundConfirmDto.setRefundNo(pCompleteRefundVo.getRefundNo());
        refundConfirmDto.setPassed(pCompleteRefundVo.getPassed());
        refundConfirmDto.setOperatorName(userName);
        refundConfirmDto.setOperatorId(userId);
        return mRefundDubboService.confirmRefund(refundConfirmDto);
    }

    public Response<Boolean> confirmReceiving(String orderId){
        return ActionResponse.wrap(webOrderDubboService.updateShippingState(orderId));
    }

    /**
     * 添加退款记录
     *
     * @param req
     * @return
     */
    /*@RequestMapping(value = "addRefundRecord", method = RequestMethod.POST)
    public Response<Boolean> addRefundRecord(@RequestBody @Validated AddRefundRecordVo pAddRefundRecordVo, HttpServletRequest req) {
        logger.debug("request param:{}", pAddRefundRecordVo);
        AddRefundRecordDto addRefundRecordDto = BeanConvertUtils.convert(pAddRefundRecordVo, AddRefundRecordDto.class);
        addRefundRecordDto.setOperatorName("admin");//(getCurrentOpenid(req));
        addRefundRecordDto.setOperatorId("admin");//(getCurrentOpenid(req));
        return mRefundDubboService.addRefundRecord(addRefundRecordDto);
    }*/

    /**
     * 重新传送订单
     * @param vo
     * @return
     */
    @RequestMapping(value = "resendOrder", method = RequestMethod.POST)
    public Response<Boolean> resendOrder(@RequestBody @Validated CommonVo vo,HttpServletRequest req){
        logger.debug("/resendOrder:request param,", JSONObject.toJSON(vo));
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        return ActionResponse.wrap(webOrderDubboService.resendOrder(vo.getId(),userId));
    }

    /**
     *
     * 根据参数在加盟商编码和加盟商名称两个字段里面搜索加盟商信息
     *
     * @param franchiseeQueryVo
     * @return
     */
    @RequestMapping(value = "/getFranchiseeInfo", method = RequestMethod.GET)
    public Response<PageResult<FranchiseeSimpleResultVo>> getFranchiseeInfo(@Valid FranchiseeQueryVo franchiseeQueryVo) {
        logger.debug("/getFranchiseeInfo:request param,", JSONObject.toJSON(franchiseeQueryVo));
        Response<PageResult<FranchiseeSimpleResultVo>> resultResponse = new Response<PageResult<FranchiseeSimpleResultVo>>();
        PageResult<FranchiseeSimpleResultVo> pageResult = new PageResult<FranchiseeSimpleResultVo>();
        List<FranchiseeSimpleResultVo> list = new ArrayList<FranchiseeSimpleResultVo>();

        Map<String, Object> param = new HashMap<>();
        param.put("idOrName", franchiseeQueryVo.getParam());

        Response<Integer> total = organizationSCService.querySCFranchiseeByIdOrName(param);
        if (total.isSuccess()) {
            if (0 == total.getResultObject()) {
                resultResponse.setResultObject(pageResult);
                return ActionResponse.wrap(resultResponse, "未匹配到符合条件的结果！");
            }
        } else {
            return ActionResponse.wrap(resultResponse, total.getErrorMessage());
        }

        param.put("pageCount", true);
        Response<List<FranchiseeDto>> result = organizationSCService.queryPageSCFranchiseeByIdOrName(franchiseeQueryVo.getPageNum(),
                franchiseeQueryVo.getPageSize(), param);
        if (!result.isSuccess()) {
            return ActionResponse.wrap(resultResponse, result.getErrorMessage());
        }

        List<FranchiseeDto> resultObject = result.getResultObject();
        for (FranchiseeDto franchiseeDto : resultObject) {
            FranchiseeSimpleResultVo franchiseeSimpleResultVo = new FranchiseeSimpleResultVo();
            franchiseeSimpleResultVo.setFranchiseeId(franchiseeDto.getId());
            franchiseeSimpleResultVo.setFranchiseeName(franchiseeDto.getName());
            list.add(franchiseeSimpleResultVo);
        }
        pageResult.setData(list);
        pageResult.setPageNum(franchiseeQueryVo.getPageNum());
        pageResult.setPageSize(list.size());
        pageResult.setTotal(total.getResultObject() + 0L);

        resultResponse.setSuccess(true);
        resultResponse.setResultObject(pageResult);
        return ActionResponse.wrap(resultResponse);
    }

    /**
     * 添加支付
     *
     * @return
     */
    @RequestMapping(value = "/addPaymentInfo", method = RequestMethod.POST)
    public Response<String> addPaymentInfo(@RequestBody @Valid AddPaymentInfoVO addPaymentInfoVO) {
        logger.info("/addPaymentInfo:request param{}", JSONObject.toJSON(addPaymentInfoVO));
        AddPaymentInfoDto addPaymentInfoDto = BeanConvertUtils.convert(addPaymentInfoVO, AddPaymentInfoDto.class);
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "addPaymentInfo";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        addPaymentInfoDto.setOperator(userId);
        return addPaymentInfoDubboService.addPaymentInfo(addPaymentInfoDto);
    }

    @RequestMapping(value = "/splitOrderByInventory", method = RequestMethod.POST)
    public Response<Boolean> splitOrderByInventory(@RequestBody SplitOrderByInventoryVo splitOrderByInventoryVo) {
        logger.info("/splitOrderByInventory:request param{}", JSONObject.toJSON(splitOrderByInventoryVo));
        String orderId = splitOrderByInventoryVo.getOrderId();
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "splitOrderByInventoryVo";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        Response<Boolean> res = webOrderDubboService.splitOrderByInventory(orderId, userId);
        return res;
    }
    /**
     *
     * @param confirmPaymentVO
     * @return
     */
    @RequestMapping(value = "/confirmPayment", method = RequestMethod.POST)
    public Response<Boolean> confirmPayment(@RequestBody @Valid ConfirmPaymentVO confirmPaymentVO) {
        logger.info("/confirmPayment:request param{}", JSONObject.toJSON(confirmPaymentVO));
        ConfirmPaymentDto confirmPaymentDto = BeanConvertUtils.convert(confirmPaymentVO, ConfirmPaymentDto.class);
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "confirmPayment";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        confirmPaymentDto.setOperator(userId);
        return addPaymentInfoDubboService.confirmOfflinePayment(confirmPaymentDto);
    }

    /**
     * 拆单
     * @param manualSplitOrderVo
     * @return
     */
    @RequestMapping(value = "/manualSplitOrder", method = RequestMethod.POST)
    public Response<Boolean> manualSplitOrder(@RequestBody ManualSplitOrderVo manualSplitOrderVo) {
        logger.info("/manualSplitOrder:request param{}", JSONObject.toJSON(manualSplitOrderVo));
        String parentOrderId = manualSplitOrderVo.getParentOrderId();
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        String userId = "manualSplitOrder";
        if(loginInfoVO != null){
            if (StringUtils.isBlank(loginInfoVO.getRealName())) {
                userId = loginInfoVO.getUserId();
            }else {
                userId = loginInfoVO.getRealName();
            }
        }
        Response<Boolean> res = webOrderDubboService.manualSplitOrder(parentOrderId, manualSplitOrderVo.getGroups(), userId);
        return res;
    }

    /**
     * 导出excel
     * @param conditionVo
     * @param response
     */
    @RequestMapping(value="exportOrderList",method= RequestMethod.GET)
    public void exportOrderList(WebQueryOrderConditionVo conditionVo,HttpServletResponse response) throws ParseException, IOException {

        List<String> resultCompanyIds = new ArrayList<>();
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (null==currentUser){
            logger.error("从session中获取用户信息失败");
        }
        if (Strings.isNullOrEmpty(conditionVo.getBranchCompanyId())){
            resultCompanyIds=this.queryUserCompanyIds(currentUser.getUserId());
        }else {
            resultCompanyIds.add(conditionVo.getBranchCompanyId());
        }

        logger.debug("/toExcel:request param：{}", JSONObject.toJSONString(conditionVo));

        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sb1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sb2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        WebQueryOrderConditionDto condition = BeanConvertUtils.convert(conditionVo,WebQueryOrderConditionDto.class);
        condition.setBranchCompanyIds(resultCompanyIds);
        if(condition.getSubmitStartTime()!=null ){
            condition.setSubmitStartTime(sb.parse(sb1.format(condition.getSubmitStartTime())));
        }
        if(condition.getSubmitEndTime() != null){
            condition.setSubmitEndTime(sb.parse(sb2.format(condition.getSubmitEndTime())));
        }
        condition.setPage(1);
        condition.setPageSize(Integer.MAX_VALUE);
        logger.info("/toExcel getEnhancedOrderPageList,condition{}",JSON.toJSONString(condition));
        Response<PageResult<OrderEnhancedDto>> list = webOrderDubboService.getEnhancedOrderPageList(condition);

        if (!list.isSuccess() && list.getCode().equals(CommonsEnum.RESPONSE_10023.getCode())) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<script>alert('导出订单条数大于最大条数【"+list.getErrorMessage()+"】,请重新选择条件再导出')</script>");
            response.getWriter().flush();
            return;
        }

        if(list.getResultObject() != null && list.getResultObject().getData() != null){
            try{
                logger.debug("==== 加强版订单导出datas === " + JSON.toJSON(list.getResultObject().getData()));
                ExcelUtil2.listToExcel(list.getResultObject().getData(),response);
//                excelExportService.excelExport("order", list.getResultObject().getData(), response);
            }catch (Exception e){
                logger.error("导出异常", e);
            }
        } else {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<div style='text-align: center'>暂无数据</div>");
            response.getWriter().flush();
            return;
        }
    }



    /**
     * 后台-确认收货接口
     * @param qsdqrOrderComfirmParamVo
     * @author yinyuxin
     * @return
     */
    @RequestMapping(value = "comfirmOrder",method = RequestMethod.POST)
    public Response<Boolean> comfirmOrder(@RequestBody QsdqrOrderComfirmParamVo qsdqrOrderComfirmParamVo,HttpServletRequest request){
        logger.info("orderAction-->comfirmQRDQSOrder()-->param:{}", JSONObject.toJSONString(qsdqrOrderComfirmParamVo));
        LoginInfoVO loginInfoVO = (LoginInfoVO) request.getSession().getAttribute(CURRENT_USER);
        if (null == loginInfoVO) {
            logger.error("orderAction-->comfirmQRDQSOrder()-->error:未查询到当前用户信息");
            return this.getFailResponse();
        }
        OrderReceiveDto orderReceiveDto = new OrderReceiveDto();
        orderReceiveDto.setOrderId(qsdqrOrderComfirmParamVo.getOrderId());
        Map<Long, Long> commerceItems = new HashMap<>();
        if (CollectionUtils.isNotEmpty(qsdqrOrderComfirmParamVo.getCommerceItemDatas())) {
            for (QsdqrOrderItemsConfirmVo qsdqrOrderItemsConfirmVo : qsdqrOrderComfirmParamVo.getCommerceItemDatas()) {
                if (qsdqrOrderItemsConfirmVo.getCommerceId() == null) {
                    Response response = this.getFailResponse();
                    response.setErrorMessage("存在商品行ID为空的数据");
                    response.setCode(CommonsEnum.RESPONSE_500.getCode());
                    response.setSuccess(false);
                    return response;
                }
                commerceItems.put(qsdqrOrderItemsConfirmVo.getCommerceId(), qsdqrOrderItemsConfirmVo.getCompletedQuantity());
            }
            orderReceiveDto.setSignedMap(commerceItems);
        }
        Response<Boolean> response = orderFulfillerDubboService.orderReceive(orderReceiveDto);
        return response;
    }
}
