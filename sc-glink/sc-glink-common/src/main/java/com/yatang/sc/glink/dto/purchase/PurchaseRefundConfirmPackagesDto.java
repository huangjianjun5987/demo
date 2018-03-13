package com.yatang.sc.glink.dto.purchase;

import java.io.Serializable;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月9日
 */
public class PurchaseRefundConfirmPackagesDto implements Serializable {
	private static final long						serialVersionUID	= 1L;
	private String									logisticsCode;				// 物流公司编码
	private String									logisticsName;				// 物流公司名称
	private String									expressCode;				// 运单号
	private String									packageCode;				// 包裹编码
	private Double									theoreticalWeight;			// 包裹理论重量（KG）
	private Double									weight;						// 包裹重量（KG）
	private Double									volume;						// 包裹体积（L）
	private Double									length;						// 长（cm）
	private Double									width;						// 宽（cm）
	private Double									height;						// 高（cm）
	private String									packageMaterialType;		// 耗材型号
	private Integer									packageMaterialQuantity;	// 耗材数量
	private PurchaseRefundConfirmPackagesItemsDto	items;						// 该包裹内的商品信息



	public String getLogisticsCode() {
		return logisticsCode;
	}



	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}



	public String getLogisticsName() {
		return logisticsName;
	}



	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}



	public String getExpressCode() {
		return expressCode;
	}



	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}



	public String getPackageCode() {
		return packageCode;
	}



	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}



	public Double getTheoreticalWeight() {
		return theoreticalWeight;
	}



	public void setTheoreticalWeight(Double theoreticalWeight) {
		this.theoreticalWeight = theoreticalWeight;
	}



	public Double getWeight() {
		return weight;
	}



	public void setWeight(Double weight) {
		this.weight = weight;
	}



	public Double getVolume() {
		return volume;
	}



	public void setVolume(Double volume) {
		this.volume = volume;
	}



	public Double getLength() {
		return length;
	}



	public void setLength(Double length) {
		this.length = length;
	}



	public Double getWidth() {
		return width;
	}



	public void setWidth(Double width) {
		this.width = width;
	}



	public Double getHeight() {
		return height;
	}



	public void setHeight(Double height) {
		this.height = height;
	}



	public String getPackageMaterialType() {
		return packageMaterialType;
	}



	public void setPackageMaterialType(String packageMaterialType) {
		this.packageMaterialType = packageMaterialType;
	}



	public Integer getPackageMaterialQuantity() {
		return packageMaterialQuantity;
	}



	public void setPackageMaterialQuantity(Integer packageMaterialQuantity) {
		this.packageMaterialQuantity = packageMaterialQuantity;
	}



	public PurchaseRefundConfirmPackagesItemsDto getItems() {
		return items;
	}



	public void setItems(PurchaseRefundConfirmPackagesItemsDto items) {
		this.items = items;
	}

}
