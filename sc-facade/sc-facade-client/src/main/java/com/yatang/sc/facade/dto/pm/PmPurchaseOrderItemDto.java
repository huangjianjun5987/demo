package com.yatang.sc.facade.dto.pm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @描述: 采购单商品表Dto类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/24 17:59
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class PmPurchaseOrderItemDto implements Serializable {
	private static final long	serialVersionUID	= -6284210448825913199L;

	private Long				id;

	private String				purchaseOrderId;							// 采购单号

	private String				productId;									// 商品id

	private String				prodPurchaseId;								// 采购关系主键
																			// new

	private String				productCode;								// 商品编号

	private String				productName;								// 商品名称

	private String				internationalCode;							// 条码(国际码

	private String				packingSpecifications;						// 规格

	private String				producePlace;								// 产地

	private Integer				purchaseInsideNumber;						// 采购内装数(默认箱规)

	private String				unitExplanation;							// 单位

	private BigDecimal			inputTaxRate;								// 税率,进项税率

	private BigDecimal			purchasePrice;								// 采购价格（含税）

	private Integer				purchaseNumber;								// 采购数量,必须为采购内装数的倍数

	private BigDecimal			totalAmount;								// 采购金额，含税
																			// new

	private Integer				receivedNumber;								// '已收货数量,收货数量<=采购数量

	private Date				createTime;									// 创建时间

	private Date				modifyTime;									// 修改时间

	private String				createUserId;								// 创建者id

	private String				modifyUserId;								// 修改者id

	private BigDecimal			taxAmount;									// 税额
																			// new

	private BigDecimal			totalWithoutTaxAmount;						// 不含税采购金额
																			// new

	private Integer				isValid;									// 是否有效:0,无效,1:有效

	private String				inValidateReason;							// 失败原因

	private String               productBrandId;                            //商品品牌id

	private String               productBrandName;                          //商品品牌名称

	private Integer				availableInventory;							// 可用库存，计算方式=现有库存-销售保留-调拨预留-退货预留

	private String				groups;										// 部类

	private String				dept;										// 大类

	private String				classs;										// 中类

	private String				subclass;									// 小类

}