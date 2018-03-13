package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellSectionPricePo implements Serializable {
	private Long				id;							// 区间价格id

	private BigDecimal			price;						// 区间价格

	private Integer				startNumber;				// 开始区间

	private Integer				endNumber;					// 结束区间

	private Long				sellPriceId;				// 定价id

	private Integer				deleteStatus;				// 0为未删除状态1为删除状态

	private BigDecimal          percentage;                  // 毛利率 yinyuxin（不持久化）

	private static final long	serialVersionUID	= 1L;

}