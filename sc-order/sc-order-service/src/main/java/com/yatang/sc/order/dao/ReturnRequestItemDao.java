package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.returned.AppQueryReturnRequestItemPo;
import com.yatang.sc.order.domain.returned.ReturnQuantityPo;
import com.yatang.sc.order.domain.returned.ReturnRequestItemPo;

import java.util.List;

public interface ReturnRequestItemDao {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnRequestItemPo record);

    int insertSelective(ReturnRequestItemPo record);

    ReturnRequestItemPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnRequestItemPo record);

    int updateByPrimaryKey(ReturnRequestItemPo record);

    List<ReturnRequestItemPo> queryReturnRequestItem(String returnId);

    /**************************ys*****************/
    /**
     * 批量插入退货上的商品
     *
     * @param requestItems
     * @return
     */
    int batchInsertRequestItems(List<ReturnRequestItemPo> requestItems);


    /**
     * 批量更新
     *
     * @param requestItems
     * @return
     */
    int batchUpdateRequestItems(List<ReturnRequestItemPo> requestItems);

    List<AppQueryReturnRequestItemPo> queryReturnRequestItemByReturnId(String returnId);


    /**
     * 批量更新数量和总价
     * @param returnQuantityPos
     * @return
     */
    int batchUpdateQuantityAndRawTotalPrice(List<ReturnQuantityPo> returnQuantityPos);
}