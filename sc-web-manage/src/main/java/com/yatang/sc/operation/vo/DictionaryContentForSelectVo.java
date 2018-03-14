package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 用于系统级下拉框的数据字典vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月28日
 */
@Setter
@Getter
public class DictionaryContentForSelectVo implements Serializable {

	private static final long serialVersionUID = 3967518253715851170L;

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 字典内容名称
	 */
	private String contentName;

	/**
	 * 字典内容编码
	 */
	private String contentCode;

}
