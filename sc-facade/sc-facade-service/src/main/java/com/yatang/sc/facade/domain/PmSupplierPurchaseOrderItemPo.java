package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述: 供应商采购单列表po类
 * @类名: PmSupplierPurchaseOrderItemPo
 * @作者: kangdong
 * @创建时间: 2018/1/9 17:11
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class PmSupplierPurchaseOrderItemPo implements Serializable {


	private static final long serialVersionUID = -7981739302413863280L;
	private Long				id;											// pk

	private Integer				adrType;									// 收货地点类型:0:仓库;1:门店

	private String				adrTypeCode;								// 收货地点编号

	private String				adrTypeName;								// 收货地点名称

	private String				spAdrNo;									// 供应商地点编码

	private String				spAdrName;									// 供应商地点名称

	private String				purchaseOrderNo;							// 采购单号

	private Integer				status;										// 订单状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消*

	private Integer 			spAcceptStatus;							// 供应商接单状态:0未接单;1:已接单

	private Date				createTime;									// 订货日期

	private Date				estimatedDeliveryDate;						// 预计送货日期

	//private List<PmPurchaseReceiptPo> pmPurchaseReceiptPos;

//	private String				spId;										// 供应商ID
//
//	private String				spNo;										// 供应商编码
//
//	private String				spName;										// 供应商名称
//
//	private String				spAdrId;									// 供应商地点ID


}