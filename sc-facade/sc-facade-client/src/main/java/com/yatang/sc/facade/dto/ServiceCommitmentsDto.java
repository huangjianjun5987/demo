package com.yatang.sc.facade.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述: 服务承诺.
 * @作者: yangshuang
 */

public class ServiceCommitmentsDto implements Serializable {
	private static final long	serialVersionUID	= -5874785070847529268L;
	private Integer				id;												// pk

	private String				promiseContent;									// 承诺内容

	private Integer				sort;											// 排序

	private Integer				status;											// 起停用状态:0,禁用,1启用

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPromiseContent(String promiseContent) {
		this.promiseContent = promiseContent;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public String getPromiseContent() {
		return promiseContent;
	}

	public Integer getSort() {
		return sort;
	}

	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "ServiceCommitmentsDto{" +
				"id=" + id +
				", promiseContent='" + promiseContent + '\'' +
				", sort=" + sort +
				", status=" + status +
				'}';
	}
}