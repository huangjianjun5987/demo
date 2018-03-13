package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <class description>流程审批日志DTO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年10月23日
 */
public class ProcessAuditLogDto implements Serializable {
	/**
	 * 
	 */
	private Long				id;

	/**
	 * 审批流程主键id
	 */
	private Long               processId;

	/**
	 * 业务单号ID
	 */
	private Long				businessId;

	/**
	 * 业务类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护
	 */
	private Integer businessType;

	/**
	 * 审核人ID
	 */
	private String				auditUserId;

	/**
	 * 审核人
	 */
	private String				auditUser;

	/**
	 * 审核日期
	 */
	private Date				auditTime;

	/**
	 * 审批结果:0:拒绝;1:通过
	 */
	private Long				auditResult;

	/**
	 * 审批意见
	 */
	private String				auditOpinion;


	/**
	 * process_audit_log
	 */
	private static final long	serialVersionUID	= 1L;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getBusinessId() {
		return businessId;
	}



	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getAuditUserId() {
		return auditUserId;
	}



	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId == null ? null : auditUserId.trim();
	}



	public String getAuditUser() {
		return auditUser;
	}



	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser == null ? null : auditUser.trim();
	}



	public Date getAuditTime() {
		return auditTime;
	}



	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
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
		this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
	}



	public Long getProcessId() {
		return processId;
	}



	public void setProcessId(Long processId) {
		this.processId = processId;
	}



}