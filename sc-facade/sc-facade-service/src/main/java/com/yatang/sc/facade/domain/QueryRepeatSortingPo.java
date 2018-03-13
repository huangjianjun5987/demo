package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 判断序号是否重复
 * @作者: tankejia
 * @创建时间: 2017/6/13-20:38 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class QueryRepeatSortingPo implements Serializable {

	private static final long	serialVersionUID	= -410203829213154565L;

	/**
	 * 查询类型(0:新增， 1：修改)
	 * */
	private Integer				queryType;
	/**
	 * 序号
	 * */
	private Integer				sorting;
	/**
	 * 轮播广告编号
	 * */
	private Integer				carouselAdId;
	/**
	 * 轮播广告所属areaId
	 * */
	private String areaId;
}
