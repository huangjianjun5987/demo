package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;

/**
 * @描述: 商品分类vo
 * @作者: yinyuxin
 * @创建时间: 2017年6月2日17:06:12
 * @版本: 1.0 .
 */
public class CategoryVo implements Serializable {

	private static final long serialVersionUID = 1860538254109757108L;

	private String id; // 分类id

	private String categoryName; // 分类名称

	private Integer level; // 分类级别

	private String status; // 分类状态

	private String parentCategoryId; // 父分类Id

	private Integer sortOrder; // 排序号

	private Integer displayStatus; // 0：显示 1：隐藏

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String iconImg;// 图标地址

	private List<CategoryVo> childCategories; // 子分类列表



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	public Integer getLevel() {
		return level;
	}



	public void setLevel(Integer level) {
		this.level = level;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getParentCategoryId() {
		return parentCategoryId;
	}



	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}



	public Integer getSortOrder() {
		return sortOrder;
	}



	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}



	public Integer getDisplayStatus() {
		return displayStatus;
	}



	public void setDisplayStatus(Integer displayStatus) {
		this.displayStatus = displayStatus;
	}



	public String getIconImg() {
		return iconImg;
	}



	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}



	public List<CategoryVo> getChildCategories() {
		return childCategories;
	}



	public void setChildCategories(List<CategoryVo> childCategories) {
		this.childCategories = childCategories;
	}

}
