package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 收货单详情
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月26日
 */
@Setter
@Getter
@ToString
public class PmPurchaseReceiptDetailDto implements Serializable {

	private static final long serialVersionUID = -3473792620486598534L;

	/**
	 * 收货单基础信息
	 */
	private PmPurchaseReceiptDto pmPurchaseReceipt;

	/**
	 * 收货单明细
	 */
	private List<PmPurchaseReceiptItemsDto> receiptPruducts;


}
