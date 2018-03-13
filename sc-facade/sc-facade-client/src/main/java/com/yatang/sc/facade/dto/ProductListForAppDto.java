package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: app端dubbo服务DTO（查询商品列表返回字段）
 * @作者: yinyuxin
 * @创建时间: 2017年7月11日11:17:36
 * @版本: 1.0 .
 */
@Setter
@Getter
public class ProductListForAppDto implements Serializable {

	private static final long	serialVersionUID	= 3171999542193218666L;

	/*
	 * 商品ID
	 */
	private String				id;
	/*
	 * 商品名称
	 */
	private String				name;
	/*
	 * 商品销售名称
	 */
	private String				saleName;
	/*
	 * 上下架状态 0上架，1下架
	 */
	private Integer				onshelfStatus;
	/*
	 * 图片信息
	 */
	private String				mainImage;
	/**
	 * 销售价格
	 */
	private BigDecimal			price;
	/*
	 * 成交数量
	 */
	private Integer				saleNum;
	/*
	 * 好评率
	 */
	private String				goodTate;
}
