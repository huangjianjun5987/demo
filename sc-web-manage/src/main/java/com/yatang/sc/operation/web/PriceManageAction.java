package com.yatang.sc.operation.web;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import com.google.common.base.Throwables;
import com.yatang.sc.common.staticvalue.FileType;
import com.yatang.sc.operation.util.DateUtil;
import com.yatang.sc.operation.util.SessionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Strings;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.RegionBasicDto;
import com.yatang.sc.facade.dto.prod.ProdBatchParameterDto;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeListResultDto;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeQueryDto;
import com.yatang.sc.facade.dto.prod.ProdSellInfoImportDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceQueryParamDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceUpdateDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceUpdateParamDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.facade.dubboservice.GoodsPriceWriteDubboService;
import com.yatang.sc.facade.dubboservice.ProdPriceChangeQueryDubboService;
import com.yatang.sc.facade.dubboservice.ProdPurchaseInfoImportWriteDubboService;
import com.yatang.sc.facade.dubboservice.ProdSellInfoImportQueryDubboService;
import com.yatang.sc.facade.dubboservice.ProdSellInfoImportWriteDubboService;
import com.yatang.sc.operation.vo.ProdBatchParameterVo;
import com.yatang.sc.operation.vo.ProdPriceChangeListResultVo;
import com.yatang.sc.operation.vo.ProdPriceChangeQueryVo;
import com.yatang.sc.operation.vo.ProdSellPriceUpdateParamVo;
import com.yatang.sc.operation.vo.ProdSellPriceUpdateVo;
import com.yatang.sc.operation.vo.RegionBasicVo;
import com.yatang.sc.operation.vo.prod.BranchCompanyInfoVo;
import com.yatang.sc.operation.vo.prod.ProdSellInfoListVo;
import com.yatang.sc.operation.vo.prod.ProdSellPriceInfoParameterVo;
import com.yatang.sc.operation.vo.prod.ProdSellPriceInfoVo;
import com.yatang.sc.operation.vo.prod.ProdSellPriceQueryParamVo;
import com.yatang.sc.operation.vo.prod.ProdSellSectionPriceVo;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.common.excel.service.impl.ExcelExportService;
import com.yatang.sc.web.common.excel.service.impl.ExcelImport;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.view.XlsData;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述:销售价格管理
 * @类名:PriceManageAction
 * @作者: lvheping
 * @创建时间: 2017/5/26 9:31
 * @版本: v1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("sc/prodSell")
public class PriceManageAction extends BaseAction {

	private final GoodsPriceQueryDubboService		goodsPriceQueryDubboService;

	private final GoodsPriceWriteDubboService		goodsPriceWriteDubboService;

	private final ProductScIndexDubboService		indexDubboService;

	private final OrganizationService				organizationService;

	private final ProdPriceChangeQueryDubboService	prodPriceChangeQueryDubboService;

	private final ProdSellInfoImportWriteDubboService prodSellInfoImportWriteDubboService;

	private final ProdSellInfoImportQueryDubboService prodSellInfoImportQueryDubboService;

	private final ExcelExportService excelExportService;


	/**
	 * 根据传入参数查询销售价格信息管理
	 *
	 * @param prodSellPriceQueryParamVo
	 * @return
	 */
	@RequestMapping(value = "/findPriceInfo", method = { RequestMethod.GET })
	public Response<ProdSellInfoListVo> findPriceinfo(
			@Validated(DefaultGroup.class) ProdSellPriceQueryParamVo prodSellPriceQueryParamVo,
			HttpServletRequest request) {
		log.info("sc/prodSell/findPriceInfo->param:" + JSON.toJSONString(prodSellPriceQueryParamVo));
		Response<ProdSellInfoListVo> response=new Response<>();
		// 只能查询当前用户子公司的销售关系
		LoginInfoVO userInfo = (LoginInfoVO) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (null==userInfo){
			response.setErrorMessage("查询用户信息失败,操作终止");
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(false);
			return response;
		}
		List<String> branchCompanyIds=new ArrayList<>();
		if (Strings.isNullOrEmpty(prodSellPriceQueryParamVo.getBranchCompanyId())){
			branchCompanyIds=this.queryUserCompanyIds(userInfo.getUserId());
		}else {
			branchCompanyIds.add(prodSellPriceQueryParamVo.getBranchCompanyId());
		}

		ProdSellPriceQueryParamDto convert = BeanConvertUtils.convert(prodSellPriceQueryParamVo,
				ProdSellPriceQueryParamDto.class);
		// 查询商品销售价格信息
		convert.setBranchCompanyIds(branchCompanyIds);
		Response<PageResult<ProdSellPriceInfoDto>> pageResultResponse = goodsPriceQueryDubboService
				.pageQuerySellPrice(convert);
		if (!pageResultResponse.isSuccess()) {
			return BeanConvertUtils.convert(pageResultResponse, Response.class);
		}
		PageResult<ProdSellPriceInfoDto> resultObject = pageResultResponse.getResultObject();
		PageResult convert1 = BeanConvertUtils.convert(resultObject, PageResult.class);
		List<ProdSellPriceInfoVo> goodsPriceInfoVos = BeanConvertUtils.convertList(resultObject.getData(),
				ProdSellPriceInfoVo.class);
		convert1.setData(goodsPriceInfoVos);
		ProdSellInfoListVo prodSellInfoListVo = new ProdSellInfoListVo();
		prodSellInfoListVo.setSellPriceInfoVos(convert1);
		response = BeanConvertUtils.convert(pageResultResponse, Response.class);
		response.setResultObject(prodSellInfoListVo);
		return response;
	}



