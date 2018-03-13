package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class WarehouseLogicInfoPo implements Serializable {
	private static final long		serialVersionUID	= 1L;
	private Integer					id;

	private Integer					physicalWarehouseId;

	private String					warehouseCode;

	private String					warehouseName;

	private String					branchCompanyId;

	private List<String> 			branchCompanyIds;

	private String					branchCompanyCode;

	private String					branchCompanyName;
	/**
	 * 物流公司编码
	 */
	private String					tmsCode;
	/**
	 * 物流公司名称
	 */
	private String					tmsName;
	private Date					createTime;

	private Date					modifyTime;

	private String					createUser;

	private String					modifyUser;

	private WarehousePhysicalInfoPo	warehousePhysicalInfo;

	// 需求新增的查询参数，需要根据供应商地点过滤仓库
	private Integer					supplierAddressId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPhysicalWarehouseId() {
		return physicalWarehouseId;
	}

	public void setPhysicalWarehouseId(Integer physicalWarehouseId) {
		this.physicalWarehouseId = physicalWarehouseId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public List<String> getBranchCompanyIds() {
		return branchCompanyIds;
	}

	public void setBranchCompanyIds(List<String> branchCompanyIds) {
		this.branchCompanyIds = branchCompanyIds;
	}

	public String getBranchCompanyCode() {
		return branchCompanyCode;
	}

	public void setBranchCompanyCode(String branchCompanyCode) {
		this.branchCompanyCode = branchCompanyCode;
	}

	public String getBranchCompanyName() {
		return branchCompanyName;
	}

	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}

	public String getTmsCode() {
		return tmsCode;
	}

	public void setTmsCode(String tmsCode) {
		this.tmsCode = tmsCode;
	}

	public String getTmsName() {
		return tmsName;
	}

	public void setTmsName(String tmsName) {
		this.tmsName = tmsName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public WarehousePhysicalInfoPo getWarehousePhysicalInfo() {
		return warehousePhysicalInfo;
	}

	public void setWarehousePhysicalInfo(WarehousePhysicalInfoPo warehousePhysicalInfo) {
		this.warehousePhysicalInfo = warehousePhysicalInfo;
	}

	public Integer getSupplierAddressId() {
		return supplierAddressId;
	}

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}
}