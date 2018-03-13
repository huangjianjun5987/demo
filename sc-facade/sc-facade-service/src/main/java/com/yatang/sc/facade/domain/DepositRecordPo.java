package com.yatang.sc.facade.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.yatang.sc.facade.enums.PayChannel;
import com.yatang.sc.facade.enums.PaymentType;

/**
 * @描述: 供应商保证金记录.
 * @作者: yipeng
 * @创建时间: 2017年05月22日21:02:03
 * @版本: 1.0 .
 */
@Getter
@Setter
public class DepositRecordPo implements java.io.Serializable {

	private static final long	serialVersionUID	= -7550039390911841722L;

	private Integer				id;

	private String				spId;

	/**
	 * 交易流水号
	 */
	private String				tradeNo;

	/**
	 * 支付渠道
	 */
	private PayChannel			payChannel;
	/**
	 * 缴纳类型
	 */
	private PaymentType			paymentType;
	/**
	 * 支付账号
	 */
	private String				payAccount;
	/**
	 * 本次缴纳金额
	 */
	private BigDecimal			amount;
	/**
	 * 缴前纳余额
	 */
	private BigDecimal			beforeAmount;
	/**
	 * 后缴纳余额
	 */
	private BigDecimal			afterAmount;
	private Date				createdTime;

	private SupplierInfoPo		supplier;

}