package com.yatang.sc.operation.vo.ag;

import java.io.Serializable;
import java.util.Date;

public class StoreAreaVo implements Serializable{

	private static final long serialVersionUID = 1005533925678694670L;

	/**
	 * 区域组编码
	 */
	private String id;

	/**
	 * 区域组名称
	 */
	private String areaGroupName;

	/**
	 * 子公司Id
	 */
	private String branchCompanyId;

	/**
	 * 子公司名称
	 */
	private String branchCompanyName;

	/**
	 * 创建者
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 修改者
	 */
	private String modifyBy;

	/**
	 * 修改时间
	 */
	private String modifyDate;

	/**
	 * 删除标记(0:未删除；3：删除)
	 **/
	private Integer deleteFlag;

	/**
	 * 是否分组(true:已分组；false:未分组)
	 */
	private boolean exists;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaGroupName() {
		return areaGroupName;
	}

	public void setAreaGroupName(String areaGroupName) {
		this.areaGroupName = areaGroupName;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public String getBranchCompanyName() {
		return branchCompanyName;
	}

	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}
}
