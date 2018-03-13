package com.yatang.sc.facade.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @描述:接受在售商品查询条件DTO,复制的主数据dubbo服务里的dto
 * @类名: ProductQueryVo
 * @作者: zby
 * @创建时间: 2017年6月2日 下午4:00:50
 * @版本: v1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoQueryDto implements Serializable {

	private static final long	serialVersionUID	= 2474966618052022779L;

	/** 商品名称 */
	private String				productName;

	/** 商品编号 */
	private String				productNo;

	/** 商品一级分类 */
	private String				firstCategoryId;

	/** 商品二级分类 */
	private String				secondCategoryId;

	/** 商品三级分类 */
	private String				thirdCategoryId;

	/** 上下架开始时间 */
	private String				startTime;

	/** 上下架结束时间 */
	private String				endTime;

	/** 上下架状态 0 表示上架(在售) 1 表示(待售)下架 */
	private String				shelfStatus;

	/** 上下架排序 true 升序 false 降序 */
	private String				sortBoolean;

	/** 一页多少条 */
	private Integer				pageSize;

	/** 页数 */
	private Integer				pageNum;

}
