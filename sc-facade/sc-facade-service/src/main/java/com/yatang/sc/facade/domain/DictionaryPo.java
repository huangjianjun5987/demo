package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:数据字典
 * @类名:DictionaryPo
 * @作者: kangdong
 * @创建时间: 2017/6/8 14:23
 * @版本: v1.0
 */
@Getter
@Setter
public class DictionaryPo implements Serializable {
	private static final long	serialVersionUID	= 260355914749080937L;
	private Integer				id;
	/**
	 * 字典单位名称
	 */
	private String				dictionary;
	/**
	 * 字典编号
	 */
	private String				code;
	/**
	 * 说明
	 */
	private String				remark;
	
	/**
	 * 字段类型 0：业务级字段 1:系统级字典
	 */
	private String				dictionaryType;
}
