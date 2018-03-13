package com.yatang.sc.facade.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.AuditFailedReasonPo;
import com.yatang.sc.facade.domain.MultiQuerySupplierPo;
import com.yatang.sc.facade.domain.SpAdrBasicPo;
import com.yatang.sc.facade.domain.SpSearchBoxPo;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.domain.SupplierAuditInfoPo;
import com.yatang.sc.facade.domain.SupplierAuditsPo;
import com.yatang.sc.facade.domain.SupplierBankInfoPo;
import com.yatang.sc.facade.domain.SupplierBasicInfoPo;
import com.yatang.sc.facade.domain.SupplierEnterQueryParamPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.domain.SupplierInfoQueryListPo;
import com.yatang.sc.facade.domain.SupplierInfoQueryParamPo;
import com.yatang.sc.facade.domain.SupplierOperTaxInfoPo;
import com.yatang.sc.facade.domain.SupplierSaleRegionPo;
import com.yatang.sc.facade.domain.SupplierlicenseInfoPo;

/**
 * @描述: 供应商服务接口.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-下午10:51:43 .
 * @版本: 1.0 .
 * @param <T>
 */
public interface SupplierService {

	/**
	 * @Description: 查询供应商详情
	 * @author huangjianjun
	 * @date 2017年5月13日 下午11:02:59
	 * @param id
	 *            供应商主表ID
	 */
	SupplierInfoPo queryById(String id);



	/**
	 * @Description: 查询供应商主表信息
	 * @author tankejia
	 * @date 2017/7/28- 17:50
	 * @param id
	 */
	SupplierInfoPo queryMainById(String id);



	/**
	 * @Description: 查询供应商入驻申请分页信息列表
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 */
	PageInfo<SupplierInfoPo> querySettledRequestListPage(SupplierEnterQueryParamPo supplierEnterQueryParamPo);



	/**
	 * @Description: 查询供应商入驻分页信息列表
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 */
	PageInfo<SupplierInfoPo> querySettledListPage(MultiQuerySupplierPo record);



	/**
	 * @Description: 根据条件查询供应商、供应商地点列表
	 * @author yipeng
	 * @date 2017年07月18日15:41:21
	 */
	PageInfo<SupplierInfoQueryListPo> querySettledList(SupplierInfoQueryParamPo param);



	/**
	 * @Description: 更新公司经营及税务信息审核状态
	 * @author kangdong
	 * @date 2017年5月27日 下午11:42:00
	 */
	Boolean updateSupplierTaxApproval(SupplierOperTaxInfoPo record, Integer newId);



	/**
	 * @Description: 更新银行修改信息审核状态
	 * @author kangdong
	 * @date 2017年5月27日 下午11:42:00
	 */
	Boolean updateBankApproval(SupplierBankInfoPo bankInfoPo, Integer newId);



	/**
	 * @Description: 修改公司营业执照（副本）
	 * @author kangdong
	 * @date 2017年6月15日 下午11:25:00
	 */
	Boolean updateLicenseInfoApproval(SupplierlicenseInfoPo licenseInfoPo, Integer newId);



	/**
	 * @Description: 新增供应商主表信息
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 */
	Boolean insertSupplier(SupplierInfoPo record);



	/**
	 * @Description: 更新供应商主表信息
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 */
	Boolean updateSupplier(SupplierInfoPo record);



	/**
	 * @Description: 新增供应商基本信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 * @param SupplierBasicInfoPo
	 */
	Boolean insertBasicInfo(SupplierBasicInfoPo po);



	/**
	 * @Description: 新增供应商经营及税务信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Boolean insertOperTaxInfo(SupplierOperTaxInfoPo po);



	/**
	 * @Description: 删除供应商经营及税务信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 * @param
	 */
	Boolean deleteOperTaxInfo(Integer id);



