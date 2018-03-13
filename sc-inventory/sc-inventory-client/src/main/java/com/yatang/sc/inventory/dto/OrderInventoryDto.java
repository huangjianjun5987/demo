package com.yatang.sc.inventory.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
public class OrderInventoryDto implements Serializable{

    private String id;
    private List<ItemInventoryDto> itemInventoryDtos;

    public OrderInventoryDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ItemInventoryDto> getItemInventoryDtos() {
        return itemInventoryDtos;
    }

    public void setItemInventoryDtos(List<ItemInventoryDto> itemInventoryDtos) {
        this.itemInventoryDtos = itemInventoryDtos;
    }
}
