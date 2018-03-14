package com.yatang.sc.operation.vo;

import com.yatang.sc.operation.common.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 退换货单查询条件VO
 * @类名: ReturnRequestQueryParamVo
 * @作者: kangdong
 * @创建时间: 2017/10/17 15:11
 * @版本: v1.0
 */
@Getter
@Setter
public class ReturnRequestQueryParamVo extends BaseVo implements Serializable {

	private static final long	serialVersionUID	= -6171392261973699261L;

	private String				id;											// 退货单号
	private String				returnRequestType;							// 退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)
	private String				orderId;									// 原订单号id
	private String				branchCompanyId;							// 子公司//编码和子公司名称
	private String				franchiseeId;								// 小超加盟商//编码和小超名称
	private Date				startCreateTime;							// 开始日期
	private Date				endCreateTime;								// 结束日期
	private Short				state;										// 退货单状态
	private Short				shippingState;								// 收货总状态
	private Short				paymentState;								// 退款状态
	//private Short				productState;								// 商品状态
	//private String				profileId;									// 用户id
	//private String				returnRequestType;							// 退货单类型
	//private String				orderType;									// 订单类型
}
