package com.yatang.sc.common.staticvalue;

/**
 * 区域组类型枚举
 * PURCHASECONDITION,REWARDLIST 优惠种类下来框
 * @author zhudanning
 *
 */
public enum AreaGroupTypeEnum {


	/**
	 * 省份
	 */
	PROVINCE("PROVINCE"),

	/**
	 * 城市
	 */
	CITY("CITY"),

	/**
	 * 区县
	 */
	DISTRICT("DISTRICT"),


	/**
	 * 门店
	 */
	STORE("STORE"),

	/**
	 * 子公司
	 */
	COMPANY("COMPANY");

	AreaGroupTypeEnum(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
