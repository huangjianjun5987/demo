package com.yatang.sc.operation.vo.im;

import java.io.Serializable;
import java.util.Date;

import com.yatang.sc.operation.common.BaseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述:查询库存列表参数类
 * @类名:QueryImAdjustmentParamVo
 * @作者: lvheping
 * @创建时间: 2017/8/30 11:17
 * @版本: v1.0
 */


public class ImAdjustmentQueryParamVo extends BaseVo implements Serializable {
	private static final long	serialVersionUID	= 4942288502918382410L;
	private Long				id;								// 单据编号
	private Integer				status;										// 状态:0:制单;1:已生效
	private Date				adjustmentTime;									// 调整日期
	private Integer				type;										// 类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）
	private String				warehouseCode;								// 调整仓库,逻辑仓code
	private String				branchCompanyId;							// 子公司id
	private String				productId;									// 商品id
	private String				externalBillNo;								// 外部单据号
	private Date				adjustmentStartTime;						// 调整开始日期
	private Date				adjustmentEndTime;							// 调整结束日期

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setAdjustmentTime(Date adjustmentTime) {
		this.adjustmentTime = adjustmentTime;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	public void setAdjustmentStartTime(Date adjustmentStartTime) {
		this.adjustmentStartTime = adjustmentStartTime;
	}

	public void setAdjustmentEndTime(Date adjustmentEndTime) {
		this.adjustmentEndTime = adjustmentEndTime;
	}

	public Long getId() {
		return id;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getAdjustmentTime() {
		return adjustmentTime;
	}

	public Integer getType() {
		return type;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public String getProductId() {
		return productId;
	}

	public String getExternalBillNo() {
		return externalBillNo;
	}

	public Date getAdjustmentStartTime() {
		return adjustmentStartTime;
	}

	public Date getAdjustmentEndTime() {
		return adjustmentEndTime;
	}

	@Override
	public String toString() {
		return "ImAdjustmentQueryParamVo{" +
				"id=" + id +
				", status=" + status +
				", adjustmentTime=" + adjustmentTime +
				", type=" + type +
				", warehouseCode='" + warehouseCode + '\'' +
				", branchCompanyId='" + branchCompanyId + '\'' +
				", productId='" + productId + '\'' +
				", externalBillNo='" + externalBillNo + '\'' +
				", adjustmentStartTime=" + adjustmentStartTime +
				", adjustmentEndTime=" + adjustmentEndTime +
				'}';
	}
}
