package com.yatang.sc.operation.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * <class description>发放优惠券VO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年9月19日
 */

public class GrantCouponVo {
	// 门店ID列表
	private String[]	storeIds;
	// 优惠券ID列表
	private String[]	promoIds;



	public void setStoreIds(String[] storeIds) {

		this.storeIds = trimStringArray(storeIds);
	}



	public void setPromoIds(String[] promoIds) {
		this.promoIds = trimStringArray(promoIds);
	}



	public String[] getStoreIds() {
		return storeIds;
	}



	public String[] getPromoIds() {
		return promoIds;
	}



	/**
	 * 
	 * <method description>去掉字符串数组里面的无效项和无效字符
	 *
	 * @param origin
	 * @return
	 */
	private static String[] trimStringArray(String[] origin) {
		if (null == origin || origin.length == 0) {
			return null;
		}
		List<String> list = new ArrayList<>();
		for (String string : origin) {
			if (!StringUtils.isBlank(string)) {
				list.add(string.trim());
			}
		}
		return list.toArray(new String[list.size()]);
	}
}
