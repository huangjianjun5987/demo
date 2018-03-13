package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商主表信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-下午10:29:34 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierInfoPo implements Serializable {

	private static final long		serialVersionUID	= -6276941435912872664L;

	private String					id;

	private Integer					basicId;

	private Integer					operatTaxatId;

	private Integer					bankId;

	private Integer					licenseId;

	/** 销售区域ID */
	private Integer					saleRegionId;

	/** 供应商状态: 0：草稿, 1:待审核,2:已审核,3:已拒绝,4:修改中 */
	private Integer					status;

	/** 失败原因 */
	private String					failedReason;

	/** 审核人id */
	private String					auditUserId;

	/** 审核时间 */
	private Date					auditTime;

	private Date					createTime;

	private Date					modifyTime;

	private String					createUser;

	private String					modifyUser;

	private SupplierBasicInfoPo		supplierBasicInfo;

	private SupplierOperTaxInfoPo	supplierOperTaxInfo;

	private SupplierBankInfoPo		supplierBankInfo;

	private SupplierlicenseInfoPo	supplierlicenseInfo;

	private SupplierSaleRegionPo	saleRegionInfo;
	/** 提交类型 0：保存草稿， 1：提交 */
	private Integer					commitType;

}