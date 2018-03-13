package com.yatang.sc.kidd.dto.im;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:调用际链返回参数
 * @类名:ImAdjustmentParameterDto
 * @作者: lvheping
 * @创建时间: 2017/9/4 17:16
 * @版本: v1.0
 */

public class ImAdjustmentResultDto implements Serializable {
	private static final long	serialVersionUID	= -5550492056375979085L;
	private int total;//总记录数
	private int pageNo;//当前页
	private int pageSize;//每页记录数
	private List<WarehouseProductDto> result;//商品信息

	public int getTotal() {
		return total;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<WarehouseProductDto> getResult() {
		return result;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setResult(List<WarehouseProductDto> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ImAdjustmentResultDto{" +
				"total=" + total +
				", pageNo=" + pageNo +
				", pageSize=" + pageSize +
				", result=" + result +
				'}';
	}
}
