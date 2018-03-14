package com.yatang.sc.service;

import com.yatang.sc.order.domain.Order;

public interface ReserveOrderInventoryHelper {
    /**
     * 预留库存
     *
     * @param pOrder
     * @param splitByInventory 预留库存时，是否拆单
     * @return
     */
    boolean reserve(Order pOrder, boolean splitByInventory, String trigger);
}
