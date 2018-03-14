package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.AdrTypeEnum;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.staticvalue.PurchaseRefundStatusEnum;
import com.yatang.sc.common.utils.ConvertUtil;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemQueryParamDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundAuditQueryDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundAuditResultDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundItemDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundQueryListDto;
import com.yatang.sc.facade.dto.pm.ProcessAuditLogParamDto;
import com.yatang.sc.facade.dto.pm.ProcessDefinitionDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundWriteDubboService;
import com.yatang.sc.facade.dubboservice.ProcessDefinitionQueryDubboService;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelOrdersDto;
import com.yatang.sc.kidd.service.KiddOrderCancelService;
import com.yatang.sc.operation.util.NumberToCN;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundAuditListQueryVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundAuditListResultVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundBrandVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundCancelVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundItemVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundListQueryVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundListResultVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundProductVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundQueryProductParamVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundVo;
import com.yatang.sc.operation.vo.pm.ProcessAuditLogParamVo;
import com.yatang.sc.operation.vo.pm.ProcessDefinitionVo;
import com.yatang.sc.operation.vo.prod.BranchCompanyInfoVo;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.sc.web.view.XlsData;
import com.yatang.xc.mbd.biz.org.dto.StoreDetailsDto;
import com.yatang.xc.mbd.biz.org.dubboservice.StoreDetailsDubboService;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import com.yatang.xc.mbd.web.system.vo.RoleVO;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @description: 采购退货单action
 * @author: yinyuxin
 * @date: 2017/10/17 15:55
 * @version: v1.0
 */
@RestController
@RequestMapping("/sc/pmPurchaseRefund")
public class PmPurchaseRefundAction extends BaseAction {

	private static final Logger					log	= LoggerFactory.getLogger(PmPurchaseRefundAction.class);

	@Autowired
	private PmPurchaseRefundWriteDubboService	pmPurchaseRefundWriteDubboService;

	@Autowired
	private PmPurchaseRefundQueryDubboService	pmpurchaseRefundQueryDubboService;

	@Autowired
	private PmPurchaseOrderQueryDubboService	pmPurchaseOrderQueryDubboService;

	@Autowired
	private WarehouseLogicQueryDubboService		warehouseLogicQueryDubboService;

	@Autowired
	private StoreDetailsDubboService			storeDetailsDubboService;

	@Autowired
	private ProcessDefinitionQueryDubboService	processDefinitionQueryDubboService;

	@Autowired
	private KiddOrderCancelService				kiddOrderCancelService;

	@Autowired
	private ItemLocInventoryDubboService		itemLocInventoryDubboService;

	@Value("${image.view.domain}")
	private String								imageViewDomain;											// 图片域名



	/**
	 * 
	 * <method description>退货单列表查询，暂时不考虑门店
	 * 
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundListQueryVo
	 * @return
	 */
	@RequestMapping(value = "/queryPurchaseRefundList", method = RequestMethod.GET)
	public Response<PageResult<PmPurchaseRefundListResultVo>> queryPurchaseRefundList(
			@Valid PmPurchaseRefundListQueryVo pmPurchaseRefundListQueryVo, HttpSession session) {

		log.info("/sc/pmPurchaseRefund/queryPurchaseRefundList收到请求，请求参数为pmPurchaseRefundListQueryVo={}",
				JSON.toJSONString(pmPurchaseRefundListQueryVo));
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		log.info("当前用户登录信息为：{}", JSON.toJSONString(currentUser));
		if (null == currentUser) {
			return getFailResponse();
		}
		// 公司权限
		List<String> branchCompanyIds = this.queryUserCompanyIds(currentUser.getUserId());

		// TODO 判断门店账户和仓库账户,如果是门店则判断传入参数只能是门店类型和门店地址

		PmPurchaseRefundQueryListDto pmPurchaseRefundQueryListDto = BeanConvertUtils
				.convert(pmPurchaseRefundListQueryVo, PmPurchaseRefundQueryListDto.class);
		pmPurchaseRefundQueryListDto.setBranchCompanyIds(branchCompanyIds);
		Response<PageResult<PmPurchaseRefundDto>> result = pmpurchaseRefundQueryDubboService
				.queryPmPurchaseRefund(pmPurchaseRefundQueryListDto);
		log.info("查询退货列表返回结果：{}", JSON.toJSONString(result));
		if (null == result || result.getCode().equals(CommonsEnum.RESPONSE_500.getCode())) {
			return getFailResponse();
		}
		if (result.getCode().equals(CommonsEnum.RESPONSE_10006.getCode())) {
			return getSuccessResponse();
		}
		List<PmPurchaseRefundListResultVo> list = new ArrayList<>();
		PageResult<PmPurchaseRefundListResultVo> pageResult = new PageResult<>();
		pageResult.setPageNum(result.getResultObject().getPageNum());
		pageResult.setPageSize(result.getResultObject().getPageSize());
		pageResult.setTotal(result.getResultObject().getTotal());
		pageResult.setData(list);
		transData(result, list);
		log.info("退货查询列表转换为前端需要的数据：{}", JSON.toJSONString(list));

		return getSuccessResponse(pageResult);
	}



