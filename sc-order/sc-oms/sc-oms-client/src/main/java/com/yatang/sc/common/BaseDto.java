package com.yatang.sc.common;

import java.io.Serializable;

import lombok.Data;

/**
 * @描述:接收分页的参数
 * @类名:BaseDto
 * @作者: lvheping
 * @创建时间: 2017/6/12 11:35
 * @版本: v1.0
 */
@Data
public class BaseDto implements Serializable {
	private static final long	serialVersionUID	= -2103438815299432990L;
	private Integer				pageNum;									// 当前页
	private Integer				pageSize;									// 每页显示记录数
}
