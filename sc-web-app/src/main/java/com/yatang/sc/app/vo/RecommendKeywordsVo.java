package com.yatang.sc.app.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @描述:搜索推荐页类
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/8 20:37
 * @版本:v1.0
 */
@Data
public class RecommendKeywordsVo implements Serializable {

    private static final long serialVersionUID = 725165742682985226L;
    private Integer				id;											// 主键id
    private Integer				sort;										// 排序值
    private String				content;									// 热门推荐内容
    private Integer				inputKey;									// 是否是搜索框的  1-是 2-不是
}
