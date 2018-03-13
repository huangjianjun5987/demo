package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.supplier.*;

/**
 * @描述: 供应商dubbo服务接口.
 * @作者: huangjianjun
 * @创建时间: 2017年5月15日-下午11:12:35 .
 * @版本: 1.0 .
 * @param <T>
 */
public interface SupplierWriteDubboService {

	/**
	 * @Description: 新增供应商主表信息
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 */
	Response<Boolean> insertSupplier(SupplierInfoDto record);



	/**
	 * @Description: 更新供应商主表信息
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 */
	Response<Boolean> updateSupplier(SupplierInfoDto record);



	/**
	 * 
	 * <method description>供应商入驻审核
	 *
	 * @param supplierAuditDto
	 * @return
	 */
	Response<Boolean> auditSupplier(SupplierAuditDto supplierAuditDto);



	/**
	 * 
	 * <method description>供应商地点入驻审核
	 *
	 * @param supplierAuditDto
	 * @return
	 */
	Response<Boolean> auditSupplierAdr(SupplierAdrAuditDto supplierAdrAuditDto);



	/**
	 * @Description: 新增供应商基本信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 * @param SupplierBasicInfoPo
	 */
	Response<Boolean> insertBasicInfo(SupplierBasicInfoDto record);



	/**
	 * @Description: 新增供应商经营及税务信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Response<Boolean> insertOperTaxInfo(SupplierOperTaxInfoDto record);



	/**
	 * @Description: 删除供应商经营及税务信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 * @param
	 */
	Response<Boolean> deleteOperTaxInfo(Integer id);



	/**
	 * @Description: 新增供应商银行信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Response<Boolean> insertBankInfo(SupplierBankInfoDto record);



	/**
	 * @Description: 删除供应商银行信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Response<Boolean> deleteBankInfo(Integer id);



	/**
	 * @Description: 新增供应商营业执照信息(副本)
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Response<Boolean> insertlicenseInfo(SupplierlicenseInfoDto record);



	/**
	 * @Description: 删除供应商营业执照信息(副本)
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Response<Boolean> deletelicenseInfo(Integer id);



	/**
	 * @Description: 新增供应商
	 * @author tankejia
	 * @date 2017/7/19- 14:20
	 * @param supplierInfoDto
	 */
	Response<String> insertSupplierInfo(SupplierInfoDto supplierInfoDto, String username);



	/**
	 * @Description: 修改供应商
	 * @author tankejia
	 * @date 2017/7/19- 14:20
	 * @param supplierInfoDto
	 */
	Response<String> updateSupplierInfo(SupplierInfoDto supplierInfoDto, String username);



	/**
	 * @Description: 新增供应商地点
	 * @author tankejia
	 * @date 2017/7/19- 15:29
	 * @param supplierAdrInfoDto
	 */
	Response<Integer> insertSupplierAddInfo(SupplierAdrInfoDto supplierAdrInfoDto, String username);



	/**
	 * @Description: 修改供应商地点
	 * @author tankejia
	 * @date 2017/7/19- 15:29
	 * @param supplierAdrInfoDto
	 */
	Response<Boolean> updateSupplierAddInfo(SupplierAdrInfoDto supplierAdrInfoDto, String username);

	/**
	 * 供应商最终审核信息录入
	 * 
	 * @param supplierAuditInfoDto
	 * @return
	 */
	Response<Boolean> insertSupplierAuditInfo(SupplierAuditInfoDto supplierAuditInfoDto);



	/**
	 * @Description: 供应商公司经营税务资料修改审核
	 * @author kangdong
	 * @date 2017年5月27日 下午11:08:25
	 */
	Response<Boolean> updateSupplierTaxApproval(SupplierOperTaxInfoDto supplierOperTaxInfoDto, Integer newId);



	/**
	 * @Description: 供应商银行信息修改审核
	 * @author kangdong
	 * @date 2017年5月31日 下午17:49:25
	 */
	Response<Boolean> updateBankApproval(SupplierBankInfoDto bankInfoDto, Integer newId);



	/**
	 * @Description:审核公司营业执照（副本）修改内容
	 * @author kangdong
	 * @date 2017年6月15日 下午11:20:10
	 * @param licenseInfoDto
	 * @return
	 */
	Response<Boolean> updateLicenseApproval(SupplierlicenseInfoDto licenseInfoDto, Integer newId);



	/**
	 * @Description: 新增供应商销售区域
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Response<Void> insertSaleRegionInfo(SupplierSaleRegionDto record);



	/**
	 * @Description:审核供应商修改的信息
	 * @author kangdong
	 * @param supplierAuditsDto
	 * @return 2017年7月21日 下午10:10:06
	 */
	Response<Boolean> auditSupplierEditInfo(SupplierAuditsDto supplierAuditsDto);
}
