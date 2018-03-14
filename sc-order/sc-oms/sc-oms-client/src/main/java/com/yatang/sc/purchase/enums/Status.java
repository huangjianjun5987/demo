package com.yatang.sc.purchase.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 审批状态
 * @作者: kangdong
 * @创建时间: 2017年05月23日09:31:22
 * @版本: 1.0 .
 */

public enum Status {
	PENDING_APPROVAL("待审批"),
	NOT_PASS("审核不通过"),
	NORMAL("正常供应"),
	FROZEN("已冻结"),
	TERMINATE_CONTRACT("终止合同"),
	EDIT_PENDING_APPROVAL("修改待审核"),
	EDIT_NOT_PASS("修改审核未通过"),
	;

	private final String description;

	Status(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
