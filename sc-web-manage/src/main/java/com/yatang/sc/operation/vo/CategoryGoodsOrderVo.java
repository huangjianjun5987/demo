package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 列表页商品排序VO(用于传递数据给前端).
 * @作者: yinyuxin
 * @创建时间: 2017年6月8日15:54:17
 * @版本: 1.0 .
 */
@Getter
@Setter
public class CategoryGoodsOrderVo implements Serializable{
	
	private static final long serialVersionUID = -1064324892961358881L;

	private Integer 			pkId;                                       //pk主键
	
	private String				id;											//商品编号

    private String				name;										//商品名称

    private String				firstCategoryId;                           //一级分类id

    private String				secondCategoryId;                          //二级分类id

    private String				thirdCategoryId;                           //三级分类id
    
    private String				firstCategoryName;                          //一级分类名称

    private String				secondCategoryName;                         //二级分类名称

    private String				thirdCategoryName;                          //三级分类名称

    private Integer				orderNum;								 	//商品排序号

    private Integer				newOrderNum;								//商品新的排序号
}