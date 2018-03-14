package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:所有的省市
 * @类名:ProvinceCityListVo
 * @作者: lvheping
 * @创建时间: 2017/6/3 20:29
 * @版本: v1.0
 */
@Getter
@Setter
public class ProvinceCityListVo implements Serializable {
	private static final long			serialVersionUID	= 9042883252829214891L;
	private String						code;										// 省市编码
	private String						parentCode;									// 父编码
	private String						regionName;									// 省市名字
	private String						regionType;									// 表示省市的类型
	private List<ProvinceCityListVo>	childs				= new ArrayList();		// 子集
}
