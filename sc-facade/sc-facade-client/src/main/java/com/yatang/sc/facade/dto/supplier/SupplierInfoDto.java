package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @描述: 供应商信息传输对象.
 * @作者: huangjianjun
 * @创建时间: 2017年5月15日-下午11:24:46 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierInfoDto implements Serializable {
	
	private static final long	serialVersionUID	= -6276941435912872664L;

	private String						id;												// 编号

	@JsonIgnore
	private Integer						basicId;

	@JsonIgnore
	private Integer						operatTaxatId;

	@JsonIgnore
	private Integer						bankId;

	@JsonIgnore
	private Integer						licenseId;

	/** 销售区域ID */
	@JsonIgnore
	private Integer						saleRegionId;

	/** 供应商状态: 0：草稿, 1:待审核,2:已审核,3:已拒绝,4:修改中 */
	private Integer						status;

	/** 失败原因 */
	private String						failedReason;

	/** 审核人id */
	private String						auditUserId;

	/** 审核时间 */
	private Date						auditTime;

	private Date						createTime;

	private Date						modifyTime;

	private String						createUser;

	private String 						modifyUser;

	@JsonManagedReference
	private SupplierBasicInfoDto		supplierBasicInfo;

	@JsonManagedReference
	private SupplierOperTaxInfoDto		supplierOperTaxInfo;

	@JsonManagedReference
	private SupplierBankInfoDto			supplierBankInfo;

	@JsonManagedReference
	private SupplierlicenseInfoDto		supplierlicenseInfo;
	
	@JsonManagedReference
	private SupplierSaleRegionDto 		saleRegionInfo;


	/** 提交类型 0：保存草稿， 1：提交 */
	private Integer						commitType;


	public void setSupplierBasicInfo(SupplierBasicInfoDto supplierBasicInfo) {
		if(supplierBasicInfo.getModification() != null){
			this.supplierBasicInfo = supplierBasicInfo.getModification();
		}else{
			this.supplierBasicInfo = supplierBasicInfo;
		}
	}



	public void setSupplierOperTaxInfo(SupplierOperTaxInfoDto supplierOperTaxInfo) {
		//如果有修改记录则详情显示修改后待审批的记录并显示审核按钮
		if(supplierOperTaxInfo.getModification() != null){
			this.supplierOperTaxInfo = supplierOperTaxInfo.getModification();
		}else{
			this.supplierOperTaxInfo = supplierOperTaxInfo;
		}
	}
	
	
	
	public void setSupplierBankInfo(SupplierBankInfoDto supplierBankInfo) {
		if(supplierBankInfo.getModification() != null){
			this.supplierBankInfo = supplierBankInfo.getModification();
		}else{
			this.supplierBankInfo = supplierBankInfo;
		}
	}



	public void setSupplierlicenseInfo(SupplierlicenseInfoDto supplierlicenseInfo) {
		if(supplierlicenseInfo.getModification() != null){
			this.supplierlicenseInfo = supplierlicenseInfo.getModification();
		}else{
			this.supplierlicenseInfo = supplierlicenseInfo;
		}
	}


}