	/**
	 * 根据 id删除销售区域价格
	 *
	 * @param id
	 *            区域价格管理主表的id
	 * @return
	 */
	@RequestMapping(value = "/deleteSellPriceById", method = RequestMethod.GET)
	public Response<Boolean> deleteSellPriceById(
			@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id,
			@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String productId, HttpSession session) {
		log.info("sc/prodSell/deletePurchasePriceById->id:" + id);

		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (attribute == null || attribute.getUserId() == null) {
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		String userId = attribute.getUserId();
		Response<Boolean> response = goodsPriceWriteDubboService.deleteSellPriceById(id, productId, userId);
		return response;
	}



	/**
	 * 根据供应商id查询查询下面的市
	 *
	 * @author yangshuang
	 * @return
	 */
	@RequestMapping(value = "/queryRegionListBySupplierId", method = RequestMethod.GET)
	public Response<List<RegionBasicVo>> queryRegionListBySupplierId(@RequestParam String supplierId) {
		log.info("sc/prodSell/queryRegionListBySupplierId->supplierId:" + supplierId);
		Response<List<RegionBasicVo>> response = new Response<>();
		Response<List<RegionBasicDto>> listResponse = goodsPriceQueryDubboService
				.queryRegionListBySupplierId(supplierId);
		// 错误的响应码
		if (!listResponse.isSuccess()) {
			return BeanConvertUtils.convert(listResponse, Response.class);
		}
		List<RegionBasicDto> supplierBasicDtoList = listResponse.getResultObject();
		// dto2vo
		List<RegionBasicVo> regionBasicVoList = BeanConvertUtils.convertList(supplierBasicDtoList, RegionBasicVo.class);
		response.setSuccess(listResponse.isSuccess());
		response.setCode(listResponse.getCode());
		response.setErrorMessage(listResponse.getErrorMessage());
		response.setResultObject(regionBasicVoList);
		return response;
	}



	/**
	 * 新增销售价格
	 *
	 * @param prodSellPriceInfoParameterVo
	 * @return
	 */
	@RequestMapping(value = "/addSellPrice", method = RequestMethod.POST)
	public Response<Boolean> addSellPrice(
			@RequestBody @Validated(DefaultGroup.class) ProdSellPriceInfoParameterVo prodSellPriceInfoParameterVo,
			HttpSession session) {
		log.info("sc/prodSell/addSellPrice->param:" + JSON.toJSONString(prodSellPriceInfoParameterVo));
		//判断起订量是否与第一个区间价相同
		if (!prodSellPriceInfoParameterVo.getMinNumber().toString().equals(prodSellPriceInfoParameterVo.getSellSectionPrices().get(0).getStartNumber().toString())){
			Response response = new Response();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_400.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			return response;
		}
		Response<ProductIndexDto> productDtoResponse = indexDubboService
				.queryByProductId(prodSellPriceInfoParameterVo.getProductId());
		if (productDtoResponse.getResultObject() == null) {
			Response response = new Response();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("商品查询失败");
			return response;
		}
		if (prodSellPriceInfoParameterVo.getMinNumber() % prodSellPriceInfoParameterVo.getSalesInsideNumber() != 0) {
			Response response = new Response();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_400.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			return response;
		}
		// 校验建议零售价是否小于零
		if (prodSellPriceInfoParameterVo.getSuggestPrice().compareTo(new BigDecimal(0)) == -1) {
			log.info("建议零售价小于零" + prodSellPriceInfoParameterVo.getSuggestPrice());
			Response response = new Response();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_400.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			return response;
		}

		// 获取当前用户信息
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		log.info("获取当前用户信息 LoginInfoVO: {}" + JSON.toJSONString(attribute));
		if (attribute == null || attribute.getUserId() == null) {
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		List<ProdSellSectionPriceVo> sellSectionPrices = prodSellPriceInfoParameterVo.getSellSectionPrices();
		// 把传入最后一个阶梯价区间设置为无穷大
		if (sellSectionPrices.get(sellSectionPrices.size() - 1).getEndNumber() != Integer.MAX_VALUE) {
			sellSectionPrices.get(sellSectionPrices.size() - 1).setEndNumber(Integer.MAX_VALUE);
		}
		// 校验传入区间是否重复跳跃
		for (int i = 0; i < sellSectionPrices.size(); i++) {
			if (sellSectionPrices.get(i).getPrice().compareTo(new BigDecimal(0)) == -1) {// 校验商品价格是否小于0

				log.info("销售价格小于零" + sellSectionPrices.get(i).getPrice());
				Response<Boolean> response = new Response<>();
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
				response.setCode(CommonsEnum.RESPONSE_400.getCode());
				return response;
			}

			if (i == sellSectionPrices.size() - 1) {
				break;
			}
			if (sellSectionPrices.get(i).getStartNumber() > sellSectionPrices.get(i).getEndNumber()
					|| sellSectionPrices.get(i).getEndNumber() > sellSectionPrices.get(i + 1).getStartNumber()
					|| (sellSectionPrices.get(i).getEndNumber() + 1 != sellSectionPrices.get(i + 1).getStartNumber())) {
				Response<Boolean> response = new Response<>();
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
				response.setCode(CommonsEnum.RESPONSE_400.getCode());
				return response;
			}

		}
		prodSellPriceInfoParameterVo.setSellSectionPrices(sellSectionPrices);

		ProdSellPriceInfoDto convert = BeanConvertUtils.convert(prodSellPriceInfoParameterVo,
				ProdSellPriceInfoDto.class);
		convert.setCreateUserId(attribute.getUserId());
		convert.setSellSectionPrices(BeanConvertUtils.convertList(prodSellPriceInfoParameterVo.getSellSectionPrices(),
				ProdSellSectionPriceDto.class));
		Response<Boolean> response = goodsPriceWriteDubboService.insertSellPrice(convert);

		return response;
	}



	/**
	 * 修改销售价格
	 *
	 * @param prodSellPriceInfoParameterVo
	 * @return
	 */
	@RequestMapping(value = "/updateSellPrice", method = RequestMethod.POST)
	public Response<Boolean> updateSellPrice(@RequestBody @Validated({ DefaultGroup.class,
			GroupOne.class }) ProdSellPriceInfoParameterVo prodSellPriceInfoParameterVo, HttpSession session) {
		log.info("sc/prodSell/updateSellPrice->param:" + JSON.toJSONString(prodSellPriceInfoParameterVo));
		if (1==prodSellPriceInfoParameterVo.getAuditStatus()){//已提交状态不能修改
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage("该销售关系为已提交状态不能修改");
			return response;
		}
		//判断起订量是否与第一个区间价相同
		if (!prodSellPriceInfoParameterVo.getMinNumber().toString().equals(prodSellPriceInfoParameterVo.getSellSectionPrices().get(0).getStartNumber().toString())){
			Response response = new Response();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("起订量与第一个区间价不相同");
			return response;
		}
		// 校验建议零售价是否小于零
		if (prodSellPriceInfoParameterVo.getSuggestPrice().compareTo(new BigDecimal(0)) == -1) {
			log.info("建议零售价小于零" + prodSellPriceInfoParameterVo.getSuggestPrice());
			Response response = new Response();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("建议零售价小于零");
			return response;
		}
		if (prodSellPriceInfoParameterVo.getMinNumber() % prodSellPriceInfoParameterVo.getSalesInsideNumber() != 0) {
			Response response = new Response();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			return response;
		}
		// 获取当前用户信息
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (attribute == null || attribute.getUserId() == null) {
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage("用户未登录");
			return response;
		}

		List<ProdSellSectionPriceVo> sellSectionPrices = prodSellPriceInfoParameterVo.getSellSectionPrices();
			// 把传入最后一个阶梯价区间设置为无穷大
			if (sellSectionPrices.get(sellSectionPrices.size() - 1).getEndNumber() != Integer.MAX_VALUE) {
				sellSectionPrices.get(sellSectionPrices.size() - 1).setEndNumber(Integer.MAX_VALUE);
			}
			// 校验传入区间是否重复跳跃
			for (int i = 0; i < sellSectionPrices.size(); i++) {
				if (sellSectionPrices.get(i).getPrice().compareTo(new BigDecimal(0)) == -1) {// 校验商品价格是否小于0

					log.info("销售价格小于零" + sellSectionPrices.get(i).getPrice());
					Response<Boolean> response = new Response<>();
					response.setSuccess(false);
					response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
					response.setCode("商品价格小于0");
					return response;
				}
				if (i == sellSectionPrices.size() - 1) {
					break;
				}
				if (sellSectionPrices.get(i).getStartNumber() > sellSectionPrices.get(i).getEndNumber()
						|| sellSectionPrices.get(i).getEndNumber() > sellSectionPrices.get(i + 1).getStartNumber()
						|| (sellSectionPrices.get(i).getEndNumber() + 1 != sellSectionPrices.get(i + 1).getStartNumber())) {
					Response<Boolean> response = new Response<>();
					response.setSuccess(false);
					response.setErrorMessage("销售区间参数出错");
					response.setCode(CommonsEnum.RESPONSE_500.getCode());
					return response;
				}

			}
			prodSellPriceInfoParameterVo.setSellSectionPrices(sellSectionPrices);



		ProdSellPriceInfoDto convert = BeanConvertUtils.convert(prodSellPriceInfoParameterVo,
				ProdSellPriceInfoDto.class);
		convert.setModifyUserId(attribute.getUserId());
		convert.setSellSectionPrices(BeanConvertUtils.convertList(prodSellPriceInfoParameterVo.getSellSectionPrices(),
				ProdSellSectionPriceDto.class));
		Response<Boolean> response = goodsPriceWriteDubboService.updateSellPrice(convert);
		return response;
	}



	/**
	 * 根据销售区间定价id查询商品销售信息（跳转到修改页面）
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getSellPriceInfoById", method = RequestMethod.GET)
	public Response<ProdSellPriceInfoVo> getSellPriceInfoById(
			@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Long id) {
		log.info("sc/prodSell/getSellPriceInfoById->param:" + id);
		Response<ProdSellPriceInfoVo> voResponse=new Response<>();
		ProdSellPriceInfoVo prodSellPriceInfoVo;
		Response<ProdSellPriceInfoDto> dtoResponse = goodsPriceQueryDubboService
				.queryPriceDetailById(id);
		if (!dtoResponse.isSuccess()) {
			voResponse.setErrorMessage(dtoResponse.getErrorMessage());
			voResponse.setCode(dtoResponse.getCode());
			voResponse.setSuccess(dtoResponse.isSuccess());
			return voResponse;
		}
		prodSellPriceInfoVo=BeanConvertUtils.convert(dtoResponse.getResultObject(),ProdSellPriceInfoVo.class);
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setSuccess(dtoResponse.isSuccess());
		voResponse.setResultObject(prodSellPriceInfoVo);
		return voResponse;

	}



	/**
	 * 根据传入的信息查询该商品在该子公司是否已配置
	 * 
	 * @param prodSellPriceQueryParamDto
	 * @return
	 */
	public Response<Integer> checkSellPriceInfo(ProdSellPriceQueryParamDto prodSellPriceQueryParamDto) {
		Response<Integer> response = goodsPriceQueryDubboService.checkSellPriceInfo(prodSellPriceQueryParamDto);
		return response;
	}



	/**
	 * 根据传入的商品List<id>和子公司code批量新增
	 * 
	 * @return
	 */
	@RequestMapping(value = "/prodBatchPutaway", method = RequestMethod.POST)
	public Response<Boolean> prodBatchPutaway(@RequestBody @Validated(DefaultGroup.class) ProdBatchParameterVo p,
			HttpSession session) {
		log.info("sc/prodSell/prodBatchPutaway->param:" + JSON.toJSONString(p));
		// 获取当前用户信息
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (attribute == null || attribute.getUserId() == null) {
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		// 设置已配置关系的需批量修改状态参数
		List<String> uplist = new ArrayList<>();
		ProdBatchParameterDto prodBatchParameterDto = new ProdBatchParameterDto();
		prodBatchParameterDto.setBranchCompanyId(p.getBranchCompanyId());
		prodBatchParameterDto.setUserId(attribute.getUserId());
		prodBatchParameterDto.setModifyTime(new Date());
		prodBatchParameterDto.setStatus(p.getStatus());
		prodBatchParameterDto.setProductIds(uplist);
		// 设置没有配置关系的需批量创建关系的参数
		List<ProdSellPriceInfoDto> addList = new ArrayList<>();
		List<String> productIds = p.getProductIds();
		for (String s : productIds) {
			// 获取商品信息
			Response<ProductIndexDto> productDtoResponse = indexDubboService.queryByProductId(s);

			ProductIndexDto resultObject = productDtoResponse.getResultObject();

			// 校验该商品在该区域是否已经配置
			ProdSellPriceQueryParamDto g = new ProdSellPriceQueryParamDto();
			g.setProductId(s);
			g.setBranchCompanyId(p.getBranchCompanyId());
			Response<Integer> response1 = this.checkSellPriceInfo(g);
			if (!response1.isSuccess()) {
				return BeanConvertUtils.convert(response1, Response.class);
			}
			if (response1.getResultObject() >= 1) {
				// 说明商品与该子公司已经存在配置
				uplist.add(s);
			} else {
				// 说明该商品与子公司没有关系需要创建关系
				ProdSellPriceInfoDto prodSellPriceInfoDto = new ProdSellPriceInfoDto();
				prodSellPriceInfoDto.setBranchCompanyId(p.getBranchCompanyId());// 分公司的id
				prodSellPriceInfoDto.setProductId(s); // 商品id
				prodSellPriceInfoDto.setLowestPrice(resultObject.getGuideShipmentPrice());// 销售区间最低价(建议出货价)
				prodSellPriceInfoDto.setBranchCompanyName(p.getBranchCompanyName());// 分公司名称
				prodSellPriceInfoDto.setStatus(p.getStatus());// 状态0为失效状态1为正常使用状态
				prodSellPriceInfoDto.setSalesInsideNumber(1);// 销售内装数
				prodSellPriceInfoDto.setCreateTime(new Date());// 创建时间
				prodSellPriceInfoDto.setSuggestPrice(resultObject.getPrice());// 建议零售价
				prodSellPriceInfoDto.setDeliveryDay(Integer
						.parseInt(resultObject.getDeliveryTime() == null ? "0" : resultObject.getDeliveryTime()));// 承诺最迟发货时间
				prodSellPriceInfoDto.setMinNumber(1);// 起订量
				prodSellPriceInfoDto.setDeleteStatus(0);// 未删除状态
				prodSellPriceInfoDto.setPreHarvestPinStatus(0);// 默认先销后采
				prodSellPriceInfoDto.setMaxNumber(Integer.MAX_VALUE);// 默认最大采购量为int最大值
				prodSellPriceInfoDto.setCreateUserId(attribute.getUserId());
				List<ProdSellSectionPriceDto> dtoList = new ArrayList<>();
				ProdSellSectionPriceDto sectionPriceDto = new ProdSellSectionPriceDto();
				sectionPriceDto.setEndNumber(Integer.MAX_VALUE);
				sectionPriceDto.setStartNumber(1);
				sectionPriceDto.setPrice(resultObject.getGuideShipmentPrice());// 销售价格（商品上的出货价）
				sectionPriceDto.setDeleteStatus(0);// 未删除状态
				dtoList.add(sectionPriceDto);
				prodSellPriceInfoDto.setSellSectionPrices(dtoList);
				addList.add(prodSellPriceInfoDto);
			}
		}
		// 批量修改已存在销售区间价为启用状态 批量添加商品与子公司的销售关系
		Response<Boolean> response = goodsPriceWriteDubboService.prodBatchUpdateInfo(prodBatchParameterDto, addList);

		// Response<Boolean> response1 =
		// goodsPriceWriteDubboService.prodBatchAdd(addList);
		return response;
	}



	/**
	 * 根据传入定价id修改商品区域销售状态
	 * 
	 * @param prodSellPriceQueryParamVo
	 * @return
	 */
	@RequestMapping(value = "/updateSellPriceStatus", method = RequestMethod.GET)
	public Response<Boolean> updateSellPriceStatus(ProdSellPriceQueryParamVo prodSellPriceQueryParamVo,
			HttpSession session) {
		// 获取当前用户信息
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (attribute == null || attribute.getUserId() == null) {
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		ProdSellPriceInfoDto convert = BeanConvertUtils.convert(prodSellPriceQueryParamVo, ProdSellPriceInfoDto.class);
		convert.setModifyUserId(attribute.getUserId());
		Response<Boolean> response = goodsPriceWriteDubboService.updateSellPriceStatus(convert);
		return response;
	}



	/**
	 * 根据传入信息批量区域下架
	 * 
	 * @return
	 */
	@RequestMapping(value = "/prodBatchUpdate", method = RequestMethod.POST)
	public Response<Boolean> prodBatchUpdate(@RequestBody @Validated(DefaultGroup.class) ProdBatchParameterVo p,
			HttpSession session) {
		log.info("sc/prodSell/prodBatchUpdate->param:" + JSON.toJSONString(p));
		// 获取当前用户信息
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (attribute == null || attribute.getUserId() == null) {
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		p.setUserId(attribute.getUserId());// 修改人员
		p.setModifyTime(new Date());// 修改时间
		ProdBatchParameterDto convert = BeanConvertUtils.convert(p, ProdBatchParameterDto.class);
		Response<Boolean> response = goodsPriceWriteDubboService.prodBatchUpdate(convert);
		return response;
	}



	/**
	 * 根据传入的信息查询子公司的信息
	 * 
	 * @param branchCompanyId
	 *            公司id（code）
	 * @param branchCompanyName
	 *            公司名称
	 * @return
	 */
	@RequestMapping(value = "/findCompanyBaseInfo", method = RequestMethod.GET)
	public Response<List<BranchCompanyInfoVo>> findCompanyBaseInfo(String branchCompanyId, String branchCompanyName,
			HttpServletRequest request) {
		// 只能查询当前用户子公司
		LoginInfoVO userInfo = (LoginInfoVO) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		String companyId = null;
		if (userInfo.getCompanyType() == 0) {
			companyId = userInfo.getCompanyId();
		} else {
			companyId = branchCompanyId;
		}

		Response<List<BranchCompanyDto>> response = organizationService.querySimpleByBranchCompanyIdAndName(companyId,
				branchCompanyName);
		if (!response.isSuccess()) {
			return BeanConvertUtils.convert(response, Response.class);
		}
		List<BranchCompanyDto> resultObject = response.getResultObject();
		if (resultObject == null) {
			return BeanConvertUtils.convert(response, Response.class);
		}
		List<BranchCompanyInfoVo> branchCompanyInfoVos = BeanConvertUtils.convertList(resultObject,
				BranchCompanyInfoVo.class);
		Response convert = BeanConvertUtils.convert(response, Response.class);
		convert.setResultObject(branchCompanyInfoVos);
		return convert;
	}



	@RequestMapping(value = "/queryBranchCompanyInfo", method = RequestMethod.GET)
	public Response<List<BranchCompanyInfoVo>> queryBranchCompanyInfo(
			@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) String productId, String branchCompanyId,
			String branchCompanyName, HttpServletRequest request) {

		// 只能查询当前用户子公司
		LoginInfoVO userInfo = (LoginInfoVO) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		String companyId = null;
		if (userInfo.getCompanyType() == 0) {
			companyId = userInfo.getCompanyId();
		} else {
			companyId = branchCompanyId;
		}

		Response<List<BranchCompanyDto>> response = organizationService.querySimpleByBranchCompanyIdAndName(companyId,
				branchCompanyName);
		if (!response.isSuccess()) {
			return BeanConvertUtils.convert(response, Response.class);
		}
		List<BranchCompanyDto> resultObject = response.getResultObject();
		if (resultObject == null) {
			return BeanConvertUtils.convert(response, Response.class);
		}
		List<BranchCompanyInfoVo> branchCompanyInfoVos = BeanConvertUtils.convertList(resultObject,
				BranchCompanyInfoVo.class);
		Response convert = BeanConvertUtils.convert(response, Response.class);
		convert.setResultObject(branchCompanyInfoVos);
		// 根据商品id查询改商品已配置的子公司id集合
		ProdSellPriceQueryParamDto prodSellPriceQueryParamDto = new ProdSellPriceQueryParamDto();
		prodSellPriceQueryParamDto.setProductId(productId);
		prodSellPriceQueryParamDto.setPageNum(1);
		prodSellPriceQueryParamDto.setPageSize(Integer.MAX_VALUE);
		Response<PageResult<ProdSellPriceInfoDto>> pageResultResponse = goodsPriceQueryDubboService
				.pageQuerySellPrice(prodSellPriceQueryParamDto);
		if (!pageResultResponse.isSuccess()) {
			return convert;
		}
		PageResult<ProdSellPriceInfoDto> resultObject1 = pageResultResponse.getResultObject();
		if (resultObject1 == null || resultObject1.getData() == null) {
			return convert;
		}
		List<String> list = new ArrayList<>();
		List<ProdSellPriceInfoDto> data = resultObject1.getData();
		for (ProdSellPriceInfoDto priceInfoDto : data) {
			list.add(priceInfoDto.getBranchCompanyId());
		}
		for (int i = 0; i < branchCompanyInfoVos.size(); i++) {
			if (list.contains(branchCompanyInfoVos.get(i).getId())) {
				branchCompanyInfoVos.remove(i);
				i--;
			}
		}

		return convert;
	}

	/**
	 * 
	 * <method description>查询商品改变列表
	 * 
	 * @author zhoubaiyun
	 * @param prodPriceChangeQueryVo
	 * @return
	 */
	@RequestMapping(value = "/queryProdPriceChangeList", method = RequestMethod.GET)
	public Response<PageResult<ProdPriceChangeListResultDto>> queryProdPriceChangeList(
			ProdPriceChangeQueryVo prodPriceChangeQueryVo) {
		log.info("/sc/prodSell/queryProdPriceChangeList收到请求，参数为：{}", JSON.toJSONString(prodPriceChangeQueryVo));
		ProdPriceChangeQueryDto prodPriceChangeQueryDto = BeanConvertUtils.convert(prodPriceChangeQueryVo,
				ProdPriceChangeQueryDto.class);
		return prodPriceChangeQueryDubboService.queryProdPriceChangeList(prodPriceChangeQueryDto);
	}



	/**
	 * 
	 * <method description>导出商品价格改变列表
	 * 
	 * @author zhoubaiyun
	 * @param prodPriceChangeQueryVo
	 * @return
	 */
	@RequestMapping(value = "/exportProdPriceChangeList", method = RequestMethod.GET)
	public ModelAndView exportProdPriceChangeList(ProdPriceChangeQueryVo prodPriceChangeQueryVo) {
		log.info("/sc/prodSell/exportProdPriceChangeList收到请求，参数为：{}", JSON.toJSONString(prodPriceChangeQueryVo));
		ProdPriceChangeQueryDto prodPriceChangeQueryDto = BeanConvertUtils.convert(prodPriceChangeQueryVo,
				ProdPriceChangeQueryDto.class);
		prodPriceChangeQueryDto.setPageNum(1);
		prodPriceChangeQueryDto.setPageSize(Integer.MAX_VALUE);
		Response<PageResult<ProdPriceChangeListResultDto>> result = prodPriceChangeQueryDubboService
				.queryProdPriceChangeList(prodPriceChangeQueryDto);
		if (null == result || CommonsEnum.RESPONSE_500.getCode().equals(result.getCode())
				|| null == result.getResultObject() || null == result.getResultObject().getData()) {
			return null;
		}
		List<ProdPriceChangeListResultDto> prodPriceChangeListResultDtos = result.getResultObject().getData();
		List<ProdPriceChangeListResultVo> prodPriceChangeListResultVos = BeanConvertUtils
				.convertList(prodPriceChangeListResultDtos, ProdPriceChangeListResultVo.class);
		XlsData xlsData = new XlsData("商品价格改变");
		xlsData.setData(ProdPriceChangeListResultVo.class, prodPriceChangeListResultVos);
		xlsData.setPattern("YYYY-MM-dd hh:mm:ss");
		return getXlsModelAndView(xlsData);
	}

	/**
	 * 售价变更批量导入
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "sellPriceChangeUpload", method = RequestMethod.POST)
	public Response sellPriceChangeUpload(MultipartFile file, HttpSession session) {
		log.info("/sc/prodSell/sellPriceChangeUpload 收到导入请求， Time:{}", DateUtil.format(new Date()));
		Response response = new Response();
		if (file == null || file.isEmpty()){
			log.error("售价变更Excel导入文件数据为空："+ CommonsEnum.RESPONSE_10047.getName());
			response.setErrorMessage(CommonsEnum.RESPONSE_10047.getName());
			response.setCode(CommonsEnum.RESPONSE_10047.getCode());
			response.setSuccess(false);
			return response;
		}
		if(!FileType.fileTypeMap.get("excel").contains(file.getContentType())){
			log.error("售价变更Excel导入格式错误："+ CommonsEnum.RESPONSE_10048.getName());
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
			List<ProdSellPriceUpdateDto> list = ExcelImport.excelImport(file.getInputStream(), "sellPriceUpdate", ProdSellPriceUpdateDto.class);
			response = prodSellInfoImportWriteDubboService.prodSellListImport(list, attribute.getUserId(), branchCompanyIds);
		} catch (Exception e) {
			log.error("售价变更申请Excel导入失败\n{}", ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("售价变更申请Excel导入失败");
			response.setSuccess(false);
		}
		return response;
	}

	/**
	 * 售价变更导入列表导出
	 * @param paramVo
	 * @return
	 */
	@RequestMapping(value = "sellPriceChangeExport", method = RequestMethod.GET)
	public void sellPriceChangeExport(ProdSellPriceUpdateParamVo paramVo, HttpServletResponse resp, HttpSession session){
		log.info("/sc/prodSell/sellPriceChangeExport，参数为：{}", JSON.toJSONString(paramVo));
        // 获取当前用户信息
        LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		// 获取数据
        paramVo.setPageNum(1);
        paramVo.setPageSize(Integer.MAX_VALUE);
		ProdSellPriceUpdateParamDto paramDto = BeanConvertUtils.convert(paramVo, ProdSellPriceUpdateParamDto.class);
		paramDto.setUserId(attribute.getUserId());
		Response<PageResult<ProdSellInfoImportDto>> listResponse = prodSellInfoImportQueryDubboService.getProdSellInfoImportByParam(paramDto);
		List<ProdSellInfoImportDto> list = listResponse.getResultObject().getData();
		// 导出
		try {
			excelExportService.excelExport("updateSellPriceExport", list, resp);
		} catch (Exception e) {
			log.error("售价变更Excel导出失败\n{}", ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * 售价变更导入列表查询
	 * @param paramVo
	 * @return
	 */
	@RequestMapping(value = "sellPriceChangeList", method = RequestMethod.GET)
	public Response<PageResult<ProdSellPriceUpdateVo>> sellPriceChangeList(ProdSellPriceUpdateParamVo paramVo, HttpSession session){
		log.info("/sc/prodSell/sellPriceChangeList，参数为：{}", JSON.toJSONString(paramVo));
		// 获取当前用户信息
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		// 获取数据
		Response<PageResult<ProdSellPriceUpdateVo>> response = new Response<>();
		ProdSellPriceUpdateParamDto paramDto = BeanConvertUtils.convert(paramVo, ProdSellPriceUpdateParamDto.class);
		paramDto.setUserId(attribute.getUserId());
		Response<PageResult<ProdSellInfoImportDto>> listResponse = prodSellInfoImportQueryDubboService.getProdSellInfoImportByParam(paramDto);
		// 封装响应
        PageResult<ProdSellPriceUpdateVo> pageResult = new PageResult<>();
        List<ProdSellPriceUpdateVo> result = BeanConvertUtils.convertList(listResponse.getResultObject().getData(), ProdSellPriceUpdateVo.class);
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
	 * @Description: 创建提交售价单
	 * @author kangdong
	 * @date 2017/12/11 14:50
	 * @return
	 */
	@RequestMapping(value = "createSell", method = RequestMethod.POST)
	public Response<Long> createSell(@RequestBody Map map) {
		Long id = MapUtils.getLong(map, "importsId");
		LoginInfoVO attribute = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		return prodSellInfoImportWriteDubboService.updateProdSellInfoImport(id,attribute.getUserId());
	}


	/**
	 * 售价变更申请批量导入Excel模板
	 * @param httpServletResponse
	 */
	@RequestMapping(value = "sellPriceChangeExcelTemplate", method = RequestMethod.GET)
	public void sellPriceChangeExcelTemplate(HttpServletResponse httpServletResponse){
		try {
			excelExportService.excelExport("sellPriceChangeExcelTemplate", null, httpServletResponse);
		} catch (Exception e) {
			log.error("sellPriceChangeExcelTemplate error --> {}", ExceptionUtils.getFullStackTrace(e));
		}
	}

}