	/**
	 * 
	 * <method description>退货单列表查询结果导出，暂时不考虑门店
	 * 
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundListQueryVo
	 * @return
	 */
	@RequestMapping(value = "/exportPurchaseRefundList", method = RequestMethod.GET)
	public ModelAndView exportPurchaseRefundList(PmPurchaseRefundListQueryVo pmPurchaseRefundListQueryVo,
			HttpSession session) {
		log.info("/sc/pmPurchaseRefund/exportPurchaseRefundList收到请求，请求参数为pmPurchaseRefundListQueryVo={}",
				JSON.toJSONString(pmPurchaseRefundListQueryVo));
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		log.info("当前用户登录信息为：{}", JSON.toJSONString(currentUser));
		if (null == currentUser) {
			return null;
		}
		// 公司权限
		List<String> branchCompanyIds = this.queryUserCompanyIds(currentUser.getUserId());
		pmPurchaseRefundListQueryVo.setPageNum(1);
		pmPurchaseRefundListQueryVo.setPageSize(Integer.MAX_VALUE);
		PmPurchaseRefundQueryListDto pmPurchaseRefundQueryListDto = BeanConvertUtils
				.convert(pmPurchaseRefundListQueryVo, PmPurchaseRefundQueryListDto.class);
		pmPurchaseRefundQueryListDto.setBranchCompanyIds(branchCompanyIds);
		Response<PageResult<PmPurchaseRefundDto>> result = pmpurchaseRefundQueryDubboService
				.queryPmPurchaseRefund(pmPurchaseRefundQueryListDto);
		log.info("查询退货列表返回结果：{}", JSON.toJSONString(result));
		List<PmPurchaseRefundListResultVo> list = new ArrayList<>();
		transData(result, list);
		XlsData xlsData = new XlsData("退货单列表");
		xlsData.setData(PmPurchaseRefundListResultVo.class, list);
		xlsData.setPattern("YYYY-MM-dd hh:mm:ss");
		return getXlsModelAndView(xlsData);
	}



	private void transData(Response<PageResult<PmPurchaseRefundDto>> result, List<PmPurchaseRefundListResultVo> list) {
		for (PmPurchaseRefundDto pmPurchaseRefundDto : result.getResultObject().getData()) {
			// 0:仓库;1:门店
			// 状态:0:制单;1:已提交;2:已审核;3:已拒绝;4:待退货;5:已退货;6:已取消;7:取消失败;8:异常
			PmPurchaseRefundListResultVo pmPurchaseRefundListResultVo = BeanConvertUtils.convert(pmPurchaseRefundDto,
					PmPurchaseRefundListResultVo.class);
			pmPurchaseRefundListResultVo
					.setSupplier(pmPurchaseRefundDto.getSpNo() + "-" + pmPurchaseRefundDto.getSpName());
			pmPurchaseRefundListResultVo
					.setSupplierAddress(pmPurchaseRefundDto.getSpAdrNo() + "-" + pmPurchaseRefundDto.getSpAdrName());

			pmPurchaseRefundListResultVo.setAdrTypeName(AdrTypeEnum.getNameByIndex(pmPurchaseRefundDto.getAdrType()));
			pmPurchaseRefundListResultVo
					.setStatusName(PurchaseRefundStatusEnum.getNameByIndex(pmPurchaseRefundDto.getStatus()));
			list.add(pmPurchaseRefundListResultVo);
		}
	}



