package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 商品分类接收参数vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月28日
 */
@Setter
@Getter
public class CategoryParamVo implements Serializable {

	private static final long serialVersionUID = 5903495142747856096L;

	private String id; // 分类id

	private String categoryName; // 分类名称

	private Integer level; // 分类级别

	private String status; // 分类状态

	private String parentCategoryId; // 父分类Id

	private Integer sortOrder; // 排序号

	private Integer newSortOrder; // 新的排序号

	private Integer displayStatus; // 0：显示 1：隐藏

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String iconImg; // 图片链接
}
