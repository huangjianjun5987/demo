package com.yatang.sc.operation.vo.supplier;

import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 供应商信息传输对象.
 * @作者: huangjianjun
 * @创建时间: 2017年5月15日-下午11:24:46 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class SupplierInfoVo implements Serializable {
	private static final long			serialVersionUID	= -6276941435912872664L;

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private String						id;											// 编号

	private Integer						basicId;									// 供应商基本信息id

	private Integer						operatTaxatId;								// 供应商经营及税务信息id

	private Integer						bankId;										// 供应商银行信息id

	private Integer						licenseId;									// 供应商营业执照(副本)信息id

	private Integer						salesRegionId;								// 销售区域ID

	/** 供应商状态: 0：草稿, 1:待审核,2:已审核,3:已拒绝,4:修改中 */
	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
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

	@Valid
	private SupplierBasicInfoVo			supplierBasicInfo;

	@Valid
	private SupplierOperTaxInfoVo		supplierOperTaxInfo;

	@Valid
	private SupplierBankInfoVo			supplierBankInfo;

	@Valid
	private SupplierlicenseInfoVo		supplierlicenseInfo;

	@Valid
	private SupplierSaleRegionVo 		saleRegionInfo;

	/** 提交类型 0：保存草稿， 1：提交 */
	@NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private Integer						commitType;

}