	/**
	 *
	 * <method description>根据用户角色查询退货单审批列表
	 *
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundAuditListQueryVo
	 * @return
	 */
	/*
	@RequestMapping(value = "/queryAuditPurchaseRefundList", method = RequestMethod.GET)
	public Response<PageResult<PmPurchaseRefundAuditListResultVo>> queryAuditPurchaseRefundList(
			@Valid PmPurchaseRefundAuditListQueryVo pmPurchaseRefundAuditListQueryVo, HttpSession session) {

		log.info("/sc/pmPurchaseRefund/queryAuditPurchaseRefundList收到请求，请求参数为={}",
				JSON.toJSONString(pmPurchaseRefundAuditListQueryVo));
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		String companyId = null;
		log.info("当前用户登录信息为：{}", JSON.toJSONString(currentUser));
		if (null == currentUser) {
			getFailResponse();
		}
		// 类型0，子公司
		if (currentUser.getCompanyType() == 0) {
			if (null == currentUser.getCompanyId()) {
				getFailResponse();
			} else {
				companyId = currentUser.getCompanyId();
			}
		}
		if (null == currentUser.getRoles() || currentUser.getRoles().isEmpty()) {
			getFailResponse();
		}
		PmPurchaseRefundAuditQueryDto pmPurchaseRefundAuditQueryDto = BeanConvertUtils
				.convert(pmPurchaseRefundAuditListQueryVo, PmPurchaseRefundAuditQueryDto.class);
		pmPurchaseRefundAuditQueryDto.setBranchCompanyId(companyId);
		List<String> roles = new ArrayList<>();
		for (RoleVO roleVO : currentUser.getRoles()) {
			roles.add(roleVO.getRoleId() + "");
		}
		pmPurchaseRefundAuditQueryDto.setRoles(roles);
		Response<PageResult<PmPurchaseRefundAuditResultDto>> result = pmpurchaseRefundQueryDubboService
				.queryPmPurchaseRefundAudit(pmPurchaseRefundAuditQueryDto);
		log.info("查询退货列表返回结果：{}", JSON.toJSONString(result));
		if (null == result || result.getCode().equals(CommonsEnum.RESPONSE_500.getCode())) {
			return getFailResponse();
		}
		if (result.getCode().equals(CommonsEnum.RESPONSE_10006.getCode())) {
			return getSuccessResponse();
		}
		List<PmPurchaseRefundAuditListResultVo> list = new ArrayList<>();
		List<PmPurchaseRefundAuditResultDto> data = result.getResultObject().getData();
		for (PmPurchaseRefundAuditResultDto pmPurchaseRefundAuditResultDto : data) {
			PmPurchaseRefundAuditListResultVo pmPurchaseRefundAuditListResultVo = BeanConvertUtils
					.convert(pmPurchaseRefundAuditResultDto, PmPurchaseRefundAuditListResultVo.class);
			pmPurchaseRefundAuditListResultVo.setSupplier(
					pmPurchaseRefundAuditResultDto.getSpNo() + "-" + pmPurchaseRefundAuditResultDto.getSpName());
			pmPurchaseRefundAuditListResultVo.setSupplierAddress(
					pmPurchaseRefundAuditResultDto.getSpAdrNo() + "-" + pmPurchaseRefundAuditResultDto.getSpAdrName());
			pmPurchaseRefundAuditListResultVo
					.setAdrType(AdrTypeEnum.getNameByIndex(pmPurchaseRefundAuditResultDto.getAdrType()));
			pmPurchaseRefundAuditListResultVo
					.setProcessNodeName(pmPurchaseRefundAuditResultDto.getProcessNodeName() + "审批");
			pmPurchaseRefundAuditListResultVo.setProcessStartTime(pmPurchaseRefundAuditResultDto.getCreateTime());
			if (pmPurchaseRefundAuditResultDto.getStatus() > 1) {
				pmPurchaseRefundAuditListResultVo.setProcessEndTime(pmPurchaseRefundAuditResultDto.getAuditTime());
			}
			list.add(pmPurchaseRefundAuditListResultVo);
		}
		PageResult<PmPurchaseRefundAuditListResultVo> pageResult = new PageResult<>();
		pageResult.setPageNum(result.getResultObject().getPageNum());
		pageResult.setPageSize(result.getResultObject().getPageSize());
		pageResult.setTotal(result.getResultObject().getTotal());
		pageResult.setData(list);
		log.info("退货查询列表转换为前端需要的数据：{}", JSON.toJSONString(list));
		return getSuccessResponse(pageResult);
	}
	*/


