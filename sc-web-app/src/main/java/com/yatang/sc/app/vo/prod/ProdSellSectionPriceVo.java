package com.yatang.sc.app.vo.prod;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdSellSectionPriceVo implements Serializable {
	private Integer				id;						// 区间价格id
	private BigDecimal			price;						// 区间价格
	private Integer				startNumber;				// 开始区间

	private Integer				endNumber;					// 结束区间

	private Integer				sellPriceId;				// 定价id
	private Integer				deleteStatus;				// 0为未删除状态1为删除状态

	private static final long	serialVersionUID	= 1L;

}