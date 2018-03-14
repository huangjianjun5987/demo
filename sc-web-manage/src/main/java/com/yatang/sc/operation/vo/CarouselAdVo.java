package com.yatang.sc.operation.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @描述: 轮播广告信息
 * @作者: tankejia
 * @创建时间: 2017/6/8-15:22 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class CarouselAdVo implements Serializable {

    private static final long serialVersionUID = -4060691856487401754L;

    /**
     * 编号
     * */
    @NotNull(groups = {GroupOne.class, DefaultGroup.class}, message = "{msg.notEmpty.message}")
    private Integer id;

    /**
     * 排序
     * */
    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, GroupTwo.class})
    private Integer sorting;

    /**
     * 链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接
     * */
    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, GroupTwo.class})
    private Integer linkType;

    /**
     * 商品编号
     * */
    @Length(max=20, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", groups = {GroupOne.class, GroupTwo.class}, message = "{msg.letter&NumberOnly.message}")
    private String goodsId;

    /**
     * 链接地址
     * */
    private String linkAddress;

    /**
     * 图片地址
     * */
    @JsonSerialize(using = ImageUrlSerializer.class)
    @JsonDeserialize(using = ImageUrlDeserializer.class)
    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, GroupTwo.class})
    private String picAddress;

    /**
     * 状态（0：停用，1：已启用）
     * */
    @NotNull(groups = {GroupOne.class, GroupTwo.class, DefaultGroup.class}, message = "{msg.notEmpty.message}")
    private Integer status;

    /**
     * 创建人
     * */
    private String createPerson;

    /**
     * 修改人
     * */
    private String  updatePerson;

    /**
     * 所属areaID
     */
    private String				areaId;

    /**
     * 关键字
     */
    private String				linkKeyword;

    /**
     * 分类ID
     */
    private String				linkId;
}