	/**
	 * 新增采购退货单以及清单
	 *
	 * @author yinyuxin
	 * @param pmPurchaseRefundVo
	 * @return
	 */
	@RequestMapping(value = "createRefundWithItems", method = RequestMethod.POST)
	public Response<Boolean> createRefundWithItems(
			@RequestBody @Validated({ GroupOne.class }) PmPurchaseRefundVo pmPurchaseRefundVo, HttpSession session) {
		// 获取用户登录信息
		LoginInfoVO loginInfoVO = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (null == loginInfoVO) {
			Response<Boolean> response = new Response<>();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("未查询到当前用户信息 ");
			return response;
		}
		pmPurchaseRefundVo.setCreateUserId(loginInfoVO.getUserId());
		pmPurchaseRefundVo.setBranchCompanyId(loginInfoVO.getCompanyId());
		return pmPurchaseRefundWriteDubboService
				.saveRefundWithItems(BeanConvertUtils.convert(pmPurchaseRefundVo, PmPurchaseRefundDto.class));
	}



	/**
	 * 修改采购退货单以及清单
	 *
	 * @author yinyuxin
	 * @param pmPurchaseRefundVo
	 * @return
	 */
	@RequestMapping(value = "updateRefundWithItems", method = RequestMethod.POST)
	public Response<Boolean> updateRefundWithItems(
			@RequestBody @Validated({ GroupOne.class }) PmPurchaseRefundVo pmPurchaseRefundVo, HttpSession session) {

		// 获取用户登录信息
		LoginInfoVO loginInfoVO = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (null == loginInfoVO) {
			Response<Boolean> response = new Response<>();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("未查询到当前用户信息  ");
			return response;
		}
		pmPurchaseRefundVo.setModifyUserId(loginInfoVO.getUserId());
		pmPurchaseRefundVo.setModifyTime(new Date());
		return pmPurchaseRefundWriteDubboService
				.saveRefundWithItems(BeanConvertUtils.convert(pmPurchaseRefundVo, PmPurchaseRefundDto.class));
	}



	/**
	 * @Description: 查询采购退货详情
	 * @author tankejia
	 * @date 2017/10/18- 13:46
	 * @param id
	 */
	@ParamValid
	@RequestMapping(value = "/queryRefundDetailById", method = RequestMethod.GET)
	public Response<PmPurchaseRefundVo> queryRefundDetailById(
			@NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id) {
		Response<PmPurchaseRefundVo> response = new Response<>();
		Response<PmPurchaseRefundDto> resp = pmpurchaseRefundQueryDubboService.queryRefundDetailById(id);
		response.setResultObject(BeanConvertUtils.convert(resp.getResultObject(), PmPurchaseRefundVo.class));
		response.setSuccess(resp.isSuccess());
		response.setErrorMessage(resp.getErrorMessage());
		response.setCode(resp.getCode());
		return response;
	}



