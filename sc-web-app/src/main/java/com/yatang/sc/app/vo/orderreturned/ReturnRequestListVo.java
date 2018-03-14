package com.yatang.sc.app.vo.orderreturned;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yatang.sc.purchase.dto.OrderProductListDto;
import lombok.Getter;
import lombok.Setter;

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

	private static final long serialVersionUID = 5601021508359742139L;
	private Long				id;											// 退货单号
	// private Date creationTime; // 申请日期
	// private String orderId; // 原订单号id
	// private String branchCompanyId; // 子公司
	// private String franchiseeId; // 加盟商
	// private String branchCompanyName; // 子公司
	// private String franchiseeName; // 加盟名称
	private List<OrderProductListDto> productList;				// 购物车商品
	private Integer				productCount;										// 商品数量
	private Double				total;										// 总金额
	private Short				state;										// 退货单状态

}
