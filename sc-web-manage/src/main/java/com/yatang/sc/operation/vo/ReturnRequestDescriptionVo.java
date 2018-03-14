package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @描述: 退换货修改备注信息
 * @类名: ReturnRequestDescriptionVo
 * @作者: kangdong
 * @创建时间: 2017/10/31 10:11
 * @版本: v1.0
 */
public class ReturnRequestDescriptionVo implements Serializable {

	private static final long		serialVersionUID	= -3020537301566489188L;

	@NotBlank(message = "{msg.notEmpty.message}")
	private String					returnId;

	// @NotBlank(message = "{msg.notEmpty.message}")
	private String					description;

	private Short					returnReasonType;

	private String					returnReason;

	// 退货/拒收数量
	private List<ReturnQuantityVo>	items;



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


	public List<ReturnQuantityVo> getItems() {
		return items;
	}

	public void setItems(List<ReturnQuantityVo> items) {
		this.items = items;
	}
}