	/**
	 * @Description: 批量删除退货单及商品信息
	 * @author huangjianjun
	 * @date 2017年10月26日下午1:52:28
	 */
	@ParamValid
	@RequestMapping(value = "deleteBatchRefundOrder", method = RequestMethod.GET)
	public Response<Boolean> deleteBatchRefundOrder(
			@NotBlank(message = MessageConstantUtil.NOT_EMPTY) String pmRefundOrderIds) {
		return pmPurchaseRefundWriteDubboService
				.deleteBatchRefundOrder(ConvertUtil.strArrToLongArr(pmRefundOrderIds.split(",")));
	}



	/**
	 * @Description: 下载PDF(采购退货单)
	 * @author tankejia
	 * @date 2017/10/18- 13:46
	 * @param id
	 */
	@RequestMapping(value = "/exportPdf", method = { RequestMethod.GET })
	public void exportPdf(Long id, @RequestParam(required = false) Boolean isTest, HttpServletResponse response,
			HttpServletRequest req) throws IOException {
		PmPurchaseRefundVo refundVo;
		if (BooleanUtils.isTrue(isTest)) {
			refundVo = PmPurchaseOrderPdfGenerater.refundVo();
		} else {
			Response<PmPurchaseRefundDto> resp = pmpurchaseRefundQueryDubboService.queryRefundDetailById(id);
			refundVo = BeanConvertUtils.convert(resp.getResultObject(), PmPurchaseRefundVo.class);
		}

		if (null == refundVo) {
			log.error("导出采购退货单PDF时查询采购退货单详情出错，采购退货单id：" + id);
			throw new RuntimeException("导出采购退货单PDF时查询采购退货单详情出错！");
		}

		refundVo.setNumberToCN(NumberToCN.number2CNMontrayUnit(refundVo.getTotalRealRefundMoney()));

		// 退货地点类型为仓库
		if (0 == refundVo.getAdrType()) {
			Response<LogicWarehouseDto> dtoResponse = warehouseLogicQueryDubboService
					.selectLogicWarehouseByPrimaryKey(refundVo.getRefundAdrCode());
			if (dtoResponse != null && dtoResponse.getResultObject() != null) {
				refundVo.setWarehouseOrStoreName(dtoResponse.getResultObject().getWarehouseName());
				refundVo.setWarehouseOrStoreAddress(
						dtoResponse.getResultObject().getPhysicalWarehouseDto().getAddress());
			}
		} else if (1 == refundVo.getAdrType()) { // 地点类型为门店
			Response<StoreDetailsDto> dtoResponse = storeDetailsDubboService
					.queryStoreDatailInfo(refundVo.getRefundAdrCode());
			if (dtoResponse != null && dtoResponse.getResultObject() != null) {
				refundVo.setWarehouseOrStoreName(dtoResponse.getResultObject().getStoreName());
				refundVo.setWarehouseOrStoreAddress(dtoResponse.getResultObject().getStoreAddress());
			}
		}

		PmPurchaseOrderPdfGenerater generater = new PmPurchaseOrderPdfGenerater(imageViewDomain);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		generater.generatePdf(baos, refundVo);

		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setContentLength(baos.size());
		response.setHeader("Content-disposition", "attachment;filename=" + refundVo.getPurchaseRefundNo() + ".pdf");

		// Flush byte array to servlet output stream.
		ServletOutputStream out = response.getOutputStream();
		baos.writeTo(out);
		out.flush();
	}



