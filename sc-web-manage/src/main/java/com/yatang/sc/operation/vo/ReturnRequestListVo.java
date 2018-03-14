package com.yatang.sc.operation.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述:
 * @类名:
 * @作者: kangdong
 * @创建时间: 2017/10/17 14:38
 * @版本: v1.0
 */
@Getter
@Setter
public class ReturnRequestListVo implements Serializable {

	private static final long	serialVersionUID	= 4077255879732128125L;

	private String				id;											// 退货单号
	private String				orderId;									// 原订单号id
	// private String branchCompanyId; // 子公司
	// private String franchiseeId; // 加盟商
	private String				returnRequestType;							// 退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)
	private String				branchCompanyName;							// 子公司名称
	private String				franchiseeName;								// 加盟名称
	private Date				creationTime;								// 申请日期
	private Double				amount;										// 总金额
	// private Double actualAmount; // 实际退换货总总额
	private Short				state;										// 退货单状态
	private String				stateDetail;								// 退货单总状态描述(1:待确认,2:已确认,3:已完成,4:已取消)
	private Short				shippingState;								// 收货状态
	private String				shippingStateDetail;						// 收货状态描述(1:部分收货,2:全部收货,3:待收货)
	private Short				productState;								// 商品状态
	private String				productStateDetail;							// 商品状态描述（1待取货、2已取货、3已发货）
	private String				orderType;									// 订单类型（ZCXS：加盟商，ZYYH：直营店）
	private String				paymentState;								// 退款状态
	private String				paymentStateDetail;							// 退款状态描述（YTK：已退款，WTK：未退款,YSQ:已申请）
	// private Double refundAmount; // 实际退款总总额
}
