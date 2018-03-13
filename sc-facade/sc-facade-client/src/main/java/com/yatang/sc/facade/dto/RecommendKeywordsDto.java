package com.yatang.sc.facade.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:推荐关键字dto类
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/8 13:58
 * @版本:v1.0
 */
@Getter
@Setter
public class RecommendKeywordsDto implements Serializable {
	private static final long	serialVersionUID	= -2543553707967392271L;

	private Integer				id;											// 主键id
	private Integer				sort;										// 排序值
	private String				content;									// 热门推荐内容
	private Integer				inputKey;									// 是否是搜索框的  1-是 2-不是


}
