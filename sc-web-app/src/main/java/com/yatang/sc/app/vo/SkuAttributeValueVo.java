package com.yatang.sc.app.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/8 14:28
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class SkuAttributeValueVo implements Serializable{
    private static final long serialVersionUID = -652009944615963916L;
    private String				id;
    private String				value;
    private String				name;

}
