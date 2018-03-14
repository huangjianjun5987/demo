package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 收货单明细vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月26日
 */
@Setter
@Getter
@ToString
public class PmPurchaseReceiptItemVo implements Serializable {

	private static final long serialVersionUID = 8103342484990526714L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 商品编号
	 */
	private String productCode;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品条码
	 */
	private String internationalCode;

	/**
	 * 规格
	 */
	private String packingSpecifications;

	/**
	 * 产地
	 */
	private String producePlace;

	/**
	 * 采购内装数
	 */
	private Integer purchaseInsideNumber;

	/**
	 * 单位
	 */
	private String unitExplanation;

	/**
	 * 采购数量
	 */
	private Integer purchaseNumber;

	/**
	 * 供应商出库数量
	 */
	private Integer deliveryNumber;

	/**
	 * 收货数量
	 */
	private Integer receivedNumber;

	/********************************************************/

	/**
	 * 采购价格（含税）
	 */
	private BigDecimal purchasePrice;

	/**
	 * 收货金额（含税，值为采购价格x收货数量）
	 */
	private BigDecimal receiptPrice;
}
