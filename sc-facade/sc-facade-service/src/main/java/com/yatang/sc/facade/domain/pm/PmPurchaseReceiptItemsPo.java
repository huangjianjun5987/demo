package com.yatang.sc.facade.domain.pm;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 收货单商品清单po
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月26日
 */
@Setter
@Getter
@ToString
public class PmPurchaseReceiptItemsPo implements Serializable {

	private static final long serialVersionUID = 7874072853381797974L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 收货单ID
	 */
	private String purchaseReceiptId;

	/**
	 * 采购单ID
	 */
	private String purchaseOrderId;

	/**
	 * 采购单商品明细ID
	 */
	private String purchaseOrderItemsId;

	/**
	 * 供应商出库数量,默认等于采购数量,预留与供应商系统对接预发货通知
	 */
	private Integer deliveryNumber;

	/**
	 * 已收货数量,收货数量<=出货数量
	 */
	private Integer receivedNumber;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 创建人
	 */
	private String createUserId;

	/**
	 * 修改人
	 */
	private String modifyUserId;

}