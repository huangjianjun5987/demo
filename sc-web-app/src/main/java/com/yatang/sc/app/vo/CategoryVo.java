package com.yatang.sc.app.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.app.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.app.web.jackson.ImageUrlSerializer;

import lombok.Data;

/**
 * @描述: 商品分类vo(app)
 * @作者: yinyuxin
 * @创建时间: 2017年7月8日16:15:53
 * @版本: 1.0 .
 */
@Data
public class CategoryVo implements Serializable {

	private static final long serialVersionUID = 6399214271493710441L;

	private String id; // 分类id

	private String categoryName; // 分类名称

	private Integer level; // 分类级别（暂时都为null）

	private String status; // 分类状态

	private String parentCategoryId; // 父分类Id

	private Integer sortOrder; // 排序号

	private Integer newSortOrder; // 新的排序号

	private Integer displayStatus; // 0：显示 1：隐藏

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String iconUrl; // 分类图片

	private List<CategoryVo> childCategories; // 子分类列表



	public String getParentCategoryId() {

		return ("".equals(parentCategoryId)) ? null : parentCategoryId;
	}

}
