package com.yatang.sc.facade.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @描述: 列表页商品排序DTO.
 * @作者: yinyuxin
 * @创建时间: 2017年6月8日15:54:17
 * @版本: 1.0 .
 */
@Data
public class CategoryGoodsOrderDto implements Serializable {

	private static final long	serialVersionUID	= -4346369180134950129L;

	private Integer				pkId;											// pk主键

	private String				id;											// 商品编号

	private String				name;											// 商品名称

	private String				firstCategoryId;								// 一级分类id

	private String				secondCategoryId;								// 二级分类id

	private String				thirdCategoryId;								// 三级分类id

	private String				firstCategoryName;								// 一级分类名称

	private String				secondCategoryName;							// 二级分类名称

	private String				thirdCategoryName;								// 三级分类名称

	private Integer				orderNum;										// 商品排序号

	private Integer				newOrderNum;									// 商品新的排序号

	/********************************************** 以下字段用于传参 ***************************************/

	private Integer				pageNum;										// 页码

	private Integer				pageSize;										// 每页显示记录数
}