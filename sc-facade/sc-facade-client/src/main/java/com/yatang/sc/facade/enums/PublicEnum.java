package com.yatang.sc.facade.enums;

/**
 * @描述: 课堂公共枚举类
 * @作者: huangjianjun
 * @创建时间: 2017年4月11日-下午3:32:35 .
 * @版本: 1.0 .
 * @param <T>
 */
public enum PublicEnum {
	ZERO("0"), ONE("1"), TWO("2"), THREE("3"),FOUR("4"),FIVE("5"),SIX("6"),SEVEN("7"),EIGHT("8"),NINE("9"),

	ID("id"), NAME("systemUser"),

	ERROR_CODE("500"), ERROR_MSG("后台操作异常"), SUCCESS_CODE("200"), BUSINESS_SAVE_CHECK_CODE("300"), ERROR_PUSH_MSG("推送失败"),

	MAP_TOTAL("total"), MAP_DATA("data"),MAP_PAGE_NUM("pageNum"),MAP_PAGE_SIZE("pageSize"), DEFAULT_PAGE_NUM("1"), DEFAULT_PAGE_SIZE("20"),
	;

	private String	codeStr;



	public String getCodeStr() {
		return codeStr;
	}



	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}



	private PublicEnum(String codeStr) {
		this.codeStr = codeStr;
	}

}
