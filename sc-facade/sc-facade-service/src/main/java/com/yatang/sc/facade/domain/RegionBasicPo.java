package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/5/28 9:51
 * @版本: v1.0
 */
@Getter
@Setter
public class RegionBasicPo implements Serializable {
	private static final long	serialVersionUID	= -6024311770609045787L;
	private Integer				id;											// 区域id
	private String				regionName;										// 区域名称
	private String				regionType;										// 区域类型
}
