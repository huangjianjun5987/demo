package com.yatang.sc.app.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @描述:商品价格信息
 * @类名:GoodsPriceInfoDto
 * @作者: lvheping
 * @创建时间: 2017/5/22 20:30
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class GoodsPriceInfoVo implements Serializable {


	private static final long serialVersionUID = 5933562937812209775L;
	private Integer				id;											// pk

	private BigDecimal			price1;										// 阶梯价格1

	private Integer				startNumber1;								// 阶梯价格起始商品数量1

	private Integer				endNumber1;									// 阶梯价格结束商品数量1

	private Integer				pricingId;									// 定价ID

	private BigDecimal			price2;										// 阶梯价格2

	private Integer				startNumber2;								// 阶梯价格起始商品数量2

	private Integer				endNumber2;									// 阶梯价格结束商品数量2

	private BigDecimal			price3;										// 阶梯价格3

	private Integer				startNumber3;								// 阶梯价格起始商品数量3

	private Integer				endNumber3;									// 阶梯价格结束商品数量3

	private Integer				state;										// 0表示为销售价格，1表示采购价格

	private String				cityCodes;									// 获取到拼接的城市编码

	private String				provinceCodes;								// 获取到拼接到的省份编码
	private String				cityNames;									// 获取到拼接到的城市名称

}
