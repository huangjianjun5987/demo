package com.yatang.sc.facade.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:用作接收查询静态页列表参数
 * @类名:StaticPageQueyVo
 * @作者: lvheping
 * @创建时间: 2017/6/8 16:22
 * @版本: v1.0
 */
@Getter
@Setter
public class StaticPageQueyDto implements Serializable {
	private static final long	serialVersionUID	= -7597848819114230653L;
	private int					id;											// 静态页ID
	private String				name;										// 静态页名称
	private String				linkUrl;									// 链接地址
	private Integer				pageNum;									// 当前页
	private Integer				pageSize;									// 每页显示记录数
}
