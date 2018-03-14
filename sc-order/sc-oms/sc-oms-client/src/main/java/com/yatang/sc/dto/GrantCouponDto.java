package com.yatang.sc.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>优惠券发放DTO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年9月19日
 */
@Getter
@Setter
public class GrantCouponDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	// 门店ID列表
	private String[]			storeIds;
	// 优惠券ID列表
	private String[]			promoIds;
	// 当前操作用户ID
	private String				userId;
}
