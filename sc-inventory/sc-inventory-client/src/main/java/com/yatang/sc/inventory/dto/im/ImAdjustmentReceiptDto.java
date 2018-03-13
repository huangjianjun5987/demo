package com.yatang.sc.inventory.dto.im;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 库存调整单
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午10:39
 * @版本: v1.0
 */

public class ImAdjustmentReceiptDto implements Serializable {


    private static final long serialVersionUID = -850174562982017651L;
    private ImAdjustmentDto imAdjustment;// 主表

    private List<ImAdjustmentItemDto> imAdjustmentItems;//库存调整单item(商品)

    public ImAdjustmentDto getImAdjustment() {
        return imAdjustment;
    }

    public void setImAdjustment(ImAdjustmentDto imAdjustment) {
        this.imAdjustment = imAdjustment;
    }

    public List<ImAdjustmentItemDto> getImAdjustmentItems() {
        return imAdjustmentItems;
    }

    public void setImAdjustmentItems(List<ImAdjustmentItemDto> imAdjustmentItems) {
        this.imAdjustmentItems = imAdjustmentItems;
    }
}
