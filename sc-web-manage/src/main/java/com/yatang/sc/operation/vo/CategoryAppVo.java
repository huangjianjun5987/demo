package com.yatang.sc.operation.vo;


import java.io.Serializable;

/**
 * @描述: 商品分类vo(app)
 * @作者: yinyuxin
 * @创建时间: 2017年7月8日16:15:53
 * @版本: 1.0 .
 */
public class CategoryAppVo implements Serializable {

	private static final long serialVersionUID = 6399214271493710441L;
	/**
	 * 分类名称
	 */
	private String name;

	/**
	 * 分类id
	 */
	private String categoryId;


	/**
	 * 选中查询时的dimension节点id
	 */
	private String dimValId;



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}



	public String getDimValId() {
		return dimValId;
	}



	public void setDimValId(String dimValId) {
		this.dimValId = dimValId;
	}
}
