package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * <class description>仓库信息DTO。
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月17日
 */
public class WarehouseLogicDto implements Serializable {

	private static final long	serialVersionUID	= 1L;
	// 仓库ID
	private Integer				id;
	// 物理仓库ID
	private Integer				physicalWarehouseId;
	// 仓库编码
	private String				warehouseCode;
	// 仓库名称
	private String				warehouseName;

	private String				branchCompanyId;

	private List<String> 		branchCompanyIds;

	private String				branchCompanyCode;

	private String				branchCompanyName;

	private Date				createTime;

	private Date				modifyTime;

	private String				createUser;

	private String				modifyUser;

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
}
