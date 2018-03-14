package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;

/**
 * @描述: 404广告方案
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 10:04
 * @版本: v1.0
 */
@Getter
@Setter
public class AdPlanVo implements Serializable {
    private static final long serialVersionUID = 3280125194592018309L;

    @NotNull(groups = GroupOne.class, message = "{msg.notEmpty.message}")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "{msg.range.message}", groups = GroupOne.class)
    private Integer id; //主键

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Length(message = "{msg.length.message}", min = 1, max = 30, groups = {GroupOne.class, DefaultGroup.class})
    private String planName;//方案名

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Length(message = "{msg.length.message}", min = 1, max = 30, groups = {GroupOne.class, DefaultGroup.class})
    private String picName1;//图片1名称

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
//    @URL(message = "{msg.url.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Length(message = "{msg.length.message}", min = 1, max = 200, groups = {GroupOne.class, DefaultGroup.class})
    @JsonSerialize(using = ImageUrlSerializer.class)
    @JsonDeserialize(using = ImageUrlDeserializer.class)
    private String picUrl1;//图片1保存路径

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
//    @URL(message = "{msg.url.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Length(message = "{msg.length.message}", min = 1, max = 200, groups = {GroupOne.class, DefaultGroup.class})
    private String linkUrl1;//链接地址1


    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Length(message = "{msg.length.message}", min = 1, max = 30, groups = {GroupOne.class, DefaultGroup.class})
    private String picName2;//图片1名称


    @NotEmpty(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
//    @URL(message = "{msg.url.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Length(message = "{msg.length.message}", min = 1, max = 200, groups = {GroupOne.class, DefaultGroup.class})
    private String linkUrl2;//链接地址1

    @NotEmpty(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
//    @URL(message = "{msg.url.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Length(message = "{msg.length.message}", min = 1, max = 200, groups = {GroupOne.class, DefaultGroup.class})
    @JsonSerialize(using = ImageUrlSerializer.class)
    @JsonDeserialize(using = ImageUrlDeserializer.class)
    private String picUrl2;//图片1保存路径

    private Date createTime;//创建时间

    private Integer status;//启用状态(1:启用,0:停用)

    private Date modifyTime;//修改时间

    private String modifyUserId;//修改者id

    private String createUserId;//创建者id



}