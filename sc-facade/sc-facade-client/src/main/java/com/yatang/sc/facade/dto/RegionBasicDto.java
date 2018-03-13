package com.yatang.sc.facade.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 区域的basicDto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/5/27 15:09
 * @版本: v1.0
 */
@Getter
@Setter
public class RegionBasicDto implements Serializable {
	private static final long	serialVersionUID	= -8198832640974301939L;
	private Integer				id;											// 区域id
	private String				regionName;									// 区域名称
	private String				regionType;									// 区域类型
}
