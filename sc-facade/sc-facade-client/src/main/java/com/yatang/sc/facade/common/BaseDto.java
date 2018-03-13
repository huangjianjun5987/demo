package com.yatang.sc.facade.common;

import java.io.Serializable;

import lombok.Data;

/**
 * @描述:接收分页的参数
 * @类名:BaseDto
 * @作者: lvheping
 * @创建时间: 2017/6/12 11:35
 * @版本: v1.0
 */

public class BaseDto implements Serializable {
	private static final long	serialVersionUID	= -2103438815299432990L;
	private Integer				pageNum=Integer.valueOf(CommonsEnum.RESPONSE_PAGE_NUM.getCode());	// 当前页
	private Integer				pageSize=Integer.valueOf(CommonsEnum.RESPONSE_PAGE_SIZE.getCode());	// 每页显示记录数



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



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
}
