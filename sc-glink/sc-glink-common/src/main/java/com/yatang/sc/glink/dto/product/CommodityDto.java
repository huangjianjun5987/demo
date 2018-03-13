package com.yatang.sc.glink.dto.product;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 商品DTO
 * @作者: yijiang
 * @创建时间: 2017年7月31日20:30:57
 */
public class CommodityDto implements Serializable{

    private String type;//系统重复订单处理方式:1、直接返回错误；2、重复商品跳过(skip)；3、重复商品修改(replace)；

    private List<CommodityItemDto> items;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CommodityItemDto> getItems() {
        return items;
    }

    public void setItems(List<CommodityItemDto> items) {
        this.items = items;
    }
}
