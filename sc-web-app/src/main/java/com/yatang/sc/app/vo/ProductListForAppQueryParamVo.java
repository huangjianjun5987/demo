package com.yatang.sc.app.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: app端dubbo服务vo（查询商品列表所需参数）
 * @作者: yinyuxin
 * @创建时间: 2017年7月10日20:52:30
 * @版本: 1.0 .
 */
@Getter
@Setter
public class ProductListForAppQueryParamVo implements Serializable{

	private static final long serialVersionUID = -1287320074973449086L;
	/*
	 * 商品一级分类ID
	 */
	private String firstCategoryId;
	/*
	 * 商品二级分类ID
	 */
	private String secondCategoryId;
	/*
	 * 商品三级分类ID
	 */
	private String thirdCategoryId;
	/*
	 * 上下架状态  0 表示上架(在售)  1 表示(待售)下架
	 */
	private String shelfStatus;
	/*
	 * 商品名称
	 */
	private String name;
	/*
	 * 城市编码（用于获取商品对应区域的阶梯价格）
	 */
	private String cityCode;	
	
	//TODO 等待主数据根据商品参数查询商品列表接口

	/*
	 * 每页显示记录数
	 */
	private Integer pageSize;
	/*
	 * 页码
	 */
	private Integer pageNum;
}
