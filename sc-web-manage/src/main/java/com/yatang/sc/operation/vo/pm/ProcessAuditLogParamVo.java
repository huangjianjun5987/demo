package com.yatang.sc.operation.vo.pm;

import com.yatang.sc.validgroup.GroupOne;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/11/4 17:42
 * @version: v1.0
 */
public class ProcessAuditLogParamVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 业务单id
	 */
	@NotNull(message = "{msg.notEmpty.message}", groups = GroupOne.class)
	private Long                businessId;

	/**
	 * 流程类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护
	 */
	@NotNull(message = "{msg.notEmpty.message}", groups = GroupOne.class)
	private Long 				type;


	/**
	 * 流程节点id
	 */
	@NotBlank(message = "{msg.notEmpty.message}", groups = GroupOne.class)
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
	@NotNull(message = "{msg.notEmpty.message}", groups = GroupOne.class)
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
