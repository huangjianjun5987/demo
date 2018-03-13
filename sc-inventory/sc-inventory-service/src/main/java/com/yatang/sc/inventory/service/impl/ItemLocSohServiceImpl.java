package com.yatang.sc.inventory.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.common.utils.BigDemicalUtil;
import com.yatang.sc.inventory.dao.ItemLocSohDao;
import com.yatang.sc.inventory.dao.TranDataDao;
import com.yatang.sc.inventory.domain.ItemInventoryPageQueryParamPo;
import com.yatang.sc.inventory.domain.ItemInventoryQueryParamPo;
import com.yatang.sc.inventory.domain.ItemLocSoh;
import com.yatang.sc.inventory.domain.ItemTranPo;
import com.yatang.sc.inventory.domain.ItemTranPoList;
import com.yatang.sc.inventory.domain.TranData;
import com.yatang.sc.inventory.service.ItemLocSohService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiangyonghong on 2017/7/27.
 */
@Slf4j
@Service
public class ItemLocSohServiceImpl implements ItemLocSohService {

    @Autowired
    private ItemLocSohDao itemLocSohDao;

    @Autowired
    private TranDataDao tranDataDao;

    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    @Override
    public long reserveOrder(String itemId, String loc, long itemQty, int unitQuantity, boolean preHarvestPinStatus) {
        log.info("{}:{}->商品库存为：{}", itemId, loc, preHarvestPinStatus ? "先采后销" : "先销后采");

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("itemId", itemId);
        condition.put("loc", loc);
        condition.put("itemQty", itemQty);

        int count = itemLocSohDao.saleOut(condition);

        if (count > 0) {
            log.info("库存预留成功:{},{},{}", itemId, loc, itemQty);
            return 0;
        }
        log.info("{}:{}->商品预留库存不足:{}", itemId, loc, itemQty);

        /**
         * 判断为先销后采 按照正常流程，库存不足就无法采购
         */

            /*if (!preHarvestPinStatus) {
                throw new RuntimeException("库存不足");
            }*/

        ItemInventoryQueryParamPo queryParamPo = new ItemInventoryQueryParamPo();
        queryParamPo.setLogicWareHouseCode(loc);
        queryParamPo.setProductId(itemId);

        List<ItemLocSoh> itemLocSohs = queryItemInventoryListByParam(queryParamPo);
        if (CollectionUtils.isEmpty(itemLocSohs)) {
            log.info("库存预留失败:{},{},{}，库存信息不存在", itemId, loc, itemQty);
            return itemQty;
        }

        if (itemLocSohs.size() > 1) {
            log.warn("查询到多于一条记录的库存信息:loc:{},itemId:{}", loc, itemId);
        }
        ItemLocSoh itemLocSoh = itemLocSohs.get(0);
        Long stockOnHandCount = itemLocSoh.getStockOnHand() - itemLocSoh.getOrderReservedQty();

        log.info("当前可用库存：{}:{}-->{},销售内装数：{}", itemId, loc, stockOnHandCount, unitQuantity);
        if (stockOnHandCount <= 0) {
            return itemQty;
        }

        Long saleQuantity = stockOnHandCount / unitQuantity;
        if (saleQuantity <= 0) {
            return itemQty;
        }

        Long realSaleOut = Long.valueOf(saleQuantity * unitQuantity);
        condition.put("itemQty", realSaleOut);
        count = itemLocSohDao.saleOut(condition);
        if (count > 0) {
            log.info("库存部分预留成功:{},{},{}->{}", itemId, loc, itemQty, realSaleOut);
            return itemQty - realSaleOut;
        }
        log.info("库存不足,预留失败:{},{},{}", itemId, loc, itemQty);
        return itemQty;
    }

