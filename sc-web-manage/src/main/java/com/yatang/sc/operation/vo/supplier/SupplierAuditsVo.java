package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>供应商修改审核提交信息
 * 
 * @author: kangdong
 * @version: 1.0, 2017年8月2日
 */
@Getter
@Setter
public class SupplierAuditsVo implements Serializable {
	private static final long	serialVersionUID	= 1L;
	// 供应商主表ID
	@NotNull
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
	// 是否通过
	@NotNull
	private Boolean				pass;
	// 失败原因
	private String				failedReason;
}
