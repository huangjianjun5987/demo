package com.yatang.sc.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @描述:数据字典维护的字典内容
 * @类名:DictionaryContentDto
 * @作者: kangdong
 * @创建时间: 2017/6/9 9:59
 * @版本: v1.0
 */
@Data
public class DictionaryContentDto implements Serializable {
	private static final long	serialVersionUID	= 7703056831762154390L;
	private Integer				id;
	private Integer				dictionaryId;
	private String				contentName;
	private String				contentCode;
	private Integer				state;
}
