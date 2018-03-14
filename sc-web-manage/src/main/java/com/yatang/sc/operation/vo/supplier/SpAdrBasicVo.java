package com.yatang.sc.operation.vo.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SpAdrBasicVo implements Serializable {

	private static final long	serialVersionUID	= -514921684656672852L;

	@NotNull(groups = { GroupOne.class }, message = "{msg.notEmpty.message}")
	private Integer				id;

	/**
	 * 供应商地点编号
	 */
	private String				providerNo;

	/**
	 * 供应商地点名称
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	@Length(max = 150, groups = { GroupOne.class, GroupTwo.class }, message = "{msg.length.message}")
	private String				providerName;

	/**
	 * 到货周期
	 */
	@NotNull(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	@Max(value = 30, groups = { GroupOne.class, GroupTwo.class }, message = "{msg.max.message}")
	private Integer				goodsArrivalCycle;

	/**
	 * 分公司ID
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	private String				orgId;

	private String				orgName;

	/**
	 * 供应地点级别:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他
	 */
	@NotNull(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	private Integer				grade;

	/**
	 * 供应商地点经营状态0:禁用:1:启用
	 */
	@NotNull(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	private Integer				operaStatus;

	/**
	 * 账期: 0:周结；1：半月结；2：月结；
	 */
	@NotNull(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	private Integer				settlementPeriod;

	/**
	 * 付款条件:1:票到七天,2:票到十五天,3:票到三十天，4：票到付款;
	 */
	@NotNull(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	private Integer				payCondition;

	/**
	 * 所属区域
	 */
	@NotNull(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	private Integer				belongArea;

	/**
	 * 所属区域名字
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	private String				belongAreaName;

	/**
	 * 供应商付款方式：0：网银，1：银行转账，2：现金，3：支票
	 */
	@NotNull(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	private Integer				payType;

	/**
	 * 审核人姓名
	 */
	private String				auditPerson;

	/**
	 * 审核时间
	 */
	private Date				auditDate;

	private Integer				modifyId;

	/**
	 * 状态：0：原记录, 1：修改记录
	 */
	@NotNull(groups = { GroupOne.class }, message = "{msg.notEmpty.message}")
	private Integer				status;

	@JsonIgnore
	private SpAdrBasicVo		modification;								// 基本信息
}