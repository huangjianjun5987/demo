package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ProvinceDto;
import com.yatang.sc.facade.dto.SupplierPlaceDto;
import com.yatang.sc.facade.dto.supplier.QueryAvailableBranchCompanyDto;
import com.yatang.sc.facade.dto.supplier.SpAdrBasicDto;
import com.yatang.sc.facade.dto.supplier.SpSearchBoxDto;
import com.yatang.sc.facade.dto.supplier.SupplierAdrAuditDto;
import com.yatang.sc.facade.dto.supplier.SupplierAdrInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierAuditDto;
import com.yatang.sc.facade.dto.supplier.SupplierAuditsDto;
import com.yatang.sc.facade.dto.supplier.SupplierBankInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierBasicInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoQueryListDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoQueryParamDto;
import com.yatang.sc.facade.dto.supplier.SupplierlicenseInfoDto;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.dubboservice.SupplierWriteDubboService;
import com.yatang.sc.operation.service.BranchCompanyHelper;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.SupplierPlaceVo;
import com.yatang.sc.operation.vo.prod.BranchCompanyInfoVo;
import com.yatang.sc.operation.vo.supplier.QueryAvailableBranchCompanyVo;
import com.yatang.sc.operation.vo.supplier.SupplierAdrAuditVo;
import com.yatang.sc.operation.vo.supplier.SupplierAdrInfoVo;
import com.yatang.sc.operation.vo.supplier.SupplierAuditVo;
import com.yatang.sc.operation.vo.supplier.SupplierAuditsVo;
import com.yatang.sc.operation.vo.supplier.SupplierBankInfoVo;
import com.yatang.sc.operation.vo.supplier.SupplierBasicInfoVo;
import com.yatang.sc.operation.vo.supplier.SupplierInfoQueryVo;
import com.yatang.sc.operation.vo.supplier.SupplierInfoVo;
import com.yatang.sc.operation.vo.supplier.SupplierQueryResultExportVo;
import com.yatang.sc.operation.vo.supplier.SupplierQueryResultVo;
import com.yatang.sc.operation.vo.supplier.SupplierQuerySearchBoxVo;
import com.yatang.sc.operation.vo.supplier.SupplierlicenseInfoVo;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.sc.web.view.XlsData;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;
import static org.apache.commons.lang3.StringUtils.startsWith;

