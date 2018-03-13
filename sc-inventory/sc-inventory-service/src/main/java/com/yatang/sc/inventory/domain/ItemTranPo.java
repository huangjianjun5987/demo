package com.yatang.sc.inventory.domain;

import java.io.Serializable;

/**
 * @描述: 包裹Item和transData
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/5 18:43
 * @版本: v1.0
 */
public class ItemTranPo implements Serializable{

    private static final long serialVersionUID = -3466812068623072988L;
    private ItemLocSoh itemLocSoh;//库存

    private TranData tTranData;//交易事务

    public ItemLocSoh getItemLocSoh() {
        return itemLocSoh;
    }

    public void setItemLocSoh(ItemLocSoh itemLocSoh) {
        this.itemLocSoh = itemLocSoh;
    }

    public TranData gettTranData() {
        return tTranData;
    }

    public void settTranData(TranData tTranData) {
        this.tTranData = tTranData;
    }
}
