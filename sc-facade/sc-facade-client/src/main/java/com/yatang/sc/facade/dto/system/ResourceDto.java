package com.yatang.sc.facade.dto.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @描述: 资源DtO.
 * @作者: huangjianjun
 * @创建时间: 2017年6月9日 下午1:58:42 .
 */
public class ResourceDto implements Serializable {

	private static final long	serialVersionUID	= 2615272239888491561L;

	private Long				id;

	private String				permissionCode;

	private String				name;

	private ResourceType		type;

	private Long				parentId;

	private String				parentIds;

	private Boolean				available;

	private Date				createTime;

	private Date				updateTime;

	private String              menuUrl;

	private String              displayName;

	private String              description;

	private Integer                 sequence;

	private List<ResourceDto>   subRes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
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

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<ResourceDto> getSubRes() {
		return subRes;
	}

	public void setSubRes(List<ResourceDto> subRes) {
		this.subRes = subRes;
	}

	public enum ResourceType {
		menu("菜单"), page("页面"), button("按钮");

		private final String	info;



		ResourceType(String info) {
			this.info = info;
		}



		public String getInfo() {
			return info;
		}
	}


	@Override
	public String toString() {
		return "ResourceDto [id=" + id + ", permissionCode=" + permissionCode + ", name=" + name + ", type=" + type.getInfo()
				+ ", parentId=" + parentId + ", parentIds=" + parentIds + ", available=" + available + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", menuUrl=" +menuUrl+ ", displayName=" +displayName+
				", description=" +description+ ", sequence=" +sequence+ ", subRes=" + subRes +  "]";
	}
}