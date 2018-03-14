package com.yatang.sc.settlement.common;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 分页对象Dto.
 * @作者: huangjianjun
 * @创建时间: 2017年6月9日 下午1:59:04 .
 */
public class PageResult<T> implements Serializable {

	private static final long	serialVersionUID	= 5814502229142380611L;

	/** 当前页数 */
	private Integer				pageNum;

	/** 每页页数 */
	private Integer				pageSize;

	/** 总记录数 */
	private Long				total;

	/** 结果集 */
	private List<T>				data;



	public Integer getPageNum() {
		return pageNum;
	}



	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}



	public Integer getPageSize() {
		return pageSize;
	}



	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}



	public Long getTotal() {
		return total;
	}



	public void setTotal(Long total) {
		this.total = total;
	}



	public List<T> getData() {
		return data;
	}



	public void setData(List<T> data) {
		this.data = data;
	}



	@Override
	public String toString() {
		return "PageDto [pageNum=" + pageNum + ", pageSize=" + pageSize + ", total=" + total + ", data=" + data + "]";
	}
}