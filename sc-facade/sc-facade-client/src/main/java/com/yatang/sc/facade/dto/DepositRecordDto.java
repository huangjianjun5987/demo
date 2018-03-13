package com.yatang.sc.facade.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @描述: 保证金记录DTO
 * @作者: yipeng
 * @创建时间: 2017年05月23日12:50:55
 * @版本: 1.0 .
 */
@Data
public class DepositRecordDto implements java.io.Serializable {

	private Integer		id;
	private String		spId;
	private String		spNo;
	private String		companyName;
	private String		tradeNo;
	private String		payChannel;
	private String		paymentType;
	private String		payAccount;
	private BigDecimal	amount;
	private BigDecimal	beforeAmount;
	private BigDecimal	afterAmount;
	private Date		createdTime;

}
