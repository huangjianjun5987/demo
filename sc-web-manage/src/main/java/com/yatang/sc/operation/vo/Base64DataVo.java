package com.yatang.sc.operation.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述: 图片Base64图片流接收对象
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/17 9:38
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class Base64DataVo implements Serializable{
    private static final long serialVersionUID = 1658098341464413026L;

    private String base64Content;//图片base64文件内容
}
