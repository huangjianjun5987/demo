package com.yatang.sc.facade.dto.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class ProdSellPriceInfoDto implements Serializable {
	private Long						id;							// 销售区间价id

	private String						productId;					// 商品id

	private BigDecimal					lowestPrice;				// 销售区间最低价

	private Date						createTime;					// 创建时间

	private Date						modifyTime;					// 停用或伪删除时间

	private Integer						status;						// 状态0为失效状态1为正常使用状态

	private String						branchCompanyId;			// 分公司的id

	private String						branchCompanyName;			// 分公司名称

	private BigDecimal					suggestPrice;				// 建议零售价

	private Integer						deliveryDay;				// 承诺最迟发货时间（天）

	private Integer						salesInsideNumber;			// 销售内装数

	private Integer						minNumber;					// 最小销售单位

	private Integer						deleteStatus;				// 0为未删除状态1为删除状态

	private String						modifyUserId;				// 修改或删除操作人员id

	private String                      modifyUserName;             // 修改人姓名    yinyuxin

	private String						createUserId;				// 创建价格人员id

	private String                      createUserName;             // 创建价格人员姓名     yinyuxin

	private Integer                     preHarvestPinStatus;        //是否先采后销  0：否  1:是

	private Integer                     maxNumber;                  //最大销售量

	private List<ProdSellSectionPriceDto>	sellSectionPrices;		// 区间价格信息

	private ProdSellPriceInfoDto    	sellPricesInReview; 			//审核中的价格信息  yinyuxin（不持久化）

	private BigDecimal                  purchasePrice;              //采购价格   yinyuxin （不持久化）

	private Integer						auditStatus;				// 审核状态:1:已提交;2:已审核;3:已拒绝(默认2)

	private String						auditUserId;				// 审核人ID

	private String						auditUserName;				// 审核人姓名

	private Date						auditTime;					// 审核时间

	private Integer 					sellFullCase;               // 是否整箱销售：0-否；1-是(只有销售关系详情接口时有效)    yinyuxin

	private String 						fullCaseUnit;               // 整箱单位  (只有销售关系详情接口时有效)       yinyuxin

	private Integer						firstCreated;				// 第一次创建使用:1:是;0:否(默认0)

	private boolean priceProtection = false; //是否价格保护

	private static final long			serialVersionUID	= 1L;


	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String pProductId) {
		productId = pProductId;
	}

	public void setLowestPrice(BigDecimal pLowestPrice) {
		lowestPrice = pLowestPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date pCreateTime) {
		createTime = pCreateTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date pModifyTime) {
		modifyTime = pModifyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer pStatus) {
		status = pStatus;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String pBranchCompanyId) {
		branchCompanyId = pBranchCompanyId;
	}

	public String getBranchCompanyName() {
		return branchCompanyName;
	}

	public void setBranchCompanyName(String pBranchCompanyName) {
		branchCompanyName = pBranchCompanyName;
	}

	public BigDecimal getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(BigDecimal pSuggestPrice) {
		suggestPrice = pSuggestPrice;
	}

	public Integer getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(Integer pDeliveryDay) {
		deliveryDay = pDeliveryDay;
	}

	public Integer getSalesInsideNumber() {
		return salesInsideNumber;
	}

	public void setSalesInsideNumber(Integer pSalesInsideNumber) {
		salesInsideNumber = pSalesInsideNumber;
	}

	public Integer getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(Integer pMinNumber) {
		minNumber = pMinNumber;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer pDeleteStatus) {
		deleteStatus = pDeleteStatus;
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String pModifyUserId) {
		modifyUserId = pModifyUserId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String pCreateUserId) {
		createUserId = pCreateUserId;
	}

	public Integer getPreHarvestPinStatus() {
		if(preHarvestPinStatus == null){
			return 1;
		}
		return preHarvestPinStatus;
	}

	public void setPreHarvestPinStatus(Integer pPreHarvestPinStatus) {
		if(pPreHarvestPinStatus == null){
			pPreHarvestPinStatus = 1;
		}
		preHarvestPinStatus = pPreHarvestPinStatus;
	}

	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer pMaxNumber) {
		maxNumber = pMaxNumber;
	}

	public List<ProdSellSectionPriceDto> getSellSectionPrices() {
		return sellSectionPrices;
	}

	public void setSellSectionPrices(List<ProdSellSectionPriceDto> pSellSectionPrices) {
		sellSectionPrices = pSellSectionPrices;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice==null?new BigDecimal(0):lowestPrice;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}



	public ProdSellPriceInfoDto getSellPricesInReview() {
		return sellPricesInReview;
	}



	public void setSellPricesInReview(ProdSellPriceInfoDto sellPricesInReview) {
		this.sellPricesInReview = sellPricesInReview;
	}



	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}



	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}



	public String getAuditUserId() {
		return auditUserId;
	}



	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}



	public Date getAuditTime() {
		return auditTime;
	}



	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}



	public String getAuditUserName() {
		return auditUserName;
	}



	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}



	public Integer getSellFullCase() {
		return sellFullCase;
	}



	public void setSellFullCase(Integer sellFullCase) {
		this.sellFullCase = sellFullCase;
	}

	public boolean isPriceProtection() {
		return priceProtection;
	}

	public void setPriceProtection(boolean pPriceProtection) {
		priceProtection = pPriceProtection;
	}

	public String getFullCaseUnit() {
		return fullCaseUnit;
	}



	public void setFullCaseUnit(String fullCaseUnit) {
		this.fullCaseUnit = fullCaseUnit;
	}



	public String getModifyUserName() {
		return modifyUserName;
	}



	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}



	public String getCreateUserName() {
		return createUserName;
	}



	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}



	public Integer getFirstCreated() {
		return firstCreated;
	}



	public void setFirstCreated(Integer firstCreated) {
		this.firstCreated = firstCreated;
	}
}