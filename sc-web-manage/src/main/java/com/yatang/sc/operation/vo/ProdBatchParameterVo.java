package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.yatang.sc.validgroup.DefaultGroup;

/**
 * @描述:商品批量上下架操作接收参数类
 * @类名:ProdBatchParameterVo
 * @作者: lvheping
 * @创建时间: 2017/7/18 11:07
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdBatchParameterVo implements Serializable {
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private String			branchCompanyName;	// 分公司名字
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private String			branchCompanyId;	// 分公司id
	@NotNull(groups = DefaultGroup.class, message = "{msg.notEmpty.message}")
	private Integer			status;			// 销售区间价启用禁用状态
	private List<String>	productIds;		// 商品id集合
	private String			userId;			// 当前操作人员
	private Date			modifyTime;		// 修改时间
}
