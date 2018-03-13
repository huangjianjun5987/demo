package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:数据字典内容
 * @类名:DictionaryContentPo
 * @作者: kangdong
 * @创建时间: 2017/6/9 10:23
 * @版本: v1.0
 */
@Getter
@Setter
public class DictionaryContentPo implements Serializable {
	private static final long	serialVersionUID	= -2022470680800593690L;
	private Integer				id;
	/**
	 * 字典ID
	 */
	private Integer				dictionaryId;
	/**
	 * 字典名称
	 */
	private String				contentName;
	/**
	 * 字典编码
	 */
	private String				contentCode;
	/**
	 * 状态
	 */
	private Integer				state;
}
