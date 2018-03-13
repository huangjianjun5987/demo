package com.yatang.sc.facade.dto.system;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.yatang.sc.facade.common.BaseDto;

/**
 * @描述: 角色Dto.
 * @作者: huangjianjun
 * @创建时间: 2017年6月9日 下午1:58:16 .
 */
public class RoleDto extends BaseDto implements Serializable {

	private static final long	serialVersionUID	= -8258163586456215391L;

	private Long				id;

	private String				role;

	private String				description;

	private Boolean				available;

	private Date				createTime;

	private Date				updateTime;

	private Set<Long>			resourceIds;									// 拥有的资源

    private Integer            roleType;

	/**
	 * 角色编码，保留字段，用于区分地域权限
	 */
	private String              roleCode;

	private String              createUser;

	private String              updateUser;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set<Long> getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(Set<Long> resourceIds) {
		this.resourceIds = resourceIds;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}


	@Override
	public String toString() {
		return "RolePo [id=" + id + ", role=" + role + ", description=" + description + ", available=" + available
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", roleType=" + roleType + ", roleCode=" + roleCode + "]";
	}

}