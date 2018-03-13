package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>供应商修改审核PO
 * 
 * @author: kangdong
 * @version: 1.0, 2017年8月2日
 */
@Getter
@Setter
public class SupplierAuditsPo implements Serializable {

	private static final long	serialVersionUID	= 1L;
	// 供应商主表ID
	private String				id;
	//修改前供应商基本信息ID
	private Integer				basicId;
	//修改前供应商银行信息ID
	private Integer				bankId;
	//修改前供应商经营及税务信息ID
	private Integer				operatTaxatId;
	//修改前供应商营业执照(副本)信息ID
	private Integer				licenseId;
	//地点基本信息ID
	private Integer				adrBasicId;
	//联系人信息ID
	private Integer				contId;
	// 审核人ID
	private String				auditUserId;
	// 审核人姓名
	private String				auditPerson;
	// 是否通过
	private Boolean				pass;
	// 失败原因
	private String				failedReason;
}
