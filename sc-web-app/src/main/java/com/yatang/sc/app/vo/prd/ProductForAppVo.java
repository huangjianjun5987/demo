package com.yatang.sc.app.vo.prd;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.app.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.app.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 商品列表中商品字段
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年8月1日
 */

public class ProductForAppVo implements Serializable,Comparable<ProductForAppVo>{

	private static final long serialVersionUID = -8394826321087771366L;

	/** 商品id */
	private String productId;

	/** 商品编码 */
	private String productCode;

	/** 缩略图url 180*180*/

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String thumbnailImage;

	/** 缩略图url * 200*200 */
	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String smallImage;

	// /** 商品品名 */
	// private String name;

	/** 商品品牌 */
	private String brand;

	/** 销售名称 */
	private String saleName;

	/** 起订量 */
	private Integer				minNumber;

	/** 显示单位 */
	private String				displayUnit;

	/**
	 * 规格
	 */
	private String              packingSpecifications;

	/** 最小包装单位 **/
	private String	minUnit;

	/*库存信息*/
	private boolean inventory;


	// /** 一级商品分类名称 */
	// private String firstLevelCategoryName;
	//
	// /** 二级商品分类名称 */
	// private String secondLevelCategoryName;
	//
	// /** 三级商品分类名称 */
	// private String thirdLevelCategoryName;
	//
	// /** 四级商品分类名称 */
	// private String fourthLevelCategoryName;

	// /** 供应链状态：1-草稿 2-生效 3-暂停使用（全国性下架） 4-停止使用 */
	// private String supplyChainStatus;

	public String price;

	/** 是否整箱销售：0-否；1-是 */
	private String				sellFullCase;



	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public String getProductCode() {
		return productCode;
	}



	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public boolean isInventory() {
		return inventory;
	}

	public void setInventory(boolean inventory) {
		this.inventory = inventory;
	}

	public String getThumbnailImage() {
		return thumbnailImage;
	}



	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public String getSaleName() {
		return saleName;
	}



	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}



	public Integer getMinNumber() {
		return minNumber;
	}



	public void setMinNumber(Integer minNumber) {
		this.minNumber = minNumber;
	}



	public String getDisplayUnit() {
		return displayUnit;
	}



	public void setDisplayUnit(String displayUnit) {
		this.displayUnit = displayUnit;
	}



	public String getPackingSpecifications() {
		return packingSpecifications;
	}



	public void setPackingSpecifications(String packingSpecifications) {
		this.packingSpecifications = packingSpecifications;
	}



	public String getMinUnit() {
		return minUnit;
	}



	public void setMinUnit(String minUnit) {
		this.minUnit = minUnit;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getSmallImage() {
		return smallImage;
	}



	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	@Override
	public int compareTo(ProductForAppVo o) {
		if (inventory){
			return -1;
		}
		return 1;
	}



	public String getSellFullCase() {
		return sellFullCase;
	}



	public void setSellFullCase(String sellFullCase) {
		this.sellFullCase = sellFullCase;
	}
}
