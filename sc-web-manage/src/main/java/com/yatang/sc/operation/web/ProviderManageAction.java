// package com.yatang.sc.operation.web;
//
// import com.busi.common.resp.Response;
// import com.busi.common.utils.BeanConvertUtils;
// import com.github.pagehelper.PageInfo;
// import com.yatang.sc.facade.common.CommonsEnum;
// import com.yatang.sc.facade.common.PageResult;
// import com.yatang.sc.facade.dto.AuditFailedReasonDto;
// import com.yatang.sc.facade.dto.MultiQuerySupplierDto;
// import com.yatang.sc.facade.dto.SupplierAuditInfoDto;
// import com.yatang.sc.facade.dto.SupplierBankInfoDto;
// import com.yatang.sc.facade.dto.SupplierBasicInfoDto;
// import com.yatang.sc.facade.dto.SupplierCooperationEditDto;
// import com.yatang.sc.facade.dto.SupplierCooperationInfoDto;
// import com.yatang.sc.facade.dto.SupplierEmerContInfoDto;
// import com.yatang.sc.facade.dto.SupplierEnterQueryParamDto;
// import com.yatang.sc.facade.dto.SupplierInfoDto;
// import com.yatang.sc.facade.dto.SupplierOldNewBankInfoDto;
// import com.yatang.sc.facade.dto.SupplierOldNewEmerContInfoDto;
// import com.yatang.sc.facade.dto.SupplierOldNewOperTaxInfoDto;
// import com.yatang.sc.facade.dto.SupplierOldNewOrgCodeInfoDto;
// import com.yatang.sc.facade.dto.SupplierOldNewSettledContInfoDto;
// import com.yatang.sc.facade.dto.SupplierOldNewlicenseInfoDto;
// import com.yatang.sc.facade.dto.SupplierOperTaxInfoDto;
// import com.yatang.sc.facade.dto.SupplierOrgCodeInfoDto;
// import com.yatang.sc.facade.dto.SupplierSaleRegionDto;
// import com.yatang.sc.facade.dto.SupplierSettledContInfoDto;
// import com.yatang.sc.facade.dto.SupplierShowListDto;
// import com.yatang.sc.facade.dto.SupplierlicenseInfoDto;
// import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
// import com.yatang.sc.facade.dubboservice.SupplierWriteDubboService;
// import com.yatang.sc.facade.enums.PublicEnum;
// import com.yatang.sc.operation.vo.ApprovalSupplierVo;
// import com.yatang.sc.operation.vo.AuditFailedReasonVo;
// import com.yatang.sc.operation.vo.LicenseNoAndOrgCodeVo;
// import com.yatang.sc.operation.vo.MultiQuerySupplierVo;
// import com.yatang.sc.operation.vo.SupplierAuditInfoVo;
// import com.yatang.sc.operation.vo.SupplierBankInfoVo;
// import com.yatang.sc.operation.vo.SupplierBasicInfoVo;
// import com.yatang.sc.operation.vo.SupplierCheckVo;
// import com.yatang.sc.operation.vo.SupplierCooperationEditVo;
// import com.yatang.sc.operation.vo.SupplierEmerContInfoVo;
// import com.yatang.sc.operation.vo.SupplierEnterExcelModel;
// import com.yatang.sc.operation.vo.SupplierEnterExcelView;
// import com.yatang.sc.operation.vo.SupplierEnterQueryParamVo;
// import com.yatang.sc.operation.vo.SupplierExcelModel;
// import com.yatang.sc.operation.vo.SupplierExcelView;
// import com.yatang.sc.operation.vo.SupplierInfoVo;
// import com.yatang.sc.operation.vo.SupplierListExcelModel;
// import com.yatang.sc.operation.vo.SupplierListExcelView;
// import com.yatang.sc.operation.vo.SupplierOperTaxInfoVo;
// import com.yatang.sc.operation.vo.SupplierOrgCodeInfoVo;
// import com.yatang.sc.operation.vo.SupplierSettledContInfoVo;
// import com.yatang.sc.operation.vo.SupplierShowListVo;
// import com.yatang.sc.operation.vo.SupplierlicenseInfoVo;
// import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.lang.exception.ExceptionUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.ModelAndView;
//
// import javax.servlet.http.HttpSession;
// import java.util.List;
// import java.util.Map;
//
// import static com.yatang.sc.facade.common.Constants.CURRENT_USER;
// import static com.yatang.sc.facade.enums.PublicEnum.DEFAULT_PAGE_NUM;
// import static com.yatang.sc.facade.enums.PublicEnum.DEFAULT_PAGE_SIZE;
//
/// **
// * @描述: 供应商控制层
// * @作者: huangjianjun
// * @创建时间: 2017年5月17日-下午9:34:00 .
// * @版本: 1.0 .
// */
// @Slf4j
// @RestController
// @RequiredArgsConstructor(onConstructor = @__(@Autowired))
// @RequestMapping(value = "/sc/provider")
// public class ProviderManageAction extends BaseAction {
//
// private final SupplierQueryDubboService supplierQueryDubboService;
//
// private final SupplierWriteDubboService supplierWriteDubboService;
//
//
//
// @RequestMapping("/exception")
// public void exception() {
// log.info("异常测试");
// throw new RuntimeException("我是一个自定义异常");
// }
//
//
//
// /**
// * @param
// * @Description:查询供应商详情
// * @author huangjianjun
// * @date 2017年5月19日 下午2:36:26
// */
// @RequestMapping(value = "queryProviderDetail", method = { RequestMethod.GET
// })
// public Response<SupplierInfoDto> queryProviderDetail(@RequestParam String
// spId) {
// return supplierQueryDubboService.queryById(spId);
// }
//
//
//
// /**
// * @param
// * @Description:录入供应商的详细信息
// * @author yangshuang
// * @date 2017/5/20 14:12
// */
// @RequestMapping(value = "/insertSupplierSettlementInfo", method =
// RequestMethod.POST)
// public Response<Boolean> insertSupplierSettlementInfo(@RequestBody
// SupplierInfoVo supplierInfoVo) {
// // 一、录入前进行接口校验
// // 1.供应商基本信息校验
// SupplierBasicInfoVo supplierBasicInfo =
// supplierInfoVo.getSupplierBasicInfo();
// // vo2dto
// SupplierBasicInfoDto basicDto = BeanConvertUtils.convert(supplierBasicInfo,
// SupplierBasicInfoDto.class);
// Response<Void> basicResponse =
// supplierQueryDubboService.checkSupplierBasicInfo(basicDto);
// // 校验有误
// if (!basicResponse.isSuccess()) {
// return BeanConvertUtils.convert(basicResponse, Response.class);
// }
// // 2. 校验公司以及税务信息
// String taxpayerNumber =
// supplierInfoVo.getSupplierOperTaxInfo().getTaxpayerNumber();
// Response<Void> operTaxResponse =
// supplierQueryDubboService.checkSupplierOperTaxBytaxpayerNumber(taxpayerNumber,
// supplierInfoVo.getSupplierOperTaxInfo().getId() == null ? null
// : supplierInfoVo.getSupplierOperTaxInfo().getId().toString());
// if (!operTaxResponse.isSuccess()) {
// return BeanConvertUtils.convert(operTaxResponse, Response.class);
// }
// // 校3.验银行信息
// String bankAccount = supplierInfoVo.getSupplierBankInfo().getBankAccount();//
// 可能有错
// Response<Void> bankResponse =
// supplierQueryDubboService.checkSupplierBankInfoByBankAccount(bankAccount,
// supplierInfoVo.getSupplierBankInfo().getId() == null ? null
// : supplierInfoVo.getSupplierBankInfo().getId().toString());
// if (!bankResponse.isSuccess()) {
// return BeanConvertUtils.convert(bankResponse, Response.class);
// }
// // 4.校验营业执照重复情况
// Response<Void> licenceResponse =
// supplierQueryDubboService.checkSupplierLicenseInfoByRegistLicenceNo(
// supplierInfoVo.getSupplierlicenseInfo().getRegistLicenceNumber(),
// supplierInfoVo.getSupplierlicenseInfo().getId() == null ? null
// : supplierInfoVo.getSupplierlicenseInfo().getId().toString());
// if (!licenceResponse.isSuccess()) {
// return BeanConvertUtils.convert(licenceResponse, Response.class);
// }
// // 5.校验组织机构码
// Response<Void> orgResponse =
// supplierQueryDubboService.checkSupplierOrgCodeInfoByCode(
// supplierInfoVo.getSupplierOrgCodeInfo().getOrgCode(),
// supplierInfoVo.getSupplierOrgCodeInfo().getId() == null ? null
// : supplierInfoVo.getSupplierOrgCodeInfo().getId().toString());
// if (!orgResponse.isSuccess()) {
// return BeanConvertUtils.convert(orgResponse, Response.class);
// }
// // 6.紧急联系人
//
// Response<Void> emerResponse =
// supplierQueryDubboService.checkSupplierEmerCont(
// supplierInfoVo.getSupplierEmerContInfo().getPhone(),
// supplierInfoVo.getSupplierEmerContInfo().getCompanyPhoneNumber(),
// supplierInfoVo.getSupplierEmerContInfo().getId() == null ? null
// : supplierInfoVo.getSupplierEmerContInfo().getId().toString());
// if (!emerResponse.isSuccess()) {
// return BeanConvertUtils.convert(emerResponse, Response.class);
// }
// // 7.入住联系人
//
// Response<Void> settledResponse =
// supplierQueryDubboService.checkSupplierSettledCont(
// supplierInfoVo.getSupplierSettledContInfo().getPhone(),
// supplierInfoVo.getSupplierSettledContInfo().getEmail(),
// supplierInfoVo.getSupplierSettledContInfo().getId() == null ? null
// : supplierInfoVo.getSupplierSettledContInfo().getId().toString());
// if (!settledResponse.isSuccess()) {
// return BeanConvertUtils.convert(settledResponse, Response.class);
// }
// // 二、录入信息
// SupplierInfoDto supplierInfoDto = BeanConvertUtils.convert(supplierInfoVo,
// SupplierInfoDto.class);
// Response<Boolean> response =
// supplierWriteDubboService.insertSupplierSettlementInfo(supplierInfoDto);
// return response;
//
// }
//
//
//
// /**
// * 判定供应商的基本信息是否有重复
// *
// * @param supplierBasicInfoVo
// * @return
// * @author yangshuang
// */
// @RequestMapping(value = "/checkSupplierBasicInfo", method =
// RequestMethod.POST)
// public Response<Void> checkSupplierBasicInfo(@RequestBody SupplierBasicInfoVo
// supplierBasicInfoVo) {
// // vo2Dto
// SupplierBasicInfoDto basicDto = BeanConvertUtils.convert(supplierBasicInfoVo,
// SupplierBasicInfoDto.class);
// Response<Void> response =
// supplierQueryDubboService.checkSupplierBasicInfo(basicDto);
// return response;
//
// }
//
//
//
// /**
// * 通过纳税人识别号验证纳税人编号已存在
// *
// * @param taxpayerNumber
// * 纳税人识别号
// * @param id
// * 主键
// * @return
// * @author yangshuang
// */
// @RequestMapping(value = "/checkSupplierOperTaxBytaxpayerNumber", method =
// RequestMethod.GET)
// public Response<Void> checkSupplierOperTaxBytaxpayerNumber(String
// taxpayerNumber, String id) {
// Response<Void> response =
// supplierQueryDubboService.checkSupplierOperTaxBytaxpayerNumber(taxpayerNumber,
// id);
// return response;
// }
//
//
//
// /**
// * 通过银行账户判定是否重复
// *
// * @param bankAccount
// * 银行账号
// * @param id
// * 主键
// * @return
// * @author yangshuang
// */
// @RequestMapping(value = "/checkSupplierBankInfoByBankAccount", method =
// RequestMethod.GET)
// public Response<Void> checkSupplierBankInfoByBankAccount(String bankAccount,
// String id) {
// Response<Void> response =
// supplierQueryDubboService.checkSupplierBankInfoByBankAccount(bankAccount,
// id);
// return response;
//
// }
//
//
//
// /**
// * 供应商信息校验stepOne:基本信息公司经营以及税务信息和银行信息校验
// *
// * @param supplierCheckVo
// * @return
// */
// @RequestMapping(value = "/stepOneCheckSupplierInfo", method =
// RequestMethod.POST)
// public Response<Void> stepOneCheckSupplierInfo(@RequestBody SupplierCheckVo
// supplierCheckVo) {
// Response<Void> response = new Response<>();
// // 1.供应商基本信息校验
// SupplierBasicInfoVo supplierBasicInfo =
// supplierCheckVo.getSupplierBasicInfo();
// // vo2dto
// SupplierBasicInfoDto basicDto = BeanConvertUtils.convert(supplierBasicInfo,
// SupplierBasicInfoDto.class);
// Response<Void> basicResponse =
// supplierQueryDubboService.checkSupplierBasicInfo(basicDto);
// // 校验有误
// if (!basicResponse.isSuccess()) {
// return basicResponse;
// }
// // 2. 校验公司以及税务信息
// String taxpayerNumber = supplierCheckVo.getTaxpayerNo();
// Response<Void> operTaxResponse =
// supplierQueryDubboService.checkSupplierOperTaxBytaxpayerNumber(taxpayerNumber,
// supplierCheckVo.getTaxId());
// if (!operTaxResponse.isSuccess()) {
// return operTaxResponse;
// }
// // 校3.验银行信息
// String bankAccount = supplierCheckVo.getBankAccount();
// Response<Void> bankResponse =
// supplierQueryDubboService.checkSupplierBankInfoByBankAccount(bankAccount,
// supplierCheckVo.getBankId());
// if (!bankResponse.isSuccess()) {
// return bankResponse;
// }
// response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
// response.setSuccess(true);
// return response;
// }
//
//
//
// /**
// * 第二步校验 校验营业执照号和组织机构代码
// *
// * @param licenseNoAndOrgCodeVo
// * @return
// */
// @RequestMapping(value = "/stepTwoCheckSupplierInfo", method =
// RequestMethod.GET)
// public Response<Void> stepTwoCheckSupplierInfo(LicenseNoAndOrgCodeVo
// licenseNoAndOrgCodeVo) {
// Response<Void> response = new Response<>();
// // 1.校验营业执照重复情况
// Response<Void> licenceResponse =
// supplierQueryDubboService.checkSupplierLicenseInfoByRegistLicenceNo(
// licenseNoAndOrgCodeVo.getRegistLicenceNo(),
// licenseNoAndOrgCodeVo.getLicenceId());
// if (!licenceResponse.isSuccess()) {
// return licenceResponse;
// }
// // 2.校验组织机构码
// Response<Void> orgResponse =
// supplierQueryDubboService.checkSupplierOrgCodeInfoByCode(
// licenseNoAndOrgCodeVo.getOrgCode(), licenseNoAndOrgCodeVo.getOrgCodeId());
// if (!orgResponse.isSuccess()) {
// return orgResponse;
// }
// response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
// response.setSuccess(true);
// return response;
// }
//
//
//
// /**
// * 通过营业执照编号判定是否重复
// *
// * @param registLicenceNo
// * 营业执照
// * @param id
// * 营业执照的主键
// * @return
// * @author yangshuang
// */
// @RequestMapping(value = "/checkSupplierLicenseInfoByRegistLicenceNo", method
// = RequestMethod.GET)
// public Response<Void> checkSupplierLicenseInfoByRegistLicenceNo(String
// registLicenceNo, String id) {
// Response<Void> response =
// supplierQueryDubboService.checkSupplierLicenseInfoByRegistLicenceNo(registLicenceNo,
// id);
// return response;
// }
//
//
//
// /**
// * 通过组织代码判定是否重复
// *
// * @param orgCode
// * 组织机构代码
// * @param id
// * 组织机构代码主键
// * @return
// * @author yangshuang
// */
// @RequestMapping(value = "/checkSupplierOrgCodeInfoByCode", method =
// RequestMethod.GET)
// public Response<Void> checkSupplierOrgCodeInfoByCode(String orgCode, String
// id) {
// Response<Void> response =
// supplierQueryDubboService.checkSupplierOrgCodeInfoByCode(orgCode, id);
// return response;
// }
//
//
//
// /**
// * 通过供应商手机号和公司电话判定紧急联系人是否已存在
// *
// * @param phone
// * 紧急联系人电话
// * @param companyPhoneNumber
// * 公司电话
// * @param id
// * 供应商紧急联系人表主键
// * @return
// * @author yangshuang
// */
// @RequestMapping(value = "/checkSupplierEmerCont", method = RequestMethod.GET)
// public Response<Void> checkSupplierEmerCont(String phone, String
// companyPhoneNumber, String id) {
// Response<Void> response =
// supplierQueryDubboService.checkSupplierEmerCont(phone, companyPhoneNumber,
// id);
// return response;
// }
//
//
//
// /**
// * 通过供应商手机号和公司电话判定紧急联系人是否已存在
// *
// * @param phone
// * 招商入驻联系人
// * @param email
// * 公司电话
// * @param id
// * 入住联系人主键
// * @return
// * @author yangshuang
// */
//
// @RequestMapping(value = "/checkSupplierSettledCont", method =
// RequestMethod.GET)
// public Response<Void> checkSupplierSettledCont(String phone, String email,
// String id) {
// Response<Void> response =
// supplierQueryDubboService.checkSupplierSettledCont(phone, email, id);
// return response;
// }
//
// /*
// */
// /**
// * 第三步校验 对所有需要校验的数据再次校验并校验公司入驻联系人和紧急联系人相关信息
// *
// * @author yangshuang
// * @return
// *//*
// *
// * @RequestMapping(value = "/stepThreeCheckAllSupplierInfo", method =
// * RequestMethod.GET) public Response<Void>
// * stepThreeCheckSupplierInfo(SupplierCheckAllVo supplierCheckAllVo) {
// * Response<Void> response = new Response<>(); //1.供应商基本信息校验
// * SupplierBasicInfoVo supplierBasicInfo =
// * supplierCheckAllVo.getSupplierBasicInfo(); //vo2dto
// * SupplierBasicInfoDto basicDto =
// * BeanConvertUtils.convert(supplierBasicInfo,
// * SupplierBasicInfoDto.class); Response<Void> basicResponse =
// * supplierQueryDubboService.checkSupplierBasicInfo(basicDto); //校验有误 if
// * (!basicResponse.isSuccess()) { return basicResponse; } //2.
// * 校验公司以及税务信息 String taxpayerNumber =
// * supplierCheckAllVo.getTaxpayerNo(); Response<Void> operTaxResponse =
// * supplierQueryDubboService.checkSupplierOperTaxBytaxpayerNumber(
// * taxpayerNumber); if (!operTaxResponse.isSuccess()) { return
// * operTaxResponse; } //校3.验银行信息 String bankAccount =
// * supplierCheckAllVo.getBankAccount(); Response<Void> bankResponse =
// * supplierQueryDubboService.checkSupplierBankInfoByBankAccount(
// * bankAccount); if (!bankResponse.isSuccess()) { return bankResponse; }
// * //4.校验营业执照重复情况 Response<Void> licenceResponse =
// * supplierQueryDubboService.checkSupplierLicenseInfoByRegistLicenceNo(
// * supplierCheckAllVo.getRegistLicenceNo()); if
// * (!licenceResponse.isSuccess()) { return licenceResponse; }
// * //5.校验组织机构码 Response<Void> orgResponse =
// * supplierQueryDubboService.checkSupplierOrgCodeInfoByCode(
// * supplierCheckAllVo.getOrgCode()); if (!orgResponse.isSuccess()) {
// * return orgResponse; } //6.紧急联系人 Response<Void> emerResponse =
// * supplierQueryDubboService.checkSupplierEmerCont(supplierCheckAllVo.
// * getEmerPhone(), supplierCheckAllVo.getEmerCompanyPhoneNo()); if
// * (!emerResponse.isSuccess()) { return emerResponse; } //7.入住联系人
// * Response<Void> settledResponse =
// * supplierQueryDubboService.checkSupplierSettledCont(supplierCheckAllVo
// * .getSettledPhone(), supplierCheckAllVo.getSettledEmail()); if
// * (!settledResponse.isSuccess()) { return settledResponse; }
// * response.setErrorMessage("校验成功"); response.setSuccess(true); return
// * response; }
// */
//
//
//
// /**
// * 供应商入驻申请列表查询
// *
// * @param supplierEnterQueryParamVo
// * @author lvheping
// */
// @RequestMapping(value = "/queryProviderEnterList", method = {
// RequestMethod.GET })
// public Response<PageResult<SupplierShowListVo>> queryProviderEnterList(
// SupplierEnterQueryParamVo supplierEnterQueryParamVo) {
// if (supplierEnterQueryParamVo.getPageNum() == null &&
// supplierEnterQueryParamVo.getPageSize() == null) {
// supplierEnterQueryParamVo.setPageNum(Integer.parseInt(PublicEnum.DEFAULT_PAGE_NUM.getCodeStr()));
// supplierEnterQueryParamVo.setPageSize(Integer.parseInt(PublicEnum.DEFAULT_PAGE_SIZE.getCodeStr()));
// }
// SupplierEnterQueryParamDto convert1 =
// BeanConvertUtils.convert(supplierEnterQueryParamVo,
// SupplierEnterQueryParamDto.class);
// Response response = new Response<>();
// Response<PageResult<SupplierShowListDto>> mapResponse =
// supplierQueryDubboService
// .querySettledRequestListPage(convert1);
// PageResult<SupplierShowListDto> resultObject = mapResponse.getResultObject();
// List<SupplierShowListDto> data = resultObject.getData();
// List<SupplierShowListVo> supplierShowListVos =
// BeanConvertUtils.convertList(data, SupplierShowListVo.class);
// PageResult convert = BeanConvertUtils.convert(resultObject,
// PageResult.class);
// convert.setData(supplierShowListVos);
// response = BeanConvertUtils.convert(mapResponse, Response.class);
// response.setResultObject(convert);
// return response;
// }
//
//
//
// /**
// * 供应商综合审核录入
// *
// * @param
// * @return
// * @author lvheping
// *
// */
// @RequestMapping(value = "/insertSupplierAuditInfo", method =
// RequestMethod.POST)
// public Response<Boolean> insertSupplierAuditInfo(@RequestBody
// SupplierAuditInfoVo supplierAuditInfoVo, HttpSession session) {
// LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
// if (attribute == null || supplierAuditInfoVo.getId() == null) {
// Response response = new Response();
// response.setCode(CommonsEnum.RESPONSE_401.getCode());
// response.setSuccess(false);
// response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
// return response;
// }
// supplierAuditInfoVo.setAuditUserId(attribute.getUserId());
//
// SupplierAuditInfoDto supplierAuditInfoDto =
// BeanConvertUtils.convert(supplierAuditInfoVo,
// SupplierAuditInfoDto.class);
// Response<Boolean> response =
// supplierWriteDubboService.insertSupplierAuditInfo(supplierAuditInfoDto);
// return response;
//
// }
//
//
//
// /**
// * 根据供应商ID查询审核失败原因
// *
// * @author lvheping
// * @param
// * @return
// * @author lvheping
// */
// @RequestMapping(value = "/findAuditFailedReason", method = RequestMethod.GET)
// public Response<AuditFailedReasonVo> findAuditFailedReason(String id) {
// Response<AuditFailedReasonDto> response =
// supplierQueryDubboService.findAuditFailedReason(id);
// if (!response.isSuccess()) {
// return BeanConvertUtils.convert(response, Response.class);
// }
// AuditFailedReasonDto resultObject = response.getResultObject();
// AuditFailedReasonVo convert = BeanConvertUtils.convert(resultObject,
// AuditFailedReasonVo.class);
// Response convert1 = BeanConvertUtils.convert(response, Response.class);
// convert1.setResultObject(convert);
// return convert1;
// }
//
//
//
// /**
// * 资料
// *
// * @param
// * @return
// * @Description:导出供应商入驻申请列表
// * @author lvheping
// * @date 2017年5月24日 下午9:50:10
// */
// @RequestMapping(value = "exportSupplierEnterList", method =
// RequestMethod.GET)
// public ModelAndView exportSupplierEnterList(SupplierEnterQueryParamVo
// supplierEnterQueryParamVo) {
// supplierEnterQueryParamVo.setPageNum(1);
// supplierEnterQueryParamVo.setPageSize(Integer.MAX_VALUE);
// SupplierEnterQueryParamDto convert1 =
// BeanConvertUtils.convert(supplierEnterQueryParamVo,
// SupplierEnterQueryParamDto.class);
// Response<PageResult<SupplierShowListDto>> pageResultResponse =
// supplierQueryDubboService
// .querySettledRequestListPage(convert1);
// List<SupplierEnterExcelModel> orderExcelDtos = SupplierEnterExcelModel
// .ofListEnter(pageResultResponse.getResultObject().getData());
// SupplierEnterExcelView excelView = new
// SupplierEnterExcelView(orderExcelDtos);
// // 如果导出文件名不正确，请用其他浏览器试试
// return new ModelAndView(excelView);
// }
//
//
//
// /**
// * @param multiQueryVo
// * @Description: 查询供应商管理列表
// * @author tankejia
// * @date 2017/5/22- 10:05
// */
// @RequestMapping(value = "queryApprovedSupplier", method = RequestMethod.GET)
// public Response<PageResult<SupplierShowListVo>>
// queryApprovedSupplier(MultiQuerySupplierVo multiQueryVo) {
// log.info("params:{}", multiQueryVo);
// multiQueryVo.setType(1);
//
// Response<PageResult<SupplierShowListVo>> response = new Response();
// MultiQuerySupplierDto multiDto = BeanConvertUtils.convert(multiQueryVo,
// MultiQuerySupplierDto.class);
// multiDto.setPageNum(multiQueryVo.getPageNum() != null ?
// multiQueryVo.getPageNum() : 1);
// multiDto.setPageSize(multiQueryVo.getPageSize() != null ?
// multiQueryVo.getPageSize()
// : Integer.valueOf(DEFAULT_PAGE_SIZE.getCodeStr()));
//
// try {
// PageResult<SupplierShowListDto> pageResult =
// supplierQueryDubboService.querySettledListPage(multiDto)
// .getResultObject();
// List<SupplierShowListVo> listVos =
// BeanConvertUtils.convertList(pageResult.getData(),
// SupplierShowListVo.class);
// PageResult<SupplierShowListVo> pageResultListVo = new PageResult<>();
// pageResultListVo.setData(listVos);
// pageResultListVo.setPageNum(pageResult.getPageNum());
// pageResultListVo.setPageSize(pageResult.getPageSize());
// pageResultListVo.setTotal(pageResult.getTotal());
// response.setResultObject(pageResultListVo);
// response.setSuccess(true);
// } catch (Exception e) {
// log.error(ExceptionUtils.getFullStackTrace(e));
// response.setCode(CommonsEnum.RESPONSE_500.getCode());
// response.setSuccess(false);
// response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
// }
// return response;
// }
//
//
//
// /**
// * @param cooperationId
// * @Description: 查询供应商合作信息（回显）
// * @author tankejia
// * @date 2017/5/22- 10:21
// */
// @RequestMapping(value = "querySupplierCooperationInfo", method =
// RequestMethod.GET)
// public Response<SupplierCooperationEditVo> queryCooperationInfo(@RequestParam
// Integer cooperationId) {
// Response<SupplierCooperationEditVo> response = new Response<>();
// try {
// Response<SupplierCooperationEditDto> responseDto =
// supplierQueryDubboService.querySupplierCooperationInfoById(cooperationId);
// SupplierCooperationEditVo vo =
// BeanConvertUtils.convert(responseDto.getResultObject(),
// SupplierCooperationEditVo.class);
// response.setSuccess(true);
// response.setResultObject(vo);
// } catch (Exception e) {
// log.error(ExceptionUtils.getFullStackTrace(e));
// response.setCode(CommonsEnum.RESPONSE_500.getCode());
// response.setSuccess(false);
// response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
// }
// return response;
// }
//
//
//
// /**
// * @param record
// * @Description: 修改供应商合作信息
// * @author tankejia
// * @date 2017/5/22- 11:02
// */
// @RequestMapping(value = "updateSupplierCooperationInfo", method =
// RequestMethod.POST)
// public Response<Boolean> updateCooperationInfo(@RequestBody @Validated
// SupplierCooperationEditVo record) {
// SupplierCooperationInfoDto dto = BeanConvertUtils.convert(record,
// SupplierCooperationInfoDto.class);
// return supplierWriteDubboService.updateCooperationInfo(dto);
// }
//
//
//
// /**
// * @Description: 修改供应商状态（冻结、终止合作、解除冻结、重启合作）
// * @author tankejia
// * @param record
// * @date 2017/5/22- 11:12
// */
// @RequestMapping(value = "editSupplierStatus", method = RequestMethod.POST)
// public Response<Boolean> editSupplierStatus(@RequestBody SupplierInfoVo
// record) {
// SupplierInfoDto dto = BeanConvertUtils.convert(record,
// SupplierInfoDto.class);
// return supplierWriteDubboService.editSupplierStatus(dto);
// }
//
//
//
// /**
// * @param multiQueryVo
// * @return
// * @Description: 导出供应商列表Excel文件
// * @author tankejia
// * @date 2017/5/26- 21:03
// */
// @RequestMapping(value = "exportSupplierList", method = RequestMethod.GET)
// public ModelAndView exportSupplierList(MultiQuerySupplierVo multiQueryVo) {
// log.info("params:{}", multiQueryVo);
// multiQueryVo.setType(1);
// multiQueryVo.setPageNum(Integer.valueOf(DEFAULT_PAGE_NUM.getCodeStr()));
// multiQueryVo.setPageSize(Integer.MAX_VALUE);
// MultiQuerySupplierDto multiDto = BeanConvertUtils.convert(multiQueryVo,
// MultiQuerySupplierDto.class);
// Response<PageResult<SupplierShowListDto>> mapResponse =
// supplierQueryDubboService
// .querySettledListPage(multiDto);
// List<SupplierListExcelModel> models =
// SupplierListExcelModel.ofList(mapResponse.getResultObject().getData());
// SupplierListExcelView excelView = new SupplierListExcelView(models);
// // 如果导出文件名不正确，请用其他浏览器试试
// return new ModelAndView(excelView);
// }
//
//
//
// @RequestMapping(value = "supplierSaleRegionList", method = RequestMethod.GET)
// Response<PageInfo<Map<String, Object>>> supplierSaleRegionList(@RequestParam
// Map<String, Object> paramMap) {
// return supplierQueryDubboService.supplierSaleRegionList(paramMap);
// }
//
//
//
// @RequestMapping(value = "supplierSaleRegions", method = RequestMethod.GET)
// Response<List<SupplierSaleRegionDto>> supplierSaleRegions(String spNo) {
// return supplierQueryDubboService.supplierSaleRegions(spNo);
// }
//
//
//
// /**
// * @Description: 供应商修改资料申请列表
// * @author kangdong
// * @date 2017年5月22日 下午14:50:33
// * @param multiQueryVo
// * @return
// */
// @RequestMapping(value = "supplierEditApplyList", method = { RequestMethod.GET
// })
// public Response<PageResult<SupplierShowListVo>>
// supplierEditApplyList(MultiQuerySupplierVo multiQueryVo) {
// log.info("params:{}", multiQueryVo);
// multiQueryVo.setType(0);
// Response<PageResult<SupplierShowListVo>> response = new Response();
// MultiQuerySupplierDto multiDto = BeanConvertUtils.convert(multiQueryVo,
// MultiQuerySupplierDto.class);
// multiDto.setPageNum(multiQueryVo.getPageNum() != null ?
// multiQueryVo.getPageNum() : 1);
// multiDto.setPageSize(multiQueryVo.getPageSize() != null ?
// multiQueryVo.getPageSize()
// : Integer.valueOf(DEFAULT_PAGE_SIZE.getCodeStr()));
// try {
// PageResult<SupplierShowListDto> pageResult = supplierQueryDubboService
// .querySettledEditRequestListPage(multiDto).getResultObject();
// List<SupplierShowListVo> listVos =
// BeanConvertUtils.convertList(pageResult.getData(),
// SupplierShowListVo.class);
// PageResult<SupplierShowListVo> pageResultListVo = new PageResult<>();
// pageResultListVo.setData(listVos);
// pageResultListVo.setPageNum(pageResult.getPageNum());
//
// pageResultListVo.setPageSize(pageResult.getPageSize());
// pageResultListVo.setTotal(pageResult.getTotal());
// response.setResultObject(pageResultListVo);
// response.setCode(CommonsEnum.RESPONSE_200.getCode());
// response.setSuccess(true);
// } catch (Exception e) {
// log.error(ExceptionUtils.getFullStackTrace(e));
// response.setCode(CommonsEnum.RESPONSE_500.getCode());
// response.setSuccess(false);
// response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
// }
// return response;
// }
//
//
//
// /**
// * 资料
// *
// * @Description:导出供应商列表
// * @author kangdong
// * @date 2017年5月24日 下午9:50:10
// * @param multiQueryVo
// * @return
// */
// @RequestMapping(value = "exportEditApplySupplier", method =
// RequestMethod.GET)
// public ModelAndView exportSupplierEditApplyList(MultiQuerySupplierVo
// multiQueryVo) {
// multiQueryVo.setType(0);
// multiQueryVo.setPageNum(Integer.valueOf(DEFAULT_PAGE_NUM.getCodeStr()));
// multiQueryVo.setPageSize(Integer.MAX_VALUE);
// MultiQuerySupplierDto multiDto = BeanConvertUtils.convert(multiQueryVo,
// MultiQuerySupplierDto.class);
// Response<PageResult<SupplierShowListDto>> mapResponse =
// supplierQueryDubboService
// .querySettledEditRequestListPage(multiDto);
// List<SupplierExcelModel> orderExcelDtos =
// SupplierExcelModel.ofList(mapResponse.getResultObject().getData());
// SupplierExcelView excelView = new SupplierExcelView(orderExcelDtos);
// // 如果导出文件名不正确，请用其他浏览器试试
// return new ModelAndView(excelView);
// }
//
//
//
// /**
// * @Description:公司经营及税务信息修改前和修改后内容
// * @author kangdong
// * @date 2017年5月26日 上午11:22:10
// * @param id
// * @return
// */
// @RequestMapping(value = "showOldAndNewOperTaxContent", method = {
// RequestMethod.GET })
// public Response<SupplierOldNewOperTaxInfoDto>
// showOldAndNewOperTaxContent(@RequestParam Integer id) {
// Response<SupplierOldNewOperTaxInfoDto> mapResponse =
// supplierQueryDubboService.queryOperTaxInOldAndNewById(id);
// return mapResponse;
// }
//
//
//
// /**
// * @Description:商家银行信息修改前和修改后内容
// * @author kangdong
// * @date 2017年5月26日 上午11:22:10
// * @param id
// * @return
// */
// @RequestMapping(value = "showOldAndNewBankContent", method = {
// RequestMethod.GET })
// public Response<SupplierOldNewBankInfoDto>
// showOldAndNewBankContent(@RequestParam Integer id) {
// Response<SupplierOldNewBankInfoDto> mapResponse =
// supplierQueryDubboService.queryBankOldAndNewById(id);
// return mapResponse;
// }
//
//
//
// /**
// * @Description:公司营业执照（副本）修改前和修改后内容
// * @author kangdong
// * @date 2017年6月15日 上午11:22:10
// * @param id
// * @return
// */
// @RequestMapping(value = "showOldAndNewLicenseInfo", method = {
// RequestMethod.GET })
// public Response<SupplierOldNewlicenseInfoDto>
// showOldAndNewLicenseInfo(@RequestParam Integer id) {
// Response<SupplierOldNewlicenseInfoDto> mapResponse =
// supplierQueryDubboService.queryLicenseOldAndNewById(id);
// return mapResponse;
// }
//
//
//
// /**
// * @Description:组织机构代码证修改前和修改后内容
// * @author kangdong
// * @date 2017年6月15日 上午11:22:10
// * @param id
// * @return
// */
// @RequestMapping(value = "showOldAndNewOrgCodeInfo", method = {
// RequestMethod.GET })
// public Response<SupplierOldNewOrgCodeInfoDto>
// showOldAndNewOrgCodeInfo(@RequestParam Integer id) {
// Response<SupplierOldNewOrgCodeInfoDto> mapResponse =
// supplierQueryDubboService.queryOrgCodeOldAndNewById(id);
// return mapResponse;
// }
//
//
//
// /**
// * @Description:供应商紧急联系人修改前和修改后内容
// * @author kangdong
// * @date 2017年6月15日 上午11:22:10
// * @param id
// * @return
// */
// @RequestMapping(value = "showOldAndNewEmerContInfo", method = {
// RequestMethod.GET })
// public Response<SupplierOldNewEmerContInfoDto>
// showOldAndNewEmerContInfo(@RequestParam Integer id) {
// Response<SupplierOldNewEmerContInfoDto> mapResponse =
// supplierQueryDubboService.showOldAndNewEmerContById(id);
// return mapResponse;
// }
//
//
//
// /**
// * @Description:招商入驻联系人修改前和修改后内容
// * @author kangdong
// * @date 2017年6月15日 上午11:22:10
// * @param id
// * @return
// */
// @RequestMapping(value = "showOldAndNewSettledContInfo", method = {
// RequestMethod.GET })
// public Response<SupplierOldNewSettledContInfoDto>
// showOldAndNewSettledContInfo(@RequestParam Integer id) {
// Response<SupplierOldNewSettledContInfoDto> mapResponse =
// supplierQueryDubboService
// .showOldAndNewSettledContById(id);
// return mapResponse;
// }
//
//
//
// /**
// * @Description:审核供应商公司经营及税务信息修改内容
// * @author kangdong
// * @date 2017年5月27日 下午16:20:10
// * @param record
// * @return
// */
// @RequestMapping(value = "approveSupplierOperTax", method = {
// RequestMethod.POST })
// public Response<Boolean> approveOperTax(@RequestBody ApprovalSupplierVo
// record) {
// SupplierOperTaxInfoDto vo = BeanConvertUtils.convert(record,
// SupplierOperTaxInfoDto.class);
// int newId = record.getNewId();
// return supplierWriteDubboService.updateSupplierTaxApproval(vo, newId);
// }
//
//
//
// /**
// * @Description:审核供应商银行信息修改内容
// * @author kangdong
// * @date 2017年5月27日 下午16:20:10
// * @param record
// * @return
// */
// @RequestMapping(value = "approveSupplierBank", method = { RequestMethod.POST
// })
// public Response<Boolean> approveBank(@RequestBody ApprovalSupplierVo record)
// {
// SupplierBankInfoDto vo = BeanConvertUtils.convert(record,
// SupplierBankInfoDto.class);
// int newId = record.getNewId();
// return supplierWriteDubboService.updateBankApproval(vo, newId);
// }
//
//
//
// /**
// * @Description:审核公司营业执照（副本）修改内容
// * @author kangdong
// * @date 2017年5月27日 下午16:20:10
// * @param record
// * @return
// */
// @RequestMapping(value = "approveSupplierLicense", method = {
// RequestMethod.POST })
// public Response<Boolean> approveSupplierLicense(@RequestBody
// ApprovalSupplierVo record) {
// SupplierlicenseInfoDto vo = BeanConvertUtils.convert(record,
// SupplierlicenseInfoDto.class);
// int newId = record.getNewId();
// return supplierWriteDubboService.updateLicenseApproval(vo, newId);
// }
//
//
//
// /**
// * @Description:审核组织机构代码证修改内容
// * @author kangdong
// * @date 2017年5月27日 下午16:20:10
// * @param record
// * @return
// */
// @RequestMapping(value = "approveSupplierOrgCode", method = {
// RequestMethod.POST })
// public Response<Boolean> approveSupplierOrgCode(@RequestBody
// ApprovalSupplierVo record) {
// SupplierOrgCodeInfoDto vo = BeanConvertUtils.convert(record,
// SupplierOrgCodeInfoDto.class);
// int newId = record.getNewId();
// return supplierWriteDubboService.updateOrgCodeApproval(vo, newId);
// }
//
//
//
// /**
// * @Description:审核紧急联系人修改后的内容
// * @author kangdong
// * @date 2017年5月27日 下午16:20:10
// * @param record
// * @return
// */
// @RequestMapping(value = "approveSupplierEmerCont", method = {
// RequestMethod.POST })
// public Response<Boolean> approveSupplierEmerCont(@RequestBody
// ApprovalSupplierVo record) {
// SupplierEmerContInfoDto vo = BeanConvertUtils.convert(record,
// SupplierEmerContInfoDto.class);
// int newId = record.getNewId();
// return supplierWriteDubboService.updateEmerContApproval(vo, newId);
// }
//
//
//
// /**
// * @Description:审核招商入驻联系人修改后内容
// * @author kangdong
// * @date 2017年5月27日 下午16:20:10
// * @param record
// * @return
// */
// @RequestMapping(value = "approveSupplierSettledCont", method = {
// RequestMethod.POST })
// public Response<Boolean> approveSupplierSettledCont(@RequestBody
// ApprovalSupplierVo record) {
// SupplierSettledContInfoDto vo = BeanConvertUtils.convert(record,
// SupplierSettledContInfoDto.class);
// int newId = record.getNewId();
// return supplierWriteDubboService.updateSettledContApproval(vo, newId);
// }
//
//
//
// /**
// * @Description:修改供应商紧急联系人(新增一条,保留旧记录)
// * @author kangdong
// * @date 2017年6月19日 下午16:57:10
// * @param record
// * @return
// */
// @RequestMapping(value = "editSupplierEmerContApproval", method = {
// RequestMethod.POST })
// public Response<Boolean> editSupplierEmerContApproval(@RequestBody
// SupplierEmerContInfoVo record) {
// SupplierEmerContInfoDto vo = BeanConvertUtils.convert(record,
// SupplierEmerContInfoDto.class);
// return supplierWriteDubboService.editSupplierEmerContApproval(vo);
// }
//
//
//
// /**
// * @Description:修改招商入驻联系人(新增一条,保留旧记录)
// * @author kangdong
// * @date 2017年6月19日 下午16:57:10
// * @param record
// * @return
// */
//
// @RequestMapping(value = "editSupplierSettledContApproval", method = {
// RequestMethod.POST })
// public Response<Boolean> editSupplierSettledContApproval(@RequestBody
// SupplierSettledContInfoVo record) {
// SupplierSettledContInfoDto vo = BeanConvertUtils.convert(record,
// SupplierSettledContInfoDto.class);
// return supplierWriteDubboService.editSupplierSettledContApproval(vo);
// }
//
//
//
// /**
// * @Description: 修改公司营业执照信息(新增一条,保留旧记录)
// * @author tankejia
// * @date 2017/6/19- 16:55
// * @param record
// */
// @RequestMapping(value = "updateSupplierLicenseInfo", method =
// RequestMethod.POST)
// public Response<Boolean> updateSupplierLicenseInfo(@RequestBody
// SupplierlicenseInfoVo record) {
// SupplierlicenseInfoDto dto = BeanConvertUtils.convert(record,
// SupplierlicenseInfoDto.class);
// return supplierWriteDubboService.editSupplierLicenseInfo(dto);
// }
//
//
//
// /**
// * @Description: 修改公司组织机构代码证信息(新增一条,保留旧记录)
// * @author tankejia
// * @date 2017/6/19- 17:51
// * @param record
// */
// @RequestMapping(value = "updateSupplierOrgCodeInfo", method =
// RequestMethod.POST)
// public Response<Boolean> updateSupplierOrgCodeInfo(@RequestBody
// SupplierOrgCodeInfoVo record) {
// SupplierOrgCodeInfoDto dto = BeanConvertUtils.convert(record,
// SupplierOrgCodeInfoDto.class);
// return supplierWriteDubboService.editSupplierOrgCodeInfo(dto);
// }
//
//
//
// /**
// * @Description:修改公司经营及税务信息(新增一条,保留旧记录)
// * @author baiyun
// * @date 2017年6月20日 上午9:36:10
// * @param record
// * @return
// */
// @RequestMapping(value = "updateSupplierOperTaxInfo", method = {
// RequestMethod.POST })
// public Response<Boolean> updateOperTaxInfo(@RequestBody SupplierOperTaxInfoVo
// record) {
// SupplierOperTaxInfoDto dto = BeanConvertUtils.convert(record,
// SupplierOperTaxInfoDto.class);
// return supplierWriteDubboService.editSupplierOperTaxInfo(dto);
// }
//
//
//
// /**
// * @Description:修改银行信息(新增一条,保留旧记录)
// * @author baiyun
// * @date 2017年6月20日 上午10:11:12
// * @param record
// * @return
// */
// @RequestMapping(value = "updateSupplierBankInfo", method = {
// RequestMethod.POST })
// public Response<Boolean> updateBankInfo(@RequestBody SupplierBankInfoVo
// record) {
// SupplierBankInfoDto dto = BeanConvertUtils.convert(record,
// SupplierBankInfoDto.class);
// return supplierWriteDubboService.editBankInfo(dto);
// }
// }
