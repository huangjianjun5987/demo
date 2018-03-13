package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.util.Date;

import com.yatang.sc.facade.common.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商采购单列表查询条件类
 * @类名: PmSupplierPurchaseOrderQueryParamDto
 * @作者: kangdong
 * @创建时间: 2018/1/9 17:11
 * @版本: v1.0
 */
@Getter
@Setter
public class PmSupplierPurchaseOrderQueryParamDto extends BaseDto implements Serializable {

	private static final long	serialVersionUID	= -7666282827408321358L;
	private String				spId;										// 供应商ID

	private String				spAdrId;									// 供应商地点ID

	private Integer				purchaseOrderType;							// 采购单类型:0:普通采购单;1:赠品采购;2:促销釆购

	private String				purchaseOrderNo;							// 采购单号

	private Integer				status;										// 订单状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消

	private Integer				adrType;									// 地点类型:0:仓库;1:门店

	private String				adrTypeCode;								// 地点类型编码

	private Integer				spAcceptStatus;								// 供应商接单状态:0未接单;1:已接单

	private Integer				sort;										// 排序:按订单号升序(莫默null); 1按预计收货日期升序;2按预计收货日期降序;3按订单号降序

	private Date				startCreateTime;							// 开始订货日期

	private Date				endCreateTime;								// 结束订货日期

	private Date				startEstimatedDeliveryDate;								// 开始预计送货日期

	private Date				endEstimatedDeliveryDate;								// 结束预计送货日期
}
