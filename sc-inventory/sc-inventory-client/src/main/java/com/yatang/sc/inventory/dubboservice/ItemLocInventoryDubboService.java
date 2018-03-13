package com.yatang.sc.inventory.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.dto.*;
import com.yatang.sc.inventory.dto.im.InventoryBIDto;

import java.util.List;
import java.util.Map;


/**
 * xiangyonghong on 2017/7/28.
 */
public interface ItemLocInventoryDubboService {


    /**
     * 销售入库
     */
    Response<Integer> reserveOrder(OrderInventoryDto orderInventoryDto, boolean shouldSplit);

    /**
     * 销售出库，仓库确认
     *
     * @return
     */
    Response<Boolean> saleOutOrder(OrderInventoryDto orderInventoryDto);

    /**
     * 销售出库，b端确认
     *
     * @return
     */
    Response<Boolean> orderArrived(OrderInventoryDto orderInventoryDto);

    /**
     * 销售出库未送达
     *
     * @return
     */
    Response<Boolean> orderUnArrive(OrderInventoryDto orderInventoryDto);

    /**
     * 取消销售订单
     *
     * @return
     */
    Response<Boolean> cancelReserveOrder(OrderInventoryDto orderInventoryDto);

    /**
     * 销售退货
     */
    Response<Boolean> saleReturn(OrderInventoryDto orderInventoryDto);






    /**
     * 订单的明细
     * @param data
     * @return
     */
    Response<Void> purchaseStockIn(ItemTranDtoList data);


    /**
     *根据商品id,逻辑仓库或者商品id+逻辑仓库查询入库商品信息
     * @return
     */
    Response<List<ItemLocSohDto>> queryItemInventoryListByParam(ItemInventoryQueryParamDto queryParamDto);
    /**
     * 分页查询库存(商品ID+逻辑仓库)
     * @param pageQueryParamDto
     * @return
     */
    Response<PageResult<ItemLocSohDto>> queryItemInventoryListByPageQueryParam(ItemInventoryPageQueryParamDto pageQueryParamDto);


    /**
     * 检查库存是否充足(只检查)
     * @param itemInventoryDto
     * @return
     */
    Response<Boolean> checkInventory(ItemInventoryDto itemInventoryDto);

    /**
     * 检查库存是否充足(只检查)
     * @param itemInventoryDtos
     * @return
     */
    Response<Map<String,Boolean>> checkAllInventory(List<ItemInventoryDto> itemInventoryDtos);


    /**
     * 分页查询库存bi报表
     * @param pageQueryParamDto
     * @return
     */
    Response<PageResult<InventoryBIDto>> queryInventoryBIByPageQueryParam(ItemInventoryPageQueryParamDto pageQueryParamDto);

    /**
     * 获取可用库存
     * @param itemInventoryDtos
     * @return
     */
    Response<Map<String,Long>> queryAllAvailableInventory(List<ItemInventoryDto> itemInventoryDtos);

    /**
     * 电商获取可用库存
     * @param
     * @return
     */
    Response<Map<String,Long>> queryAvailableInventoryForDS(String province, String city, List<String> productCodes);

    /**
     * 批量更新退货预留 增加用正数 减少用负数
     * @author yinyuxin
     * @param itemInventoryQueryParamDtos
     * @return
     */
    Response<Boolean> updateBatchRtvQty(List<ItemInventoryQueryParamDto> itemInventoryQueryParamDtos);

    /**
     * 根据退货预留批量更新现有库存 (预留 增加用正数 减少用负数)
     * @author yinyuxin
     * @param itemInventoryQueryParamDtos
     * @return
     */
    Response<Boolean> updateBatchStockOnHandByRtvQty(List<ItemInventoryQueryParamDto> itemInventoryQueryParamDtos,List<TranDataDto> tranDataDtos);


    /**
     * 批量查询库存的信息
     * @param itemInventoryDtos
     * @return
     */
    Response<BathCheckInventoryDto> batchCheckInventory(List<ItemInventoryDto> itemInventoryDtos);
}
