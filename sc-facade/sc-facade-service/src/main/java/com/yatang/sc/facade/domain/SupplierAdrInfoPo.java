package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierAdrInfoPo implements Serializable {

	private static final long		serialVersionUID	= 8797337183444983610L;

	/**
	 * 供应商地点主表ID
	 */
	private Integer					id;

	/**
	 * 供应商主表ID
	 */
	private String					parentId;

	/**
	 * 基本信息ID
	 */
	private Integer					basicId;

	/**
	 * 联系信息ID
	 */
	private Integer					contId;

	/**
	 * 供应商地点状态:0：草稿, 1:待审核,2:已审核,3:已拒绝,4:修改中
	 */
	private Integer					status;

	/**
	 * 失败原因
	 */
	private String					failedReason;

	/**
	 * 审核人ID
	 */
	private String					auditUserId;

	/**
	 * 审核时间
	 */
	private Date					auditTime;

	private Date					createTime;

	private Date					modifyTime;

	private String					createUser;

	private String					modifyUser;

	private SpAdrBasicPo			spAdrBasic;

	private SpAdrContactPo			spAdrContact;

	private Set<SpAdrDeliveryPo>	spAdrDeliverys;

	/** 提交类型 0：保存草稿， 1：提交 */
	private Integer					commitType;

}