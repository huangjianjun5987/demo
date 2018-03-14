package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 主数据
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月31日
 */
@Getter
@Setter
public class CategoryFromSolrVo implements Serializable {

	private static final long serialVersionUID = -5768069652255405174L;

	/** 分类名称 */
	private String name;

	/** 选中查询时的dimension节点id */
	// private String dimValId;

	/** 分类id */

	private String categoryId;

	/** 图片url */
	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String iconImgUrl;

	private Integer sortOrder;

	/** 0显示，1隐藏 */
	private Integer displayStatus;

	private List<CategoryFromSolrVo> childCategories;
}
