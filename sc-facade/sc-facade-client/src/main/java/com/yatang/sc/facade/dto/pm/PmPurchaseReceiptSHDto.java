package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述:封装收货单数据
 * @类名:PmPurchaseReceiptSHDto
 * @作者: lvheping
 * @创建时间: 2018/1/11 11:45
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class PmPurchaseReceiptSHDto implements Serializable {
	private static final long					serialVersionUID	= -4224809077992780454L;
	private String								purchaseReceiptNo;								// 收货单号
	private List<PurchaseConfirmOrderLinesDto>	orderLines;									// 商品详细信息
}
