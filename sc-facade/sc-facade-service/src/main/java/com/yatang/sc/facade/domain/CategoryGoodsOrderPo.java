package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @描述: 列表页商品排序Po.
 * @作者: yinyuxin
 * @创建时间: 2017年6月8日13:54:40
 * @版本: 1.0 .
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGoodsOrderPo implements Serializable{
	
	private static final long serialVersionUID = 2891646820246013909L;
	
	private Integer 			pkId;                                       //pk主键
	
	private String				id;											//商品编号

    private String				name;										//商品名称

    private String				firstCategoryId;                            //一级分类id

    private String				secondCategoryId;                           //二级分类id

    private String				thirdCategoryId;                            //三级分类id
    
    private String				firstCategoryName;                          //一级分类名称

    private String				secondCategoryName;                         //二级分类名称

    private String				thirdCategoryName;                          //三级分类名称

    private Integer				orderNum;								 	//商品排序号

}