package com.yatang.sc.purchase.dto;

import com.yatang.xc.mbd.biz.prod.dubboservice.dto.InternationalCodeDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @描述: 退货商品列表信息
 * @类名: ReturnRequestProductDto
 * @作者: kangdong
 * @创建时间: 2017/10/19 10:58
 * @版本: v1.0
 */
public class ReturnRequestProductDto implements Serializable {

	private static final long			serialVersionUID	= -40878527835798205L;

	private Long						id;

	/**
	 * 产品id
	 */
	private String						productId;
	/**
	 * 商品名称
	 */
	private String						productName;

	/**
	 * 商品图片
	 */
	private String						productImg;

	/**
	 * 二级分类
	 */
	private String						secondLevelCategoryName;

	/**
	 * 三级分类
	 */
	private String						thirdLevelCategoryName;

	/**
	 * 商品编码
	 */
	private String						productCode;

	/**
	 * 国际码信息
	 */
	private List<InternationalCodeDto>	internationalCodes;

	/**
	 * 退货数量
	 */
	private Long						quantity;

	/**
	 * 实际退换货的数量
	 */
	private Long						actualReturnQuantity;

	/**
	 * 价格类
	 */
	// private ItemPriceInfoDto itemPrice;

	/**
	 * 商品总额
	 */
	private double						rawTotalPrice;

	/**
	 * 基础价格
	 */
	private double						listPrice;

	/**
	 * 购物车商品售价
	 */
	private double						salePrice;

	/**
	 * 配送的数量
	 */
	private Long						shippedQuantity;

	/**
	 * 是否整箱销售：0-否；1-是
	 */
	private Integer						sellFullCase;

	/**
	 * 销售内装数
	 */
	private Integer						unitQuantity;

	/**
	 * 商品单价
	 */
	// private Double price;



	/**
	 * 商品金额
	 */
	// private Double itemPrice;

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
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



	public String getSecondLevelCategoryName() {
		return secondLevelCategoryName;
	}



	public void setSecondLevelCategoryName(String secondLevelCategoryName) {
		this.secondLevelCategoryName = secondLevelCategoryName;
	}



	public String getThirdLevelCategoryName() {
		return thirdLevelCategoryName;
	}



	public void setThirdLevelCategoryName(String thirdLevelCategoryName) {
		this.thirdLevelCategoryName = thirdLevelCategoryName;
	}



	public String getProductCode() {
		return productCode;
	}



	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}



	public List<InternationalCodeDto> getInternationalCodes() {
		return internationalCodes;
	}



	public void setInternationalCodes(List<InternationalCodeDto> internationalCodes) {
		this.internationalCodes = internationalCodes;
	}



	public Long getQuantity() {
		return quantity;
	}



	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}



	public Long getActualReturnQuantity() {
		return actualReturnQuantity;
	}



	public void setActualReturnQuantity(Long actualReturnQuantity) {
		this.actualReturnQuantity = actualReturnQuantity;
	}



	public double getRawTotalPrice() {
		return rawTotalPrice;
	}



	public void setRawTotalPrice(double rawTotalPrice) {
		this.rawTotalPrice = rawTotalPrice;
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



	public Long getShippedQuantity() {
		return shippedQuantity;
	}



	public void setShippedQuantity(Long shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}



	public Integer getSellFullCase() {
		return sellFullCase;
	}



	public void setSellFullCase(Integer sellFullCase) {
		this.sellFullCase = sellFullCase;
	}



	public Integer getUnitQuantity() {
		return unitQuantity;
	}



	public void setUnitQuantity(Integer unitQuantity) {
		this.unitQuantity = unitQuantity;
	}
}
