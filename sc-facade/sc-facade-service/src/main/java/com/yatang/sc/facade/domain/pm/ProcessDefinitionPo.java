package com.yatang.sc.facade.domain.pm;

import java.io.Serializable;
import java.util.Date;

public class ProcessDefinitionPo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 流程节点编码
     */
    private String processNodeCode;

    /**
     * 流程节点名称
     */
    private String processNodeName;

    /**
     * 下一个流程节点ID
     */
    private Long nextNodeId;

    /**
     * 子公司ID
     */
    private String branchCompanyId;

    /**
     * 流程类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护
     */
    private Long type;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date modifyTime;

    /**
     * 
     */
    private String createUserId;

    /**
     * 
     */
    private String modifyUserId;

    /**
     * 是否首节点:0,是,1:否
     */
    private Integer isFirstNode;

    /**
     * 当前节点审批记录
     */
    private ProcessAuditLogPo processAuditLog;

    /**
     * 业务单id
     */
    private Long                businessId;

    /**
     * process_definition
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessNodeCode() {
        return processNodeCode;
    }

    public void setProcessNodeCode(String processNodeCode) {
        this.processNodeCode = processNodeCode == null ? null : processNodeCode.trim();
    }

    public String getProcessNodeName() {
        return processNodeName;
    }

    public void setProcessNodeName(String processNodeName) {
        this.processNodeName = processNodeName == null ? null : processNodeName.trim();
    }

    public Long getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(Long nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId == null ? null : branchCompanyId.trim();
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId == null ? null : modifyUserId.trim();
    }

    public Integer getIsFirstNode() {
        return isFirstNode;
    }

    public void setIsFirstNode(Integer isFirstNode) {
        this.isFirstNode = isFirstNode;
    }



    public ProcessAuditLogPo getProcessAuditLog() {
        return processAuditLog;
    }



    public void setProcessAuditLog(ProcessAuditLogPo processAuditLog) {
        this.processAuditLog = processAuditLog;
    }



    public Long getBusinessId() {
        return businessId;
    }



    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}