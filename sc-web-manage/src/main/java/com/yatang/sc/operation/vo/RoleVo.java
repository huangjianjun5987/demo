package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @描述 角色传输对象
 * @作者 libin
 * @创建时间 2017年6月27日-下午5:14:11
 * @版本
 * 
 */
@Data
public class RoleVo implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8188661117645382882L;
	// 角色id
	private Integer				roleId;
	// 角色名
	private String				roleName;
	// 角色编码
	private String				roleCode;
	// 角色状态 1:启用 0：未启用
	private int					roleStatus;
	// 备注
	private String				roleRemark;
	// 创建时间
	private Date				roleCreateTime;
	// 创建人
	private Integer				roleCreateId;
	// 修改时间
	private Date				roleUpdateTime;
	// 最后一次修改人
	private Integer				roleUpdateId;
	// 角色类型 0=操作类型 1=审批类型
	private Integer				roleType;
}
