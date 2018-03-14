package com.yatang.sc.operation.vo;

import java.io.Serializable;

/**
 * @描述: 退货/拒收数量
 * @类名: ReturnQuantityVo
 * @作者: kangdong
 * @创建时间: 2017/11/24 10:40
 * @版本: v1.0
 */
public class ReturnQuantityVo implements Serializable {

	private static final long	serialVersionUID	= -1584108399505818566L;

	// @NotBlank(message = "{msg.notEmpty.message}")
	private Long				id;
	// @NotBlank(message = "{msg.notEmpty.message}")
	private Long				returnQuantity;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getReturnQuantity() {
		return returnQuantity;
	}



	public void setReturnQuantity(Long returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
}
