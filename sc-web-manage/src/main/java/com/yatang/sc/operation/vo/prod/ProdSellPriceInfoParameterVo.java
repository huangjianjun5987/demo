package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * 新增销售区间价格接收参数的实体类
 */
@Getter
@Setter
public class ProdSellPriceInfoParameterVo implements Serializable {
	@NotNull(groups = GroupOne.class, message = "{msg.notEmpty.message}")
	private Long						id;						// 销售区间价id
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private String						productId;					// 商品id
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private String						branchCompanyId;				// 分公司的id
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private String						branchCompanyName;				// 分公司名称
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	@Digits(integer = 8, fraction = 2, message = "{msg.bigDecimal.message}", groups = DefaultGroup.class)
	private BigDecimal					suggestPrice;				// 建议零售价
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private Integer						deliveryDay;				// 承诺最迟发货时间（天）
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	@Range(min = 1, max = Integer.MAX_VALUE, message = "{msg.range.message}", groups = DefaultGroup.class)
	private Integer						salesInsideNumber;			// 销售内装数
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	@Range(min = 1, max = Integer.MAX_VALUE, message = "{msg.range.message}", groups = DefaultGroup.class)
	private Integer						minNumber;					// 起定量

	@Valid
	private List<ProdSellSectionPriceVo>	sellSectionPrices;			// 区间价格信息


	private Integer maxNumber;                           			// 最大销售量

	private Integer preHarvestPinStatus;                        	// 是否先采后销   0 ：否   1： 是  默认：否
	@NotNull(groups = GroupOne.class, message = "{msg.notEmpty.message}")
	private Integer						auditStatus;				// 审核状态:1:已提交;2:已审核;3:已拒绝(默认2)

	private static final long			serialVersionUID	= 1L;

}