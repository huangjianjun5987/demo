package com.yatang.sc.app.vo.prd;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 商品列表排序条件vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年8月1日
 */
@Getter
@Setter
public class SortOptionVo implements Serializable {

	private static final long serialVersionUID = -7435751211761741320L;

	private String	label;
	private String	value;
	private boolean	selected;
	private boolean	descending;
}
