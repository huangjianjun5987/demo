package com.yatang.sc.common.staticvalue;

/**
 * 
 * <class description>采购退货单状态枚举
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年12月5日
 */
public enum PurchaseRefundStatusEnum {
	touch(0, "制单"), commit(1, "已提交"), audited(2, "已审核"), refused(3, "已拒绝"), preReturn(4, "待退货"), returned(5,
			"已退货"), cancelled(6, "已取消"), cancelFailed(7, "取消失败"), unusual(8, "异常");
	private Integer	code;
	private String	name;



	private PurchaseRefundStatusEnum(Integer code, String name) {
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
