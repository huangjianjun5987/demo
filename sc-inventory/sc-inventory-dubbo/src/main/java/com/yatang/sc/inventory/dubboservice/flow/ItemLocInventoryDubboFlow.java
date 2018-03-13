package com.yatang.sc.inventory.dubboservice.flow;

import com.yatang.sc.inventory.domain.ItemInventoryQueryParamPo;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dto.ItemTranDtoList;
import com.yatang.sc.inventory.dto.OrderInventoryDto;

import java.util.List;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/9 11:48
 * @版本: v1.0
 */
public interface ItemLocInventoryDubboFlow {

    void reserveOrder(OrderInventoryDto orderInventoryDto, boolean shouldSplit) throws Exception;


    void saleOutOrder(OrderInventoryDto orderInventoryDto);


    void orderArrived(OrderInventoryDto orderInventoryDto);


    void orderUnArrive(OrderInventoryDto orderInventoryDto);


    void cancelReserveOrder(OrderInventoryDto orderInventoryDto);

    void saleReturn(OrderInventoryDto orderInventoryDto);


    void purchaseStockIn(ItemTranDtoList data) throws Exception;


    List<ItemLocSohDto> queryItemInventoryListByParam(ItemInventoryQueryParamPo queryParamPo);
}
