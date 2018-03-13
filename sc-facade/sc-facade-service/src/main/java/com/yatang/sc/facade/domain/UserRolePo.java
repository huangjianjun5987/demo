package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 用户角色PO.
 * @作者: huangjianjun
 * @创建时间: 2017年6月9日 下午1:57:26 .
 */
@Getter
@Setter
public class UserRolePo implements Serializable {

	private static final long	serialVersionUID	= -218381753736529432L;

	private Integer				id;

	private Long				userId;

	private Long				roleId;

}