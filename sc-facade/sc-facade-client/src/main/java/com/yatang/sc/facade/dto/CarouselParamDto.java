package com.yatang.sc.facade.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 轮播参数信息
 * @作者: tankejia
 * @创建时间: 2017/6/8-15:23 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class CarouselParamDto implements Serializable {

	private static final long	serialVersionUID	= -8736484167392313485L;

	private Integer				id;

	/**
	 * 轮播间隔
	 * */
	private Integer				carouselInterval;

	/**
	 * 状态（0：未设置间隔，1：当前设置间隔）
	 * */
	private Integer				status;

	/**
	 * 对应的区域Id
	 */
	private String 				areaId;

}