	/**
	 * @Description: 新增供应商银行信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Boolean insertBankInfo(SupplierBankInfoPo po);



	/**
	 * @Description: 删除供应商银行信息
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Boolean deleteBankInfo(Integer id);



	/**
	 * @Description: 新增供应商营业执照信息(副本)
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Boolean insertlicenseInfo(SupplierlicenseInfoPo po);



	/**
	 * @Description: 删除供应商营业执照信息(副本)
	 * @author huangjianjun
	 * @date 2017年5月13日 下午10:57:06
	 */
	Boolean deletelicenseInfo(Integer id);



	/**
	 * @Description: 新增供应商(返回供应商主表ID)
	 * @author tankejia
	 * @date 2017/7/19- 14:20
	 * @param supplierInfoPo
	 */
	String insertSupplierInfo(SupplierInfoPo supplierInfoPo);



	/**
	 * 供应商最终审核信息录入
	 * 
	 * @param supplierAuditInfoPo
	 * @return
	 */
	Boolean insertSupplierAuditInfo(SupplierAuditInfoPo supplierAuditInfoPo);



	/**
	 * 通过营业执照编号判定是否重复
	 * 
	 * @param po
	 * @author yangshuang
	 */
	List<SupplierlicenseInfoPo> checkSupplierLicenseInfoByRegistLicenceNo(SupplierlicenseInfoPo po);



	/**
	 * 判定供应商的基本信息是否有重复
	 * 
	 * @param basicPo
	 * @author yangshuang
	 * @return
	 */
	List<SupplierBasicInfoPo> checkSupplierBasicInfo(SupplierBasicInfoPo basicPo);



	/**
	 * 通过银行账户判定是否重复
	 *
	 * @param po
	 * @author yangshuang
	 */
	List<SupplierBankInfoPo> checkSupplierBankInfoByBankAccount(SupplierBankInfoPo po);



	/**
	 * 根据传入的供应商ID查询失败原因
	 * 
	 * @param id
	 * @return
	 */
	AuditFailedReasonPo findAuditFailedReason(String id);



	/**
	 * @Description: 修改供应商
	 * @author tankejia
	 * @date 2017/7/19- 14:20
	 * @param supplierInfoPo
	 */
	boolean updateSupplierInfo(SupplierInfoPo supplierInfoPo);



	/**
	 * @Description: 新增销售区域
	 * @author huangjianjun
	 * @date 2017/7/17- 9:00
	 */
	void insertSaleRegion(SupplierSaleRegionPo po);



	/**
	 * @Description:存入供应商修改前修改后的信息
	 * @author kangdong
	 * @date 2017/7/18- 10:30
	 */
	ArrayList queryBeforeAndAfter(String spId);



	/**
	 * @Description: 查询供应商地点信息
	 * @author kangdong
	 * @date 2017年7月21日 下午12:10:25
	 * @param id
	 */
	SupplierAdrInfoPo queryProviderPlace(Integer id);



	/**
	 * @Description: 根据供应商编码或名称查询供应商分页列表组件
	 * @author huangjianjun
	 * @date 2017年7月20日上午11:20:18
	 */
	PageInfo<SpSearchBoxPo> supplierSearchBox(String condition, Integer pageNum, Integer pageSize);



	/**
	 * @Description:审核供应商修改的信息 author kangdong
	 * @param supplierAuditsPo
	 * @return 2017年8月1日 下午10:10:06
	 */
	Boolean auditSupplierEditInfo(SupplierAuditsPo supplierAuditsPo);



	/**
	 * @Description: 根据地点ID查询供应商地点基本信息
	 * @author kangdong
	 * @date 2017年8月1日 晚上20:10:25
	 * @param id
	 */
	SpAdrBasicPo queryByAdrInfoId(Integer id);



	boolean auditSupplier(SupplierInfoPo supplierInfoPo);

	/**
	 * @Description: 查询供应商管理列表的导出结果
	 * @author liuxiaokun
	 * @date 2017/12/8
	 * @param po
	 */
    PageInfo<SupplierInfoQueryListPo> queryExportManageList(SupplierInfoQueryParamPo po);


	/**
	 * @Description: 根据supplierNo查询供应商
	 * @author kangdong
	 * @date 2017年12月7日 下午16:10:25
	 * @param supplierNo
	 */
    SupplierInfoPo queryBySpNo(String supplierNo);
}
