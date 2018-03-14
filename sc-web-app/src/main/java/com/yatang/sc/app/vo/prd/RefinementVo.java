package com.yatang.sc.app.vo.prd;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 商品列表筛选条件每个选项对应的值
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年8月1日
 */
@Getter
@Setter
public class RefinementVo implements Serializable {

	private static final long serialVersionUID = -995594727364215363L;

	/**
	 * dimension节点名字
	 */
	private String label;

	/**
	 * dimension节点id
	 */
	private String dimValId;

	/**
	 * 是否选中
	 */
	private boolean selected;
}
