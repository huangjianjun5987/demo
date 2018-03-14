package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderInfoDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemQueryParamDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseQueryParamDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderWriteDubboService;
import com.yatang.sc.operation.util.NumberToCN;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.pm.PmPurchaseOrderAuditVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseOrderExtVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseOrderInfoVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseOrderItemQueryParamVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseOrderItemVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseOrderVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseQueryParamVo;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.xc.mbd.biz.system.dto.UserDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.UserDubboService;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @描述:采购订单管理
 * @类名:PmPurchaseOrderAction
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:42
 * @版本: v1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/pmPurchaseOrder")
public class PmPurchaseOrderAction {
    private final PmPurchaseOrderQueryDubboService queryDubboService;

    private final PmPurchaseOrderWriteDubboService writeDubboService;

    private final UserDubboService userDubboService;

    @Value("${image.view.domain}")
    private String imageViewDomain;//图片域名


    public String getCurrentCompanyId(HttpServletRequest req) {
        LoginInfoVO userInfo = (LoginInfoVO) req.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        String companyId = null;
        if (userInfo.getCompanyType() == 0) {
            companyId = userInfo.getCompanyId();
        }
        return companyId;
    }


    /**
     * 根据查询条件查询 采购单iem信息
     *
     * @param pmPurchaseOrderItemQueryParamVo 查询条件（productId,spId,spAdrId）
     * @return
     */
    @RequestMapping(value = "/getNewPmPurchaseOrderItem", method = RequestMethod.GET)
    public Response<PmPurchaseOrderItemVo> getNewPmPurchaseOrderItem(@Validated PmPurchaseOrderItemQueryParamVo pmPurchaseOrderItemQueryParamVo) {

        Response<PmPurchaseOrderItemVo> response = new Response<>();
        //vo2dto
        PmPurchaseOrderItemQueryParamDto queryParamDto = BeanConvertUtils.convert(pmPurchaseOrderItemQueryParamVo, PmPurchaseOrderItemQueryParamDto.class);
        Response<PmPurchaseOrderItemDto> dtoResponse = queryDubboService.getNewPmPurchaseOrderItem(queryParamDto);
        if (!CommonsEnum.RESPONSE_200.getCode().equals(dtoResponse.getCode())) {
            return BeanConvertUtils.convert(dtoResponse, Response.class);
        }
        //dto2vo
        PmPurchaseOrderItemVo pmPurchaseOrderItemVo = BeanConvertUtils.convert(dtoResponse.getResultObject(), PmPurchaseOrderItemVo.class);
        response.setResultObject(pmPurchaseOrderItemVo);
        response.setSuccess(dtoResponse.isSuccess());
        response.setCode(dtoResponse.getCode());
        response.setErrorMessage(dtoResponse.getErrorMessage());
        return response;
    }

    /**
     * 新增商品采购订单
     *
     * @param pmPurchaseOrderExtVo 采购订单ext类
     * @return
     */

    @RequestMapping(value = "/addPmPurchaseOrder", method = RequestMethod.POST)
    public Response<String> addPmPurchaseOrder(@RequestBody @Validated({DefaultGroup.class}) PmPurchaseOrderExtVo pmPurchaseOrderExtVo) {
        Response<String> response = new Response<>();

        if (log.isInfoEnabled()) {
            log.info("action--addPmPurchaseOrder--新增采购订单>>pmPurchaseOrderExtVo:{}", JSON.toJSONString(pmPurchaseOrderExtVo));
        }
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response = new Response<>();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }

        pmPurchaseOrderExtVo.getPmPurchaseOrder().setCreateUserId(loginInfoVO.getUserId());
        pmPurchaseOrderExtVo.getPmPurchaseOrder().setBranchCompanyId(loginInfoVO.getCompanyId());

        //生成订单号
        //vo2po
        PmPurchaseOrderExtDto pmPurchaseOrderExtDto = BeanConvertUtils.convert(pmPurchaseOrderExtVo, PmPurchaseOrderExtDto.class);
        response = writeDubboService.addPmPurchaseOrder(pmPurchaseOrderExtDto);
        return response;
    }

    /**
     * 更改商品采购订单
     *
     * @param pmPurchaseOrderExtVo 采购订单ext类
     * @return
     */
    @RequestMapping(value = "/updatePmPurchaseOrder", method = RequestMethod.POST)
    public Response<Void> updatePmPurchaseOrder(@RequestBody @Validated({GroupOne.class}) PmPurchaseOrderExtVo pmPurchaseOrderExtVo) {
        Response<Void> response = new Response<>();
        if (log.isInfoEnabled()) {
            log.info("action--updatePmPurchaseOrder--更新采购订单>>pmPurchaseOrderExtVo:{}", pmPurchaseOrderExtVo);
        }
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response = new Response<>();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }


        pmPurchaseOrderExtVo.getPmPurchaseOrder().setModifyUserId(loginInfoVO.getUserId());
        pmPurchaseOrderExtVo.getPmPurchaseOrder().setBranchCompanyId(loginInfoVO.getCompanyId());

