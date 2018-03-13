package com.yatang.sc.inventory.dao;

import com.yatang.sc.inventory.domain.ItemInventoryQueryParamPo;
import com.yatang.sc.inventory.domain.ItemLocSoh;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemLocSohDao {
    int deleteByPrimaryKey(String itemId);

    int insert(ItemLocSoh record);

    int insertSelective(ItemLocSoh record);

    ItemLocSoh selectByPrimaryKey(String itemId);

    int updateByPrimaryKeySelective(ItemLocSoh record);

    int updateByPrimaryKey(ItemLocSoh record);

    int saleOut(Map<String, Object> condition);

    int agreeSaleOutd(Map<String, Object> condition);

    int bAgreeSaleOut(Map<String, Object> condition);

    int saleOutUndelivered(Map<String, Object> condition);

    int cancelSaleOrder(Map<String, Object> condition);

    /**
     * 根据商品id库存的基本信息
     *
     * @param productId
     * @param loc
     * @return
     * @author yangshuang
     */
    List<ItemLocSoh> getItemLocSohByProductId(@Param("productId") String productId, @Param("loc") String loc);

    /**
     * 更新库存
     *
     * @param itemLocSohPo
     * @return
     */
    int updateItemLocSoh(ItemLocSoh itemLocSohPo);

    /**
     * 根据商品id,逻辑仓库或者商品id+逻辑仓库查询入库商品信息
     *
     * @return
     */
    List<ItemLocSoh> queryItemInventoryListByParam(ItemInventoryQueryParamPo queryParamPo);

    /**
     * 增加对应商品编号的库存数量
     *
     * @param productId     商品id
     * @param quantity      调整数量
     * @param warehouseCode 仓库编号
     * @return
     */
    int addInventoryItemNum(@Param(value = "productId") String productId, @Param(value = "quantity") Long quantity, @Param(value = "warehouseCode") String warehouseCode);

    /**
     * 减少对应商品编号的库存数量
     *
     * @param productId     商品id
     * @param quantity      调整数量
     * @param warehouseCode 仓库编号
     * @return
     */
    int decreaseInventoryItemNum(@Param(value = "productId") String productId, @Param(value = "quantity") Long quantity, @Param(value = "warehouseCode") String warehouseCode);
    /**
     * 获取当前库存
     * @param productId
     * @param loc
     * @return
     */
    ItemLocSoh queryItemInventory(@Param("productId") String productId, @Param("loc") String loc);

    /**
     * 获取当前库存
     * @param productCode
     * @param loc
     * @return
     */
    ItemLocSoh queryItemInventoryByProductCode(@Param("productCode") String productCode, @Param("loc") String loc);

    ItemLocSoh getItemLocInventory(Map<String, Object> map);

    /**
     * 批量获取当前的库存
     * @param queryParamPo 查询参数对象
     * @return
     */
    List<ItemLocSoh> batchQueryItemInventoryListByList(List<ItemInventoryQueryParamPo> queryParamPo);

    /**
     * 采购退货：更新退货预留
     * @param itemLocSoh
     * @author yinyuxin
     * @return
     */
    int updateRtvQty(ItemLocSoh itemLocSoh);

    /**
     * 采购退货：更新退货预留 ，并且更新库存
     * @param itemLocSoh
     * @author yinyuxin
     * @return
     */
    int updateBatchStockOnHandByRtvQty(ItemLocSoh itemLocSoh);
}