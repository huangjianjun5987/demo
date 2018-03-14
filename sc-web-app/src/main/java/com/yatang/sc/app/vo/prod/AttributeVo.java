package com.yatang.sc.app.vo.prod;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class AttributeVo implements Serializable {
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
