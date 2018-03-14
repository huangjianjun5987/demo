package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 收货单详情vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月26日
 */
@Setter
@Getter
@ToString
public class PmPurchaseReceiptDetailsVo implements Serializable {

	private static final long serialVersionUID = 9056304308910392060L;

	/**
	 * 收货单基础信息
	 */
	private PmPurchaseReceiptVo pmPurchaseReceipt;

	/**
	 * 收货单明细
	 */
	private List<PmPurchaseReceiptItemVo> receiptPruducts;

}
