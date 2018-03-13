package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(SupplierInfoPo record);

    int insertSelective(SupplierInfoPo record);

    SupplierInfoPo queryById(String id);

    int updateByPrimaryKeySelective(SupplierInfoPo record);

    int updateByPrimaryKey(SupplierInfoPo record);
	/**
	 * 根据条件查询供应商修改及管理列表
	 * @param record
	 * @return 返回集合
	 */
	public List<SupplierInfoPo> listByParamSortSettledTime(MultiQuerySupplierPo record);

	/**
	 * 根据条件查询供应商入驻申请列表
	 * @param supplierEnterQueryParamPo
	 * @return 返回集合
	 */
	public List<SupplierInfoPo> listByParamSortSettledRequestTime(SupplierEnterQueryParamPo supplierEnterQueryParamPo);

	/**
	 * 根据条件查询供应商、供应商地点列表
	 * @param param
	 * @return
	 */
  List<SupplierInfoQueryListPo> listByParam(SupplierInfoQueryParamPo param);

	List<SupplierBasicPo> queryAllSupplier();

	/**
	 * 根据传入的供应商ID查询审核失败原因
	 * @param id
	 * @return
	 */
	AuditFailedReasonPo findAuditFailedReason(String id);

	/**
	 * @param id 基本信息的id
	 * @param status 审核状态(0)用于重置
	 * @author yangshuang
	 * @return
	 */
	int updateStatusById(@Param(value = "basicId") Integer id, @Param(value = "status")Integer status);

	/**
	 * @Description: 查询供应商主表信息
	 * @author tankejia
	 * @date 2017/7/28- 17:44
	 * @param id
	 */
	SupplierInfoPo queryMainById(String id);

	/**
	 * @Description: 查询供应商最后一条信息的id
	 * @author tankejia
	 * @date 2017/7/17- 9:00
	 */
	String queryNewestSupplierRecordId();

	/**
	 * @Description: 查询供应商管理列表的导出结果
	 * @author liuxiaokun
	 * @date 2017/12/8
	 */
    List<SupplierInfoQueryListPo> queryExportManageList(SupplierInfoQueryParamPo po);

	/**
	 * @Description: 通过供应商编码查询供应商
	 * @author kangdong
	 * @date 2017/12/7- 14:00
	 */
	SupplierInfoPo queryBySpNo(String spNo);
}