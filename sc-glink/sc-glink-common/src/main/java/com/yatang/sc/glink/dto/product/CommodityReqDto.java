package com.yatang.sc.glink.dto.product;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 商品同步响应结果DTO
 * @作者: yijiang
 * @创建时间: 2017年8月04日17:10:57
 */
public class CommodityReqDto implements Serializable {

	private static final long serialVersionUID = -9092471613786012527L;

	private String total;// 处理总数

	private List<FailOrdersDto> fail;// 失败订单

	private List<String> success;// 成功订单



	public String getTotal() {
		return total;
	}



	public void setTotal(String total) {
		this.total = total;
	}



	public List<FailOrdersDto> getFail() {
		return fail;
	}



	public void setFail(List<FailOrdersDto> fail) {
		this.fail = fail;
	}



	public List<String> getSuccess() {
		return success;
	}



	public void setSuccess(List<String> success) {
		this.success = success;
	}
}
