package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 采购单值清单方式查询商品vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年8月8日
 */
@Getter
@Setter
public class ProductForSelectVo implements Serializable {

	private static final long serialVersionUID = 5286437377362662044L;

	/**
	 * 商品id
	 */
	private String productId;

	/**
	 * 商品编码
	 */
	private String productCode;

	/**
	 * 销售名称(用的是主数据saleName字段的值)
	 */
	private String productName;
}
