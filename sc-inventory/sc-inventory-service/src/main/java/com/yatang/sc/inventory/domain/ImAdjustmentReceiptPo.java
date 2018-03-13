package com.yatang.sc.inventory.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 库存调整单
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午10:39
 * @版本: v1.0
 */

public class ImAdjustmentReceiptPo implements Serializable {


    private static final long serialVersionUID = 4579466679324184015L;
    private ImAdjustmentPo imAdjustment;// 主表

    private List<ImAdjustmentItemPo> imAdjustmentItems;//库存调整单item(商品)


    public ImAdjustmentPo getImAdjustment() {
        return imAdjustment;
    }

    public void setImAdjustment(ImAdjustmentPo imAdjustment) {
        this.imAdjustment = imAdjustment;
    }

    public List<ImAdjustmentItemPo> getImAdjustmentItems() {
        return imAdjustmentItems;
    }

    public void setImAdjustmentItems(List<ImAdjustmentItemPo> imAdjustmentItems) {
        this.imAdjustmentItems = imAdjustmentItems;
    }
}
