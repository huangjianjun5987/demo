package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:供应商审核录入信息
 * @类名:SupplierAuditInfoVo
 * @作者: lvheping
 * @创建时间: 2017/5/25 9:51
 * @版本: v1.0
 */
@Getter
@Setter
public class SupplierAuditInfoVo implements Serializable {
	private static final long	serialVersionUID	= -3574143324526157404L;
	private String				id;											// 供应商ID
	private Integer				status;										// 供应商状态
	private Integer				settlementAccountType;						// 结算账户类型
	private Integer				settlementPeriod;							// 结算账期
	private BigDecimal			rebateRate;									// 返利
	private BigDecimal			guaranteeMoney;								// 保证金
	private Integer				storeContractDate;							// 商家合约时间
	private String				failedReason;								// 失败原因
	private String				auditUserId;									// 审核人id

}
