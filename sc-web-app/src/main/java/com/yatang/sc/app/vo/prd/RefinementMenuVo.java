package com.yatang.sc.app.vo.prd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 商品列表筛选条件
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年8月1日
 */
@Setter
@Getter
public class RefinementMenuVo implements Serializable {

	private String				dimensionName;
	private String				displayName;
	private String				dimensionId;
	private List<RefinementVo>	refinements	= new ArrayList<>();
}
