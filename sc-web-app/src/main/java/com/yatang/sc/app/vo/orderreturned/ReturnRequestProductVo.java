package com.yatang.sc.app.vo.orderreturned;

import java.io.Serializable;

import com.yatang.sc.purchase.dto.ItemPriceInfoDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 退货商品列表信息
 * @类名: ReturnRequestProductDto
 * @作者: kangdong
 * @创建时间: 2017/10/19 10:58
 * @版本: v1.0
 */
@Getter
@Setter
public class ReturnRequestProductVo implements Serializable {

    private static final long serialVersionUID = -40878527835798205L;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     *二级分类
     */
    //private String secondLevelCategoryName;

    /**
     *三级分类
     */
    //private String thirdLevelCategoryName;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 国际码信息
     */
    private String internationalCode;

    /**
     * 商品数量
     */
    private Long quantity;

    /**
     * 价格
     */
    private ItemPriceInfoDto itemPrice;
    /**
     * 商品单价
     */
    //private Double price;

    /**
     * 商品金额
     */
    //private Double itemPrice;
}