	/**
	 * 根据采购单号和品牌名称查询品牌值清单
	 *
	 * @author yinyuxin
	 * @param pmPurchaseRefundQueryProductParamVo
	 * @return
	 */
	@RequestMapping(value = "queryPurchaseOrderBrands", method = RequestMethod.GET)
	public Response<PageResult<PmPurchaseRefundBrandVo>> queryPurchaseOrderBrands(
			@Validated({ GroupOne.class }) PmPurchaseRefundQueryProductParamVo pmPurchaseRefundQueryProductParamVo) {
		Response<PageResult<PmPurchaseRefundBrandVo>> response = new Response<>();
		PageResult<PmPurchaseRefundBrandVo> pageResult = new PageResult<>();
		PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto = BeanConvertUtils
				.convert(pmPurchaseRefundQueryProductParamVo, PmPurchaseOrderItemQueryParamDto.class);
		pmPurchaseOrderItemQueryParamDto.setIsSuccess(1);
		Response<PageResult<PmPurchaseOrderItemDto>> dtoResponse = pmPurchaseOrderQueryDubboService
				.selectBrandsByOrderNo(pmPurchaseOrderItemQueryParamDto);
		response.setErrorMessage(dtoResponse.getErrorMessage());
		response.setCode(dtoResponse.getCode());
		response.setSuccess(dtoResponse.isSuccess());
		List<PmPurchaseRefundBrandVo> data = BeanConvertUtils.convertList(dtoResponse.getResultObject().getData(),
				PmPurchaseRefundBrandVo.class);
		pageResult.setTotal(dtoResponse.getResultObject().getTotal());
		pageResult.setData(data);
		pageResult.setPageNum(dtoResponse.getResultObject().getPageNum());
		pageResult.setPageSize(dtoResponse.getResultObject().getPageSize());
		response.setResultObject(pageResult);
		return response;
	}



	/**
	 * 根据采购单号和商品名称或者商品编码或者商品条码查询商品值清单
	 *
	 * @author yinyuxin
	 * @param pmPurchaseRefundQueryProductParamVo
	 * @return
	 */
	@RequestMapping(value = "queryPurchaseOrderProducts", method = RequestMethod.GET)
	public Response<PageResult<PmPurchaseRefundProductVo>> queryPurchaseOrderProducts(
			@Validated({ GroupOne.class }) PmPurchaseRefundQueryProductParamVo pmPurchaseRefundQueryProductParamVo) {
		Response<PageResult<PmPurchaseRefundProductVo>> response = new Response<>();
		PageResult<PmPurchaseRefundProductVo> pageResult = new PageResult<>();
		PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto = BeanConvertUtils
				.convert(pmPurchaseRefundQueryProductParamVo, PmPurchaseOrderItemQueryParamDto.class);
		pmPurchaseOrderItemQueryParamDto.setIsSuccess(1);
		Response<PageResult<PmPurchaseOrderItemDto>> dtoResponse = pmPurchaseOrderQueryDubboService
				.selectProductByOrderNo(pmPurchaseOrderItemQueryParamDto);
		response.setErrorMessage(dtoResponse.getErrorMessage());
		response.setCode(dtoResponse.getCode());
		response.setSuccess(dtoResponse.isSuccess());
		List<PmPurchaseRefundProductVo> data = BeanConvertUtils.convertList(dtoResponse.getResultObject().getData(),
				PmPurchaseRefundProductVo.class);
		pageResult.setTotal(dtoResponse.getResultObject().getTotal());
		pageResult.setData(data);
		pageResult.setPageNum(dtoResponse.getResultObject().getPageNum());
		pageResult.setPageSize(dtoResponse.getResultObject().getPageSize());
		response.setResultObject(pageResult);
		return response;
	}



