package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @描述: 商品分类信息DTO（带图片）.
 * @作者: yinyuxin
 * @创建时间: 2017年7月7日17:50:57
 * @版本: 1.0 .
 */
@Data
public class CategoryWithIconDto implements Serializable {

	private static final long			serialVersionUID	= -3754737759723487805L;
	/**
	 * 主键ID
	 */
	private String						id;
	/**
	 * 分类名称
	 */
	private String						categoryName;
	/**
	 * 级别
	 */
	private Integer						level;
	/**
	 * 分类状态
	 */
	private String						status;
	/**
	 * 父分类
	 */
	private String						parentCategoryId;

	/**
	 * 排序号
	 */
	private Integer						sortOrder;

	/**
	 * 是否隐藏（0 显示 1 隐藏）
	 */
	private Integer						displayStatus;

	/**
	 * 图片连接
	 */
	private String						iconUrl;

	private List<CategoryWithIconDto>	childCategories;

}
