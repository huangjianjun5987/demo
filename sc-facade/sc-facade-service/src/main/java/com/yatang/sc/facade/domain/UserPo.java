package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 用户PO.
 * @作者: huangjianjun
 * @创建时间: 2017年6月9日 下午1:59:04 .
 */
@Getter
@Setter
public class UserPo implements Serializable {

	private static final long	serialVersionUID	= 5098089285436729511L;

	private Long				id;

	private String				userName;

	private String				password;
	
	private String 				spId;											//供应商ID
	
	private Integer 			type;											//用户类型((1:管理员2:供应商))

	private String				salt;

	private Boolean				locked;

	private String				fullName;

	private String				position;

	private String				mobile;

	private String				email;

	private Date				createTime;

	private Date				updateTime;
	
	private String				createUser;
	
	private String				updateUser;

	private Set<Long>			roleIds;									// 拥有的角色列表



	public String getCredentialsSalt() {
		return userName + salt;
	}



	@Override
	public String toString() {
		return "UserPo [id=" + id + ", userName=" + userName + ", password=" + password + ", salt=" + salt
				+ ", locked=" + locked + ", fullName=" + fullName + ", position=" + position + ", mobile=" + mobile
				+ ", email=" + email + ", createTime=" + createTime + ", updateTime=" + updateTime + ", roleIds="
				+ getRoleIds() + "]";
	}

}