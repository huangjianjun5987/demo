package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>供应商结算表PO，不可修改
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年8月31日
 */
@Getter
@Setter
public class SupplierSettledPo implements Serializable {
	private Long				id;

	private String				purchaseReceiptNo;

	private String				asn;

	private String				purchaseOrderNo;

	private String				spNo;

	private String				spName;

	private String				spAdrNo;

	private String				branchCompanyCode;

	private String				branchCompanyName;

	private Integer				adrType;

	private String				adrTypeCode;

	private String				adrTypeName;

	private Integer				settlementPeriod;

	private Integer				payType;

	private String				groups;

	private String				groupsDesc;

	private String				dept;

	private String				deptDesc;

	private String				productCode;

	private String				productName;

	private BigDecimal			inputTaxRate;

	private Date				receivedTime;

	private Integer				receivedNumber;

	private BigDecimal			receivedMoneyWithoutTax;

	private BigDecimal			receivedMoneyWithTax;

	private static final long	serialVersionUID	= 1L;

}