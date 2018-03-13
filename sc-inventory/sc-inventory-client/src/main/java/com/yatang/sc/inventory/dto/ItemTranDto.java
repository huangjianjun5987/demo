package com.yatang.sc.inventory.dto;

import java.io.Serializable;

/**
 * @描述: 包裹Item和transData
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/5 18:43
 * @版本: v1.0
 */
public class ItemTranDto implements Serializable{

    private ItemLocSohDto itemLocSoh;//库存

    private TTranDataDto tTranData;//交易事务

    public ItemLocSohDto getItemLocSoh() {
        return itemLocSoh;
    }

    public void setItemLocSoh(ItemLocSohDto itemLocSoh) {
        this.itemLocSoh = itemLocSoh;
    }

    public TTranDataDto gettTranData() {
        return tTranData;
    }

    public void settTranData(TTranDataDto tTranData) {
        this.tTranData = tTranData;
    }

    @Override
    public String toString() {
        return "ItemTranDto{" +
                "itemLocSoh=" + itemLocSoh +
                ", tTranData=" + tTranData +
                '}';
    }
}
