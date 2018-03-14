package com.yatang.sc.operation.vo;

import com.yatang.sc.validgroup.GroupOne;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @描述: 轮播参数信息
 * @作者: tankejia
 * @创建时间: 2017/6/8-15:23 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class CarouselParamVo implements Serializable {

    private static final long serialVersionUID = -8161728884899469577L;

    @NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private Integer				id;

    /**
     * 轮播间隔
     * */
    @NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private Integer				carouselInterval;

    /**
     * 状态（0：未设置间隔，1：当前设置间隔）
     * */
    private Integer				status;

    /**
     * 对应的区域Id
     */
    private String 				areaId;
}
