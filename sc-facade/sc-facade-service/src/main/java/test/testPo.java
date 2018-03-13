package test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class testPo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 退货单号
     */
    private String purchaseRefundNo;

    /**
     * 供应商ID
     */
    private String spId;

    /**
     * 供应商编码
     */
    private String spNo;

    /**
     * 供应商名称
     */
    private String spName;

    /**
     * 供应商地点ID
     */
    private String spAdrId;

    /**
     * 供应商地点编码
     */
    private String spAdrNo;

    /**
     * 供应商地点名称
     */
    private String spAdrName;

    /**
     * 退货地点
     */
    private String refundAdr;

    /**
     * 合计退货数量
     */
    private Integer totalRefundAmount;

    /**
     * 合计退货金额(含税)
     */
    private BigDecimal totalRefundMoney;

    /**
     * 合计退货成本额
     */
    private BigDecimal totalRefundCost;

    /**
     * 合计实际退货数量
     */
    private Integer totalRealRefundAmount;

    /**
     * 合计实际退货金额(含税)
     */
    private BigDecimal totalRealRefundMoney;

    /**
     * 退货地点类型:0:仓库;1:门店
     */
    private Integer adrType;

    /**
     * 二级分类id(大类)
     */
    private String secondCategoryId;

    /**
     * 二级分类名称(大类)
     */
    private String secondCategoryName;

    /**
     * 货币种类代码,CNY
     */
    private String currencyCode;

    /**
     * 子公司ID
     */
    private String branchCompanyId;

    /**
     * 状态:0:制单;1:已提交;2:已审核;3:已拒绝;4:待退货;5:已退货;6:已取消;7:取消失败;8:异常
     */
    private Integer status;

    /**
     * 退货日期
     */
    private Date refundTime;

    /**
     * 退货日期早于(参数配置值加当前日期)
     */
    private Date refundTimeEarly;

    /**
     * 失败原因
     */
    private String failedReason;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String modifyUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 审核人ID
     */
    private String auditUserId;

    /**
     * 审核日期
     */
    private Date auditTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * pm_purchase_refund
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseRefundNo() {
        return purchaseRefundNo;
    }

    public void setPurchaseRefundNo(String purchaseRefundNo) {
        this.purchaseRefundNo = purchaseRefundNo == null ? null : purchaseRefundNo.trim();
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId == null ? null : spId.trim();
    }

    public String getSpNo() {
        return spNo;
    }

    public void setSpNo(String spNo) {
        this.spNo = spNo == null ? null : spNo.trim();
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName == null ? null : spName.trim();
    }

    public String getSpAdrId() {
        return spAdrId;
    }

    public void setSpAdrId(String spAdrId) {
        this.spAdrId = spAdrId == null ? null : spAdrId.trim();
    }

    public String getSpAdrNo() {
        return spAdrNo;
    }

    public void setSpAdrNo(String spAdrNo) {
        this.spAdrNo = spAdrNo == null ? null : spAdrNo.trim();
    }

    public String getSpAdrName() {
        return spAdrName;
    }

    public void setSpAdrName(String spAdrName) {
        this.spAdrName = spAdrName == null ? null : spAdrName.trim();
    }

    public String getRefundAdr() {
        return refundAdr;
    }

    public void setRefundAdr(String refundAdr) {
        this.refundAdr = refundAdr == null ? null : refundAdr.trim();
    }

    public Integer getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(Integer totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public BigDecimal getTotalRefundMoney() {
        return totalRefundMoney;
    }

    public void setTotalRefundMoney(BigDecimal totalRefundMoney) {
        this.totalRefundMoney = totalRefundMoney;
    }

    public BigDecimal getTotalRefundCost() {
        return totalRefundCost;
    }

    public void setTotalRefundCost(BigDecimal totalRefundCost) {
        this.totalRefundCost = totalRefundCost;
    }

    public Integer getTotalRealRefundAmount() {
        return totalRealRefundAmount;
    }

    public void setTotalRealRefundAmount(Integer totalRealRefundAmount) {
        this.totalRealRefundAmount = totalRealRefundAmount;
    }

    public BigDecimal getTotalRealRefundMoney() {
        return totalRealRefundMoney;
    }

    public void setTotalRealRefundMoney(BigDecimal totalRealRefundMoney) {
        this.totalRealRefundMoney = totalRealRefundMoney;
    }

    public Integer getAdrType() {
        return adrType;
    }

    public void setAdrType(Integer adrType) {
        this.adrType = adrType;
    }

    public String getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(String secondCategoryId) {
        this.secondCategoryId = secondCategoryId == null ? null : secondCategoryId.trim();
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName == null ? null : secondCategoryName.trim();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId == null ? null : branchCompanyId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Date getRefundTimeEarly() {
        return refundTimeEarly;
    }

    public void setRefundTimeEarly(Date refundTimeEarly) {
        this.refundTimeEarly = refundTimeEarly;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId == null ? null : modifyUserId.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId == null ? null : auditUserId.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}