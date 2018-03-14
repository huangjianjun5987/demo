package com.yatang.sc.app.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: sku
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/8 14:27
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class SkuVo implements Serializable{
    private static final long serialVersionUID = -2551111791368739033L;
    private String					id;
    private String					productId;
    // 0正常；1，删除
    private Integer					status;
    private SkuAttributeValueVo attr1;
    private SkuAttributeValueVo attr2;
    private SkuAttributeValueVo attr3;
    private List<ImageVo> images;

}
