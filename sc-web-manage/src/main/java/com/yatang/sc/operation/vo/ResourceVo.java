package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @描述:
 * @作者 libin
 * @日期 2017年6月15日 下午8:35:06
 */
@Getter
@Setter
public class ResourceVo implements Serializable {
	private static final long	serialVersionUID	= 4717390000304698618L;

	private Long				id;

	private String				permission;

	private String				name;

	private Long				parentId;

	private String				parentIds;

	private Boolean				available;

	private Date				createTime;

	private Date				updateTime;

	private List<ResourceVo>	subMenus;									// 二级菜单

}
