package com.yatang.sc.app.vo.prd;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: app端dubbo服务vo（查询商品列表所需参数）
 * @作者: yinyuxin
 * @创建时间: 2017年7月10日20:52:30
 * @版本: 1.0 .
 */
@Getter
@Setter
public class ProductListForAppQueryParamVo implements Serializable {

	private static final long	serialVersionUID	= -1287320074973449086L;
	/** 选中的facet节点id */
	private List<String>		dimensionIds;
	/** 搜索关键字 */
	private String				searchTerm;
	/** 分公司Id */
	private String				branchCompanyId;

	/**
	 * 是否经销  0 是 1 否
	 */
	private String distributionStatus;

	private String	sort;
	private int		pageNum		= 1;
	private int		pageSize	= 10;



	public void setSort(String sort) {
		if (sort != null || !"".equals(sort)) {
			try {
				this.sort = java.net.URLDecoder.decode(sort, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			this.sort = sort;
		}
	}
}
