package com.yatang.sc.glink.dto.product;

import java.io.Serializable;

/**
 * @描述: 返回失败订单DTO
 * @作者: yijiang
 * @创建时间: 2017年8月04日17:05:57
 */
public class FailOrdersDto implements Serializable {

	private static final long serialVersionUID = 6919181009020818377L;

	private String itemCode;// 商品编码

	private String errorCode;// 错误代码

	private String errorMsg;// 错误信息



	public String getItemCode() {
		return itemCode;
	}



	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}



	public String getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



	public String getErrorMsg() {
		return errorMsg;
	}



	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
