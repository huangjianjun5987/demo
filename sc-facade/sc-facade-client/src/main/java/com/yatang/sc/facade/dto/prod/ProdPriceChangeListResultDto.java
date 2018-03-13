package com.yatang.sc.facade.dto.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdPriceChangeListResultDto implements Serializable {

	private static final long	serialVersionUID	= 1L;
	/**
	 * 主键
	 */
	private Long				id;

	/**
	 * 变更类型:0:采购进价变更;1:售价变更
	 */
	private Integer				changeType;
	private String				changeTypeName;
	/**
	 * 供应商编码和供应商名称
	 */
	private String				spCodeAndName;

	/**
	 * 供应商地点编码和供应商地点名称
	 */
	private String				spAdrCodeAndName;

	/**
	 * 子公司编码及子公司名称
	 */
	private String				branchCompanyCodeAndName;
	/**
	 * 部类名
	 */
	private String				firstLevelCategoryName;

	/**
	 * 大类名
	 */
	private String				secondLevelCategoryName;

	/**
	 * 中类名
	 */
	private String				thirdLevelCategoryName;

	/**
	 * 小类名
	 */
	private String				fourthLevelCategoryName;
	/**
	 * 商品编码及商品描述
	 */
	private String				productCodeAndDesc;
	/**
	 * 操作人
	 */
	private String				createUserId;
	private String				createUserName;
	/**
	 * 操作时间
	 */
	private Date				createTime;

	/**
	 * 当前价格
	 */
	private BigDecimal			price;

	/**
	 * 提交价格
	 */
	private BigDecimal			newestPrice;

	/**
	 * 调价百分比
	 */
	private String				percentage;
	/**
	 * 毛利率
	 */
	private String				grossProfitMargin;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getChangeType() {
		return changeType;
	}



	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}



	public String getChangeTypeName() {
		return changeTypeName;
	}



	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}



	public String getSpCodeAndName() {
		return spCodeAndName;
	}



	public void setSpCodeAndName(String spCodeAndName) {
		this.spCodeAndName = spCodeAndName;
	}



	public String getSpAdrCodeAndName() {
		return spAdrCodeAndName;
	}



	public void setSpAdrCodeAndName(String spAdrCodeAndName) {
		this.spAdrCodeAndName = spAdrCodeAndName;
	}



	public String getBranchCompanyCodeAndName() {
		return branchCompanyCodeAndName;
	}



	public void setBranchCompanyCodeAndName(String branchCompanyCodeAndName) {
		this.branchCompanyCodeAndName = branchCompanyCodeAndName;
	}



	public String getFirstLevelCategoryName() {
		return firstLevelCategoryName;
	}



	public void setFirstLevelCategoryName(String firstLevelCategoryName) {
		this.firstLevelCategoryName = firstLevelCategoryName;
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



	public String getFourthLevelCategoryName() {
		return fourthLevelCategoryName;
	}



	public void setFourthLevelCategoryName(String fourthLevelCategoryName) {
		this.fourthLevelCategoryName = fourthLevelCategoryName;
	}



	public String getProductCodeAndDesc() {
		return productCodeAndDesc;
	}



	public void setProductCodeAndDesc(String productCodeAndDesc) {
		this.productCodeAndDesc = productCodeAndDesc;
	}



	public String getCreateUserId() {
		return createUserId;
	}



	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}



	public String getCreateUserName() {
		return createUserName;
	}



	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public BigDecimal getPrice() {
		return price;
	}



	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	public BigDecimal getNewestPrice() {
		return newestPrice;
	}



	public void setNewestPrice(BigDecimal newestPrice) {
		this.newestPrice = newestPrice;
	}



	public String getPercentage() {
		return percentage;
	}



	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}



	public String getGrossProfitMargin() {
		return grossProfitMargin;
	}



	public void setGrossProfitMargin(String grossProfitMargin) {
		this.grossProfitMargin = grossProfitMargin;
	}

}