	/**
	 * 添加采购退货单商品
	 *
	 * @author yinyuxin
	 * @param pmPurchaseRefundQueryProductParamVo
	 * @return
	 */
	@RequestMapping(value = "addRefundProducts", method = RequestMethod.GET)
	public Response<List<PmPurchaseRefundItemVo>> addRefundProducts(
			@Validated({ GroupOne.class }) PmPurchaseRefundQueryProductParamVo pmPurchaseRefundQueryProductParamVo) {
		Response<List<PmPurchaseRefundItemVo>> response = new Response<>();
		PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto = BeanConvertUtils
				.convert(pmPurchaseRefundQueryProductParamVo, PmPurchaseOrderItemQueryParamDto.class);
		pmPurchaseOrderItemQueryParamDto.setIsSuccess(1);
		Response<List<PmPurchaseRefundItemDto>> dtoResponse = pmPurchaseOrderQueryDubboService
				.addRefundProducts(pmPurchaseOrderItemQueryParamDto);
		response.setSuccess(dtoResponse.isSuccess());
		response.setCode(dtoResponse.getCode());
		response.setErrorMessage(dtoResponse.getErrorMessage());
		response.setResultObject(
				BeanConvertUtils.convertList(dtoResponse.getResultObject(), PmPurchaseRefundItemVo.class));
		return response;
	}



	/**
	 * 查看审批流程
	 *
	 * @author yinyuxin
	 * @param processType
	 *            流程类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护
	 * @return
	 */
	/*
	@RequestMapping(value = "queryProcessDefinitions", method = RequestMethod.GET)
	public Response<List<ProcessDefinitionVo>> queryProcessDefinitions(@RequestParam @NotNull Long processType,
			Long businessId, HttpServletRequest request) {
		Response<List<ProcessDefinitionVo>> response = new Response<>();
		ProcessDefinitionDto processDefinitionDto = new ProcessDefinitionDto();
		LoginInfoVO userInfo = (LoginInfoVO) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		String companyId = null;
		if (userInfo.getCompanyType() == 0) {
			companyId = userInfo.getCompanyId();
		}
		processDefinitionDto.setBranchCompanyId(companyId);
		processDefinitionDto.setType(processType);
		processDefinitionDto.setBusinessId(businessId);
		Response<List<ProcessDefinitionDto>> response1 = processDefinitionQueryDubboService
				.queryProcessDefinitions(processDefinitionDto);
		response.setSuccess(response1.isSuccess());
		response.setCode(response1.getCode());
		response.setErrorMessage(response1.getErrorMessage());
		if (response1.getResultObject() != null && response1.getResultObject().size() > 0) {
			response.setResultObject(
					BeanConvertUtils.convertList(response1.getResultObject(), ProcessDefinitionVo.class));
		}
		return response;
	}

	*/


	/**
	 * 获取采购退货单号
	 * 
	 * @return
	 */
	@RequestMapping(value = "getRefundNo", method = RequestMethod.GET)
	public Response<String> getRefundNo() {
		return pmPurchaseRefundWriteDubboService.getRefundNo();
	}



	/**
	 * 审批采购退货单
	 *
	 * @param processAuditLogParamVo
	 * @param session
	 * @return
	 */
	/*
	@RequestMapping(value = "approveRefund", method = RequestMethod.POST)
	public Response<Boolean> approveRefund(
			@RequestBody @Validated({ GroupOne.class }) ProcessAuditLogParamVo processAuditLogParamVo,
			HttpSession session) {
		log.info("审批采购退货单,参数：{}", JSON.toJSONString(processAuditLogParamVo));
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (null == currentUser) {
			getFailResponse();
		}
		processAuditLogParamVo.setAuditUserId(currentUser.getUserId());
		processAuditLogParamVo.setAuditUser(currentUser.getRealName());
		processAuditLogParamVo.setBranchCompanyId(currentUser.getCompanyId());
		Response<Boolean> dtoResponse = pmPurchaseRefundWriteDubboService
				.approveRefund(BeanConvertUtils.convert(processAuditLogParamVo, ProcessAuditLogParamDto.class));
		return dtoResponse;
	}
	*/


