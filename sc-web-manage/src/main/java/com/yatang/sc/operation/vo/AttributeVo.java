package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 商品参数vo
 * @作者: yinyuxin
 * @创建时间: 2017年6月2日17:06:12
 * @版本: 1.0 .
 */
@Getter
@Setter
public class AttributeVo implements Serializable {

	private static final long	serialVersionUID	= 6738852251376734443L;

	protected String			id;

	protected String			name;

	// protected String firstLevelCategoryId;
	// protected String secondLevelCategoryId;
	// protected String thirdLevelCategoryId;
	// protected String firstLevelCategoryName;
	// protected String secondLevelCategoryName;
	// protected String thirdLevelCategoryName;
	// protected Integer sortOrder;
	// protected Integer required;
	// protected Integer chooseType;
	// protected Byte state;
	// protected List<AttributeValueDto> options;
}