//        pmPurchaseOrderExtVo.getPmPurchaseOrder().setCreateUserId("10018");
//        pmPurchaseOrderExtVo.getPmPurchaseOrder().setBranchCompanyId("10000");
        //vo2po
        PmPurchaseOrderExtDto pmPurchaseOrderExtDto = BeanConvertUtils.convert(pmPurchaseOrderExtVo, PmPurchaseOrderExtDto.class);
        response = writeDubboService.updatePmPurchaseOrder(pmPurchaseOrderExtDto);
        return response;
    }


    /**
     * 根据订单id删除(草稿状态的商品,物理删除 草稿状态)
     *
     * @param orderId 订单集合
     * @return
     */
    @RequestMapping(value = "/deletePmPurchaseOrderById", method = RequestMethod.GET)
    public Response<Void> deletePmPurchaseOrderById(@NotNull(message = MessageConstantUtil.NOT_EMPTY) Long orderId) {
        Response<Void> response = new Response<>();
        return response = writeDubboService.deletePmPurchaseOrderById(orderId);

    }

    /**
     * 批量删除(草稿状态的商品,物理删除 草稿状态)
     *
     * @param pmPurchaseOrderIds 订单集合
     * @return
     */
    @RequestMapping(value = "/batchDeletePmPurchaseOrderByIds", method = RequestMethod.GET)
    public Response<Void> batchDeletePmPurchaseOrderByIds(@NotBlank(message = MessageConstantUtil.NOT_EMPTY) String pmPurchaseOrderIds) {
        Response<Void> response = new Response<>();
        List<String> pmPurchaseOrderIdList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(pmPurchaseOrderIds);
        if (pmPurchaseOrderIdList.size() > 0) {//只有当lissize大于0才能查询
            response = writeDubboService.batchDeletePmPurchaseOrderByIds(pmPurchaseOrderIdList);
        } else {//查询条件为空
            response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
            response.setCode(CommonsEnum.RESPONSE_400.getCode());
            response.setSuccess(false);
        }
        return response;
    }


    /**
     * 根据传入参数查询采购单列表
     *
     * @param pmPurchaseQueryParamVo
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryPurchaseOrderList", method = RequestMethod.GET)
    public Response<PageResult> queryPurchaseOrderList(PmPurchaseQueryParamVo pmPurchaseQueryParamVo, HttpServletRequest req) {

        String companyId = getCurrentCompanyId(req);
        if (!StringUtils.isEmpty(companyId)) {
            pmPurchaseQueryParamVo.setBranchCompanyId(companyId);
        }

        PmPurchaseQueryParamDto convert = BeanConvertUtils.convert(pmPurchaseQueryParamVo, PmPurchaseQueryParamDto.class);
        Response<PageResult<PmPurchaseOrderDto>> response = queryDubboService.queryPurchaseOrderList(convert);
        PageResult<PmPurchaseOrderDto> resultObject = response.getResultObject();
        List<PmPurchaseOrderDto> data = resultObject.getData();
        List<PmPurchaseOrderVo> pmPurchaseOrderVos = BeanConvertUtils.convertList(data, PmPurchaseOrderVo.class);
        PageResult convert2 = BeanConvertUtils.convert(resultObject, PageResult.class);
        convert2.setData(pmPurchaseOrderVos);
        Response convert1 = BeanConvertUtils.convert(response, Response.class);
        convert1.setResultObject(convert2);
        return convert1;

    }

    /**
     * 根据采购单id查询审核信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPurchaseOrderAuditInfoById", method = RequestMethod.GET)
    public Response<PmPurchaseOrderAuditVo> getPurchaseOrderAuditInfoById(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id) {
        Response<PmPurchaseOrderInfoDto> response = queryDubboService.getPurchaseOrderInfoById(id);
        if (!response.isSuccess()) {
            return BeanConvertUtils.convert(response, Response.class);
        }
        PmPurchaseOrderAuditVo convert = BeanConvertUtils.convert(response.getResultObject(), PmPurchaseOrderAuditVo.class);
        Response convert1 = BeanConvertUtils.convert(response, Response.class);
        convert1.setResultObject(convert);
        return convert1;
    }

    /**
     * 根据采购单id查询采购单详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPurchaseOrderInfoById", method = RequestMethod.GET)
    public Response<PmPurchaseOrderInfoVo> getPurchaseOrderInfoById(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id) {
        Response<PmPurchaseOrderInfoDto> response = queryDubboService.getPurchaseOrderInfoById(id);

        if (!response.isSuccess()) {
            return BeanConvertUtils.convert(response, Response.class);
        }
        PmPurchaseOrderInfoVo purchaseOrderInfoVo = BeanConvertUtils.convert(response.getResultObject(), PmPurchaseOrderInfoVo.class);
        if (purchaseOrderInfoVo == null) {
            log.info("查询订单详情错误" ,JSON.toJSONString(purchaseOrderInfoVo) );
            return BeanConvertUtils.convert(response, Response.class);
        }
        if (StringUtils.isNotBlank(purchaseOrderInfoVo.getCreateUserId())) {
            int i = Integer.parseInt(purchaseOrderInfoVo.getCreateUserId());
            Response<UserDTO> userById = userDubboService.findUserById(i);
            if (!userById.isSuccess()) {
                log.info("查询当前用户名失败" + userById);
            }
            if (userById.isSuccess() && userById.getResultObject() != null) {
                purchaseOrderInfoVo.setCreateUserName(userById.getResultObject().getEmployeeName());//设置创建人名称
            }
        } else {
            purchaseOrderInfoVo.setCreateUserName("系统创建");

        }
        String auditUserId = purchaseOrderInfoVo.getAuditUserId();
        if (auditUserId != null) {
            Response<UserDTO> auditUserResp = userDubboService.findUserById(Integer.parseInt(auditUserId));
            if (!auditUserResp.isSuccess()) {
                log.info("查询当前用户名失败" + auditUserResp);
            }
            if (auditUserResp != null && auditUserResp.getResultObject() != null) {

                purchaseOrderInfoVo.setAuditUserName(auditUserResp.getResultObject().getEmployeeName());//设置审核人名称
            }
        }
        Response<PmPurchaseOrderInfoVo> orderInfoVoResp = new Response<>();
        orderInfoVoResp.setResultObject(purchaseOrderInfoVo);
        orderInfoVoResp.setSuccess(true);
        orderInfoVoResp.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return orderInfoVoResp;
    }

    /**
     * 审核采购单(废弃)
     *
     * @param pmPurchaseOrderAuditVo
     * @return
     */
    @RequestMapping(value = "/auditPurchaseOrderInfo", method = RequestMethod.POST)
    public Response<Boolean> auditPurchaseOrderInfo(@RequestBody @Validated(DefaultGroup.class) PmPurchaseOrderAuditVo pmPurchaseOrderAuditVo, HttpSession session) {
        //获取当前登录用户信息
        LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
        if (attribute == null || attribute.getUserId() == null) {
            Response response = new Response();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        PmPurchaseOrderDto convert = BeanConvertUtils.convert(pmPurchaseOrderAuditVo, PmPurchaseOrderDto.class);
        convert.setAuditUserId(attribute.getUserId());
        convert.setAuditTime(new Date());
        Response<Boolean> response = null;
//        response = writeDubboService.auditPurchaseOrderInfo(convert);
        return response;
    }

    /**
     * 根据传入参数分页查询采购单打印列表详情
     *
     * @param pmPurchaseQueryParamVo
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryPurchaseOrderListInfo", method = RequestMethod.GET)
    public Response<PageResult<PmPurchaseOrderInfoVo>> queryPurchaseOrderListInfo(PmPurchaseQueryParamVo pmPurchaseQueryParamVo, HttpServletRequest req) {

        String companyId = getCurrentCompanyId(req);
        if (!StringUtils.isEmpty(companyId)) {
            pmPurchaseQueryParamVo.setBranchCompanyId(companyId);
        }
        PmPurchaseQueryParamDto convert = BeanConvertUtils.convert(pmPurchaseQueryParamVo, PmPurchaseQueryParamDto.class);
        Response<PageResult<PmPurchaseOrderInfoDto>> response = queryDubboService.queryPurchaseOrderListInfo(convert);
        PageResult resultObject = response.getResultObject();

        if (null == resultObject || CollectionUtils.isEmpty(resultObject.getData())) {
            log.info("查询采购单打印列表无数据");
            Response res = new Response();
            res.setCode(CommonsEnum.RESPONSE_10046.getCode());
            res.setSuccess(true);
            res.setErrorMessage(CommonsEnum.RESPONSE_10046.getName());
            return res;
        }

        List<PmPurchaseOrderInfoVo> pmPurchaseOrderExtVos = BeanConvertUtils.convertList(resultObject.getData(), PmPurchaseOrderInfoVo.class);
        NumberToCN numberToCN = new NumberToCN();
        for (PmPurchaseOrderInfoVo p : pmPurchaseOrderExtVos) {
            if (StringUtils.isNotBlank(p.getCreateUserId())) {
                int i = Integer.parseInt(p.getCreateUserId());
                Response<UserDTO> userById = userDubboService.findUserById(i);
                if (!userById.isSuccess()) {
                    log.info("查询当前用户名失败" + userById);
                }
                if (userById.isSuccess() && userById.getResultObject() != null) {
                    p.setCreateUserName(userById.getResultObject().getEmployeeName());//设置创建人名称
                }
            } else {
                p.setCreateUserName("系统创建");

            }

            p.setNumberToCN(numberToCN.number2CNMontrayUnit(p.getTotalAmount()));
            int totalNumber = 0;
            for (PmPurchaseOrderItemDto pmPurchaseOrderItemDto : p.getPmPurchaseOrderItems()) {
                totalNumber = totalNumber + pmPurchaseOrderItemDto.getPurchaseNumber();
            }
            p.setTotalNumber(totalNumber);
        }
        resultObject.setData(pmPurchaseOrderExtVos);
        Response<PageResult<PmPurchaseOrderInfoVo>> convert1 = new Response<>();
        BeanConvertUtils.convert(response, convert1.getClass());
        convert1.setResultObject(resultObject);
        return convert1;

    }

    @RequestMapping(value = "/exportPdf", method = {RequestMethod.GET})
    public void exportPdf(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) String purchaseOrderNo,
                          @RequestParam(required = false) Boolean isTest,
                          HttpServletResponse response, HttpServletRequest req) throws IOException {
        PmPurchaseOrderInfoVo order;
        if (BooleanUtils.isTrue(isTest)) {
            order = PmPurchaseOrderPdfGenerater.order();
        } else {
            PmPurchaseQueryParamVo pmPurchaseQueryParamVo = new PmPurchaseQueryParamVo();
            pmPurchaseQueryParamVo.setPageNum(1);
            pmPurchaseQueryParamVo.setPageSize(1);
            pmPurchaseQueryParamVo.setPurchaseOrderNo(purchaseOrderNo);
            Response<PageResult<PmPurchaseOrderInfoVo>> pageResultResponse = queryPurchaseOrderListInfo(pmPurchaseQueryParamVo, req);
            order = pageResultResponse.getResultObject().getData().get(0);
        }

        PmPurchaseOrderPdfGenerater generater = new PmPurchaseOrderPdfGenerater(imageViewDomain);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        generater.generate(baos, order);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setContentLength(baos.size());
        response.setHeader("Content-disposition", "attachment;filename=" + order.getPurchaseOrderNo() + ".pdf");

        // Flush byte array to servlet output stream.
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }

    @RequestMapping(value = "/exportPdfs", method = {RequestMethod.GET})
    public void exportPdfs(PmPurchaseQueryParamVo pmPurchaseQueryParamVo,
                           @RequestParam(required = false) Boolean isTest,
                           HttpServletResponse response, HttpServletRequest req) throws IOException {
        List<PmPurchaseOrderInfoVo> orders;
        if (BooleanUtils.isTrue(isTest)) {
            int length = RandomUtils.nextInt(1, 20);
            orders = Lists.newArrayList();
            for (int i = 0; i < length; i++) {
                orders.add(PmPurchaseOrderPdfGenerater.order());
            }
        } else {
            pmPurchaseQueryParamVo.setPageNum(1);
            pmPurchaseQueryParamVo.setPageSize(Integer.MAX_VALUE);
            Response<PageResult<PmPurchaseOrderInfoVo>> pageResultResponse = queryPurchaseOrderListInfo(pmPurchaseQueryParamVo, req);
            orders = pageResultResponse.getResultObject().getData();
        }

        List<PmPurchaseOrderPdfsGenerater.GeneraterPdf> pdfs = Lists.newArrayList();
        PmPurchaseOrderPdfGenerater generater;
        PmPurchaseOrderPdfsGenerater.GeneraterPdf pdf;
        for (PmPurchaseOrderInfoVo order : orders) {
            generater = new PmPurchaseOrderPdfGenerater(imageViewDomain);
            pdf = new PmPurchaseOrderPdfsGenerater.GeneraterPdf();
            pdf.setFileName(order.getPurchaseOrderNo() + ".pdf");
            pdf.setBytes(generater.generate(order));
            pdfs.add(pdf);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PmPurchaseOrderPdfsGenerater.generate(baos, pdfs);

        response.setContentType("application/x-zip-compressed");
        response.setContentLength(baos.size());
        response.setHeader("Content-disposition", "attachment;filename=orders.zip");

        // Flush byte array to servlet output stream.
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }


}
