package com.yatang.sc.inventory.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ImAdjustmentPo implements Serializable {
    private static final long serialVersionUID = -1415717101464907269L;
    private Long id;//pk

    private String adjustmentNo;//单据编号

    private Integer type;//类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）

    private Integer status;//状态:0:制单;1:已生效

    private Date adjustmentTime;//调整日期

    private String warehouseCode;//调整仓库,逻辑仓id

    private Long totalQuantity;//调整数量合计

    private BigDecimal totalAdjustmentCost;//调整成本合计

    private String description;//备注

    private String externalBillNo;//外部单据号

    private String failedReason;//失败原因

    private String auditUserId;//批准人ID

    private String auditUserName;//批准人名称

    private Date auditTime;//批准日期

    private Date createTime;

    private Date modifyTime;

    private String createUserId;

    private String createUserName;//创建人名称

    private String modifyUserId;

    private String modifyUserName;//修改人名称

    private String branchCompanyId;//子公司id

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setAuditUserName(String auditUserName) {

        this.auditUserName = auditUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdjustmentNo() {
        return adjustmentNo;
    }

    public void setAdjustmentNo(String adjustmentNo) {
        this.adjustmentNo = adjustmentNo == null ? null : adjustmentNo.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAdjustmentTime() {
        return adjustmentTime;
    }

    public void setAdjustmentTime(Date adjustmentTime) {
        this.adjustmentTime = adjustmentTime;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAdjustmentCost() {
        return totalAdjustmentCost;
    }

    public void setTotalAdjustmentCost(BigDecimal totalAdjustmentCost) {
        this.totalAdjustmentCost = totalAdjustmentCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getExternalBillNo() {
        return externalBillNo;
    }

    public void setExternalBillNo(String externalBillNo) {
        this.externalBillNo = externalBillNo == null ? null : externalBillNo.trim();
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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
}