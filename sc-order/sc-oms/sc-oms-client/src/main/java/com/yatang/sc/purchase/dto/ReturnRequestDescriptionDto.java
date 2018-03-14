package com.yatang.sc.purchase.dto;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @描述: 退换货修改备注信息
 * @类名: ReturnRequestDescriptionDto
 * @作者: kangdong
 * @创建时间: 2017/10/31 10:11
 * @版本: v1.0
 */
public class ReturnRequestDescriptionDto implements Serializable {

	private static final long		serialVersionUID	= -4872273976025790980L;
	@NotBlank(message = "{msg.notEmpty.message}")
	private String					returnId;

	// @NotBlank(message = "{msg.notEmpty.message}")
	private String					description;

	private Short					returnReasonType;

	private String					returnReason;

	private String					userId;

	// 退货/拒收数量
	private List<ReturnQuantityDto>	items;



	public String getReturnId() {
		return returnId;
	}



	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Short getReturnReasonType() {
		return returnReasonType;
	}



	public void setReturnReasonType(Short returnReasonType) {
		this.returnReasonType = returnReasonType;
	}



	public String getReturnReason() {
		return returnReason;
	}



	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public List<ReturnQuantityDto> getItems() {
		return items;
	}



	public void setItems(List<ReturnQuantityDto> items) {
		this.items = items;
	}
}
