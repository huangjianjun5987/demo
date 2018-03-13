package com.yatang.sc.facade.dto;

import com.yatang.sc.facade.common.BaseDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述:数据字典列表dto
 * @类名:DictionaryDto
 * @作者: kangdong
 * @创建时间: 2017/6/8 13:59
 * @版本: v1.0
 */
@Data
public class DictionaryDto implements Serializable {
	private static final long	serialVersionUID	= -590399755370806496L;
	private Integer				id;										// 主键ID
	private String				dictionary;								// 字典单位名称
	private String				code;										// 字典编码
	private String				remark;									// 说明
}
