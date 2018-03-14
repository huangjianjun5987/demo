package com.yatang.sc.app.vo.prd;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 商品列表查询接口返回的对象
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年8月1日
 */
@Getter
@Setter
@ToString
public class ProductListQueryResultVo implements Serializable {

	private static final long serialVersionUID = -2233427010987303074L;

	private List<ProductForAppVo> records;

	private List<RefinementMenuVo> refinementMenus;

	private List<SortOptionVo> sortOptions;

	/** 当前页数 */
	private int	pageNum;
	/** 总条数 */
	private int	total;

	private int pageSize;
}
