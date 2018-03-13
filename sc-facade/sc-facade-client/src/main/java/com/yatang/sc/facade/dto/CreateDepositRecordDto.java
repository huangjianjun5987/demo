package com.yatang.sc.facade.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @描述: 创建保证金记录DTO
 * @作者: yipeng
 * @创建时间: 2017年05月23日12:50:55
 * @版本: 1.0 .
 */
@Data
public class CreateDepositRecordDto implements java.io.Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	private Integer				spId;
	private String				tradeNo;
	private String				payChannel;
	private String				paymentType;
	private String				payAccount;
	private BigDecimal			amount;
	private BigDecimal			beforeAmount;
	private BigDecimal			afterAmount;

}
