package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 角色资源Po.
 * @作者: huangjianjun
 * @创建时间: 2017年6月9日 下午1:58:00 .
 */
@Getter
@Setter
public class RoleResourcePo implements Serializable {

	private static final long	serialVersionUID	= 1733860376826522157L;

	private Integer				id;

	private Long				roleId;

	private Long				resourceId;

}