	/**
	 * 取消采购退货单
	 * 
	 * @param param
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "cancel", method = RequestMethod.GET)
	public Response<Boolean> cancel(@Valid PmPurchaseRefundCancelVo param, HttpSession session) {
		log.info("取消采购退货单,参数：{}", JSON.toJSONString(param));
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (null == currentUser) {
			return getFailResponse();
		}
		PmPurchaseRefundDto beanParam = new PmPurchaseRefundDto();
		beanParam.setPurchaseRefundNo(param.getPurchaseRefundNo());

		if (PublicEnum.ZERO.getCodeStr().equals(param.getAdrType().toString())) {
			// 获取仓库信息
			Response<LogicWarehouseDto> wareRes = warehouseLogicQueryDubboService
					.selectLogicWarehouseByPrimaryKey(param.getRefundAdrCode());
			LogicWarehouseDto wareDto = wareRes.getResultObject();
			Assert.notNull(wareDto, CommonsEnum.RESPONSE_20023.getName());

			// 组装退货参数
			KiddCancelAllOrdersDto saleOrderCancelDto = new KiddCancelAllOrdersDto();
			List<KiddCancelOrdersDto> list = new ArrayList<KiddCancelOrdersDto>();
			KiddCancelOrdersDto cancelDto = new KiddCancelOrdersDto();

			cancelDto.setOrderType(KiddOrderLogisticsType.KIDD_CGTH);
			cancelDto.setOrgCode(wareDto.getBranchCompanyCode());
			cancelDto.setWarehouseCode(wareDto.getWarehouseCode());
			cancelDto.setOrderCode(param.getPurchaseRefundNo());
			list.add(cancelDto);

			saleOrderCancelDto.setType(2);
			saleOrderCancelDto.setOrderType(KiddOrderLogisticsType.KIDD_CGTH);
			saleOrderCancelDto.setOrders(list);
			Response<String> response = kiddOrderCancelService.cancel(saleOrderCancelDto);
			log.info("取消采购退货单,返回结果：{}", JSON.toJSONString(response.getResultObject()));
			// 第三方仓库取消成功
			if (response.isSuccess()) {
				log.info("取消采购退货单调用第三方接口成功");
				// 1:更新退货单状态为已取消
				beanParam.setStatus(Integer.parseInt(PublicEnum.SIX.getCodeStr()));
				// 2:取消成功释放退货预留
				Response<PmPurchaseRefundDto> refundOrderRes = pmpurchaseRefundQueryDubboService
						.queryRefundDetailById(param.getId());
				PmPurchaseRefundDto refundOrder = refundOrderRes.getResultObject();
				List<PmPurchaseRefundItemDto> items = refundOrder.getPmPurchaseRefundItems();
				List<ItemInventoryQueryParamDto> inventoryList = new ArrayList<ItemInventoryQueryParamDto>();
				ItemInventoryQueryParamDto inventoryParamDto = new ItemInventoryQueryParamDto();
				for (PmPurchaseRefundItemDto pmPurchaseRefundItemDto : items) {
					inventoryParamDto.setBranchCompanyId(refundOrder.getBranchCompanyId());
					inventoryParamDto.setLogicWareHouseCode(refundOrder.getRefundAdrCode());
					inventoryParamDto.setProductId(pmPurchaseRefundItemDto.getProductId());
					inventoryParamDto.setProductCode(pmPurchaseRefundItemDto.getProductCode());
					inventoryParamDto.setRefundAmount(-pmPurchaseRefundItemDto.getRefundAmount());
					inventoryList.add(inventoryParamDto);
				}
				log.info("取消采购退货单,更新库存请求参数：{}", JSON.toJSONString(inventoryList));
				Response<Boolean> res = itemLocInventoryDubboService.updateBatchRtvQty(inventoryList);
				log.info("取消采购退货单,更新库存返回结果：{}", JSON.toJSONString(res));
			} else {
				log.info("取消采购退货单调用第三方接口失败,失败原因{}", JSON.toJSONString(response));
				// 失败更新退货单状态为取消失败
				beanParam.setStatus(Integer.parseInt(PublicEnum.SEVEN.getCodeStr()));
			}
		} else {
			// 1:更新退货单状态为已取消
			beanParam.setStatus(Integer.parseInt(PublicEnum.SIX.getCodeStr()));
		}
		return pmPurchaseRefundWriteDubboService.updateStatus(beanParam);
	}
}
