package com.yatang.sc.facade.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @描述: 首页配置区域广告
 * @作者: yipeng
 * @创建时间: 2017年06月08日15:37:36
 * @版本: 1.0 .
 */
@Getter
@Setter
public class HomeAreaAdAppDto implements java.io.Serializable {

	private static final long	serialVersionUID	= -7550039390911841722L;

	private String				id;
	private String				name;
	/**
	 * 轮播间隔
	 * */
	private Integer				carouselInterval;
	private List<HomeItemAdDto>	itemAds;
	private Boolean 			isUsingNation;
	private String 				companyId;
	private int 				areaType;

}