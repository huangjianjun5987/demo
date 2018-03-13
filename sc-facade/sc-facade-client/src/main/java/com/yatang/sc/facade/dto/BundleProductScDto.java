package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 套餐商品内的商品信息
 * @author: yinyuxin
 * @date: 2018/1/8 15:23
 * @version: v1.0
 */
public class BundleProductScDto implements Serializable{

	private static final long serialVersionUID = 3418602700239972483L;

	// 明细主键
	private String id;

	// 商品id
	private String productId;

	// 国际码
	private String internationalCode;

	// 商品名称
	private String name;

	// 最小包装单位
	private String minUnit;

	// 包装数量或重量，每个商品的打包数量或重量 是否小数
	private BigDecimal prodCount;

	// 销售名字
	private String saleName;

	//主商品表id
	private String parentProductId;



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public String getInternationalCode() {
		return internationalCode;
	}



	public void setInternationalCode(String internationalCode) {
		this.internationalCode = internationalCode;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getMinUnit() {
		return minUnit;
	}



	public void setMinUnit(String minUnit) {
		this.minUnit = minUnit;
	}



	public BigDecimal getProdCount() {
		return prodCount;
	}



	public void setProdCount(BigDecimal prodCount) {
		this.prodCount = prodCount;
	}



	public String getSaleName() {
		return saleName;
	}



	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}



	public String getParentProductId() {
		return parentProductId;
	}



	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}
}
