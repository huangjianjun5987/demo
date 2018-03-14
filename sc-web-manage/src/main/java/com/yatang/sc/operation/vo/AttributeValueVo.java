package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 商品参数值vo
 * @作者: yinyuxin
 * @创建时间: 2017年6月2日17:06:12
 * @版本: 1.0 .
 */
@Getter
@Setter
public class AttributeValueVo implements Serializable {

	private static final long	serialVersionUID	= -8192059402546508046L;

	private String				value;
	private String				id;
	// private Integer sortOrder;
	private String				name;

}
