package com.yatang.sc.facade.domain.pm;

import java.io.Serializable;

/**
 * @description: 审批日志参数po（用于审批操作）
 * @author: yinyuxin
 * @date: 2017/11/4 14:35
 * @version: v1.0
 */
public class ProcessAuditLogParamPo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 业务单id
	 */
	private Long                businessId;



	/**
	 * 流程类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护
	 */
	private Long 				type;


	/**
	 * 流程节点id
	 */
	private String processId;


	/**
	 * 子公司ID
	 */
	private String branchCompanyId;

	/**
	 * 审核人ID
	 */
	private String auditUserId;

	/**
	 * 审核人
	 */
	private String auditUser;

	/**
	 * 审批结果:0:拒绝;1:通过
	 */
	private Long auditResult;

	/**
	 * 审批意见
	 */
	private String auditOpinion;



	public Long getBusinessId() {
		return businessId;
	}



	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}



	public Long getType() {
		return type;
	}



	public void setType(Long type) {
		this.type = type;
	}



	public String getProcessId() {
		return processId;
	}



	public void setProcessId(String processId) {
		this.processId = processId;
	}



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}



	public String getAuditUserId() {
		return auditUserId;
	}



	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}



	public String getAuditUser() {
		return auditUser;
	}



	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}



	public Long getAuditResult() {
		return auditResult;
	}



	public void setAuditResult(Long auditResult) {
		this.auditResult = auditResult;
	}



	public String getAuditOpinion() {
		return auditOpinion;
	}



	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
}
