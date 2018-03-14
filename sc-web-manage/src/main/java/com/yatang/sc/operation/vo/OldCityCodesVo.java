package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:查询已经选择了的城市code
 * @类名:OldCityCodesVo
 * @作者: lvheping
 * @创建时间: 2017/7/4 20:45
 * @版本: v1.0
 */
@Getter
@Setter
public class OldCityCodesVo implements Serializable {

	private static final long	serialVersionUID	= -6398016303816746316L;
	private String				cityCode;										// 城市的code
}
