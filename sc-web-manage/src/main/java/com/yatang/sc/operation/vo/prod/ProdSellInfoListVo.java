package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;

import com.yatang.sc.facade.common.PageResult;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:价格和商品信息
 * @类名:QueryGoodsPriceInfoVo
 * @作者: lvheping
 * @创建时间: 2017/5/26 10:00
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdSellInfoListVo implements Serializable {
	private static final long			serialVersionUID	= 2571089697989284916L;
	private PageResult  sellPriceInfoVos;							// 定价信息

}
