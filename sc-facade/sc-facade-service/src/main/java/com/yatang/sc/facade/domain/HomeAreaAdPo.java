package com.yatang.sc.facade.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 首页配置区域广告
 * @作者: yipeng
 * @创建时间: 2017年06月08日15:37:36
 * @版本: 1.0 .
 */
@Getter
@Setter
@EqualsAndHashCode
public class HomeAreaAdPo implements java.io.Serializable {

	private static final long	serialVersionUID	= -7550039390911841722L;
	public static final String	CAROUSEL			= "carousel";
	public static final String	QUICK_NAV			= "quick-nav";

	private String				id;
	private String				name;
	private Double				sequence;
	private Boolean				isEnabled;
	private Boolean 			isUsingNation;
	private String 				companyId;
	private int 				areaType;

}
