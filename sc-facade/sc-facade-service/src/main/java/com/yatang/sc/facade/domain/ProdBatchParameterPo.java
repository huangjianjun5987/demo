package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:商品批量上下架操作接收参数类
 * @类名:ProdBatchParameterVo
 * @作者: lvheping
 * @创建时间: 2017/7/18 11:07
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdBatchParameterPo implements Serializable {
	private String			branchCompanyName;	// 分公司名字
	private String			branchCompanyId;	// 分公司id
	private List<String>	productIds;		// 商品id集合
	private String			userId;			// 当前操作人员
	private Date			modifyTime;		// 修改时间
	private Integer			status;			// 销售区间价启用禁用状态
}
