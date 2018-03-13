package com.yatang.sc.kidd.dto.purchase;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @描述:入库确认服务商品详细信息
 * @类名:KiddConfirmOrderLinesDto
 * @作者: lvheping
 * @创建时间: 2017/9/27 10:19
 * @版本: v1.0
 */

public class KiddConfirmOrderLinesDto implements Serializable {
	private static final long	serialVersionUID	= -5611619020976567404L;
	private String				orderLineNo;// 单据行号
	private String				ownerCode;// 货主编码
	private String				itemCode;// 商品编码
	private String				itemId;	// 仓储系统商品ID
	private String				itemName;// 商品名称
	private String				inventoryType;// 库存类型
	private Integer				planQty;// 应收数量
	private Integer				actualQty;// 实收数量
	private String				batchCode;// 批次编码
	@JSONField(format = "yyyy-MM-dd")
	private Date				productDate;// 商品生产日期
	@JSONField(format = "yyyy-MM-dd")
	private Date				expireDate;// 商品过期日期
	private String				produceCode;// 生产批号



	@Override
	public String toString() {
		return "KiddConfirmOrderLinesDto{" + "orderLineNo='" + orderLineNo + '\'' + ", ownerCode='" + ownerCode + '\''
				+ ", itemCode='" + itemCode + '\'' + ", itemId='" + itemId + '\'' + ", itemName='" + itemName + '\''
				+ ", inventoryType='" + inventoryType + '\'' + ", planQty=" + planQty + ", actualQty=" + actualQty
				+ ", batchCode='" + batchCode + '\'' + ", productDate=" + productDate + ", expireDate=" + expireDate
				+ ", produceCode='" + produceCode + '\'' + '}';
	}



	public String getOrderLineNo() {
		return orderLineNo;
	}



	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}



	public String getOwnerCode() {
		return ownerCode;
	}



	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}



	public String getItemCode() {
		return itemCode;
	}



	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}



	public String getItemId() {
		return itemId;
	}



	public void setItemId(String itemId) {
		this.itemId = itemId;
	}



	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}



	public String getInventoryType() {
		return inventoryType;
	}



	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}



	public Integer getPlanQty() {
		return planQty;
	}



	public void setPlanQty(Integer planQty) {
		this.planQty = planQty;
	}



	public Integer getActualQty() {
		return actualQty;
	}



	public void setActualQty(Integer actualQty) {
		this.actualQty = actualQty;
	}



	public String getBatchCode() {
		return batchCode;
	}



	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}



	public Date getProductDate() {
		return productDate;
	}



	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}



	public Date getExpireDate() {
		return expireDate;
	}



	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}



	public String getProduceCode() {
		return produceCode;
	}



	public void setProduceCode(String produceCode) {
		this.produceCode = produceCode;
	}
}