/**
 * @描述: 供应商控制层
 * @作者: tankejia
 * @创建时间: 2017/7/14-14:05 .
 * @版本: 1.0 .
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/supplier")
public class SupplierManageAction extends BaseAction {

	private final SupplierQueryDubboService	supplierQueryDubboService;

	private final SupplierWriteDubboService	supplierWriteDubboService;

	private final OrganizationService		organizationService;

	private final BranchCompanyHelper       branchCompanyHelper;

	@Value("${image.view.domain}")
	private String							imageViewDomain;			// 图片域名



	@RequestMapping("/exception")
	public void exception() {
		log.info("异常测试");
		throw new RuntimeException("我是一个自定义异常");
	}



	/**
	 * @Description:查询供应商详情
	 * @author kangdong
	 * @date 2017/7/17- 14:23
	 */
	@ParamValid
	@RequestMapping(value = "/queryProviderDetail", method = RequestMethod.GET)
	public Response<SupplierInfoVo> queryProviderDetail(
			@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String spId) {
		Response<SupplierInfoVo> response = new Response<>();
		Response<SupplierInfoDto> supplierInfoDto = supplierQueryDubboService.queryById(spId);
		SupplierInfoVo supplierInfoVo = BeanConvertUtils.convert(supplierInfoDto.getResultObject(),
				SupplierInfoVo.class);
		response.setResultObject(supplierInfoVo);
		return response;
	}



	/**
	 * @Description:查询供应商地点信息
	 * @author kangdong
	 * @date 2017/7/18- 9:58
	 */
	@ParamValid
	@RequestMapping(value = "/queryProviderPlaceInfo", method = RequestMethod.GET)
	public Response<SupplierPlaceVo> queryProviderPlaceInfo(
			@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Integer adrInfoId) {
		Response<SupplierPlaceVo> response = new Response<SupplierPlaceVo>();
		Response<SupplierPlaceDto> supplierPlace = supplierQueryDubboService.queryProviderPlaceInfo(adrInfoId);
		SupplierPlaceDto supplierPlaceDto = supplierPlace.getResultObject();
		SpAdrBasicDto spAdrBasicDto = supplierPlaceDto.getSupplierAdrInfo().getSpAdrBasic();
		// 通过公司的ID取子公司名称
		String companyName = "";
		Response<BranchCompanyDto> branchCompanyDto = organizationService
				.querySimpleByBranchCompanyId(spAdrBasicDto.getOrgId());
		if (branchCompanyDto.getResultObject() != null) {
			companyName = branchCompanyDto.getResultObject().getName();
		}
		// 设置子公司名称
		spAdrBasicDto.setOrgName(companyName);
		SupplierAdrInfoDto supplierAdrInfoDto = supplierPlaceDto.getSupplierAdrInfo();
		supplierAdrInfoDto.setSpAdrBasic(spAdrBasicDto);
		supplierPlaceDto.setSupplierAdrInfo(supplierAdrInfoDto);
		SupplierPlaceVo supplierPlaceVo = BeanConvertUtils.convert(supplierPlaceDto, SupplierPlaceVo.class);
		response.setResultObject(supplierPlaceVo);
		return response;
	}



	/**
	 * @Description:查询供应商地点所属区域列表
	 * @author kangdong
	 * @date 2017/7/25- 15:58
	 */
	@ParamValid
	@RequestMapping(value = "/querySupplierPlaceRegion", method = RequestMethod.GET)
	public Response<List<ProvinceDto>> querySupplierPlaceRegion(
			@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String spId) {
		return supplierQueryDubboService.querySupplierPlaceRegion(spId);
	}



	/**
	 * @Description:查询供应商修改前修改后的信息
	 * @author kangdong
	 * @date 2017/7/18- 10:30
	 */
	@ParamValid
	@RequestMapping(value = "/editBeforeAfter", method = RequestMethod.GET)
	public Response<ArrayList> editBeforeAfter(
			@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String spId) {
		Response<ArrayList> maps = supplierQueryDubboService.queryEditBeforeAfter(spId);
		maps.setResultObject(dealData(maps.getResultObject()));
		return maps;
	}



	/**
	 * @Description:给值加域名
	 * @author kangdong
	 * @date 2017/8/15- 10:30
	 */
	public ArrayList dealData(ArrayList<Map> infos) {
		for (int i = 0; i < infos.size(); i++) {
			if (infos.get(i) instanceof Map) {
				Map maps = infos.get(i);
				if (Objects.equals("spAdrBasic.orgId", maps.get("categoryIndex"))) {// 通过子公司ID获取子公司名称
					String companyNameBefore = (String) maps.get("before");
					String companyNameAfter = (String) maps.get("after");
					Response<BranchCompanyDto> beforeCompanyDto = organizationService
							.querySimpleByBranchCompanyId(companyNameBefore);
					Response<BranchCompanyDto> afterCompanyDto = organizationService
							.querySimpleByBranchCompanyId(companyNameAfter);
					if (beforeCompanyDto.getResultObject() != null) {
						companyNameBefore = beforeCompanyDto.getResultObject().getName();
					}
					if (afterCompanyDto.getResultObject() != null) {
						companyNameAfter = afterCompanyDto.getResultObject().getName();
					}
					// infos.get(i).put("before",companyNameBefore);
					// infos.get(i).put("after",companyNameAfter);
					Map category = new JSONObject();
					category.put("categoryIndex", "spAdrBasic.subsidiaryName");
					category.put("before", companyNameBefore);
					category.put("after", companyNameAfter);
					infos.add(category);
				}

				boolean isContains = false;
				String categoryIndex = (String) infos.get(i).get("categoryIndex");
				for (String type : types) {
					if (categoryIndex.contains(type)) {
						isContains = true;
						break;
					}
				}
				if (!isContains) {
					continue;
				}

				String beforeText = String.valueOf(maps.get("before"));
				String afterText = String.valueOf(maps.get("after"));
				if (StringUtils.isBlank(beforeText)) {
					continue;
				}
				if (StringUtils.isBlank(afterText)) {
					continue;
				}
				String beforeImages = startsWith(beforeText, "http://") || startsWith(beforeText, "https://")
						? beforeText
						: (imageViewDomain + (startsWith(beforeText, "/") ? beforeText : "/" + beforeText));
				String afterImages = startsWith(afterText, "http://") || startsWith(afterText, "https://") ? afterText
						: (imageViewDomain + (startsWith(afterText, "/") ? afterText : "/" + afterText));
				infos.get(i).put("before", beforeImages);
				infos.get(i).put("after", afterImages);
			}
		}
		return infos;
	}

	/**
	 * @Description:需要加域名字段名
	 * @author kangdong
	 * @date 2017/8/15- 10:30
	 */
	private static final List<String> types = Lists.<String> newArrayList("registrationCertificate",
			"qualityIdentification", "foodBusinessLicense", "generalTaxpayerQualifiCerti", "bankAccountLicense",
			"legalRepreCardPic1", "legalRepreCardPic2", "registLicencePic");



	/**
	 * @Description:供应商修改审核
	 * @author kangdong
	 * @date 2017/7/19- 14:30
	 */
	@RequestMapping(value = "/auditSupplierEditInfo", method = RequestMethod.POST)
	public Response<Boolean> auditSupplierEditInfo(@RequestBody @Valid SupplierAuditsVo supplierAuditsVo,
			HttpSession session) {
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		SupplierAuditsDto upplierAuditD = BeanConvertUtils.convert(supplierAuditsVo, SupplierAuditsDto.class);
		upplierAuditD.setAuditUserId(currentUser.getUserId());
		upplierAuditD.setAuditPerson(currentUser.getRealName());
		return supplierWriteDubboService.auditSupplierEditInfo(upplierAuditD);
	}



	/**
	 * @param vo
	 * @Description: 根据传入的信息查询可用的子公司的信息
	 * @author tankejia
	 * @date 2017/8/8- 15:10
	 */
	@RequestMapping(value = "/findCompanyBaseInfo", method = RequestMethod.GET)
	public Response<List<BranchCompanyInfoVo>> findCompanyBaseInfo(@Validated QueryAvailableBranchCompanyVo vo,
			HttpServletRequest request) {
		Response<List<BranchCompanyInfoVo>> newResponse = new Response<>();
		if (vo.getBranchCompanyId() == null && vo.getBranchCompanyName() == null) {
			Response response = new Response();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_400.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
		}

		//权限修改：子公司账户也可以查出多个子公司 yinyuxin
		LoginInfoVO userInfo = (LoginInfoVO) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (null==userInfo){
			newResponse.setErrorMessage("查询用户信息失败,操作终止");
			newResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
			newResponse.setSuccess(false);
			return newResponse;
		}
		List<BranchCompanyInfoVo> branchCompanyInfoVos = this
				.queryUserCompanyInfosByParam(userInfo.getUserId(), vo.getBranchCompanyId(), vo.getBranchCompanyName());

		// 得到不可用的分公司id集合
		QueryAvailableBranchCompanyDto dto = BeanConvertUtils.convert(vo, QueryAvailableBranchCompanyDto.class);
		List<String> uselessIdList = supplierQueryDubboService.queryUselessOrgId(dto).getResultObject();

		if (uselessIdList != null && branchCompanyInfoVos != null) {
			for (int i = 0; i < branchCompanyInfoVos.size(); i++) {
				if (uselessIdList.contains(branchCompanyInfoVos.get(i).getId())) {
					branchCompanyInfoVos.remove(i);
					i--;
				}
			}
		}
		if (branchCompanyInfoVos != null) {
			newResponse.setResultObject(branchCompanyInfoVos);
			newResponse.setSuccess(true);
			newResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
			newResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} else {
			newResponse.setResultObject(null);
			newResponse.setSuccess(false);
			newResponse.setCode(CommonsEnum.RESPONSE_10006.getCode());
			newResponse.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
		}
		return newResponse;
	}



	/**
	 * @param supplierInfoVo
	 * @Description: 新增供应商
	 * @author tankejia
	 * @date 2017/7/14- 14:10
	 */
	@RequestMapping(value = "/insertSupplierInfo", method = RequestMethod.POST)
	public Response<String> insertSupplierInfo(
			@Validated({ GroupTwo.class }) @RequestBody SupplierInfoVo supplierInfoVo) {
		// 获取当前用户信息 TODO
		LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		Response response = SessionUtil.logOut(currentUser);
		if (response != null) {
			return response;
		}

		// 一、录入前进行接口校验
		// 1.供应商基本信息（校验公司名/供应商编号是否重复）
		SupplierBasicInfoVo supplierBasicInfo = supplierInfoVo.getSupplierBasicInfo();
		if (supplierBasicInfo != null) {
			SupplierBasicInfoDto basicDto = BeanConvertUtils.convert(supplierBasicInfo, SupplierBasicInfoDto.class);
			Response<Void> basicResponse = supplierQueryDubboService.checkSupplierBasicInfo(basicDto);
			// 校验有误
			if (!basicResponse.isSuccess()) {
				return BeanConvertUtils.convert(basicResponse, Response.class);
			}
		}

		// 2.校验银行信息（校验银行账号是否重复）
		SupplierBankInfoVo supplierBankInfoVo = supplierInfoVo.getSupplierBankInfo();
		if (supplierBankInfoVo != null) {
			SupplierBankInfoDto dto = BeanConvertUtils.convert(supplierBankInfoVo, SupplierBankInfoDto.class);
			Response<Void> bankResponse = supplierQueryDubboService.checkSupplierBankInfoByBankAccount(dto);
			if (!bankResponse.isSuccess()) {
				return BeanConvertUtils.convert(bankResponse, Response.class);
			}
		}

		// 3.校验营业执照重复情况（）
		SupplierlicenseInfoVo supplierlicenseInfoVo = supplierInfoVo.getSupplierlicenseInfo();
		if (supplierlicenseInfoVo != null) {
			// 校验营业开始日期跟结束日期(永续经营为0，则营业日期必填)
			if (supplierlicenseInfoVo.getPerpetualManagement() == 0) {
				if (supplierlicenseInfoVo.getStartDate() == null || supplierlicenseInfoVo.getEndDate() == null) {
					Response<String> response2 = new Response<>();
					response2.setResultObject(null);
					response2.setSuccess(false);
					response2.setCode(CommonsEnum.RESPONSE_400.getCode());
					response2.setErrorMessage("营业开始日期及结束日期不能为空");
					return response2;
				}
			}

			SupplierlicenseInfoDto dto = BeanConvertUtils.convert(supplierlicenseInfoVo, SupplierlicenseInfoDto.class);
			Response<Void> licenceResponse = supplierQueryDubboService.checkSupplierLicenseInfoByRegistLicenceNo(dto);
			if (!licenceResponse.isSuccess()) {
				return BeanConvertUtils.convert(licenceResponse, Response.class);
			}
		}

		// 二、录入信息
		SupplierInfoDto supplierInfoDto = BeanConvertUtils.convert(supplierInfoVo, SupplierInfoDto.class);
		return supplierWriteDubboService.insertSupplierInfo(supplierInfoDto, currentUser.getLoginName());
	}



	/**
	 * @param supplierInfoVo
	 * @Description: 修改供应商
	 * @author tankejia
	 * @date 2017/7/14- 14:10
	 */
	@RequestMapping(value = "/updateSupplierInfo", method = RequestMethod.POST)
	public Response<String> updateSupplierInfo(
			@RequestBody @Validated({ GroupOne.class }) SupplierInfoVo supplierInfoVo) {
		// 获取当前用户信息 TODO
		LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		Response response = SessionUtil.logOut(currentUser);
		if (response != null) {
			return response;
		}

		// 校验供应商状态（已提交状态的供应商不能进行修改）
		Response<SupplierInfoDto> infoDtoResponse = supplierQueryDubboService.queryById(supplierInfoVo.getId());
		if (!infoDtoResponse.isSuccess() || null == infoDtoResponse.getResultObject() || 1 == infoDtoResponse.getResultObject().getStatus()) {
			log.error("已提交状态的供应商不能进行修改");
			Response<String> res = new Response<>();
			res.setSuccess(false);
			res.setErrorMessage("已提交状态的供应商不能进行修改");
			res.setCode(CommonsEnum.RESPONSE_400.getCode());
			return res;
		}

		// 一、录入前进行接口校验
		// 1.供应商基本信息（校验公司名/供应商编号是否重复）
		SupplierBasicInfoVo supplierBasicInfo = supplierInfoVo.getSupplierBasicInfo();
		if (supplierBasicInfo != null) {
			SupplierBasicInfoDto basicDto = BeanConvertUtils.convert(supplierBasicInfo, SupplierBasicInfoDto.class);
			Response<Void> basicResponse = supplierQueryDubboService.checkSupplierBasicInfo(basicDto);
			// 校验有误
			if (!basicResponse.isSuccess()) {
				return BeanConvertUtils.convert(basicResponse, Response.class);
			}
		}

		// 2.校验银行信息（校验银行账号是否重复）
		SupplierBankInfoVo supplierBankInfoVo = supplierInfoVo.getSupplierBankInfo();
		if (supplierBankInfoVo != null) {
			SupplierBankInfoDto dto = BeanConvertUtils.convert(supplierBankInfoVo, SupplierBankInfoDto.class);
			Response<Void> bankResponse = supplierQueryDubboService.checkSupplierBankInfoByBankAccount(dto);
			if (!bankResponse.isSuccess()) {
				return BeanConvertUtils.convert(bankResponse, Response.class);
			}
		}

		// 3.校验营业执照重复情况（）
		SupplierlicenseInfoVo supplierlicenseInfoVo = supplierInfoVo.getSupplierlicenseInfo();
		if (supplierlicenseInfoVo != null) {
			// 校验营业开始日期跟结束日期(永续经营为0，则营业日期必填)
			if (supplierlicenseInfoVo.getPerpetualManagement() == 0) {
				if (supplierlicenseInfoVo.getStartDate() == null || supplierlicenseInfoVo.getEndDate() == null) {
					Response<String> response2 = new Response<>();
					response2.setResultObject(null);
					response2.setSuccess(false);
					response2.setCode(CommonsEnum.RESPONSE_400.getCode());
					response2.setErrorMessage("营业开始日期及结束日期不能为空");
					return response2;
				}
			}

			SupplierlicenseInfoDto dto = BeanConvertUtils.convert(supplierlicenseInfoVo, SupplierlicenseInfoDto.class);
			Response<Void> licenceResponse = supplierQueryDubboService.checkSupplierLicenseInfoByRegistLicenceNo(dto);
			if (!licenceResponse.isSuccess()) {
				return BeanConvertUtils.convert(licenceResponse, Response.class);
			}
		}

		// 二、修改信息
		SupplierInfoDto supplierInfoDto = BeanConvertUtils.convert(supplierInfoVo, SupplierInfoDto.class);
		return supplierWriteDubboService.updateSupplierInfo(supplierInfoDto, currentUser.getLoginName());
	}



	/**
	 * @param supplierAdrInfoVo
	 * @Description: 新增供应商地点
	 * @author tankejia
	 * @date 2017/7/14- 14:14
	 */
	@RequestMapping(value = "/insertSupplierAddressInfo", method = RequestMethod.POST)
	public Response<Integer> insertSupplierAddressInfo(
			@Validated({ GroupTwo.class }) @RequestBody SupplierAdrInfoVo supplierAdrInfoVo) {
		// 获取当前用户信息 TODO
		LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		Response response = SessionUtil.logOut(currentUser);
		if (response != null) {
			return response;
		}

		SupplierAdrInfoDto supplierAdrInfoDto = BeanConvertUtils.convert(supplierAdrInfoVo, SupplierAdrInfoDto.class);
		// 校验供应商地点编号
		if (supplierAdrInfoVo != null) {
			Response<Void> basicResponse = supplierQueryDubboService
					.checkSupplierAddNo(supplierAdrInfoDto.getSpAdrBasic());
			if (!basicResponse.isSuccess()) {
				return BeanConvertUtils.convert(basicResponse, Response.class);
			}
		}

		return supplierWriteDubboService.insertSupplierAddInfo(supplierAdrInfoDto, currentUser.getLoginName());
	}


    /**
     * <method description>根据条件导出供应商、供应商地点入驻列表
     *
     * @return
     * @author zhoubaiyun
     */
    @RequestMapping(value = "/exportSettledList", method = RequestMethod.GET)
    public ModelAndView exportSettledList(SupplierInfoQueryVo supplierInfoQueryVo) {
        SupplierInfoQueryParamDto supplierInfoQueryParamDto = BeanConvertUtils.convert(supplierInfoQueryVo,
                SupplierInfoQueryParamDto.class);
        supplierInfoQueryParamDto.setPageNum(1);
        supplierInfoQueryParamDto.setPageSize(Integer.MAX_VALUE);
        Response<PageResult<SupplierInfoQueryListDto>> result = supplierQueryDubboService
                .querySettledList(supplierInfoQueryParamDto);
        if (null == result || null == result.getResultObject() || null == result.getResultObject().getData()) {
            return null;
        }
        List<SupplierInfoQueryListDto> data = result.getResultObject().getData();
        List<SupplierQueryResultVo> list = BeanConvertUtils.convertList(data, SupplierQueryResultVo.class);
        transData(list);
        XlsData xlsData = new XlsData("入驻列表");
        xlsData.setData(SupplierQueryResultVo.class, list);
        return getXlsModelAndView(xlsData);
    }

	/**
	 * @param supplierAdrInfoVo
	 * @Description: 修改供应商地点
	 * @author tankejia
	 * @date 2017/7/14- 14:14
	 */
	@RequestMapping(value = "/updateSupplierAddressInfo", method = RequestMethod.POST)
	public Response<Boolean> updateSupplierAddressInfo(
			@Validated({ GroupOne.class }) @RequestBody SupplierAdrInfoVo supplierAdrInfoVo) {
		// 获取当前用户信息 TODO
		LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		Response response = SessionUtil.logOut(currentUser);
		if (response != null) {
			return response;
		}

		SupplierAdrInfoDto supplierAdrInfoDto = BeanConvertUtils.convert(supplierAdrInfoVo, SupplierAdrInfoDto.class);
		// 校验供应商地点编号
		if (supplierAdrInfoVo != null) {
			Response<Void> basicResponse = supplierQueryDubboService
					.checkSupplierAddNo(supplierAdrInfoDto.getSpAdrBasic());
			if (!basicResponse.isSuccess()) {
				return BeanConvertUtils.convert(basicResponse, Response.class);
			}
		}
		return supplierWriteDubboService.updateSupplierAddInfo(supplierAdrInfoDto, currentUser.getLoginName());
	}

	/**
	 * @param companyName
	 * @param id
	 * @param status
	 * @Description: 校验供应商名称是否重复
	 * @author tankejia
	 * @date 2017/7/25- 15:46
	 */
	@ParamValid
	@RequestMapping(value = "/checkSupplierName", method = RequestMethod.GET)
	public Response<Void> checkSupplierName(@NotBlank(message = "{msg.notEmpty.message}") String companyName,
			Integer id, Integer status) {
		SupplierBasicInfoDto basicDto = new SupplierBasicInfoDto();
		basicDto.setCompanyName(companyName == null || "".equals(companyName) ? null : companyName);
		basicDto.setId(id == null ? null : id);
		basicDto.setStatus(status == null ? null : status);
		return supplierQueryDubboService.checkSupplierBasicInfo(basicDto);
	}



	/**
	 * @param bankAccount
	 * @param id
	 * @param status
	 * @Description: 校验银行账号是否重复
	 * @author tankejia
	 * @date 2017/7/25- 16:21
	 */
	@RequestMapping(value = "/checkBankAccount", method = RequestMethod.GET)
	@ParamValid
	public Response<Void> checkBankAccount(@NotBlank(message = "{msg.notEmpty.message}") String bankAccount, Integer id,
			Integer status) {
		SupplierBankInfoDto dto = new SupplierBankInfoDto();
		dto.setBankAccount(bankAccount == null || "".equals(bankAccount) ? null : bankAccount);
		dto.setId(id == null ? null : id);
		dto.setStatus(status == null ? null : status);
		return supplierQueryDubboService.checkSupplierBankInfoByBankAccount(dto);
	}



	/**
	 * @param licenseNo
	 * @param id
	 * @param status
	 * @Description: 校验营业执照是否重复
	 * @author tankejia
	 * @date 2017/7/25- 16:30
	 */
	@ParamValid
	@RequestMapping(value = "/checkLicenseNo", method = RequestMethod.GET)
	public Response<Void> checkLicenseNo(@NotBlank(message = "{msg.notEmpty.message}") String licenseNo, Integer id,
			Integer status) {
		SupplierlicenseInfoDto dto = new SupplierlicenseInfoDto();
		dto.setRegistLicenceNumber(licenseNo == null ? null : licenseNo);
		dto.setId(id == null ? null : id);
		dto.setStatus(status == null ? null : status);
		return supplierQueryDubboService.checkSupplierLicenseInfoByRegistLicenceNo(dto);
	}



	/**
	 * @Description: 根据条件查询供应商、供应商地点入驻列表
	 * @author yipeng
	 * @date 2017年07月18日15:41:21
	 */
	@RequestMapping(value = "/querySettledList", method = RequestMethod.GET)
	public Response<PageResult<SupplierInfoQueryListDto>> querySettledList(SupplierInfoQueryParamDto param) {
		return supplierQueryDubboService.querySettledList(param);
	}





	/**
	 * @Description: 根据条件查询供应商、供应商地点管理列表
	 * @author yipeng
	 * @date 2017年07月18日15:41:21
	 */
	@RequestMapping(value = "/queryManageList", method = RequestMethod.GET)
	public Response<PageResult<SupplierInfoQueryListDto>> queryManageList(SupplierInfoQueryParamDto param) {
		return supplierQueryDubboService.queryManageList(param);
	}



	/**
	 * <method description>根据条件导出供应商、供应商地点管理列表
	 *
	 * @return
	 * @author zhoubaiyun
	 */
	@RequestMapping(value = "/exportManageList", method = RequestMethod.GET)
	public ModelAndView exportManageList(SupplierInfoQueryVo supplierInfoQueryVo) {
		log.info("/sc/supplier/exportManageList参数为：", JSON.toJSONString(supplierInfoQueryVo));
		SupplierInfoQueryParamDto supplierInfoQueryParamDto = BeanConvertUtils.convert(supplierInfoQueryVo,
				SupplierInfoQueryParamDto.class);
		supplierInfoQueryParamDto.setPageNum(1);
		supplierInfoQueryParamDto.setPageSize(Integer.MAX_VALUE);
		Response<PageResult<SupplierInfoQueryListDto>> result = supplierQueryDubboService
				.queryExportManageList(supplierInfoQueryParamDto);
		log.info("supplierQueryDubboService.queryManageList查询结果为：", JSON.toJSONString(result));
		if (null == result || null == result.getResultObject() || null == result.getResultObject().getData()) {
			return null;
		}
		List<SupplierInfoQueryListDto> data = result.getResultObject().getData();
        setBranchCompanyName(data);

		List<SupplierQueryResultExportVo> list = BeanConvertUtils.convertList(data, SupplierQueryResultExportVo.class);
		transExportData(list);
		XlsData xlsData = new XlsData("管理列表");
		xlsData.setData(SupplierQueryResultExportVo.class, list);
		return getXlsModelAndView(xlsData);

	}

	private void setBranchCompanyName(List<SupplierInfoQueryListDto> data) {
		for(SupplierInfoQueryListDto supplierInfoQueryListDto : data){
			if(null != supplierInfoQueryListDto.getOrgId()){
				BranchCompanyDto branchCompanyDto = branchCompanyHelper.queryBranchCompanyById(supplierInfoQueryListDto.getOrgId());
				String branchCompanyName = "";
				if (branchCompanyDto != null) {
					branchCompanyName = branchCompanyDto.getName();
				}
				supplierInfoQueryListDto.setOrgName(branchCompanyName);
			}
		}
	}

	private void transExportData(List<SupplierQueryResultExportVo> list) {
		for (SupplierQueryResultExportVo supplierQueryResultExportVo : list) {
			// 供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商
			// 供应商地点等级:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他
			// 供应商状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:修改中
			if (null != supplierQueryResultExportVo.getGrade()) {
				switch (supplierQueryResultExportVo.getGrade()) {
					case "1":
						supplierQueryResultExportVo.setGrade("战略供应商");
						break;
					case "2":
						supplierQueryResultExportVo.setGrade("核心供应商");
						break;
					case "3":
						supplierQueryResultExportVo.setGrade("可替代供应商");
						break;
				}
			}

			if(null != supplierQueryResultExportVo.getAdrGrade()){
				switch (supplierQueryResultExportVo.getAdrGrade()) {
					case "1":
						supplierQueryResultExportVo.setAdrGrade("生产厂家");
						break;
					case "2":
						supplierQueryResultExportVo.setAdrGrade("批发商");
						break;
					case "3":
						supplierQueryResultExportVo.setAdrGrade("经销商");
						break;
					case "4":
						supplierQueryResultExportVo.setAdrGrade("代销商");
						break;
					case "5":
						supplierQueryResultExportVo.setAdrGrade("其他");
						break;
				}
			}

			if(null != supplierQueryResultExportVo.getStatus()){
				switch (supplierQueryResultExportVo.getStatus()) {
					case "0":
						supplierQueryResultExportVo.setStatus("制单");
						break;
					case "1":
						supplierQueryResultExportVo.setStatus("已提交");
						break;
					case "2":
						supplierQueryResultExportVo.setStatus("已审核");
						break;
					case "3":
						supplierQueryResultExportVo.setStatus("已拒绝");
						break;
					case "4":
						supplierQueryResultExportVo.setStatus("修改中");
						break;
					default:
						supplierQueryResultExportVo.setStatus("");
				}
			}

			if(null != supplierQueryResultExportVo.getSpStatus()){
				switch (supplierQueryResultExportVo.getSpStatus()) {
					case "0":
						supplierQueryResultExportVo.setSpStatus("制单");
						break;
					case "1":
						supplierQueryResultExportVo.setSpStatus("已提交");
						break;
					case "2":
						supplierQueryResultExportVo.setSpStatus("已审核");
						break;
					case "3":
						supplierQueryResultExportVo.setSpStatus("已拒绝");
						break;
					case "4":
						supplierQueryResultExportVo.setSpStatus("修改中");
						break;
					default:
						supplierQueryResultExportVo.setSpStatus("");
				}
			}

		}
	}


	private void transData(List<SupplierQueryResultVo> list) {
		for (SupplierQueryResultVo supplierQueryResultVo : list) {
			// 供应商类型:0:全部;1:供应商;2:供应商地点
			// 供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商
			// 供应商地点等级:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他
			// 供应商状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:修改中
			if (null == supplierQueryResultVo.getProviderType()) {
				continue;
			}
			if ("1".equals(supplierQueryResultVo.getProviderType())) {
				if (null == supplierQueryResultVo.getGrade()) {
					continue;
				}
				switch (supplierQueryResultVo.getGrade()) {
				case "1":
					supplierQueryResultVo.setGrade("战略供应商");
					break;
				case "2":
					supplierQueryResultVo.setGrade("核心供应商");
					break;
				case "3":
					supplierQueryResultVo.setGrade("可替代供应商");
					break;
				default:
					// throw new RuntimeException("出现脏数据！");
				}
			} else if ("2".equals(supplierQueryResultVo.getProviderType())) {
				switch (supplierQueryResultVo.getGrade()) {
				case "1":
					supplierQueryResultVo.setGrade("生产厂家");
					break;
				case "2":
					supplierQueryResultVo.setGrade("批发商");
					break;
				case "3":
					supplierQueryResultVo.setGrade("经销商");
					break;
				case "4":
					supplierQueryResultVo.setGrade("代销商");
					break;
				case "5":
					supplierQueryResultVo.setGrade("其他");
					break;
				default:
					// throw new RuntimeException("出现脏数据！");
				}
			} else {
				// throw new RuntimeException("出现脏数据！");
			}
			supplierQueryResultVo
					.setProviderType("1".equals(supplierQueryResultVo.getProviderType()) ? "供应商" : "供应商地点");
			switch (supplierQueryResultVo.getStatus()) {
			case "0":
				supplierQueryResultVo.setStatus("制单");
				break;
			case "1":
				supplierQueryResultVo.setStatus("已提交");
				break;
			case "2":
				supplierQueryResultVo.setStatus("已审核");
				break;
			case "3":
				supplierQueryResultVo.setStatus("已拒绝");
				break;
			case "4":
				supplierQueryResultVo.setStatus("修改中");
				break;
			default:
				// throw new RuntimeException("出现脏数据！");
			}
		}
	}



	/**
	 * @Description: 根据类型获取供应商编码及地点编码
	 * @author huangjianjun
	 * @date 2017年7月19日下午7:58:00
	 */
	@RequestMapping(value = "/getSupplierNo", method = RequestMethod.GET)
	public Response<String> getSupplierNo(@RequestParam String type) {
		return supplierQueryDubboService.getSupplierNo(type);
	}



	/**
	 * @Description: 根据供应商编码或名称查询供应商分页列表组件
	 * @author huangjianjun
	 * @date 2017年7月21日下午7:58:00
	 */
	@RequestMapping(value = "/supplierSearchBox", method = RequestMethod.GET)
	public Response<PageResult<SpSearchBoxDto>> supplierSearchBox(SupplierQuerySearchBoxVo vo) {
		return supplierQueryDubboService.supplierSearchBox(vo.getCondition(), vo.getPageNum(), vo.getPageSize());
	}



	/**
	 * @Description: 根据供应商地点编码或名称或供应商ID查询供应商地点分页列表组件
	 * @author huangjianjun
	 * @date 2017年7月21日下午7:58:00
	 */
	@RequestMapping(value = "/supplierAdrSearchBox", method = RequestMethod.GET)
	public Response<PageResult<SpSearchBoxDto>> supplierAdrSearchBox(SupplierQuerySearchBoxVo vo,
			HttpServletRequest request) {
		// 只能查询当前用户子公司 多个子公司 yinyuxin
		LoginInfoVO userInfo = (LoginInfoVO) request.getSession().getAttribute(CURRENT_USER);
		if (null==userInfo) {
			Response<PageResult<SpSearchBoxDto>> response=new Response<>();
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("查询用户信息失败,操作终止");
			return response;
		}
		List<String> branchCompanyIds;
		branchCompanyIds=this.queryUserCompanyIds(userInfo.getUserId());
		return supplierQueryDubboService.supplierAdrSearchBox(vo.getPId(), branchCompanyIds, vo.getCondition(),
				vo.getPageNum(), vo.getPageSize());
	}



	/**
	 * <method description> 供应商入驻审核
	 *
	 * @param supplierAuditVo
	 */
	@RequestMapping(value = "/supplierSettledAudit", method = RequestMethod.POST)
	public Response<Boolean> supppliersettledAudit(@RequestBody @Valid SupplierAuditVo supplierAuditVo,
			HttpSession session) {
		Response<Boolean> response = new Response<>();
		// 获取当前用户信息 TODO
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (null == currentUser) {
			log.info("取当前用户信息为空。^_^");
		}
		SupplierAuditDto supplierAuditDto = BeanConvertUtils.convert(supplierAuditVo, SupplierAuditDto.class);
		supplierAuditDto.setAuditUserId(currentUser.getUserId());
		Response<Boolean> result = supplierWriteDubboService.auditSupplier(supplierAuditDto);
		if (result.isSuccess()) {
			response.setSuccess(result.isSuccess());
			response.setCode(result.getCode());
			response.setErrorMessage(result.getErrorMessage());
			response.setResultObject(result.getResultObject());
			return response;
		}
		return getFailResponse();
	}



	/**
	 * <method description> 供应商地点入驻审核
	 *
	 * @param supplierAdrAuditVo
	 */
	@RequestMapping(value = "/supplierAdrSettledAudit", method = RequestMethod.POST)
	public Response<Boolean> supplierAdrSettledAudit(@RequestBody @Valid SupplierAdrAuditVo supplierAdrAuditVo,
			HttpSession session) {
		Response<Boolean> response = new Response<>();
		// 获取当前用户信息 TODO
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (null == currentUser) {
			log.info("取当前用户信息为空。^_^");
		}
		SupplierAdrAuditDto supplierAdrAuditDto = BeanConvertUtils.convert(supplierAdrAuditVo,
				SupplierAdrAuditDto.class);
		supplierAdrAuditDto.setAuditUserId(currentUser.getUserId());
		Response<Boolean> result = supplierWriteDubboService.auditSupplierAdr(supplierAdrAuditDto);
		if (result.isSuccess()) {
			response.setSuccess(result.isSuccess());
			response.setCode(result.getCode());
			response.setErrorMessage(result.getErrorMessage());
			response.setResultObject(result.getResultObject());
			return response;
		}
		return getFailResponse();
	}
}
