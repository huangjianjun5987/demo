package com.yatang.sc.operation.vo;

import java.util.List;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @描述: 首页配置区域广告
 * @作者: yipeng
 * @创建时间: 2017年06月08日15:37:36
 * @版本: 1.0 .
 */
@Getter
@Setter
public class HomeAreaAdVo implements java.io.Serializable {

	private static final long	serialVersionUID	= -7550039390911841722L;

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private String				id;

	private String				name;
	private Double				sequence;
	private Boolean				isEnabled;
	private List<HomeItemAdVo>	itemAds;

	@NotNull(groups = {GroupTwo.class}, message = "{msg.notEmpty.message}")
	private Boolean 			isUsingNation;

	@NotNull(groups = {GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String 				companyId;

	private int 				areaType;

}