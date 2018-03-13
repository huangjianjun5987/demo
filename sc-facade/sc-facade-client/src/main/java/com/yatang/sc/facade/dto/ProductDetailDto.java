package com.yatang.sc.facade.dto;

import com.yatang.sc.facade.dto.prod.AttributeDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @描述: 商品详情
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/7 10:35
 * @版本: v1.0
 */

public class  ProductDetailDto implements Serializable {

	private static final long			serialVersionUID	= -2252347271529050430L;
	// 主键ID
	private String						id;
	// 商品名称
	private String						name;

	// 销售名称
	private String						saleName;

	// 最小销售单位
	private String						minUnit;

	// 标准包装单位参考值，例如：箱*盒*个'
	private String						unitExplanation;

	// 标准包装规格参考值，例如：1*2*6',
	private String						packingSpecifications;

	// 最小销售单位id
	private String						minUnitId;
	// 更新或者新建商品来源 ：平台 or 门店
	private String						updateSource;

	// 动态属性
	private List<AttributeDto>			attributes;

	// 图片信息
	private List<ImageDto>				images;

	/**
	 * 销售价格
	 */
	private ProdSellPriceInfoDto 		prodSellPriceInfo;

	/**
	 * 服务承诺
	 */
	private List<ServiceCommitmentsDto>	ServiceCommitments;

	/**
	 * 销售库存量
	 */
	private Long stock;

	// 是否整箱销售：0-否；1-是
	private Integer							sellFullCase;

	// 整箱单位
	private String							fullCaseUnit;

	// 整箱单位Id
	private String							fullCaseUnitId;

	/**
	 * 建议零售价  yinyuxin
	 */
	private BigDecimal suggestPrice;

	/******************************套餐商品字段 yinyuxin********************************/

	// 商品类型:1常规商品 2打包商品 3附属商品 4称重商品 5加工称重品 6加工非称重品 7优惠券商品

	private String 						productType;

	// 打包商品明细
	private List<BundleProductScDto>      bundleProducts;



	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public void setMinUnit(String minUnit) {
		this.minUnit = minUnit;
	}

	public void setUnitExplanation(String unitExplanation) {
		this.unitExplanation = unitExplanation;
	}

	public void setPackingSpecifications(String packingSpecifications) {
		this.packingSpecifications = packingSpecifications;
	}

	public void setMinUnitId(String minUnitId) {
		this.minUnitId = minUnitId;
	}

	public void setUpdateSource(String updateSource) {
		this.updateSource = updateSource;
	}

	public void setAttributes(List<AttributeDto> attributes) {
		this.attributes = attributes;
	}

	public void setImages(List<ImageDto> images) {
		this.images = images;
	}

	public void setProdSellPriceInfo(ProdSellPriceInfoDto prodSellPriceInfo) {
		this.prodSellPriceInfo = prodSellPriceInfo;
	}

	public void setServiceCommitments(List<ServiceCommitmentsDto> serviceCommitments) {
		ServiceCommitments = serviceCommitments;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public void setSellFullCase(Integer sellFullCase) {
		this.sellFullCase = sellFullCase;
	}

	public void setFullCaseUnit(String fullCaseUnit) {
		this.fullCaseUnit = fullCaseUnit;
	}

	public void setFullCaseUnitId(String fullCaseUnitId) {
		this.fullCaseUnitId = fullCaseUnitId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSaleName() {
		return saleName;
	}

	public String getMinUnit() {
		return minUnit;
	}

	public String getUnitExplanation() {
		return unitExplanation;
	}

	public String getPackingSpecifications() {
		return packingSpecifications;
	}

	public String getMinUnitId() {
		return minUnitId;
	}

	public String getUpdateSource() {
		return updateSource;
	}

	public List<AttributeDto> getAttributes() {
		return attributes;
	}

	public List<ImageDto> getImages() {
		return images;
	}

	public ProdSellPriceInfoDto getProdSellPriceInfo() {
		return prodSellPriceInfo;
	}

	public List<ServiceCommitmentsDto> getServiceCommitments() {
		return ServiceCommitments;
	}

	public Long getStock() {
		return stock;
	}

	public Integer getSellFullCase() {
		return sellFullCase;
	}

	public String getFullCaseUnit() {
		return fullCaseUnit;
	}

	public String getFullCaseUnitId() {
		return fullCaseUnitId;
	}



	public BigDecimal getSuggestPrice() {
		return suggestPrice;
	}



	public void setSuggestPrice(BigDecimal suggestPrice) {
		this.suggestPrice = suggestPrice;
	}



	public String getProductType() {
		return productType;
	}



	public void setProductType(String productType) {
		this.productType = productType;
	}



	public List<BundleProductScDto> getBundleProducts() {
		return bundleProducts;
	}



	public void setBundleProducts(List<BundleProductScDto> bundleProducts) {
		this.bundleProducts = bundleProducts;
	}
}
