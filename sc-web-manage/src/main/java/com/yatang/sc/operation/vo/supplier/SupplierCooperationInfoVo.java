package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述: 供应商合同信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierCooperationInfoVo implements Serializable {

	private static final long			serialVersionUID	= -6101341399429690664L;

	private String                		spId;										// 供应商主表ID

	private Integer						id;											// 编号

	private Date						settledDate;								// 入驻时间

	private Date						startDate;									// 合同开始时间

	private Date						endDate;									// 合同结束时间

	private Integer						settlementPeriod;							// 结算账期

	private Integer						storeContractDate;							// 商家合约时间

	private Integer						settlementAccountType;						// 结算账户类型0：雅堂金融账户,1;公司银行账户

	private BigDecimal 					rebateRate;									// 返利

	private BigDecimal					guaranteeMoney;								// 保证金

	private Integer						modifyId;									// 修改信息ID
	@JsonIgnore
	private SupplierCooperationInfoVo modification;								// 供应商合同信息

}