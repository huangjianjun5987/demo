package com.yatang.sc.facade.domain;

import com.yatang.sc.facade.common.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 接收供应商多条件查询信息
 * @作者: tankejia
 * @创建时间: 2017/6/12-14:58 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class MultiQuerySupplierPo extends BasePo implements Serializable {

	private static final long	serialVersionUID	= 8307397949885981694L;

	/**
	 * 公司名称
	 * */
	private String				companyName;
	/**
	 * 供应商注册号
	 * */
	private String				spRegNo;
	/**
	 * 联系人
	 * */
	private String				name;
	/**
	 * 联系人手机
	 * */
	private String				phone;
	/**
	 * 供应商编号
	 * */
	private String				spNo;
	/**
	 * 入驻时间最小值
	 * */
	private Date				minSettledDate;
	/**
	 * 入驻时间最大值
	 * */
	private Date				maxSettledDate;
	/**
	 * 供应商状态
	 * */
	private Integer				status;
	/**
	 * 账期最小值
	 * */
	private Integer				minSettlementPeriod;
	/**
	 * 账期最大值
	 * */
	private Integer				maxSettlementPeriod;
	/**
	 * 结算账户类型
	 * */
	private Integer				settlementAccountType;
	/**
	 * 返利最小值
	 * */
	private BigDecimal			minRebateRate;
	/**
	 * 返利最大值
	 * */
	private BigDecimal			maxRebateRate;
	/**
	 * 查询类型（1：查询供应商管理列表，0：查询供应商修改资料申请列表）
	 * */
	private Integer				type;

}
