package com.yatang.sc.facade.dto.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SpAdrBasicDto implements Serializable {

	private static final long	serialVersionUID	= -514921684656672852L;

	private Integer				id;

	/**
	 * 供应商地点编号
	 * */
	private String				providerNo;

	/**
	 * 供应商地点名称
	 * */
	private String				providerName;

	/**
	 * 到货周期
	 * */
	private Integer				goodsArrivalCycle;

	/**
	 * 分公司ID
	 * */
	private String				orgId;

	/**
	 * 分公司名称
	 * */
	private String				orgName;

	/**
	 * 供应地点级别:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他
	 * */
	private Integer				grade;

	/**
	 * 供应商地点经营状态0:禁用:1:启用
	 * */
	private Integer				operaStatus;

	/**
	 * 账期: 0:周结；1：半月结；2：月结；
	 * */
	private Integer				settlementPeriod;
	
	/**
	 * 付款条件:1:票到七天,2:票到十五天,3:票到三十天，4：票到付款;
	 */
	private Integer				payCondition;

	/**
	 * 所属区域
	 * */
	private Integer				belongArea;

	/**
	 * 所属区域名字
	 */
	private String				belongAreaName;

	/**
	 * 供应商付款方式：0：网银，1：银行转账，2：现金，3：支票
	 * */
	private Integer				payType;

	/**
	 * 审核人姓名
	 * */
	private String				auditPerson;

	/**
	 * 审核时间
	 * */
	private Date				auditDate;

	private Integer				modifyId;

	private Integer				status;

	@JsonIgnore
	private SpAdrBasicDto 	modification;						// 基本信息
}