    @Override
    public int saleOutOrder(String itemId, String loc, long itemQty, long stock) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("itemId", itemId);
        condition.put("loc", loc);
        condition.put("itemQty", itemQty);
        condition.put("stock", stock);
        return itemLocSohDao.agreeSaleOutd(condition);
    }

    @Override
    public int orderArrived(String itemId, String loc, long completeQty, long stock) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("itemId", itemId);
        condition.put("loc", loc);
        condition.put("completeQty", completeQty);
        condition.put("stock", stock);

        int result = itemLocSohDao.bAgreeSaleOut(condition);
        if (result <= 0) {
            return result;
        }
         /*   if (completeQty <= stock) {
                // 签收重新计算商品地点的移动加权平均成本
                List<ItemLocSoh> itemLocSohList = itemLocSohDao.getItemLocSohByProductId(itemId, loc);
                if (itemLocSohList.size() == 1) {
                    ItemLocSoh itemLocSohPo = itemLocSohList.get(0);
                    if (itemLocSohPo.getAvCost() != null && itemLocSohPo.getUnitCost() != null && itemLocSohPo.getStockOnHand() != null) {
                        BigDecimal bgAvCost = new BigDecimal(String.valueOf(itemLocSohPo.getStockOnHand() * itemLocSohPo.getAvCost() + itemLocSohPo.getStockOnHand()
                                * itemLocSohPo.getUnitCost())).divide(new BigDecimal(itemLocSohPo.getStockOnHand()), 2, BigDecimal.ROUND_HALF_UP);// 四舍五入
                        itemLocSohPo.setAvCost(bgAvCost.doubleValue());
                        itemLocSohDao.updateItemLocSoh(itemLocSohPo);
                    }
                }
            }*/
        return result;
    }

    @Override
    public int orderUnArrive(String itemId, String loc, long completeQty, long stock) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("itemId", itemId);
        condition.put("loc", loc);
        condition.put("completeQty", completeQty);
        condition.put("stock", stock);

        int result = itemLocSohDao.saleOutUndelivered(condition);
        if (result <= 0) {
            return result;
        }
        // 出库未送达，重新计算商品地点的移动加权平均成本
        /*    List<ItemLocSoh> itemLocSohList = itemLocSohDao.getItemLocSohByProductId(itemId, loc);
            if (itemLocSohList.size() == 1) {
                ItemLocSoh itemLocSohPo = itemLocSohList.get(0);
                if (itemLocSohPo.getAvCost() != null && itemLocSohPo.getUnitCost() != null && itemLocSohPo.getStockOnHand() != null) {
                    BigDecimal bgAvCost = new BigDecimal(String.valueOf(itemLocSohPo.getStockOnHand() * itemLocSohPo.getAvCost() + itemLocSohPo.getStockOnHand()
                            * itemLocSohPo.getUnitCost())).divide(new BigDecimal(itemLocSohPo.getStockOnHand()), 2, BigDecimal.ROUND_HALF_UP);// 四舍五入
                    itemLocSohPo.setAvCost(bgAvCost.doubleValue());
                    itemLocSohDao.updateItemLocSoh(itemLocSohPo);
                }
            }*/
        return result;
    }

    @Override
    public int cancelReserveOrder(String itemId, String loc, long itemQty) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("itemId", itemId);
        condition.put("loc", loc);
        condition.put("itemQty", itemQty);

        return itemLocSohDao.cancelSaleOrder(condition);
    }

    @Override
    public int saleReturn(String itemId, String loc, long itemQty) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("itemId", itemId);
        condition.put("loc", loc);
        condition.put("itemQty", itemQty);

        ItemLocSoh itemLocSohPo = itemLocSohDao.queryItemInventory(itemId, loc);
        Long stockOnHand = itemLocSohPo.getStockOnHand();//当前库存
        Long sellInTransitQty = itemLocSohPo.getSellInTransitQty();//销售在途数量
        Long currentStockOnHand = stockOnHand + itemQty;//新的库存
        double vatRate = 0.0;//税率
        BigDecimal stockInCost = new BigDecimal(itemQty * itemLocSohPo.getUnitCost()).divide(new BigDecimal(1 + vatRate), 2, BigDecimal.ROUND_HALF_UP);//入库时的总成本
        BigDecimal bgAvCost = new BigDecimal(String.valueOf((stockOnHand + sellInTransitQty) * itemLocSohPo.getAvCost() + stockInCost.doubleValue())).divide(new BigDecimal(currentStockOnHand + sellInTransitQty), 2, BigDecimal.ROUND_HALF_UP);// 四舍五入

        Long currentSellInTransitQty = itemLocSohPo.getSellInTransitQty() - itemQty;//新的销售在途
        itemLocSohPo.setSellInTransitQty(currentSellInTransitQty);
        itemLocSohPo.setStockOnHand(currentStockOnHand);
        itemLocSohPo.setAvCost(bgAvCost.doubleValue());

        return itemLocSohDao.updateItemLocSoh(itemLocSohPo);
    }

    @Override
    public void purchaseStockIn(ItemTranPoList data) throws Exception {

        String loc = data.getLoc();
        String lockPath = "/inventory_update_" + loc;//根据仓库编号枷锁 //
        List<ItemTranPo> itemTranDtos = data.getItemTrans();
        log.info("start---purchaseStockIn--采购入库更新入库信息,data:{}", JSON.toJSONString(data));
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock(30, TimeUnit.SECONDS);

            for (ItemTranPo itemTranDto : itemTranDtos) {
                ItemLocSoh itemLocSohDto = itemTranDto.getItemLocSoh();//库存
                TranData tTranDataDto = itemTranDto.gettTranData();//交易事务
                String productId = itemLocSohDto.getItemId();//商品id
                if (StringUtils.isBlank(productId)) {
                    log.error("采购入库--商品id为空");
                    throw new RuntimeException("采购入库--商品id为空");
                }
                if (StringUtils.isBlank(itemLocSohDto.getProductCode())) {
                    log.error("商品id为:{},入库仓库编号为:{}的商品编码为空", productId, loc);
                    throw new RuntimeException("商品id为:" + productId + ",入库仓库编号为:" + loc + "商品编码为空");
                }
                if (StringUtils.isBlank(tTranDataDto.getVatRate())) {
                    log.error("商品id为:{},入库仓库编号为:{},的进项税为空", productId, loc);
                    throw new RuntimeException("商品id为:" + productId + ",入库仓库编号为:" + loc + "的进项税为空");
                }
                List<ItemLocSoh> itemLocSohList = itemLocSohDao.getItemLocSohByProductId(productId, loc);//查询库存是否存在该商品
                log.info("ItemLocSohServiceImpl---purchaseStockIn 采购入库查询库存返回结果,data:{}", JSON.toJSONString(itemLocSohList));
                //1.新增操作
                if (itemLocSohList.size() == 0) {//没有数据
                    log.info("执行库存新增增加操作,商品id:{},仓库编号:{}", productId, loc);
                    addPurchaseStockIn(loc, itemLocSohDto, tTranDataDto);//新增入库操作
                }
                //2.更新操作
                if (itemLocSohList.size() == 1) {//采购入库A商品，现有库存为10，当前WAC为3，采购入库成本为5，入库数量100；
                    log.info("执行库存更新操作,商品id:{},仓库编号:{}", productId, loc);
                    ItemLocSoh itemLocSohPo = itemLocSohList.get(0);
                    updateStockOnHand(loc, itemLocSohPo, itemTranDto);//更新仓库
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            rLock.unlock();
        }
    }

    @Override
    public List<ItemLocSoh> queryItemInventoryListByParam(ItemInventoryQueryParamPo queryParamPo) {
        return itemLocSohDao.queryItemInventoryListByParam(queryParamPo);
    }

    @Override
    public PageInfo<ItemLocSoh> queryItemInventoryListByPageQueryParam(ItemInventoryPageQueryParamPo queryParamPo) {
        //bean切换
        ItemInventoryQueryParamPo inventoryQueryParamPo = BeanConvertUtils.convert(queryParamPo, ItemInventoryQueryParamPo.class);
        //分页查询
        PageHelper.startPage(queryParamPo.getPageNum(), queryParamPo.getPageSize());
        List<ItemLocSoh> list = itemLocSohDao.queryItemInventoryListByParam(inventoryQueryParamPo);
        PageInfo<ItemLocSoh> pageInfo = new PageInfo<ItemLocSoh>(list);
        return pageInfo;
    }

    @Override
    public Boolean checkInventory(String productId, String loc, Long qty) {
        ItemInventoryQueryParamPo queryParamPo = new ItemInventoryQueryParamPo();
        queryParamPo.setLogicWareHouseCode(loc);
        queryParamPo.setProductId(productId);
        List<ItemLocSoh> itemLocSohList = queryItemInventoryListByParam(queryParamPo);
        if (CollectionUtils.isEmpty(itemLocSohList)) {
            log.info("仓库中无此商品:{}->{}", productId, loc);
            return false;
        }
        ItemLocSoh itemLocSoh = itemLocSohList.get(0);
        if (itemLocSoh.getStockOnHand() < itemLocSoh.getOrderReservedQty() + itemLocSoh.getRtvQty() + qty) {
            return false;
        }
        return true;
    }

    @Override
    public ItemLocSoh queryItemInventory(String productId, String loc) {
        return itemLocSohDao.queryItemInventory(productId, loc);
    }

    /**
     * 获取当前库存
     *
     * @param productCode
     * @param loc
     * @return
     */
    public ItemLocSoh queryItemInventoryByProductCode(String productCode, String loc) {
        return itemLocSohDao.queryItemInventoryByProductCode(productCode, loc);
    }


    @Override
    public boolean updateRtvQty(List<ItemInventoryQueryParamPo> itemInventoryQueryParamPos) {
        if (itemInventoryQueryParamPos == null || itemInventoryQueryParamPos.size() <= 0) {
            throw new RuntimeException("参数为空，操作失败");
        }
        String lockPath = "/inventory_update_" + itemInventoryQueryParamPos.get(0).getLogicWareHouseCode();//根据仓库编号枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
            try {
                rLock.lock(30, TimeUnit.SECONDS);
            for (ItemInventoryQueryParamPo itemInventoryQueryParamPo : itemInventoryQueryParamPos) {
                ItemLocSoh itemLocSohParam = new ItemLocSoh();
                itemLocSohParam.setLoc(itemInventoryQueryParamPo.getLogicWareHouseCode());
                itemLocSohParam.setItemId(itemInventoryQueryParamPo.getProductId());
                itemLocSohParam.setProductCode(itemInventoryQueryParamPo.getProductCode());
                itemLocSohParam.setRtvQty(itemInventoryQueryParamPo.getRefundAmount() == null ? 0L : itemInventoryQueryParamPo.getRefundAmount().longValue());
                itemLocSohDao.updateRtvQty(itemLocSohParam);
            }
        } finally {
            rLock.unlock();
        }
        return true;
    }


    @Override
    public boolean updateBatchStockOnHandByRtvQty(List<ItemInventoryQueryParamPo> itemInventoryQueryParamPos, List<TranData> tranDatas) {
        if (itemInventoryQueryParamPos == null || itemInventoryQueryParamPos.size() <= 0) {
            throw new RuntimeException("参数为空，操作失败");
        }
        String lockPath = "/inventory_update_" + itemInventoryQueryParamPos.get(0).getLogicWareHouseCode();//根据仓库编号枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            for (ItemInventoryQueryParamPo itemInventoryQueryParamPo : itemInventoryQueryParamPos) {
                //1、根据商品code、逻辑仓code查询库存信息表
                //List<ItemLocSoh> itemLocSohs = itemLocSohDao.queryItemInventoryListByParam(itemInventoryQueryParamPo);
                //if (itemLocSohs == null || itemLocSohs.size() <= 0) {
                //throw new RuntimeException("编号为:" + itemInventoryQueryParamPo.getProductCode() + "商品库存记录为空");
                //}
                //ItemLocSoh itemLocSoh = itemLocSohs.get(0);
                //itemLocSoh.setRtvQty((itemLocSoh.getRtvQty() == null ? 0 : itemLocSoh.getRtvQty()) + (itemInventoryQueryParamPo.getRealRefundAmount() == null ? 0 : itemInventoryQueryParamPo.getRefundAmount()));
                //itemLocSoh.setStockOnHand((itemLocSoh.getStockOnHand() == null ? 0 : itemLocSoh.getStockOnHand()) - (itemInventoryQueryParamPo.getRealRefundAmount() == null ? 0 : itemInventoryQueryParamPo.getRealRefundAmount()));
                //itemLocSohDao.updateByPrimaryKeySelective(itemLocSoh);

                ItemLocSoh itemLocSohParam = new ItemLocSoh();
                itemLocSohParam.setLoc(itemInventoryQueryParamPo.getLogicWareHouseCode());
                itemLocSohParam.setItemId(itemInventoryQueryParamPo.getProductId());
                itemLocSohParam.setProductCode(itemInventoryQueryParamPo.getProductCode());
                itemLocSohParam.setRtvQty(itemInventoryQueryParamPo.getRefundAmount() == null ? 0L : itemInventoryQueryParamPo.getRefundAmount().longValue());
                itemLocSohParam.setStockOnHand(itemInventoryQueryParamPo.getRealRefundAmount() == null ? 0L : itemInventoryQueryParamPo.getRealRefundAmount().longValue());
                itemLocSohDao.updateBatchStockOnHandByRtvQty(itemLocSohParam);
            }
            for (TranData tranData : tranDatas) {
                tranDataDao.insertSelective(tranData);
            }
        } finally {
            rLock.unlock();
        }
        return true;
    }


    /**
     * 新增入库
     * itemLocSo需要传递的(仓库编号(loc)和商品编号(ItemId)和入库成本(unitCost)和入库数量(stockOnHand)和更新者Id)
     * tran_data 商品分类
     *
     * @param loc
     * @param itemLocSohDto 库存
     * @param tTranDataDto  交易数据
     */
    public void addPurchaseStockIn(String loc, ItemLocSoh itemLocSohDto, TranData tTranDataDto) {

        log.info("----addPurchaseStockIn-----新增入库库存>>仓库编号:{},库存信息:{},交易tran_data:{}", loc, JSON.toJSONString(itemLocSohDto), JSON.toJSONString(tTranDataDto));
        String productId = itemLocSohDto.getItemId();//商品id
        ItemLocSoh newItemLocSoh = new ItemLocSoh();//新建库存信息

        if (itemLocSohDto.getUnitCost() == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的入库成本为空", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的入库成本为空");
        }
        if (itemLocSohDto.getStockOnHand() == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的入库数量为空", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的入库数量为空");
        }
        //1.1 item_loc_soh库操作数据 商品编号和仓库编号，商品成本,移动平均成本,现有库存(入库数量),last_update_id
        //1.1.2计算移动平均价 计算公式 移动加权平均成本为5，WAC=（现有库存*当前WAC+入库数量*入库成本）/当前库存+入库数量=（0*6+5*100）/0+100=5
        //10/16版本的现有wac计算方式:WAC=（当前WAC*现有库存+（单据商品成本/（1+税率））*采购入库数量）/现有库存+采购入库数量
        Double unitCost = itemLocSohDto.getUnitCost() == null ? 0 : itemLocSohDto.getUnitCost();//商品成本，15位有效数字，取值业务单据上商品对应主供应商的采购价；
        newItemLocSoh.setItemId(productId);
        newItemLocSoh.setLoc(loc);
        newItemLocSoh.setUnitCost(itemLocSohDto.getUnitCost());//入库成本
        //针对新增入库 WAC=（单据商品成本/（1+税率）
        double vatRate = 0.0;
        if (StringUtils.isNotEmpty(tTranDataDto.getVatRate())) {
            vatRate = Double.parseDouble(tTranDataDto.getVatRate()) * 0.01;//解析税率
        }
        BigDecimal avCost = new BigDecimal(String.valueOf(unitCost)).divide(new BigDecimal(1 + vatRate), 2,
                BigDecimal.ROUND_HALF_UP);// 四舍五入
        newItemLocSoh.setAvCost(avCost.doubleValue());
        newItemLocSoh.setStockOnHand(itemLocSohDto.getStockOnHand());//现有库存将采购数量设定为当前入库库存
        newItemLocSoh.setLastUpdateDatetime(new Date());
        newItemLocSoh.setProductCode(itemLocSohDto.getProductCode());
        newItemLocSoh.setLastUpdateId("sys_addPurchaseStock");
        //插入操作
        boolean insertSuccess = itemLocSohDao.insert(newItemLocSoh) == 1;
        if (!insertSuccess) {
            log.error("新增入库>>商品编号为:{},入库仓库编号为:{}的入库数量为{}入库失败", productId, loc, itemLocSohDto.getStockOnHand());
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的入库数量为空");
        }
        //1.2 tran_data库操作
        // 首次采购入库A商品，期初库存为0，入库仓为101，成本为5，入库数量100； 采购入库生成20的tran_data
        //商品编号,部类,大类,中类,小，仓库编号,事务日期,事务类型,交易数量
        TranData tranData = BeanConvertUtils.convert(tTranDataDto, TranData.class);
        tranData.setItem(productId);//商品编号
        tranData.setLocation(loc);//仓库编号
        //商品的分类

        tranData.setTranCode("20");//交易类型，采购
        tranData.setTranDate(new Date());


        //交易数量*（单据商品成本/（1+税率）） //2017/10/16 调整
        BigDecimal stockInCost = new BigDecimal(itemLocSohDto.getStockOnHand() * itemLocSohDto.getUnitCost()).divide(new BigDecimal(1 + vatRate), 2, BigDecimal.ROUND_HALF_UP);//入库时的总成本
        tranData.setUnits(itemLocSohDto.getStockOnHand());//交易数量
        tranData.setTotalCost(stockInCost.doubleValue());//总成本
        //税额=单据商品成本*税率 //2017/10/16 调额

        double afterVatRateUnitCost = unitCost / (1 + vatRate);//去掉含税
        BigDecimal bigTaxes = new BigDecimal(afterVatRateUnitCost * vatRate).setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
        tranData.setTaxes(bigTaxes.doubleValue());
        //插入操作
        tranDataDao.insert(tranData);

    }

    /**
     * 更新入库
     *
     * @param loc
     * @param itemLocSohPo 已有库存
     * @param itemTranDto  待入库库存和事务的相关信息
     */
    public void updateStockOnHand(String loc, ItemLocSoh itemLocSohPo, ItemTranPo itemTranDto) {
        log.info("----updateStockOnHand-----更新入库入库库存>>仓库编号:{},库存信息:{},交易tran_data:{}", loc, JSON.toJSONString(itemLocSohPo), JSON.toJSONString(itemTranDto));
        ItemLocSoh itemLocSohDto = itemTranDto.getItemLocSoh();//新增库存
        TranData tTranDataDto = itemTranDto.gettTranData();//新增事务
        Long stockOnHand = itemLocSohPo.getStockOnHand();//当前库存
        String productId = itemLocSohDto.getItemId();//商品id
        if (stockOnHand == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的现有库存为空", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的现有库存为空");
        }

        //2.1库存大于0
        if (stockOnHand > 0) {
            //现有库存大于0 的场景
            updateInventorySoHGreaterThanZero(loc, itemLocSohPo, itemLocSohDto, tTranDataDto, stockOnHand, productId);

        } else if (stockOnHand == 0) {//现有库存等于零
            updateInventorySoHEqualZero(loc, itemLocSohPo, itemLocSohDto, tTranDataDto, productId);


        } else {//stockOnHand小于零的情况 需要修复数据
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的库存数量为负");
        }


    }


    /**
     * 库存大于0的情况
     *
     * @param loc
     * @param itemLocSohPo
     * @param itemLocSohDto
     * @param tTranDataDto
     * @param stockOnHand
     * @param productId
     */
    private void updateInventorySoHGreaterThanZero(String loc, ItemLocSoh itemLocSohPo, ItemLocSoh itemLocSohDto, TranData tTranDataDto, Long stockOnHand, String productId) {
        //2.1.1操作库存表
        //（当前WAC*现有库存+（单据商品成本/（1+税率））*采购入库数量）/现有库存+采购入库数量 //2017/10/16 调整后的wac
        if (itemLocSohDto.getStockOnHand() == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的入库数量为空", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "入库数量为空");
        }


        if (itemLocSohPo.getAvCost() == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的库存中的移动平均价为null值", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的库存中的移动平均价为null值");
        }

        //在途数量

        Long sellInTransitQty = itemLocSohPo.getSellInTransitQty();//销售在途数量
        if (sellInTransitQty == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的库存中的销售在途数量为null值", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的库存中的销售在途数量为null值");
        }
        if (itemLocSohDto.getUnitCost() == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的入库成本为null值", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的入库成本为null值");
        }

        double vatRate = 0.0;//税率
        if (StringUtils.isNotBlank(tTranDataDto.getVatRate())) {
            vatRate = Double.parseDouble(tTranDataDto.getVatRate()) * 0.01;//解析税率
        }


        Long currentStockOnHand = stockOnHand + itemLocSohDto.getStockOnHand();//新的库存
        BigDecimal stockInCost = new BigDecimal(itemLocSohDto.getStockOnHand() * itemLocSohDto.getUnitCost()).divide(new BigDecimal(1 + vatRate), 2, BigDecimal.ROUND_HALF_UP);//入库时的总成本


        //判定实际库存是否为小于等于0，如 则停止计算wac,当前采购入库价格更新为wac
        Double beforeAvCost = itemLocSohPo.getAvCost();
        long realStockOnHand = stockOnHand + sellInTransitQty;//实际库存
        if (realStockOnHand <= 0) {//
            BigDecimal avCost = new BigDecimal(String.valueOf(itemLocSohDto.getUnitCost())).divide(new BigDecimal(1 + vatRate), 2,
                    BigDecimal.ROUND_HALF_UP);// 四舍五入
            itemLocSohPo.setAvCost(avCost.doubleValue());

        } else {

            BigDecimal bgAvCost = new BigDecimal(String.valueOf((stockOnHand + sellInTransitQty) * itemLocSohPo.getAvCost() + stockInCost.doubleValue())).divide(new BigDecimal(currentStockOnHand + sellInTransitQty), 2, BigDecimal.ROUND_HALF_UP);// 四舍五入
            itemLocSohPo.setAvCost(bgAvCost.doubleValue());
        }


        itemLocSohPo.setUnitCost(itemLocSohDto.getUnitCost());//入库成本需要更新  2017/08/06
        itemLocSohPo.setStockOnHand(currentStockOnHand);//现有库存将采购数量设定为当前入库库存
        itemLocSohPo.setLastUpdateDatetime(new Date());
        itemLocSohPo.setProductCode(itemLocSohDto.getProductCode());
        itemLocSohPo.setLastUpdateId("sys_updatePurchaseStock");
        //插入操作
        boolean updateSuccess = itemLocSohDao.updateItemLocSoh(itemLocSohPo) == 1;//更新库存表
        if (!updateSuccess) {
            log.error("新增入库>>商品编号为:{},入库仓库编号为:{}的入库数量为{}入库失败", productId, loc, itemLocSohDto.getStockOnHand());
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的入库数量为空");
        }


        //2.1.2 加入tran_data操作明细 业务场景为2
        if (realStockOnHand <= 0) {//生成70成本差异


            TranData tranDataPo70 = BeanConvertUtils.convert(tTranDataDto, TranData.class);
            tranDataPo70.setTranCode("70");
            tranDataPo70.setTranDate(new Date());
            tranDataPo70.setItem(productId);//商品编号
            tranDataPo70.setLocation(loc);//仓库编号

            tranDataPo70.setUnits(itemLocSohDto.getStockOnHand());//交易数量

            //70成本差异=（stock_on_hand+sell_in_tqty）*（当前wac-入库成本）;  成本差异是否需要加上new_stock_in(新增入库)==》否
            double afterVatRateUnitCost = itemLocSohDto.getUnitCost() / (1 + vatRate);//去掉含税后的成本
            BigDecimal diff = new BigDecimal(realStockOnHand).multiply(new BigDecimal(beforeAvCost - BigDemicalUtil.round(afterVatRateUnitCost,2)));
            tranDataPo70.setTotalCost(diff.doubleValue());
            tranDataPo70.setVatRate(tTranDataDto.getVatRate());// 采购单上没有设定进项税
            //计算税额

            BigDecimal bigTaxes = new BigDecimal(afterVatRateUnitCost * vatRate).setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
            tranDataPo70.setTaxes(bigTaxes.doubleValue());
            tranDataDao.insert(tranDataPo70);


        }

        TranData tranDataPo = BeanConvertUtils.convert(tTranDataDto, TranData.class);
        tranDataPo.setItem(productId);//商品编号
        tranDataPo.setLocation(loc);//仓库编号
        tranDataPo.setTranDate(new Date());
        tranDataPo.setTranCode("20");//交易类型，采购
        tranDataPo.setUnits(itemLocSohDto.getStockOnHand());//交易数量
        tranDataPo.setTotalCost(stockInCost.doubleValue());
        double afterVatRateUnitCost = itemLocSohDto.getUnitCost() / (1 + vatRate);//去掉含税后的成本
        BigDecimal bigTaxes = new BigDecimal(afterVatRateUnitCost * vatRate).setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
        tranDataPo.setTaxes(bigTaxes.doubleValue());
        tranDataDao.insert(tranDataPo);
    }

    /**
     * 库存调整 stockOnHand 大于零的情况
     *
     * @param loc
     * @param itemLocSohPo
     * @param itemLocSohDto
     * @param tTranDataDto
     * @param productId
     */
    private void updateInventorySoHEqualZero(String loc, ItemLocSoh itemLocSohPo, ItemLocSoh itemLocSohDto, TranData tTranDataDto, String productId) {
        //2.1.1操作库存表
        Long sellInTransitQty = itemLocSohPo.getSellInTransitQty();//销售在途数量
        if (sellInTransitQty == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的库存中的在途数量为null值", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的库存中的在途数量为null值");
        }
        //计算移动平均价=现有库存(在途数量)*当前WAC+交易数量*采购入库成本）/（现有库存(在途数量)+采购入库数量）
        Long currentStockOnHand = itemLocSohDto.getStockOnHand();//新的库存
        if (currentStockOnHand == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的入库数量为空", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "入库数量为空");
        }
        if (itemLocSohDto.getUnitCost() == null) {
            log.error("商品编号为:{},入库仓库编号为:{}的入库成本为空", productId, loc);
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的入库成本为空");
        }
        double vatRate = 0.0;//税率
        if (StringUtils.isNotEmpty(tTranDataDto.getVatRate())) {
            vatRate = Double.parseDouble(tTranDataDto.getVatRate()) * 0.01;//解析税率
        }
        //（当前WAC*现有库存(销售在途)+（单据商品成本/（1+税率））*采购入库数量）/现有库存+采购入库数量 //2017/10/16 调整后的wac
        BigDecimal stockInCost = new BigDecimal(itemLocSohDto.getStockOnHand() * itemLocSohDto.getUnitCost()).divide(new BigDecimal(1 + vatRate), 2, BigDecimal.ROUND_HALF_UP);//入库时的总成本
        //判定实际库存是否为小于等于0，如 则停止计算wac,当前采购入库价格更新为wac
        long realStockOnHand = sellInTransitQty;
        Double beforeAvCost = itemLocSohPo.getAvCost();
        if (realStockOnHand <= 0) {//
            BigDecimal avCost = new BigDecimal(String.valueOf(itemLocSohDto.getUnitCost())).divide(new BigDecimal(1 + vatRate), 2,
                    BigDecimal.ROUND_HALF_UP);// 四舍五入
            itemLocSohPo.setAvCost(avCost.doubleValue());


        } else {


            BigDecimal bgAvCost = new BigDecimal(String.valueOf(sellInTransitQty * itemLocSohPo.getAvCost() + stockInCost.doubleValue())).divide(new BigDecimal(currentStockOnHand + sellInTransitQty), 2, BigDecimal.ROUND_HALF_UP);// 四舍五入
            double avCost = bgAvCost.doubleValue();
            itemLocSohPo.setAvCost(bgAvCost.doubleValue());
            itemLocSohPo.setAvCost(avCost);
        }


        itemLocSohPo.setUnitCost(itemLocSohDto.getUnitCost());//新入库的的成本
        itemLocSohPo.setStockOnHand(currentStockOnHand);//现有库存将采购数量设定为当前入库库存
        itemLocSohPo.setLastUpdateDatetime(new Date());
        itemLocSohPo.setLastUpdateId("sys_updatePurchaseStock");
        itemLocSohPo.setProductCode(itemLocSohDto.getProductCode());


        boolean updateSuccess = itemLocSohDao.updateItemLocSoh(itemLocSohPo) == 1;//更新库存表
        if (!updateSuccess) {
            log.error("新增入库>>商品编号为:{},入库仓库编号为:{}的入库数量为{}入库失败", productId, loc, itemLocSohDto.getStockOnHand());
            throw new RuntimeException("商品编号为:" + productId + ",入库仓库编号为:" + loc + "的入库数量为空");
        }
        //更新库存的相关操作

        if (realStockOnHand <= 0) {//生成70成本差异

            TranData tranDataPo70 = BeanConvertUtils.convert(tTranDataDto, TranData.class);
            tranDataPo70.setTranCode("70");
            tranDataPo70.setTranDate(new Date());
            tranDataPo70.setItem(productId);//商品编号
            tranDataPo70.setLocation(loc);//仓库编号

            tranDataPo70.setUnits(itemLocSohDto.getStockOnHand());//交易数量

            //70成本差异=（stock_on_hand+sell_in_tqty）*（当前wac-入库成本）; 成本差异是否需要加上new_stock_in(新增入库) ==》否
            /**
             * 未税后的价格
             */
            double afterVatRateUnitCost = itemLocSohDto.getUnitCost() / (1 + vatRate);//去掉含税后的成本
            BigDecimal diff = new BigDecimal(realStockOnHand).multiply(new BigDecimal(beforeAvCost -  BigDemicalUtil.round(afterVatRateUnitCost,2)));
            tranDataPo70.setTotalCost(diff.doubleValue());
            tranDataPo70.setVatRate(tTranDataDto.getVatRate());// 采购单上没有设定进项税
            //计算税额

            BigDecimal bigTaxes = new BigDecimal(afterVatRateUnitCost * vatRate).setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
            tranDataPo70.setTaxes(bigTaxes.doubleValue());
            tranDataDao.insert(tranDataPo70);


        }
        //2.1.2 加入tran_data操作明细 业务场景为2
        TranData tranDataPo = BeanConvertUtils.convert(tTranDataDto, TranData.class);
        tranDataPo.setItem(productId);//商品编号
        tranDataPo.setLocation(loc);//仓库编号
        tranDataPo.setTranDate(new Date());
        tranDataPo.setTranCode("20");//交易类型，采购
        tranDataPo.setUnits(itemLocSohDto.getStockOnHand());//交易数量
        tranDataPo.setTotalCost(stockInCost.doubleValue());
        tranDataPo.setVatRate(tTranDataDto.getVatRate());// 采购单上没有设定进项税
        //计算税额
        double afterVatRateUnitCost = itemLocSohDto.getUnitCost() / (1 + vatRate);//去掉含税后的成本
        BigDecimal bigTaxes = new BigDecimal(afterVatRateUnitCost * vatRate).setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
        tranDataPo.setTaxes(bigTaxes.doubleValue());
        tranDataDao.insert(tranDataPo);
    }
    @Override
    public ItemLocSoh getItemLocInventory(Map<String, Object> map) {
        return itemLocSohDao.getItemLocInventory(map);
    }


    @Override
    public List<ItemLocSoh> batchQueryItemInventoryListByList(List<ItemInventoryQueryParamPo> itemInventoryQueryParamPos) {
        log.info("----batchQueryItemInventoryListByList-----批量查询库存信息>>{}", JSON.toJSONString(itemInventoryQueryParamPos));
        return itemLocSohDao.batchQueryItemInventoryListByList(itemInventoryQueryParamPos);
    }

}
