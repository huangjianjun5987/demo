package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.yatang.sc.operation.common.BaseVo;


/**
 * 
 * <class description>逻辑仓库查询VO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月28日
 */
public class WarehouseLogicQueryVo extends BaseVo implements Serializable {

	private static final long	serialVersionUID	= 1L;
	// 查询条件
	private String				param;
	// 供应商地点ID
	private Integer				supplierAddressId;

	private String 				branchCompanyId;//分公司id

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Integer getSupplierAddressId() {
		return supplierAddressId;
	}

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
}
