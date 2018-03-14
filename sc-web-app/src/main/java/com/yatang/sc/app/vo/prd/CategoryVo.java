package com.yatang.sc.app.vo.prd;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.app.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.app.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 商品分类对象vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年8月1日
 */
@Setter
@Getter
public class CategoryVo implements Serializable {

	private static final long serialVersionUID = 7931797983921039905L;

	/**
	 * 分类名称
	 */
	private String name;

	/**
	 * 分类id
	 */
	private String categoryId;

	/**
	 * 图片url
	 */

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String iconImgUrl;

	/**
	 * 选中查询时的dimension节点id
	 */
	private String dimValId;

	private List<CategoryVo> childCategories;
}
