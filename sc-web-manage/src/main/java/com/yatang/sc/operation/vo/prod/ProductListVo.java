package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @描述: 商品列表vo
 * @类名: ProductListVo
 * @作者: yinyuxin
 * @创建时间: 2017年7月25日15:02:54
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class ProductListVo implements Serializable {

	private static final long serialVersionUID = 7596895825714442621L;

	/** 商品id */
	private String productId;

	/** 商品编码 */
	private String productCode;

	/** 缩略图url */

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String thumbnailImage;

	/** 销售名称 */
	private String saleName;

	/** 一级商品分类名称 */
	private String firstLevelCategoryName;

	/** 二级商品分类名称 */
	private String secondLevelCategoryName;

	/** 三级商品分类名称 */
	private String thirdLevelCategoryName;

	/** 四级商品分类名称 */
	private String fourthLevelCategoryName;

	// 品牌名
	private String brand;

	/** 供应链状态： */
	private String supplyChainStatus;

}
