package com.yatang.sc.operation.vo.pm;

import com.yatang.sc.operation.common.BaseVo;
import com.yatang.sc.validgroup.GroupOne;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @description: 采购退货单值清单查询参数vo
 * @author: yinyuxin
 * @date: 2017/10/19 16:46
 * @version: v1.0
 */
public class PmPurchaseRefundQueryProductParamVo extends BaseVo implements Serializable{

	private static final long serialVersionUID = -5000808801983516321L;

	@NotEmpty(message = "{msg.notEmpty.message}", groups = GroupOne.class)
	private String purchaseOrderNo;//采购单单号

	private String name;//品牌名称

	private String productName;//商品名称

	private String brandId;//品牌id

	private String productCode;//商品编号

	private String logicWareHouseCode;//逻辑仓code/门店code

	private String spAdrNo;//供应商地点code

	private String branchCompanyId;//分公司id





	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}



	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getBrandId() {
		return brandId;
	}



	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}



	public String getProductCode() {
		return productCode;
	}



	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}



	public String getLogicWareHouseCode() {
		return logicWareHouseCode;
	}



	public void setLogicWareHouseCode(String logicWareHouseCode) {
		this.logicWareHouseCode = logicWareHouseCode;
	}



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}



	public String getSpAdrNo() {
		return spAdrNo;
	}



	public void setSpAdrNo(String spAdrNo) {
		this.spAdrNo = spAdrNo;
	}


}
