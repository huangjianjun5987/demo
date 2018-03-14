package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;
import java.util.List;

import com.yatang.sc.purchase.dto.ProductAttributeDto;

/**
 * @描述: APP端退换货商品列表
 * @类名: ReturnRequestOrderProductListDto
 * @作者: kangdong
 * @创建时间: 2017/10/25 10:38
 * @版本: v1.0
 */
public class ReturnRequestOrderProductListDto implements Serializable {

	private static final long			serialVersionUID	= -8555659935513428941L;
	private String						iconUrl;
	private String						name;
	private String						saleName;
	private double						price;
	private double						rawTotalPrice;
	private long						num;
	private String						product_id;
	private String						sku_id;
	private Integer						quantity;									// 退货数量
	private Integer						unitQuantity;								// 销售内装数
	private List<ProductAttributeDto>	attributeDtoList;

	private String						minUnit;									// 最小销售单位
	private String						unitExplanation;							// 标准包装单位参考值，例如：箱*盒*个'
	private String						packingSpecifications;						// 标准包装规格参考值，例如：1*2*6'
	private String						fullCaseUnit;								// 整箱单位
	private Integer						sellFullCase;								// 是否整箱销售：0-否；1-是



	public String getIconUrl() {
		return iconUrl;
	}



	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSaleName() {
		return saleName;
	}



	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public double getRawTotalPrice() {
		return rawTotalPrice;
	}



	public void setRawTotalPrice(double rawTotalPrice) {
		this.rawTotalPrice = rawTotalPrice;
	}



	public long getNum() {
		return num;
	}



	public void setNum(long num) {
		this.num = num;
	}



	public String getProduct_id() {
		return product_id;
	}



	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}



	public String getSku_id() {
		return sku_id;
	}



	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}



	public Integer getQuantity() {
		return quantity;
	}



	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}



	public Integer getUnitQuantity() {
		return unitQuantity;
	}



	public void setUnitQuantity(Integer unitQuantity) {
		this.unitQuantity = unitQuantity;
	}



	public List<ProductAttributeDto> getAttributeDtoList() {
		return attributeDtoList;
	}



	public void setAttributeDtoList(List<ProductAttributeDto> attributeDtoList) {
		this.attributeDtoList = attributeDtoList;
	}



	public String getMinUnit() {
		return minUnit;
	}



	public void setMinUnit(String minUnit) {
		this.minUnit = minUnit;
	}



	public String getUnitExplanation() {
		return unitExplanation;
	}



	public void setUnitExplanation(String unitExplanation) {
		this.unitExplanation = unitExplanation;
	}



	public String getPackingSpecifications() {
		return packingSpecifications;
	}



	public void setPackingSpecifications(String packingSpecifications) {
		this.packingSpecifications = packingSpecifications;
	}



	public String getFullCaseUnit() {
		return fullCaseUnit;
	}



	public void setFullCaseUnit(String fullCaseUnit) {
		this.fullCaseUnit = fullCaseUnit;
	}



	public Integer getSellFullCase() {
		return sellFullCase;
	}



	public void setSellFullCase(Integer sellFullCase) {
		this.sellFullCase = sellFullCase;
	}
}
