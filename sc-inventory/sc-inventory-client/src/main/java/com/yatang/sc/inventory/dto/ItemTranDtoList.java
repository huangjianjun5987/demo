package com.yatang.sc.inventory.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:
 * @类名:  订单的明细返回
 * @作者: yangshuang
 * @创建时间: 2017/8/10 15:22
 * @版本: v1.0
 */

public class ItemTranDtoList implements Serializable {
    private static final long serialVersionUID = 8748458791231737745L;


    private String loc;//仓库编号
    private List<ItemTranDto> itemTrans;//订单子项集合

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public List<ItemTranDto> getItemTrans() {
        return itemTrans;
    }

    public void setItemTrans(List<ItemTranDto> itemTrans) {
        this.itemTrans = itemTrans;
    }

    @Override
    public String toString() {
        return "ItemTranDtoList{" +
                "loc='" + loc + '\'' +
                ", itemTrans=" + itemTrans +
                '}';
    }
}
