package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 商品详情页面vo
 * @作者: yinyuxin
 * @创建时间: 2017年7月19日17:16:40
 * @版本: 1.0 .
 */
@Getter
@Setter
public class ProductDetailsVo implements Serializable {

	private static final long serialVersionUID = -998888967983656364L;

	/***************************************** 商品基本信息 ***************************************************/

	// 商品主键ID
	private String id;

	// 商品编码
	private String productCode;

	// 国际码信息(单独set)
	private List<String> internationalCodes;

	// 一级商品分类名称
	private String firstLevelCategoryName;

	// 二级商品分类名称
	private String secondLevelCategoryName;

	// 三级商品分类名称
	private String thirdLevelCategoryName;

	// 第四级类别名称
	private String fourthLevelCategoryName;

	// 销售名称
	private String saleName;

	// 品牌名称(单独set)
	private String brandName;

	// 商品描述
	private String description;

	// 进项税率(不带百分号的数字)
	private BigDecimal inputTaxRate;

	// 销项税率(不带百分号的数字)
	private BigDecimal taxRate;

	// 承诺发货时间
	private Integer deliveryTime;

	// 建议出货价
	private BigDecimal guideShipmentPrice;

	// 建议零售价
	private BigDecimal price;

	// 指导采购价
	private BigDecimal guidePurchasePrice;

	// 供应链状态：1-草稿 2-生效 3-暂停使用（全国性下架） 4-停止使用
	private Integer supplyChainStatus;

	/***************************************** 货运信息 ***************************************************/

	// 产地
	private String producePlace;

	// TODO 规格
	private String packingSpecifications;

	// 销售单位
	private String minUnit;

	// 重量
	private String weight;

	// 长
	private Integer length;

	// 宽
	private Integer width;

	// 高
	private Integer height;

	// 横排商品数
	private Integer horizontalProductNum;

	// 竖排商品数
	private Integer verticalProductNum;

	// 高排商品数
	private Integer heightProductNum;

	// 保质期
	private Integer qualityGuaranteePeriod;

	// 保质期单位:1年 2月 3天
	private String guaranteePeriodUnit;

	// 储存条件
	private String	storageCondition;
	/***************************************** 商品图片信息 ***************************************************/

	// 商品主图地址

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String	mainImage;

	// 商品图片地址列表
	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private List<String> imgUrls;

	// ueditor 商品详情
	private String html;

	// 搜索关键字
	private List<String> keyWords;

}
