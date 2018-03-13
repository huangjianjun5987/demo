package com.yatang.sc.inventory.dao;


import com.yatang.sc.inventory.domain.ImAdjustmentItemPo;

import java.util.List;

public interface ImAdjustmentItemDao {
    int deleteByPrimaryKey(Long id);

    int insert(ImAdjustmentItemPo record);

    int insertSelective(ImAdjustmentItemPo record);

    ImAdjustmentItemPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImAdjustmentItemPo record);

    int updateByPrimaryKey(ImAdjustmentItemPo record);

    List<ImAdjustmentItemPo> selectItemsInfo(String adjustmentId,String productId);


    /**
     * 批量插入库存调整单实体
     *
     * @param imAdjustmentItems
     * @return
     */
    int batchInsertAdjustmentItem(List<ImAdjustmentItemPo> imAdjustmentItems);

    /**
     * 批量更新订单子项的移动加权平均价格,总成本
     * @param imAdjustmentItems
     * @return
     */
    int batchUpdateAdjustmentReceiptItem(List<ImAdjustmentItemPo> imAdjustmentItems);
}