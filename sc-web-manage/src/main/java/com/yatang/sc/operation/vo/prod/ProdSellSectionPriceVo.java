package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.yatang.sc.validgroup.DefaultGroup;

import com.yatang.sc.validgroup.GroupOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class ProdSellSectionPriceVo implements Serializable {
	private Integer				id;						// 区间价格id
	@Digits(integer = 8, fraction = 2, message = "{msg.bigDecimal.message}", groups = DefaultGroup.class)
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private BigDecimal			price;						// 售价
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	@Range(min =1 ,max = Integer.MAX_VALUE, message = "{msg.range.message}", groups = DefaultGroup.class)
	private Integer				startNumber;				// 开始区间
	@Range(max = Integer.MAX_VALUE, message = "{msg.range.message}", groups = DefaultGroup.class)
	private Integer				endNumber;					// 结束区间

	private Integer				sellPriceId;				// 定价id
	private Integer				deleteStatus;				// 0为未删除状态1为删除状态
	private BigDecimal			percentage;					// 毛利率 （不持久化） yinyuxin

	private static final long	serialVersionUID	= 1L;

}