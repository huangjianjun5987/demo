package com.yatang.sc.operation.web;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yatang.sc.common.staticvalue.FileType;
import com.yatang.sc.facade.dto.prod.*;
import com.yatang.sc.facade.dubboservice.ProdPurchaseInfoImportQueryDubboService;
import com.yatang.sc.facade.dubboservice.ProdPurchaseInfoImportWriteDubboService;
import com.yatang.sc.operation.util.DateUtil;
import com.yatang.sc.operation.vo.ProdPurchasePriceUpdateParamVo;
import com.yatang.sc.operation.vo.ProdPurchasePriceUpdateVo;
import com.yatang.sc.web.common.excel.service.impl.ExcelExportService;
import com.yatang.sc.web.common.excel.service.impl.ExcelImport;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Strings;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dubboservice.ProdPurchaseQueryDubboService;
import com.yatang.sc.facade.dubboservice.ProdPurchaseWriteDubboService;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.prod.BranchCompanyInfoVo;
import com.yatang.sc.operation.vo.prod.ProPurchaseBatchChangeStatusVo;
import com.yatang.sc.operation.vo.prod.ProdProductVo;
import com.yatang.sc.operation.vo.prod.ProdPurchaseExtVo;
import com.yatang.sc.operation.vo.prod.ProdPurchaseInfoVo;
import com.yatang.sc.operation.vo.prod.ProdPurchaseModifyParamVo;
import com.yatang.sc.operation.vo.prod.ProdPurchaseQueryParamVo;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @描述: 商品采购Action
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/15 14:04
 * @版本: v1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/sc/prodPurchase")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPurchaseAction extends BaseAction{

    private final ProdPurchaseQueryDubboService prodPurchaseQueryDubboService;

    private final ProdPurchaseWriteDubboService prodPurchaseWriteDubboService;

    private final ProductScIndexDubboService indexDubboService;//调用商品dubbo服务

    private final ProdPurchaseInfoImportWriteDubboService prodPurchaseInfoImportWriteDubboService;

    private final ProdPurchaseInfoImportQueryDubboService prodPurchaseInfoImportQueryDubboService;

    private final ExcelExportService excelExportService;




    /**
     * 检查是否有主供应商
     *
     * @param productId       商品id
     * @param branchCompanyId 分公司编号
     * @param supplierType    供应商类型:0：一般供应商,1:主供应商
     * @return
     */
    @RequestMapping(value = "/checkMainSupplier", method = RequestMethod.GET)
    @ParamValid
    public Response<Void> checkMainSupplier(@NotBlank(message = MessageConstantUtil.NOT_EMPTY) String productId, @NotNull(message = MessageConstantUtil.NOT_EMPTY) String branchCompanyId, @NotNull @Range(min = 1, max = 1, message = MessageConstantUtil.STATUS_RANGE) int supplierType) {
        Response<Void> response = prodPurchaseQueryDubboService.checkMainSupplier(productId, branchCompanyId, supplierType);
        return response;
    }

    /**
     * 新增商品采购信息
     *
     * @param prodPurchaseInfoVo 商品采购关系VO类
     * @return
     */
    @RequestMapping(value = "/addProdPurchase", method = RequestMethod.POST)
    public Response<Void> addProdPurchase(@RequestBody @Validated({DefaultGroup.class}) ProdPurchaseInfoVo prodPurchaseInfoVo) {
        Response<Void> response;
        // 获取当前用户信息
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response = new Response<>();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        prodPurchaseInfoVo.setCreateUserId(loginInfoVO.getUserId());
//         prodPurchaseInfoVo.setCreateUserId("user_add_223");
        //vo2Dto
        ProdPurchaseInfoDto prodPurchaseInfoDto = BeanConvertUtils.convert(prodPurchaseInfoVo, ProdPurchaseInfoDto.class);
        response = prodPurchaseWriteDubboService.addProdPurchase(prodPurchaseInfoDto);
        return response;
    }

    /**
     * 根据采购关系主键查询商品采购关系主键
     *
     * @param id 采购关系主键
     * @return
     */
    @RequestMapping(value = "/getProdPurchaseById", method = RequestMethod.GET)
    @ParamValid
    public Response<ProdPurchaseExtVo> getProdPurchaseById(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id) {

        Response<ProdPurchaseExtVo> response = new Response<>();
        Response<ProdPurchaseExtDto> dtoResponse = prodPurchaseQueryDubboService.getProdPurchaseById(id);
        //错误的响应
        if (!dtoResponse.isSuccess()) {
            return BeanConvertUtils.convert(dtoResponse, Response.class);
        }
        ProdPurchaseExtDto resultObject = dtoResponse.getResultObject();
        //dto2vo
        ProdPurchaseExtVo prodPurchaseExtVo = BeanConvertUtils.convert(resultObject, ProdPurchaseExtVo.class);
        response.setResultObject(prodPurchaseExtVo);
        response.setSuccess(dtoResponse.isSuccess());
        response.setCode(dtoResponse.getCode());
        response.setErrorMessage(dtoResponse.getErrorMessage());
        return response;
    }

    /**
     * 更新商品采购关系
     *
     * @param prodPurchaseInfoVo 商品采购关系VO类
     * @return
     */
    @RequestMapping(value = "/updateProdPurchase", method = RequestMethod.POST)
    public Response<Void> updateProdPurchase(@RequestBody @Validated({GroupOne.class}) ProdPurchaseInfoVo prodPurchaseInfoVo) {
        Response<Void> response;
        // 获取当前用户信息
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response = new Response<>();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        prodPurchaseInfoVo.setModifyUserId(loginInfoVO.getUserId());
//        prodPurchaseInfoVo.setModifyUserId("user_modify_123");

        //vo2Dto
        ProdPurchaseInfoDto prodPurchaseInfoDto = BeanConvertUtils.convert(prodPurchaseInfoVo, ProdPurchaseInfoDto.class);
        response = prodPurchaseWriteDubboService.updateProdPurchase(prodPurchaseInfoDto);
        return response;
    }

    /**
     * 根据 id删除商品采购关系(伪删除)
     *
     * @param id        采购关系主键
     * @param productId 商品id
     * @return
     */
    @RequestMapping(value = "/deleteProdPurchaseById", method = RequestMethod.GET)
    @ParamValid
    public Response<Void> deleteProdPurchaseById(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id, @RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String productId) {
        // 获取当前用户信息
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            Response response = new Response();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }

        ProdPurchaseModifyParamVo prodPurchaseModifyParamVo = new ProdPurchaseModifyParamVo();
