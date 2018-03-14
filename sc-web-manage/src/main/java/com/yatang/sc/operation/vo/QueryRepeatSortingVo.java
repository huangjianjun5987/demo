package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.yatang.sc.validgroup.GroupOne;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @描述: 判断序号是否重复
 * @作者: tankejia
 * @创建时间: 2017/6/13-20:38 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class QueryRepeatSortingVo implements Serializable {

    private static final long serialVersionUID = -3250798650817108949L;

    /**
     * 查询类型(0:新增， 1：修改)
     * */
    @NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private Integer queryType;
    /**
     * 序号
     * */
    @NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private Integer sorting;
    /**
     * 轮播广告编号
     * */
    private Integer carouselAdId;
    /**
     * 轮播广告所属areaId
     * */
    @NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private String areaId;
}
