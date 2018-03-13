
package com.yatang.sc.inventory.dto.im;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @描述:分页查询返回封装类
 * @类名:ImAdjustmentQueryListVo
 * @作者: lvheping
 * @创建时间: 2017/8/30 13:48
 * @版本: v1.0
 */


public class ImAdjustmentQueryListDto implements Serializable {

	private static final long			serialVersionUID	= -3508926230836031502L;
	private Long						id;											// pk

	private String						adjustmentNo;								// 单据编号

	private Integer						type;										// 类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）

	private Integer						status;										// 状态:0:制单;1:已生效

	private Date						adjustmentTime;								// 调整日期

	private String						warehouseCode;								// 调整仓库,逻辑仓code

	private String						warehouseName;								// 调整仓库,逻辑仓名称

	private Long						totalQuantity;								// 调整数量合计

	private BigDecimal					totalAdjustmentCost;						// 调整成本合计

	private String						description;								// 备注

	private String						externalBillNo;								// 外部单据号

	private String						failedReason;								// 失败原因

	private String						auditUserId;								// 批准人ID

	private String						auditUserName;								// 批准人名称

	private Date						auditTime;									// 批准日期

	private Date						createTime;

	private Date						modifyTime;

	private String						createUserId;								// 创建人id

	private String						createUserName;								// 创建人名称

	private String						modifyUserId;								// 修改人id

	private String						modifyUserName;								// 修改人名称

	private String						branchCompanyId;							// 子公司id

	private List<ImAdjustmentItemDto>	imAdjustmentItemVos;						// 库存调整单商品集合

	@Override
	public String toString() {
		return "ImAdjustmentQueryListDto{" +
				"id=" + id +
				", adjustmentNo='" + adjustmentNo + '\'' +
				", type=" + type +
				", status=" + status +
				", adjustmentTime=" + adjustmentTime +
				", warehouseCode='" + warehouseCode + '\'' +
				", warehouseName='" + warehouseName + '\'' +
				", totalQuantity=" + totalQuantity +
				", totalAdjustmentCost=" + totalAdjustmentCost +
				", description='" + description + '\'' +
				", externalBillNo='" + externalBillNo + '\'' +
				", failedReason='" + failedReason + '\'' +
				", auditUserId='" + auditUserId + '\'' +
				", auditUserName='" + auditUserName + '\'' +
				", auditTime=" + auditTime +
				", createTime=" + createTime +
				", modifyTime=" + modifyTime +
				", createUserId='" + createUserId + '\'' +
				", createUserName='" + createUserName + '\'' +
				", modifyUserId='" + modifyUserId + '\'' +
				", modifyUserName='" + modifyUserName + '\'' +
				", branchCompanyId='" + branchCompanyId + '\'' +
				", imAdjustmentItemVos=" + imAdjustmentItemVos +
				'}';
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAdjustmentNo(String adjustmentNo) {
		this.adjustmentNo = adjustmentNo;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setAdjustmentTime(Date adjustmentTime) {
		this.adjustmentTime = adjustmentTime;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public void setTotalAdjustmentCost(BigDecimal totalAdjustmentCost) {
		this.totalAdjustmentCost = totalAdjustmentCost;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public void setImAdjustmentItemVos(List<ImAdjustmentItemDto> imAdjustmentItemVos) {
		this.imAdjustmentItemVos = imAdjustmentItemVos;
	}

	public Long getId() {
		return id;
	}

	public String getAdjustmentNo() {
		return adjustmentNo;
	}

	public Integer getType() {
		return type;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getAdjustmentTime() {
		return adjustmentTime;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public BigDecimal getTotalAdjustmentCost() {
		return totalAdjustmentCost;
	}

	public String getDescription() {
		return description;
	}

	public String getExternalBillNo() {
		return externalBillNo;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public String getAuditUserId() {
		return auditUserId;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public List<ImAdjustmentItemDto> getImAdjustmentItemVos() {
		return imAdjustmentItemVos;
	}
}

