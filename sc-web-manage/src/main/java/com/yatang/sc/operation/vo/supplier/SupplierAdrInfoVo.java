package com.yatang.sc.operation.vo.supplier;

import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class SupplierAdrInfoVo implements Serializable {
	
	private static final long serialVersionUID = 8797337183444983610L;

	/**
	 * 供应商地点主表ID
	 * */
	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private Integer id;

	/**
	 * 供应商主表ID
	 * */
	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
    private String parentId;

	/**
	 * 基本信息ID
	 * */
    private Integer basicId;

	/**
	 * 联系信息ID
	 * */
    private Integer contId;

	/**
	 * 供应商地点状态:0：草稿, 1:待审核,2:已审核,3:已拒绝,4:修改中
	 * */
	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private Integer status;

	/**
	 * 失败原因
	 * */
    private String failedReason;

	/**
	 * 审核人ID
	 * */
    private String auditUserId;

	/**
	 * 审核时间
	 * */
    private Date auditTime;

    private Date createTime;

    private Date modifyTime;

    private String createUser;

    private String modifyUser;

	@Valid
	private SpAdrBasicVo spAdrBasic;

	@Valid
	private SpAdrContactVo spAdrContact;

	@Valid
	private Set<SpAdrDeliveryVo> spAdrDeliverys;

	/** 提交类型 0：保存草稿， 1：提交 */
	@NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private Integer	commitType;
    
}