//         prodPurchaseModifyParamVo.setModifyUserId("user_delete_123");
        prodPurchaseModifyParamVo.setModifyUserId(loginInfoVO.getUserId());
        prodPurchaseModifyParamVo.setId(id);
        prodPurchaseModifyParamVo.setProductId(productId);
        //vo2Dto
        ProdPurchaseModifyParamDto convert = BeanConvertUtils.convert(prodPurchaseModifyParamVo, ProdPurchaseModifyParamDto.class);
        Response<Void> response = prodPurchaseWriteDubboService.deleteProdPurchaseById(convert);
        return response;
    }

    /**
     * 根据 id改变当前的供应商类型
     *
     * @param id              采购关系主键
     * @param productId       商品id
     * @param branchCompanyId 分公司id
     * @param supplierType    供应商的类型(0：一般供应商,1:主供应商)(需要将其他的主供应商改为一般供应商状态)
     * @return
     */
    @RequestMapping(value = "/changeSupplierType", method = RequestMethod.GET)
    @ParamValid
    public Response<Void> changeSupplierType(@NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id, @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String productId,
                                             @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String branchCompanyId, @NotNull(message = MessageConstantUtil.NOT_EMPTY) @Range(min = 0, max = 1, message = MessageConstantUtil.STATUS_RANGE) Integer supplierType) {
        Response response = null;
        // 获取当前用户信息
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response = new Response();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        ProdPurchaseModifyParamVo prodPurchaseModifyParamVo = new ProdPurchaseModifyParamVo();
        // prodPurchaseModifyParamVo.setModifyUserId("user_changeSupplierType_123");
        prodPurchaseModifyParamVo.setModifyUserId(loginInfoVO.getUserId());
        prodPurchaseModifyParamVo.setId(id);
        prodPurchaseModifyParamVo.setSupplierType(supplierType);
        prodPurchaseModifyParamVo.setProductId(productId);
        prodPurchaseModifyParamVo.setBranchCompanyId(branchCompanyId);
        //vo2Dto
        ProdPurchaseModifyParamDto convert = BeanConvertUtils.convert(prodPurchaseModifyParamVo, ProdPurchaseModifyParamDto.class);
        response = prodPurchaseWriteDubboService.changeSupplierType(convert);
        return response;
    }

    /**
     * 根据 id改变当前的商品采购关系的启禁用状态
     *
     * @param id     采购关系主键
     * @param status 采购关系的状态:0,,失效,1启用
     * @return
     */
    @RequestMapping(value = "/changeProPurchaseStatus", method = RequestMethod.GET)
    @ParamValid
    public Response<Void> changeProPurchaseStatus(@NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id, @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String productId, @RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) @Range(min = 0, max = 1, message = MessageConstantUtil.STATUS_RANGE) Integer status) {
        Response<Void> response = null;
        // 获取当前用户信息
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response = new Response();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }

        ProdPurchaseModifyParamVo prodPurchaseModifyParamVo = new ProdPurchaseModifyParamVo();
        // prodPurchaseModifyParamVo.setModifyUserId("user_changeStatus_123");
        prodPurchaseModifyParamVo.setModifyUserId(loginInfoVO.getUserId());
        prodPurchaseModifyParamVo.setId(id);
        prodPurchaseModifyParamVo.setStatus(status);
        prodPurchaseModifyParamVo.setProductId(productId);
        //vo2Dto
        ProdPurchaseModifyParamDto prodPurchaseModifyParamDto = BeanConvertUtils.convert(prodPurchaseModifyParamVo, ProdPurchaseModifyParamDto.class);
        response = prodPurchaseWriteDubboService.changeProPurchaseStatus(prodPurchaseModifyParamDto);
        return response;
    }


    /**
     * 根据条件查询商品采购关系list
     *
     * @param purchasePriceQueryParamVo 查询条件
     * @return
     */
    @RequestMapping(value = "/queryProdPurchaseExtByCondition", method = RequestMethod.GET)
    public Response<PageResult<ProdPurchaseExtVo>> queryProdPurchaseExtByCondition(@Validated ProdPurchaseQueryParamVo purchasePriceQueryParamVo,HttpServletRequest request) {
        Response<PageResult<ProdPurchaseExtVo>> pageResultResponse = new Response<>();
        //vo2Dto
        ProdPurchaseQueryParamDto purchasePriceQueryParamDto = BeanConvertUtils.convert(purchasePriceQueryParamVo, ProdPurchaseQueryParamDto.class);

        //只能查询当前用户子公司的销售关系(一个账号会有多个子公司权限：yinyuixn)
        LoginInfoVO	userInfo = (LoginInfoVO)request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        List<String> branchCompanyIds=new ArrayList<>();
        if (null==userInfo){
            pageResultResponse.setSuccess(false);
            pageResultResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            pageResultResponse.setErrorMessage("查询用户信息失败,操作终止");
            return pageResultResponse;
        }
        if (Strings.isNullOrEmpty(purchasePriceQueryParamVo.getBranchCompanyId())){
            branchCompanyIds=this.queryUserCompanyIds(userInfo.getUserId());
        }else {
            branchCompanyIds.add(purchasePriceQueryParamVo.getBranchCompanyId());
        }

        purchasePriceQueryParamDto.setBranchCompanyIds(branchCompanyIds);
        Response<PageResult<ProdPurchaseExtDto>> dtoPageResultResponse = prodPurchaseQueryDubboService.queryProdPurchaseExtByCondition(purchasePriceQueryParamDto);

        //错误的响应
        if (!CommonsEnum.RESPONSE_200.getCode().equals(dtoPageResultResponse.getCode())) {
            return BeanConvertUtils.convert(dtoPageResultResponse, Response.class);
        }
        PageResult<ProdPurchaseExtDto> resultObject = dtoPageResultResponse.getResultObject();
        //dto2vo
        List<ProdPurchaseExtVo> voList = BeanConvertUtils.convertList(resultObject.getData(), ProdPurchaseExtVo.class);
        PageResult<ProdPurchaseExtVo> pageResult = new PageResult<>();
        pageResult.setData(voList);
        pageResult.setPageNum(resultObject.getPageNum());
        pageResult.setPageSize(resultObject.getPageSize());
        pageResult.setTotal(resultObject.getTotal());
        pageResultResponse.setResultObject(pageResult);
        pageResultResponse.setErrorMessage(dtoPageResultResponse.getErrorMessage());
        pageResultResponse.setSuccess(dtoPageResultResponse.isSuccess());
        return pageResultResponse;
    }

    /**
     * 批量更新商品的采购关系的启停用(采购关系的状态:0,,失效,1启用)
     *
     * @param proPurchaseBatchChangeStatusVo
     * @return
     */
    @RequestMapping(value = "/batchChangeProPurchaseStatus", method = RequestMethod.POST)
    public Response<Void> batchChangeProPurchaseStatus(@RequestBody @Validated ProPurchaseBatchChangeStatusVo proPurchaseBatchChangeStatusVo) {
        Response<Void> response = new Response<>();
        // 获取当前用户信息
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        proPurchaseBatchChangeStatusVo.setModifyUserId(loginInfoVO.getUserId());
        // proPurchaseBatchChangeStatusVo.setModifyUserId("user_batch_modify_123");
        List<String> productIdList = proPurchaseBatchChangeStatusVo.getProductIdList();

        List<String> newProductList = new ArrayList<>();
        for (int i = 0; i < productIdList.size(); i++) {
            //获取商品信息
            Response<ProductIndexDto> productDtoResponse = indexDubboService.queryByProductId(productIdList.get(i));
            //查询商品是否有效
            if (CommonsEnum.RESPONSE_200.getCode().equals(productDtoResponse.getCode())) {

                ProductIndexDto resultObject = productDtoResponse.getResultObject();
                if (null != resultObject.getSupplyChainStatus() && "2".equals(resultObject.getSupplyChainStatus())) {//有效状态的才进行处理
                    newProductList.add(productIdList.get(i));
                }
            }
        }
        if (newProductList.size() > 0) {
            proPurchaseBatchChangeStatusVo.setProductIdList(newProductList);
            //vo2dto
            ProPurchaseBatchChangeStatusDto proPurchaseBatchChangeStatusDto = BeanConvertUtils.convert(proPurchaseBatchChangeStatusVo, ProPurchaseBatchChangeStatusDto.class);
            response = prodPurchaseWriteDubboService.batchChangeProPurchaseStatus(proPurchaseBatchChangeStatusDto);
        } else {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_10027.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_10027.getName());
        }
        return response;
    }


    /**
     * 根据商品ids批量查询采购关系
     *
     * @param productIds
     * @return
     *//*
    @RequestMapping(value = "/queryProdPurchaseExtVoListByProductIds", method = RequestMethod.GET)
    public Response<List<ProdPurchaseExtVo>> queryProdPurchaseExtVoListByProductIds(String productIds) {
        Response<List<ProdPurchaseExtVo>> response = new Response<>();
        //切分字符串
        List<String> list = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(productIds);
        if (list.size() > 0) {//只有当lissize大于0才能查询
            Response<Map<String, List<ProdPurchaseExtDto>>> response1 = prodPurchaseQueryDubboService.queryProdPurchaseExtVoMapByProductIds(list);
            response1.getResultObject();
            return response;
        } else {//查询条件为空
            response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
            response.setCode(CommonsEnum.RESPONSE_400.getCode());
            response.setSuccess(false);
        }
        return response;
    }*/

    /**
     * 根据商品id查询商品信息
     *
     * @param productId 商品id
     * @return
     */
    @RequestMapping(value = "/getProductById", method = RequestMethod.GET)
    @ParamValid
    public Response<ProdProductVo> getProductById(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String productId) {

        Response<ProdProductVo> response = new Response<>();

        //1.获取商品信息
        Response<ProductIndexDto> productDtoResponse = indexDubboService.queryByProductId(productId);
        //查询商品信息出错响应判断
        if (!CommonsEnum.RESPONSE_200.getCode().equals(productDtoResponse.getCode())) {
            return BeanConvertUtils.convert(productDtoResponse, Response.class);
        }
        ProductIndexDto resultObject = productDtoResponse.getResultObject();
        if (null == resultObject) {//为空 判定
            response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
            response.setSuccess(true);
            return response;
        }
        //dto2Vo
/*        Integer horizontalProductNum = resultObject.getHorizontalProductNum() == null ? 0 : resultObject.getHorizontalProductNum();
        Integer heightProductNum = resultObject.getHeightProductNum() == null ? 0 : resultObject.getHeightProductNum();
        Integer verticalProductNum = resultObject.getVerticalProductNum() == null ? 0 : resultObject.getVerticalProductNum();
        int defaultPurchaseInsideNumber = horizontalProductNum * heightProductNum * verticalProductNum;//默认采购内装数*/
        int defaultPurchaseInsideNumber = 1;//默认采购内装数为1
        ProdProductVo prodProductVo = BeanConvertUtils.convert(resultObject, ProdProductVo.class);
        prodProductVo.setPurchaseInsideNumber(defaultPurchaseInsideNumber);
        prodProductVo.setSalesInsideNumber(defaultPurchaseInsideNumber);//设置销售内装数
        prodProductVo.setSuggestPrice(resultObject.getPrice());//设置建议零售价
        prodProductVo.setPurchasePrice(resultObject.getGuidePurchasePrice());
        prodProductVo.setDeliveryDay(Integer.parseInt(resultObject.getDeliveryTime() == null ? "0" : resultObject.getDeliveryTime()));//承诺发货时间
        prodProductVo.setBrandName(resultObject.getBrand() == null ? null : resultObject.getBrand().getName());//获取品牌名
        response.setCode(productDtoResponse.getCode());
        response.setSuccess(true);
        response.setErrorMessage(productDtoResponse.getErrorMessage());
        response.setResultObject(prodProductVo);
        return response;
    }


    /**
     * 采购价格变更批量导入
     * @param file
     * @param session
     * @return
     */
    @RequestMapping(value = "purchasePriceChangeUpload", method = RequestMethod.POST)
    public Response purchasePriceChangeUpload(MultipartFile file, HttpSession session){
        log.info("/sc/prodPurchase/purchasePriceChangeUpload 收到导入请求， Time:{}", DateUtil.format(new Date()));
        Response response = new Response();
        if (file == null || file.isEmpty()){
            log.error("进价变更Excel导入文件数据为空："+ CommonsEnum.RESPONSE_10047.getName());
            response.setErrorMessage(CommonsEnum.RESPONSE_10047.getName());
            response.setCode(CommonsEnum.RESPONSE_10047.getCode());
            response.setSuccess(false);
            return response;
        }
        if(!FileType.fileTypeMap.get("excel").contains(file.getContentType())){
            log.error("进价变更Excel导入格式错误："+ CommonsEnum.RESPONSE_10048.getName());
            response.setErrorMessage(CommonsEnum.RESPONSE_10048.getName());
            response.setCode(CommonsEnum.RESPONSE_10048.getCode());
            response.setSuccess(false);
            return response;
        }
        // 获取当前用户信息
        LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
        try {
            //获取子公司
            List<String> branchCompanyIds=new ArrayList<>();
            if (Strings.isNullOrEmpty(attribute.getCompanyId())){
                branchCompanyIds=this.queryUserCompanyIds(attribute.getUserId());
            }
            List<ProdPurchasePriceUpdateDto> list = ExcelImport.excelImport(file.getInputStream(), "purchasePriceUpdate", ProdPurchasePriceUpdateDto.class);
            response = prodPurchaseInfoImportWriteDubboService.prodPurchaseListImport(list, attribute.getUserId(), branchCompanyIds);
        } catch (Exception e) {
            log.error("采购进价变更申请Excel导入失败\n{}", ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("采购进价变更申请Excel导入失败");
            response.setSuccess(false);
        }
        return response;
    }

    /**
     * 采购进价变更导入列表导出
     * @param paramVo
     * @return
     */
    @RequestMapping(value = "purchasePriceChangeExport", method = RequestMethod.GET)
    public void purchasePriceChangeExport(ProdPurchasePriceUpdateParamVo paramVo, HttpServletResponse resp, HttpSession session){
        log.info("/sc/prodPurchase/purchasePriceChangeExport，参数为：{}", JSON.toJSONString(paramVo));
        // 获取当前用户信息
        LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
        // 获取数据
        paramVo.setPageNum(1);
        paramVo.setPageSize(Integer.MAX_VALUE);
        ProdPurchasePriceUpdateParamDto paramDto = BeanConvertUtils.convert(paramVo, ProdPurchasePriceUpdateParamDto.class);
        paramDto.setUserId(attribute.getUserId());
        Response<PageResult<ProdPurchaseInfoImportDto>> listResponse = prodPurchaseInfoImportQueryDubboService.getProdPurchaseInfoImportByParam(paramDto);
        List<ProdPurchaseInfoImportDto> list = listResponse.getResultObject().getData();
        // 导出
        try {
            excelExportService.excelExport("updatePurchasePriceExport", list, resp);
        } catch (Exception e) {
            log.error("采购进价变更Excel导出失败\n{}",ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * 采购进价变更导入列表查询
     * @param paramVo
     * @return
     */
    @RequestMapping(value = "purchasePriceChangeList", method = RequestMethod.GET)
    public Response<PageResult<ProdPurchasePriceUpdateVo>> purchasePriceChangeList(ProdPurchasePriceUpdateParamVo paramVo, HttpSession session){
        log.info("/sc/prodPurchase/purchasePriceChangeList，参数为：{}", JSON.toJSONString(paramVo));
        Response<PageResult<ProdPurchasePriceUpdateVo>> response = new Response<>();
        // 获取当前用户信息
        LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
        // 获取数据
        ProdPurchasePriceUpdateParamDto paramDto = BeanConvertUtils.convert(paramVo, ProdPurchasePriceUpdateParamDto.class);
        paramDto.setUserId(attribute.getUserId());
        Response<PageResult<ProdPurchaseInfoImportDto>> listResponse = prodPurchaseInfoImportQueryDubboService.getProdPurchaseInfoImportByParam(paramDto);
        // 封装响应
        PageResult<ProdPurchasePriceUpdateVo> pageResult = new PageResult<>();
        List<ProdPurchasePriceUpdateVo> result = BeanConvertUtils.convertList(listResponse.getResultObject().getData(), ProdPurchasePriceUpdateVo.class);
        pageResult.setData(result);
        pageResult.setTotal(listResponse.getResultObject().getTotal());
        pageResult.setPageSize(listResponse.getResultObject().getPageSize());
        pageResult.setPageNum(listResponse.getResultObject().getPageNum());

        response.setResultObject(pageResult);
        response.setCode(listResponse.getCode());
        response.setErrorMessage(listResponse.getErrorMessage());
        response.setSuccess(listResponse.isSuccess());
        return response;
    }


    /**
     * @Description: 创建提交变价单
     * @author kangdong
     * @date 2017/12/8 17:50
     * @return
     */
    @RequestMapping(value = "createPurchase", method = RequestMethod.POST)
    public Response<Long> createPurchase(@RequestBody Map map) {
        Long id = MapUtils.getLong(map, "importsId");
        LoginInfoVO attribute = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        return prodPurchaseInfoImportWriteDubboService.updateProdPurchaseInfoImport(id, attribute.getUserId());
    }


    /**
     * 根据子公司id 商品id 查询商品的采购价（优先取主供应商）
     * @param productId
     * @param branchCompanyId
     * @author yinyuxin
     * @return
     */
    @RequestMapping(value = "queryPurchasePriceForSellPrice",method = RequestMethod.GET)
    public Response<BigDecimal> queryPurchasePriceForSellPrice(@Valid @NotEmpty String productId,@Valid @NotEmpty String branchCompanyId){
        return prodPurchaseQueryDubboService.queryPurchasePriceForProdSell(productId,branchCompanyId);
    }

    /**
     * 采购价格变更申请批量导入Excel模板
     * @param httpServletResponse
     */
    @RequestMapping(value = "purchasePriceChangeExcelTemplate", method = RequestMethod.GET)
    public void purchasePriceChangeExcelTemplate(HttpServletResponse httpServletResponse){
        try {
            excelExportService.excelExport("purchasePriceChangeExcelTemplate", null, httpServletResponse);
        } catch (Exception e) {
            log.error("purchasePriceExcelTemplate error --> {}", ExceptionUtils.getFullStackTrace(e));
        }
    }
}
