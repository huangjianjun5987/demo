package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:搜索推荐页类
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/8 20:37
 * @版本:v1.0
 */
@Getter
@Setter
public class RecommendKeywordsVo implements Serializable {
    private static final long serialVersionUID = -6229272399557694755L;
    private Integer				id;											// 主键id
    private Integer				sort;										// 排序值
    private String				content;									// 热门推荐内容
    private Integer				inputKey;									// 是否是搜索框的  1-是 2-不是
}
