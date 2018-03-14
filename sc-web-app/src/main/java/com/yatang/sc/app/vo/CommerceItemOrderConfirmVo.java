package com.yatang.sc.app.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 商品分类vo(app)
 * @作者: yinyuxin
 * @创建时间: 2017年7月8日16:15:53
 * @版本: 1.0 .
 */
public class CommerceItemOrderConfirmVo implements Serializable{

	private static final long serialVersionUID = -6414873797195512854L;
	private String skuId;

	private String productName;

	private String productImg;

	private Map<String, String> properties;

	private String type;

	private List<CommerceItemOrderConfirmVo>  subItems;
	/*
	数量
	 */
	private Long quantity;
	/*
	商品原价
	 */
	private double listPrice;
	/*
	商品单价
	 */
	private double salePrice;

	/*
	商品总价
	 */
	private double amount;

	/**
	 * 是否支持改地区
	 */
	private boolean available;

	/**
	 * 销售单位的数量 eg:几箱
	 */
	private Long saleQuantity;

	/**
	 * 每箱多少瓶  就是销售内装数
	 */
	private Integer unitQuantity;

	/**
	 * 是否整箱销售：0-否；1-是
	 */
	private Integer  sellFullCase;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public double getListPrice() {
		return listPrice;
	}

	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Long getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(Long saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public Integer getUnitQuantity() {
		return unitQuantity;
	}

	public void setUnitQuantity(Integer unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	public Integer getSellFullCase() {
		return sellFullCase;
	}

	public void setSellFullCase(Integer sellFullCase) {
		this.sellFullCase = sellFullCase;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CommerceItemOrderConfirmVo> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<CommerceItemOrderConfirmVo> subItems) {
		this.subItems = subItems;
	}
}
