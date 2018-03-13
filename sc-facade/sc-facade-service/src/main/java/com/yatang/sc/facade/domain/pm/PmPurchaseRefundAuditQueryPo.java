package com.yatang.sc.facade.domain.pm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * <class description>退货单审批查询PO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年10月23日
 */
public class PmPurchaseRefundAuditQueryPo implements Serializable {
	private static final long	serialVersionUID	= 1L;
	/**
	 * 退货单号
	 */
	private String				purchaseRefundNo;
	/**
	 * 流程状态:0:进行中;1:已结束
	 */
	private Integer				auditStatus;

	/**
	 * 供应商ID
	 */
	private String				spId;

	/**
	 * 供应商地点ID
	 */
	private String				spAdrId;
	/**
	 * 退货地点类型:0:仓库;1:门店
	 */
	private Integer				adrType;

	/**
	 * 退货地点编码
	 */
	private String				refundAdrCode;

	/**
	 * 退货地点名称
	 */
	private String				refundAdrName;
	/**
	 * 流程开始时间起=创建日期
	 */
	private Date				createTimeStart;
	/**
	 * 流程开始时间止=创建日期
	 */
	private Date				createTimeEnd;
	/**
	 * 流程结束时间=审核日期
	 */
	private Date				stopTimeStart;
	/**
	 * 流程结束时间=审核日期
	 */
	private Date				stopTimeEnd;
	/**
	 * 子公司ID
	 */
	private String				branchCompanyId;
	/**
	 * 角色-对应流程节点编码
	 */
	private List<String>		roles;

	/**
	 * 当前页
	 */
	private Integer				pageNum;
	/**
	 * 每页显示记录数
	 */
	private Integer				pageSize;



	public String getPurchaseRefundNo() {
		return purchaseRefundNo;
	}



	public void setPurchaseRefundNo(String purchaseRefundNo) {
		this.purchaseRefundNo = purchaseRefundNo;
	}



	public Integer getAuditStatus() {
		return auditStatus;
	}



	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}



	public String getSpId() {
		return spId;
	}



	public void setSpId(String spId) {
		this.spId = spId;
	}



	public String getSpAdrId() {
		return spAdrId;
	}



	public void setSpAdrId(String spAdrId) {
		this.spAdrId = spAdrId;
	}



	public Integer getAdrType() {
		return adrType;
	}



	public void setAdrType(Integer adrType) {
		this.adrType = adrType;
	}



	public String getRefundAdrCode() {
		return refundAdrCode;
	}



	public void setRefundAdrCode(String refundAdrCode) {
		this.refundAdrCode = refundAdrCode;
	}



	public String getRefundAdrName() {
		return refundAdrName;
	}



	public void setRefundAdrName(String refundAdrName) {
		this.refundAdrName = refundAdrName;
	}



	public Date getCreateTimeStart() {
		return createTimeStart;
	}



	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}



	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}



	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}



	public Date getStopTimeStart() {
		return stopTimeStart;
	}



	public void setStopTimeStart(Date stopTimeStart) {
		this.stopTimeStart = stopTimeStart;
	}



	public Date getStopTimeEnd() {
		return stopTimeEnd;
	}



	public void setStopTimeEnd(Date stopTimeEnd) {
		this.stopTimeEnd = stopTimeEnd;
	}



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}



	public List<String> getRoles() {
		return roles;
	}



	public void setRoles(List<String> roles) {
		this.roles = roles;
	}



	public Integer getPageNum() {
		return pageNum;
	}



	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}



	public Integer getPageSize() {
		return pageSize;
	}



	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
