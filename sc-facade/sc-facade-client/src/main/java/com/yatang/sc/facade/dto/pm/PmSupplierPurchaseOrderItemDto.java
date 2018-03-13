package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述: 供应商采购单列表
 * @类名: PmSupplierPurchaseOrderDto
 * @作者: kangdong
 * @创建时间: 2018/1/9 17:11
 * @版本: v1.0
 */

public class PmSupplierPurchaseOrderItemDto implements Serializable {

	private static final long	serialVersionUID	= -7981739302413863280L;
	private Long				id;											// id

	private Integer				adrType;									// 收货地点类型:0:仓库;1:门店

	private String				adrTypeCode;								// 收货地点编号

	private String				adrTypeName;								// 收货地点名称

	private String				spAdrNo;									// 供应商地点编码

	private String				spAdrName;									// 供应商地点名称

	private String				purchaseOrderNo;							// 采购单号

	private Integer				status;										// 订单状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消*

	private Integer				spAcceptStatus;								// 供应商接单状态:0未接单;1:已接单

	private Date				createTime;									// 订货日期

	private Date				estimatedDeliveryDate;						// 预计送货日期

	private Boolean				isAbleInput;								// 是否可以录入ASN

	List<ASNListDto>			asnList;									// 蚂蚁金服嵌套子表格



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getAdrType() {
		return adrType;
	}



	public void setAdrType(Integer adrType) {
		this.adrType = adrType;
	}



	public String getAdrTypeCode() {
		return adrTypeCode;
	}



	public void setAdrTypeCode(String adrTypeCode) {
		this.adrTypeCode = adrTypeCode;
	}



	public String getAdrTypeName() {
		return adrTypeName;
	}



	public void setAdrTypeName(String adrTypeName) {
		this.adrTypeName = adrTypeName;
	}



	public String getSpAdrNo() {
		return spAdrNo;
	}



	public void setSpAdrNo(String spAdrNo) {
		this.spAdrNo = spAdrNo;
	}



	public String getSpAdrName() {
		return spAdrName;
	}



	public void setSpAdrName(String spAdrName) {
		this.spAdrName = spAdrName;
	}



	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}



	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public Integer getSpAcceptStatus() {
		return spAcceptStatus;
	}



	public void setSpAcceptStatus(Integer spAcceptStatus) {
		this.spAcceptStatus = spAcceptStatus;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}



	public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}



	public Boolean getIsAbleInput() {
		return isAbleInput;
	}



	public void setIsAbleInput(Boolean isAbleInput) {
		this.isAbleInput = isAbleInput;
	}



	public List<ASNListDto> getAsnList() {
		return asnList;
	}



	public void setAsnList(List<ASNListDto> asnList) {
		this.asnList = asnList;
	}

	// private String spId; // 供应商ID
	//
	// private String spNo; // 供应商编码
	//
	// private String spName; // 供应商名称
	//
	// private String spAdrId; // 供应商地点ID

}