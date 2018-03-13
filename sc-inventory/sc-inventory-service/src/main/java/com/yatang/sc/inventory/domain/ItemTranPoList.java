package com.yatang.sc.inventory.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:
 * @类名:  订单的明细返回(仓库和list(入库表信息和仓库信息))
 * @作者: yangshuang
 * @创建时间: 2017/8/10 15:22
 * @版本: v1.0
 */

public class ItemTranPoList implements Serializable {


    private static final long serialVersionUID = -9052152843606454946L;
    private String loc;//仓库编号
    private List<ItemTranPo> itemTrans;//订单子项集合

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public List<ItemTranPo> getItemTrans() {
        return itemTrans;
    }

    public void setItemTrans(List<ItemTranPo> itemTrans) {
        this.itemTrans = itemTrans;
    }

    @Override
    public String toString() {
        return "ItemTranPoList{" +
                "loc='" + loc + '\'' +
                ", itemTrans=" + itemTrans +
                '}';
    }
}
