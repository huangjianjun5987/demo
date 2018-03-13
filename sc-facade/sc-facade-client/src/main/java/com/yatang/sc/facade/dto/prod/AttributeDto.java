package com.yatang.sc.facade.dto.prod;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class AttributeDto implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 4745377173397128921L;

	// 系统id
	protected String id;

	// 参数名称
	protected String name;

	private String values;
}
