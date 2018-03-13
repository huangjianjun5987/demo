package com.yatang.sc.inventory.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.inventory.domain.ItemInventoryPageQueryParamPo;
import com.yatang.sc.inventory.domain.ItemInventoryQueryParamPo;
import com.yatang.sc.inventory.domain.ItemLocSoh;
import com.yatang.sc.inventory.domain.ItemTranPoList;
import com.yatang.sc.inventory.domain.TranData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/7/27.
 */
public interface ItemLocSohService {

    /**
     * 销售出库，正常出库
     * @param itemId 商品id
     * @param loc 仓库编码
     * @param itemQty 订单确认数量
     * @param unitQuantity 销售内装数
     * @param preHarvestPinStatus 是否为先销后采模式
     */
    long reserveOrder(String itemId,String loc,long itemQty,int unitQuantity,boolean preHarvestPinStatus);

    /**
     * 销售出库，仓库确认
     * @param itemId 商品id
     * @param loc 仓库编码
     * @param itemQty 订单确认数量
     * @param stock 已出库数量
     */
    int saleOutOrder(String itemId,String loc,long itemQty,long stock);


    /**
     * 销售出库，b端确认（少个移动加权平均成本计算）
     * @param itemId 商品id
     * @param loc 仓库编码
     * @param completeQty 已签收数量
     * @param stock 已出库数量
     */
    int orderArrived(String itemId, String loc, long completeQty, long stock);

    /**
     * 销售出库未送达
     * @param itemId 商品id
     * @param loc 仓库编码
     * @param completeQty 已签收数量
     * @param stock 已出库数量
     */
    int orderUnArrive(String itemId,String loc,long completeQty,long stock);


    /**
     * 取消销售订单（文档描述模糊，需补充）
     * @param itemId 商品id
     * @param loc 仓库编码
     * @param itemQty 订单确认数量
     */
    int cancelReserveOrder(String itemId,String loc,long itemQty);

    /**
     * 销售退货（文档描述模糊，需补充）
     * @param itemId 商品id
     * @param loc 仓库编码
     * @param itemQty 实际退货数量
     */
    int saleReturn(String itemId,String loc,long itemQty);

    /**
     * 采购入库
     * @param data
     */
    void purchaseStockIn(ItemTranPoList data) throws Exception;
    /**
     *根据商品id,逻辑仓库或者商品id+逻辑仓库查询入库商品信息
     * @return
     */
    List<ItemLocSoh> queryItemInventoryListByParam(ItemInventoryQueryParamPo queryParamPo);


    /**
     * 分页查询库存(商品ID+逻辑仓库)
     * @param queryParamPo 查询条件
     * @return
     */
    PageInfo<ItemLocSoh> queryItemInventoryListByPageQueryParam(ItemInventoryPageQueryParamPo queryParamPo);

    /**
     * 检查库存是否充足(只检查)
     * @return
     */
    Boolean checkInventory(String productId,String loc,Long qty);


    /**
     * 获取当前库存
     * @param productId
     * @param loc
     * @return
     */
    ItemLocSoh queryItemInventory(@Param("productId") String productId, @Param("loc") String loc);

    /**
     *  批量更新退货预留
     * @author yinyuxin
     * @param itemInventoryQueryParamPos
     * @return
     */
    boolean updateRtvQty(List<ItemInventoryQueryParamPo> itemInventoryQueryParamPos);

    /**
     *  根据退货预留批量更新现有库存 (根据预留数的正负决定增减库存)
     * @author yinyuxin
     * @param itemInventoryQueryParamPos
     * @return
     */
    boolean updateBatchStockOnHandByRtvQty(List<ItemInventoryQueryParamPo> itemInventoryQueryParamPos,List<TranData> tranDatas);

    /**
     * 获取当前库存
     * @param productCode
     * @param loc
     * @return
     */
    ItemLocSoh queryItemInventoryByProductCode(@Param("productCode") String productCode, @Param("loc") String loc);

    /**
     * 查询移动加权平均成本
     * @param map
     * @return
     */
    ItemLocSoh getItemLocInventory(Map<String, Object> map);


    /**
     * 批量查询商品库存
     * @param itemInventoryQueryParamPos
     * @return
     */
    List<ItemLocSoh> batchQueryItemInventoryListByList(List<ItemInventoryQueryParamPo> itemInventoryQueryParamPos);


}
