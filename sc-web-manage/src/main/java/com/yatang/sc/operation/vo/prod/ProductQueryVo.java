package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yatang.sc.operation.common.BaseVo;
import com.yatang.sc.validgroup.GroupOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @描述:查询商品清单参数vo
 * @类名: ProductQueryVo
 * @作者: yinyuxin
 * @创建时间: 2017年7月19日14:37:58
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class ProductQueryVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = -887982276232886322L;

	/** 商品名称 */
	private String productName;

	/** 商品编码 */
	private String productCode;

	/** 商品条码 */
	private String internationalCode;

	/** 商品品牌名 */
	private String brand;

	/** 一级商品分类Id */
	private String firstLevelCategoryId;

	/** 二级商品分类id */
	private String secondLevelCategoryId;

	/** 三级商品分类id */
	private String thirdLevelCategoryId;

	/** 四级商品分类id */
	private String fourthLevelCategoryId;

	/** 供应商信息 （采购关系） 格式：供应商地点id-状态 */
	private String supplierInfo;

	/** 销售信息 （销售关系）格式：经营子公司id-状态 */
	private String salesInfo;

	/** brand|0,dim_flatCategory|0,productCode|0,roductCode|1; 0升序，1降序 */
	private String sort;

	// 商品状态（1-草稿 2-生效 3-暂停使用（全国性下架） 4-停止使用）
	@NotNull(message = "{msg.notNull.message}", groups = { GroupOne.class })
	private Integer supplyChainStatus;

	// 商品ID集合
	@NotEmpty(message = "{msg.notEmpty.message}", groups = { GroupOne.class })
	private List<String> ids;

	/** 查询关键字：商品编号和商品名称 */
	private String teamText;

	/** 经销商品 0 是 1 否 */
	private String distributionStatus;

}
