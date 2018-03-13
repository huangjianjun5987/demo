package com.yatang.sc.common.staticvalue;
/**
 * 
* <class description>对应prod_price_change表change_type字段
*		
* @author: zhoubaiyun
* @version: 1.0, 2017年12月6日
 */
public enum ProdPriceChangeTypeEnum {
	purchase(0, "采购进价变更"), sell(1, "售价变更");
	private int		code;
	private String	name;



	private ProdPriceChangeTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}



	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

}
