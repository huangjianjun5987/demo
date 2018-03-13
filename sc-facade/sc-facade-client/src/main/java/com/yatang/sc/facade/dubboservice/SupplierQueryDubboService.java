package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.AuditFailedReasonDto;
import com.yatang.sc.facade.dto.MultiQuerySupplierDto;
import com.yatang.sc.facade.dto.ProvinceDto;
import com.yatang.sc.facade.dto.SupplierPlaceDto;
import com.yatang.sc.facade.dto.supplier.QueryAvailableBranchCompanyDto;
import com.yatang.sc.facade.dto.supplier.SpAdrBasicDto;
import com.yatang.sc.facade.dto.supplier.SpSearchBoxDto;
import com.yatang.sc.facade.dto.supplier.SupplierAdrInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierBankInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierBasicInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierEnterQueryParamDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoQueryListDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoQueryParamDto;
import com.yatang.sc.facade.dto.supplier.SupplierSaleRegionDto;
import com.yatang.sc.facade.dto.supplier.SupplierShowListDto;
import com.yatang.sc.facade.dto.supplier.SupplierlicenseInfoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @描述: 供应商dubbo服务接口.
 * @作者: huangjianjun
 * @创建时间: 2017年5月15日-下午11:12:35 .
 * @版本: 1.0 .
 * @param <T>
 */
public interface SupplierQueryDubboService {

	/**
	 * @Description: 查询供应商详情
	 * @author huangjianjun
	 * @date 2017年5月13日 下午11:02:59
	 * @param id
	 *            供应商主表ID
	 */
	Response<SupplierInfoDto> queryById(String id);



	/**
	 * @Description: 查询供应商主表信息
	 * @author tankejia
	 * @date 2017/7/28- 17:47
	 * @param id
	 */
	Response<SupplierInfoDto> queryMainById(String id);



	/**
	 * @Description: 查询供应商入驻申请分页信息列表
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 * @param supplierEnterQueryParamDto
	 */
	Response<PageResult<SupplierShowListDto>> querySettledRequestListPage(
			SupplierEnterQueryParamDto supplierEnterQueryParamDto);



	/**
	 * @Description: 查询供应商入驻分页信息列表
	 * @author huangjianjun
	 * @date 2017年5月15日 下午1:42:22
	 * @param record
	 */
	Response<PageResult<SupplierShowListDto>> querySettledListPage(MultiQuerySupplierDto record);



	/**
	 * @Description: 查询供应商修改资料申请分页信息列表
	 * @author kangdong
	 * @date 2017年5月15日 下午1:42:22
	 * @param record
	 */
	Response<PageResult<SupplierShowListDto>> querySettledEditRequestListPage(MultiQuerySupplierDto record);



	/**
	 * @Description: 根据条件查询供应商、供应商地点入驻列表
	 * @author yipeng
	 * @date 2017年07月18日15:41:21
	 */
	Response<PageResult<SupplierInfoQueryListDto>> querySettledList(SupplierInfoQueryParamDto param);



	/**
	 * @Description: 根据条件查询供应商、供应商地点管理列表
	 * @author yipeng
	 * @date 2017年07月18日15:41:21
	 */
	Response<PageResult<SupplierInfoQueryListDto>> queryManageList(SupplierInfoQueryParamDto param);



	/**
	 * @Description: 查询供应商销售区域列表
	 * @author tankejia
	 * @date 2017年05月26日14:50:13
	 * @param paramMap
	 */
	Response<PageInfo<Map<String, Object>>> supplierSaleRegionList(Map<String, Object> paramMap);



	/**
	 * @Description: 查询供应商销售区域
	 * @author tankejia
	 * @date 2017年05月26日14:50:13
	 * @param spNo
	 */
	Response<List<SupplierSaleRegionDto>> supplierSaleRegions(String spNo);



	/**
	 * 判定供应商的基本信息是否有重复
	 * 
	 * @param supplierBasicInfoDto
	 * @author yangshuang
	 * @return
	 */
	Response<Void> checkSupplierBasicInfo(SupplierBasicInfoDto supplierBasicInfoDto);



	/**
	 * 判定银行账户是否重复
	 * 
	 * @param dto
	 * @author yangshuang
	 */
	Response<Void> checkSupplierBankInfoByBankAccount(SupplierBankInfoDto dto);



	/**
	 * 判定营业执照编号是否重复
	 * 
	 * @param dto
	 * @author yangshuang
	 */
	Response<Void> checkSupplierLicenseInfoByRegistLicenceNo(SupplierlicenseInfoDto dto);



	/**
	 * 根据传入供应商ID查询审核失败原因
	 * 
	 * @param id
	 * @return
	 */
	Response<AuditFailedReasonDto> findAuditFailedReason(String id);



	/**
	 * 根据类型获取供应商编码和地点编码
	 * 
	 * @param type
	 * @return
	 */
	Response<String> getSupplierNo(String type);



	/**
	 * 根据供应商编码或名称查询供应商分页列表组件
	 * 
	 * @param type
	 * @return
	 */
	Response<PageResult<SpSearchBoxDto>> supplierSearchBox(String condition, Integer pageNum, Integer pageSize);





	/**
	 * 根据供应商地点编码或名称或供应商ID查询供应商地点分页列表组件
	 *
	 * @param type
	 * @return
	 */
	Response<PageResult<SpSearchBoxDto>> supplierAdrSearchBox(String pId, List<String> branchCompanyIds, String condition,
															  Integer pageNum, Integer pageSize);



	/**
	 * @Description: 查询供应商地点信息
	 * @author kangdong
	 * @param adrInfoId
	 * @return
	 */
	Response<SupplierPlaceDto> queryProviderPlaceInfo(Integer adrInfoId);



	/**
	 * @Description: 查询供应商地点主表信息
	 * @author tankejia
	 * @date 2017/7/28- 18:04
	 * @param id
	 */
	Response<SupplierAdrInfoDto> queryMainAddById(Integer id);



	/**
	 * @Description:查询供应商修改前修改后的信息
	 * @author kangdong
	 * @date 2017/7/19- 10:30
	 */
	Response<ArrayList> queryEditBeforeAfter(String spId);



	/**
	 * @Description:查询供应商地点所属区域列表
	 * @author kangdong
	 * @date 2017/7/25- 15:30
	 */
	Response<List<ProvinceDto>> querySupplierPlaceRegion(String spId);



	/**
	 * @Description: 根据地点ID查询供应商地点基本信息
	 * @author kangdong
	 * @date 2017年8月1日 晚上20:30:25
	 * @param id
	 */
	Response<SpAdrBasicDto> queryByAdrInfoId(Integer id);



	/**
	 * @Description: 查询不可用的分公司id集合
	 * @author tankejia
	 * @date 2017/8/8- 18:43
	 * @param dto
	 */
	Response<List<String>> queryUselessOrgId(QueryAvailableBranchCompanyDto dto);



	/**
	 * @Description: 校验供应商地点编号
	 * @author tankejia
	 * @date 2017/8/10- 18:09
	 * @param dto
	 */
	Response<Void> checkSupplierAddNo(SpAdrBasicDto dto);

	/**
	 * @Description: 查询供应商管理列表的导出结果
	 * @author liuxiaokun
	 * @date 2017/12/8
	 * @param supplierInfoQueryParamDto
	 */
    Response<PageResult<SupplierInfoQueryListDto>> queryExportManageList(SupplierInfoQueryParamDto supplierInfoQueryParamDto);
}
