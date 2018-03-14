package com.yatang.sc.operation.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
 * @描述: 快捷导航信息
 * @作者: tankejia
 * @创建时间: 2017/6/8-15:24 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class QuickNavigationVo implements Serializable {

    private static final long serialVersionUID = -4422371510527492323L;

    /**
     * 快捷导航编号
     * */
    @NotNull(message = "{msg.notEmpty.message}")
    private Integer id;

    /**
     * 位置
     * */
    private String navigationPosition;

    /**
     * 链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接
     * */
    @NotNull(message = "{msg.notEmpty.message}")
    private Integer navigationType;

    /**
     * 商品编号
     * */
    @Length(max=20, message = "{msg.length.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{msg.letter&NumberOnly.message}")
    private String goodsId;

    /**
     * 名称
     * */
    @NotBlank
    @Pattern(regexp="^[\\u4e00-\\u9fa5]{2,4}$", message = "{msg.ChineseOnlyTwoToFour}")
    private String navigationName;

    /**
     * 链接地址
     * */
    private String linkAddress;

    /**
     * 图片地址
     * */
    @JsonSerialize(using = ImageUrlSerializer.class)
    @JsonDeserialize(using = ImageUrlDeserializer.class)
    @NotBlank(message = "{msg.notEmpty.message}")
    private String picAddress;

    /**
     * 创建人
     * */
    private String createPerson;

    /**
     * 修改人
     * */
    private String updatePerson;

    /**
     * 状态（0：停用，1：已启用）
     * */
    private Integer status;

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
