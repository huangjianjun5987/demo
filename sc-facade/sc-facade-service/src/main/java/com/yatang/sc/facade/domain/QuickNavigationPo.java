package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 快捷导航信息
 * @作者: tankejia
 * @创建时间: 2017/6/8-15:24 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class QuickNavigationPo implements Serializable {

	private static final long	serialVersionUID	= -6723286708695200427L;

	/**
	 * 快捷导航编号
	 * */
	private Integer				id;

	/**
	 * 位置
	 * */
	private String				navigationPosition;

	/**
	 * 链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接
	 * */
	private Integer 			navigationType;

	/**
	 * 商品编号
	 * */
	private String 				goodsId;

	/**
	 * 名称
	 * */
	private String				navigationName;

	/**
	 * 链接地址
	 * */
	private String				linkAddress;

	/**
	 * 图片地址
	 * */
	private String				picAddress;

	/**
	 * 创建人
	 * */
	private String				createPerson;

	/**
	 * 修改人
	 * */
	private String				updatePerson;

	/**
	 * 创建时间
	 * */
	private Date				gmtCreate;

	/**
	 * 状态（0：停用，1：已启用）
	 * */
	private Integer				status;

	/**
	 * 所属areaID
	 */
	private String				areaId;

	/**
	 * 关键字
	 */
	private String				linkKeyword;

	/**
	 * 分类ID
	 */
	private String				linkId;

}
