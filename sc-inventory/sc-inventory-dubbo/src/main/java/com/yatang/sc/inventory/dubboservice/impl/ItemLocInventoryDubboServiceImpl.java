package com.yatang.sc.inventory.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.inventory.common.CommonsEnum;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.domain.*;
import com.yatang.sc.inventory.dto.BathCheckInventoryDto;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dto.ItemInventoryPageQueryParamDto;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dto.ItemTranDtoList;
import com.yatang.sc.inventory.dto.OrderInventoryDto;
import com.yatang.sc.inventory.dto.TranDataDto;
import com.yatang.sc.inventory.dto.im.InventoryBIDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.inventory.dubboservice.flow.InventoryBIFlowService;
import com.yatang.sc.inventory.dubboservice.flow.ItemLocInventoryDubboFlow;
import com.yatang.sc.inventory.dubboservice.service.ProdSellPriceInfoHelper;
import com.yatang.sc.inventory.dubboservice.service.ProductHelper;
import com.yatang.sc.inventory.service.AreaWarehouseService;
import com.yatang.sc.inventory.service.ItemLocSohService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiangyonghong on 2017/7/28.
 */
@Service("itemLocInventoryDubboService")
public class ItemLocInventoryDubboServiceImpl implements ItemLocInventoryDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemLocInventoryDubboFlow inventoryDubboFlow;

    @Autowired
    private ItemLocSohService itemLocSohService;

    @Autowired
    private InventoryBIFlowService inventoryBIFlowService;

    @Autowired
    private ProdSellPriceInfoHelper mProdSellPriceInfoHelper;

    @Autowired
    private ProductScIndexDubboService productScIndexDubboService;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    @Autowired
    private AreaWarehouseService areaWarehouseService;

    @Override
    public Response<Integer> reserveOrder(OrderInventoryDto orderInventoryDto, boolean shouldSplit) {
        Response<Integer> response = new Response<Integer>();
        if (CollectionUtils.isEmpty(orderInventoryDto.getItemInventoryDtos())) {
            response.setResultObject(-1);
            return response;
        }
        String lockPath = "/inventory_update_" + orderInventoryDto.getItemInventoryDtos().get(0).getLoc();//根据仓库编号枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            inventoryDubboFlow.reserveOrder(orderInventoryDto, shouldSplit);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setResultObject(-1);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } finally {
            rLock.unlock();
        }
        return response;

    }

    @Override
    public Response<Boolean> saleOutOrder(OrderInventoryDto orderInventoryDto) {
        Response<Boolean> response = new Response<Boolean>();
        if (CollectionUtils.isEmpty(orderInventoryDto.getItemInventoryDtos())) {
            response.setResultObject(false);
            return response;
        }
        String lockPath = "/inventory_update_" + orderInventoryDto.getItemInventoryDtos().get(0).getLoc();//根据仓库编号枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            inventoryDubboFlow.saleOutOrder(orderInventoryDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } finally {
            rLock.unlock();
        }
        return response;
    }

    @Override
    public Response<Boolean> orderArrived(OrderInventoryDto orderInventoryDto) {
        Response<Boolean> response = new Response<Boolean>();
        if (CollectionUtils.isEmpty(orderInventoryDto.getItemInventoryDtos())) {
            response.setResultObject(false);
            return response;
        }
        String lockPath = "/inventory_update_" + orderInventoryDto.getItemInventoryDtos().get(0).getLoc();//根据仓库编号枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            inventoryDubboFlow.orderArrived(orderInventoryDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } finally {
            rLock.unlock();
        }
        return response;
    }


    @Override
    public Response<Boolean> orderUnArrive(OrderInventoryDto orderInventoryDto) {
        Response<Boolean> response = new Response<Boolean>();
        if (CollectionUtils.isEmpty(orderInventoryDto.getItemInventoryDtos())) {
            response.setResultObject(false);
            return response;
        }
        String lockPath = "/inventory_update_" + orderInventoryDto.getItemInventoryDtos().get(0).getLoc();//根据仓库编号枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            inventoryDubboFlow.orderUnArrive(orderInventoryDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } finally {
            rLock.unlock();
        }
        return response;

    }

    @Override
    public Response<Boolean> cancelReserveOrder(OrderInventoryDto orderInventoryDto) {
        Response<Boolean> response = new Response<Boolean>();
        if (CollectionUtils.isEmpty(orderInventoryDto.getItemInventoryDtos())) {
            response.setResultObject(false);
            return response;
        }
        String lockPath = "/inventory_update_" + orderInventoryDto.getItemInventoryDtos().get(0).getLoc();//根据仓库编号枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            inventoryDubboFlow.cancelReserveOrder(orderInventoryDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } finally {
            rLock.unlock();
        }
        return response;

    }


    @Override
    public Response<Boolean> saleReturn(OrderInventoryDto orderInventoryDto) {
        Response<Boolean> response = new Response<Boolean>();
        if (CollectionUtils.isEmpty(orderInventoryDto.getItemInventoryDtos())) {
            response.setResultObject(false);
            return response;
        }
        String lockPath = "/inventory_update_" + orderInventoryDto.getItemInventoryDtos().get(0).getLoc();//根据仓库编号枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            inventoryDubboFlow.saleReturn(orderInventoryDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } finally {
            rLock.unlock();
        }
        return response;

    }


    @Override
    public Response<Void> purchaseStockIn(ItemTranDtoList data) {
        Response<Void> response = new Response<Void>();
        try {
            log.info("start-purchaseStockIn----采购入库,data:{}", JSON.toJSONString(data));
            inventoryDubboFlow.purchaseStockIn(data);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            log.info("end---采购入库");
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<List<ItemLocSohDto>> queryItemInventoryListByParam(ItemInventoryQueryParamDto queryParamDto) {
        Response<List<ItemLocSohDto>> response = new Response<List<ItemLocSohDto>>();
        try {
            //dto2po
            log.info("start--查询库存中的商品信息,queryParamDto:{}", JSON.toJSONString(queryParamDto));
            if (StringUtils.isEmpty(queryParamDto.getProductId()) && StringUtils.isEmpty(queryParamDto.getLogicWareHouseCode())) {//传递的条件不能同时为空
                response.setErrorMessage(CommonsEnum.RESPONSE_10023.getName());
                response.setCode(CommonsEnum.RESPONSE_10023.getCode());
                response.setSuccess(false);
                log.error("商品和仓库编码不能同时为空");
                return response;
            }
            ItemInventoryQueryParamPo queryParamPo = BeanConvertUtils.convert(queryParamDto, ItemInventoryQueryParamPo.class);
            List<ItemLocSohDto> itemInventoryDtos = inventoryDubboFlow.queryItemInventoryListByParam(queryParamPo);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(itemInventoryDtos);
            log.info("end--查询库存中的商品信息");
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Boolean> checkInventory(ItemInventoryDto itemInventoryDto) {
        log.info("checkInventory param{}", JSON.toJSONString(itemInventoryDto));
        Response<Boolean> response = new Response<Boolean>();
        try {
            /**
             * 判断为先销后采，
             */
            ProdSellPriceInfoDto prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(itemInventoryDto.getProductId(), itemInventoryDto.getBranchCompanyId());
            if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getPreHarvestPinStatus() == 0) {
                response.setSuccess(true);
                response.setResultObject(true);
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                log.info("先销后采模式 productId {} ,branchCompanyId{}", itemInventoryDto.getProductId(), itemInventoryDto.getBranchCompanyId());
                return response;
            }

            Boolean stockEnough = itemLocSohService.checkInventory(itemInventoryDto.getProductId(), itemInventoryDto.getLoc(), itemInventoryDto.getItemQty());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(stockEnough);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setResultObject(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;

    }

    @Override
    public Response<Map<String, Boolean>> checkAllInventory(List<ItemInventoryDto> itemInventoryDtos) {
        Response<Map<String, Boolean>> response = new Response<Map<String, Boolean>>();
        response.setSuccess(true);

        try {
            Map<String, Boolean> booleanMap = new HashMap<>(itemInventoryDtos.size());
            ProdSellPriceInfoDto prodSellPriceInfoDto;
            for (ItemInventoryDto itemInventoryDto : itemInventoryDtos) {
                prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(itemInventoryDto.getProductId(), itemInventoryDto.getBranchCompanyId());
                if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getPreHarvestPinStatus() == 0) {
                    booleanMap.put(itemInventoryDto.getProductId(), true);
                    continue;
                }
                booleanMap.put(itemInventoryDto.getProductId(), itemLocSohService.checkInventory(itemInventoryDto.getProductId(), itemInventoryDto.getLoc(), itemInventoryDto.getItemQty()));
            }
            response.setResultObject(booleanMap);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setErrorMessage("检查库存异常");
            log.error("检查库存异常", ex);
        }
        return response;
    }

    @Override
    public Response<PageResult<InventoryBIDto>> queryInventoryBIByPageQueryParam(ItemInventoryPageQueryParamDto pageQueryParamDto) {
        return inventoryBIFlowService.queryInventoryBIByPageQueryParam(pageQueryParamDto);//分页查询
    }

    @Override
    public Response<Map<String, Long>> queryAllAvailableInventory(List<ItemInventoryDto> itemInventoryDtos) {
        Response<Map<String, Long>> response = new Response<Map<String, Long>>();
        response.setSuccess(true);

        try {
            Map<String, Long> allAvailableInventory = new HashMap<>(itemInventoryDtos.size());
            ProdSellPriceInfoDto prodSellPriceInfoDto;
            ItemLocSoh itemLocSoh;
            for (ItemInventoryDto itemInventoryDto : itemInventoryDtos) {
                prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(itemInventoryDto.getProductId(), itemInventoryDto.getBranchCompanyId());
                if (!mProdSellPriceInfoHelper.isPreHarvestProd(prodSellPriceInfoDto)) {
                    allAvailableInventory.put(itemInventoryDto.getProductId(), itemInventoryDto.getItemQty());
                    continue;
                }
                if (StringUtils.isNotEmpty(itemInventoryDto.getProductId())) {
                    itemLocSoh = itemLocSohService.queryItemInventory(itemInventoryDto.getProductId(), itemInventoryDto.getLoc());
                    if (itemLocSoh == null) {
                        allAvailableInventory.put(itemInventoryDto.getProductId(), 0L);
                    } else {
                        allAvailableInventory.put(itemInventoryDto.getProductId(), itemLocSoh.getStockOnHand() - itemLocSoh.getOrderReservedQty());
                    }
                } else {
                    log.warn("查询库存 productId是空的");
                    itemLocSoh = null;
                }
            }
            response.setResultObject(allAvailableInventory);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setErrorMessage("查询库存异常");
            log.error("查询库存异常", ex);
        }
        return response;
    }


    @Override
    public Response<Map<String, Long>> queryAvailableInventoryForDS(String province, String city, List<String> productCodes) {
        Response<Map<String, Long>> response = new Response<Map<String, Long>>();
        response.setSuccess(true);
        try {
            Map<String, Long> allAvailableInventory = new HashMap<String, Long>(productCodes.size());
            //LogicWarehouseDto logicWarehouseDto = warehouseHelper.queryLogicWarehouseByArea(province, city);
            CfAreaWarehouse areaWarehouse = areaWarehouseService.queryAreawarehouseByProvince(province);
            String logicWarehouseCode = null;
            String branchCompanyId = null;
            if(areaWarehouse != null){
                logicWarehouseCode = areaWarehouse.getWarehouseCode();
                branchCompanyId = areaWarehouse.getBranchCompanyId();
            }
//            else{
//                logicWarehouseCode = AreaToWarehouseConfig.getWarehouseCode(province);
//                branchCompanyId = AreaToWarehouseConfig.getBranchCompanyId(logicWarehouseCode);
//            }
            log.info("ds find logicWarehouseCode {} branch {} by province {}", logicWarehouseCode, branchCompanyId, province);
            for (String productCode : productCodes) {
                ProductIndexDto  product = productHelper.findProductIndexByCode(productCode);
                if(product == null){
                    log.info("can't find product:{}", productCode);
                    allAvailableInventory.put(productCode, 0L);
                    continue;
                }
                ProdSellPriceInfoDto prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(product.getId(), branchCompanyId);
                if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getPreHarvestPinStatus() == 0) {
                    log.info("branchCompanyId {} product {} XXHC ", product.getId(), branchCompanyId);
                    Integer maxValue =  prodSellPriceInfoDto.getMaxNumber();
                    if(maxValue == null){
                        maxValue = Integer.MAX_VALUE;
                    }
                    allAvailableInventory.put(productCode, maxValue.longValue());
                    continue;
                }
                ItemLocSoh itemLocSoh = null;
                if (!StringUtils.isEmpty(logicWarehouseCode)) {
                    itemLocSoh = itemLocSohService.queryItemInventoryByProductCode(productCode, logicWarehouseCode);
                }
                if (itemLocSoh == null) {
                    log.info("can't find inventory province {}, product:{}", province, productCode);
                    allAvailableInventory.put(productCode, 0L);
                } else {
                    allAvailableInventory.put(productCode, itemLocSoh.getStockOnHand() - itemLocSoh.getOrderReservedQty());
                }
            }
            response.setResultObject(allAvailableInventory);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setErrorMessage("查询库存异常");
            log.error("查询库存异常", ex);
        }
        return response;
    }


    @Override
    public Response<Boolean> updateBatchRtvQty(List<ItemInventoryQueryParamDto> itemInventoryQueryParamDtos) {
        log.info("-----------批量修改退货预留:updateBatchRtvQty-->param:" + JSONObject.toJSONString(itemInventoryQueryParamDtos) + "------------");
        Response<Boolean> response = new Response<>();
        try {
            List<ItemInventoryQueryParamPo> itemInventoryQueryParamPos = BeanConvertUtils.convertList(itemInventoryQueryParamDtos, ItemInventoryQueryParamPo.class);
            Boolean status = itemLocSohService.updateRtvQty(itemInventoryQueryParamPos);

            response.setResultObject(status);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        } catch (Exception e) {
            log.info("-----------批量修改退货预留:updateBatchRtvQty-->error:" + ExceptionUtils.getFullStackTrace(e) + "------------");
            response.setResultObject(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode() + ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
        }finally {

        }
        return response;
    }


    @Override
    public Response<Boolean> updateBatchStockOnHandByRtvQty(
            List<ItemInventoryQueryParamDto> itemInventoryQueryParamDtos,List<TranDataDto> tranDataDtos) {
        log.info("-----------根据退货预留批量更新现有库存:updateBatchStockOnHandByRtvQty-->param:" + JSONObject.toJSONString(itemInventoryQueryParamDtos) + "------------");
        Response<Boolean> response = new Response<>();
        try {
            List<ItemInventoryQueryParamPo> itemInventoryQueryParamPos=BeanConvertUtils.convertList(itemInventoryQueryParamDtos,ItemInventoryQueryParamPo.class);
            List<TranData> tranDatas=BeanConvertUtils.convertList(tranDataDtos,TranData.class);
            if (tranDataDtos!=null && tranDataDtos.size()>0){
                for (TranData tranData:tranDatas){
                    //查询商品信息
                    if (!Strings.isNullOrEmpty(tranData.getItem())) {
                        Response<ProductIndexDto> productIndexDtoResponse = productScIndexDubboService.queryByProductId(tranData.getItem());
                        if (!productIndexDtoResponse.isSuccess() || null == productIndexDtoResponse.getResultObject()) {
                            throw new RuntimeException("查询商品数据失败,param:prodcutId:" + tranData.getItem());
                        }
                        ProductIndexDto productIndexDto = productIndexDtoResponse.getResultObject();
                        tranData.setProductCode(productIndexDto.getProductCode());
                        tranData.setGroups(productIndexDto.getFirstLevelCategoryId());
                        tranData.setDept(productIndexDto.getSecondLevelCategoryId());
                        tranData.setClasss(productIndexDto.getSecondLevelCategoryId());
                        tranData.setSubclass(productIndexDto.getFirstLevelCategoryId());
                        tranData.setTranDate(new Date());
                    } else {
                        throw new RuntimeException("商品id为空，新增tran_data字段失败");
                    }
                }
            }
            Boolean status= itemLocSohService.updateBatchStockOnHandByRtvQty(itemInventoryQueryParamPos,tranDatas);
            response.setResultObject(status);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
        } catch (Exception e) {
            log.info("-----------根据退货预留批量更新现有库存:updateBatchStockOnHandByRtvQty-->error:" + ExceptionUtils.getFullStackTrace(e) + "------------");
            response.setResultObject(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode() + ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public Response<BathCheckInventoryDto> batchCheckInventory(List<ItemInventoryDto> itemInventoryDtos) {
        Response<BathCheckInventoryDto> response = new Response<>();
        BathCheckInventoryDto bathCheckInventoryDto = new BathCheckInventoryDto();
        response.setResultObject(bathCheckInventoryDto);
        if (CollectionUtils.isEmpty(itemInventoryDtos)) {
            log.warn("批量检查库存入参为空");
            response.setSuccess(true);
            return response;
        }

        log.info("-----------根据商品的信息批量查询库存不足的商品:batchCheckInventory-->param:" + JSONObject.toJSONString(itemInventoryDtos) + "------------");
        bathCheckInventoryDto.setLoc(itemInventoryDtos.get(0).getLoc());
        try {
            List<ItemInventoryQueryParamPo> queryParamPos = new ArrayList<>();
            List<String> productIds = new ArrayList<>();
            for (ItemInventoryDto itemInventoryDto : itemInventoryDtos) {
                ItemInventoryQueryParamDto queryParamDto = new ItemInventoryQueryParamDto();
                queryParamDto.setProductId(itemInventoryDto.getProductId());
                queryParamDto.setLogicWareHouseCode(itemInventoryDto.getLoc());
                queryParamDto.setQuantity(itemInventoryDto.getItemQty());
                queryParamDto.setBranchCompanyId(itemInventoryDto.getBranchCompanyId());
                ItemInventoryQueryParamPo queryParamPo = BeanConvertUtils.convert(queryParamDto, ItemInventoryQueryParamPo.class);
                queryParamPos.add(queryParamPo);
                productIds.add(itemInventoryDto.getProductId());
            }
            List<String> notPreHarvestProd = new ArrayList<>();
            List<ProdSellPriceInfoDto> prodSellPriceInfoDtos = mProdSellPriceInfoHelper.getGoodsSellPriceByProductIdsAndCompanyId(itemInventoryDtos.get(0).getBranchCompanyId(), productIds);
            for (ProdSellPriceInfoDto prodSellPriceInfoDto : prodSellPriceInfoDtos) {
                if (!mProdSellPriceInfoHelper.isPreHarvestProd(prodSellPriceInfoDto)) {
                    log.info("先销后采:{}=>{}", prodSellPriceInfoDto.getBranchCompanyId(), prodSellPriceInfoDto.getProductId());
                    notPreHarvestProd.add(prodSellPriceInfoDto.getProductId());
                }
            }
            log.info("先销后采商品ID：{}", JSON.toJSONString(notPreHarvestProd));
            List<ItemLocSoh> itemLocSohs = itemLocSohService.batchQueryItemInventoryListByList(queryParamPos);

            Set<String> enoughStock = new HashSet<>();
            Set<String> outOfStock = new HashSet<>();
            bathCheckInventoryDto.setEnoughStock(enoughStock);
            bathCheckInventoryDto.setOutOfStock(outOfStock);
            for (ItemInventoryDto itemParams : itemInventoryDtos) {
                if (notPreHarvestProd.contains(itemParams.getProductId())) {//如果先销后采，不判断库存
                    log.info("先销后采，库存充足:{}", itemParams.getProductId());
                    enoughStock.add(itemParams.getProductId());
                    continue;
                }
                boolean findStock = false;
                for (ItemLocSoh itemLocSoh : itemLocSohs) {
                    if (itemParams.getProductId().equals(itemLocSoh.getItemId())) {
                        findStock = true;
                        Long availableStock = itemLocSoh.getStockOnHand() - itemLocSoh.getOrderReservedQty() - itemLocSoh.getRtvQty() - itemLocSoh.getTsfReservedQty();
                        if (availableStock == 0L || availableStock < itemParams.getItemQty()) {
                            outOfStock.add(itemParams.getProductId());
                        } else {
                            enoughStock.add(itemParams.getProductId());
                        }
                    }
                }
                if (!findStock) {
                    log.info("批量查询未查询到库存信息:{}==>{}", itemParams.getLoc(), itemParams.getProductId());
                    outOfStock.add(itemParams.getProductId());
                }
            }

            log.info("批量检查库存接口:{}", JSON.toJSONString(bathCheckInventoryDto));
            response.setSuccess(true);
        } catch (Exception e) {
            log.error("库存批量检查异常：", e);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
        }
        return response;
    }


    @Override
    public Response<PageResult<ItemLocSohDto>> queryItemInventoryListByPageQueryParam(ItemInventoryPageQueryParamDto queryParamDto) {
        Response<PageResult<ItemLocSohDto>> response = new Response<PageResult<ItemLocSohDto>>();

        try {
            log.info("start-dubbo--queryItemInventoryListByPageQueryParam>>查询库存中的商品信息,queryParamDto:{}", queryParamDto);
            //dto2po
            ItemInventoryPageQueryParamPo queryParamPo = BeanConvertUtils.convert(queryParamDto, ItemInventoryPageQueryParamPo.class);
            PageInfo<ItemLocSoh> pageInfo = itemLocSohService.queryItemInventoryListByPageQueryParam(queryParamPo);//分页查询
            //查询的结果
            log.info("分页查询库存信息结果:{}", pageInfo);
            List<ItemLocSohDto> locSohs = BeanConvertUtils.convertList(pageInfo.getList(), ItemLocSohDto.class);
            //po2dto

            PageResult<ItemLocSohDto> pageResult = new PageResult<ItemLocSohDto>();
            pageResult.setPageNum(pageInfo.getPageNum());
            pageResult.setPageSize(pageInfo.getPageSize());
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setData(locSohs);
            response.setResultObject(pageResult);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            log.info("end--queryItemInventoryListByPageQueryParam>>查询库存中的商品信息");
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
