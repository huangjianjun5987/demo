package com.yatang.sc.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 用于封装 心愿单发送mq给小超B端 的数据
 * @author: yinyuxin
 * @date: 2018/1/4 17:58
 * @version: v1.0
 */
public class WishMqDto implements Serializable{

	private static final long serialVersionUID = 7862801242287141518L;

	/**
	 * 小超运营显示的标题
	 */
	private String title;

	/**
	 * 小超运营显示的内容
	 */
	private String content;

	/**
	 * 跳转链接
	 */
	private String productDetailUrl;

	/**
	 * 门店ID集合
	 */
	private List<String> storeIds;

	/**
	 * 心愿结果 true:已完成， false：关闭(false时没有商品详情)
	 */
	private Boolean status;



	public String getProductDetailUrl() {
		return productDetailUrl;
	}



	public void setProductDetailUrl(String productDetailUrl) {
		this.productDetailUrl = productDetailUrl;
	}



	public List<String> getStoreIds() {
		return storeIds;
	}



	public void setStoreIds(List<String> storeIds) {
		this.storeIds = storeIds;
	}



	public Boolean getStatus() {
		return status;
	}



	public void setStatus(Boolean status) {
		this.status = status;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}
}
