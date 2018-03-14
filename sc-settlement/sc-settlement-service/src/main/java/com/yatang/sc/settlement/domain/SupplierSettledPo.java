package com.yatang.sc.settlement.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

	private static final long serialVersionUID = -2569749662033131880L;

	/**
	 * 编号
	 * */
	private Integer 			id;

	/**
	 * (采购)收货单号
	 * */
	private String				purchaseReceiptNo;

	/**
	 * ASN号
	 * */
	private String				asn;

	/**
	 * 采购单号
	 * */
	private String				purchaseOrderNo;

	/**
	 * 供应商编号
	 * */
	private String				spNo;

	/**
	 * 供应商名称
	 * */
	private String				spName;

	/**
	 * 供应商地点编号
	 * */
	private String				spAdrNo;

	/**
	 * 子公司编码
	 * */
	private String				branchCompanyCode;

	/**
	 * 子公司名称
	 * */
	private String				branchCompanyName;

	/**
	 * 收货地点类型 (0:仓库;1:门店)
	 * */
	private Integer				adrType;

	/**
	 * 收货地点编码
	 * */
	private String				adrTypeCode;

	/**
	 * 收货地点名称
	 * */
	private String				adrTypeName;

	/**
	 * 账期 (0:周结；1：半月结；2：月结；)
	 * */
	private Integer				settlementPeriod;

	/**
	 * 付款方式 (0：网银，1：银行转账，2：现金，3：支票)
	 * */
	private Integer				payType;

	/**
	 * 部类
	 * */
	private String				groups;

	/**
	 * 部类描述
	 * */
	private String				groupsDesc;

	/**
	 * 大类
	 * */
	private String				dept;

	/**
	 * 大类描述
	 * */
	private String				deptDesc;

	/**
	 * 商品编号
	 * */
	private String				productCode;

	/**
	 * 商品名称
	 * */
	private String				productName;

	/**
	 * 商品进项税率
	 * */
	private BigDecimal			inputTaxRate;

	/**
	 * 收货日期
	 * */
	private Date				receivedTime;

	/**
	 * 已收货数量
	 * */
	private Integer				receivedNumber;

	/**
	 * 收货金额（未税)
	 * */
	private BigDecimal			receivedMoneyWithoutTax;

	/**
	 * 收货金额（含税）
	 * */
	private BigDecimal			receivedMoneyWithTax;
}