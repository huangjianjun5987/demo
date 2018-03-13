package com.yatang.sc.common.staticvalue;

/**
 * 
 * <class description>退货地点类型
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年12月5日
 */
public enum AdrTypeEnum {

	warehouse(0, "仓库"), store(1, "门店");
	private Integer	code;
	private String	name;



	private AdrTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}



	public Integer getCode() {
		return code;
	}



	public void setCode(Integer code) {
		this.code = code;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	/**
	 * 
	 * <method description>根据索引获取枚举值
	 *
	 * @param index
	 * @return
	 */
	public static String getNameByIndex(Integer index) {
		return values()[index].getName();
	}
}
