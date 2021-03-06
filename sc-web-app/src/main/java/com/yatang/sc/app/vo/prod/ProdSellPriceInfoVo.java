package com.yatang.sc.app.vo.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdSellPriceInfoVo implements Serializable {
	private Long						id;							// 销售区间价id

	private String						productId;					// 商品id

	private BigDecimal					lowestPrice;				// 销售区间最低价

	private Date						createTime;					// 创建时间

	private Date						modifyTime;					// 停用或伪删除时间

	private Integer						status;						// 状态0为失效状态1为正常使用状态

	private String						branchCompanyId;			// 分公司id
	private String						branchCompanyName;				// 分公司名字

	private BigDecimal					suggestPrice;				// 建议零售价

	private Integer						deliveryDay;				// 承诺最迟发货时间（天）

	private Integer						salesInsideNumber;			// 销售内装数

	private Integer						minNumber;					// 最小销售单位
	private Integer						deleteStatus;				// 0为未删除状态1为删除状态
	private String						modifyUserId;				// 修改或删除操作人员id
	private String						createUserId;				// 创建价格人员id

	/**
	 * 最大销售量
	 */
	private Integer maxNumber;

	/**
	 * 是否先采后销   0 ：否   1： 是
	 */
	private  Integer preHarvestPinStatus;



	private List<ProdSellSectionPriceVo>	sellSectionPrices;			// 区间价格信息

	private static final long			serialVersionUID	= 1L;

}