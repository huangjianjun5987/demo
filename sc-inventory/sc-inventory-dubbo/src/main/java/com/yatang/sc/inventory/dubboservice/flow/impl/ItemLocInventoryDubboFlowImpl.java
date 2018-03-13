package com.yatang.sc.inventory.dubboservice.flow.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.inventory.domain.InventoryLog;
import com.yatang.sc.inventory.domain.ItemInventoryQueryParamPo;
import com.yatang.sc.inventory.domain.ItemLocSoh;
import com.yatang.sc.inventory.domain.ItemTranPoList;
import com.yatang.sc.inventory.domain.TranData;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dto.ItemTranDtoList;
import com.yatang.sc.inventory.dto.OrderInventoryDto;
import com.yatang.sc.inventory.dto.ReservedInventoryResult;
import com.yatang.sc.inventory.dubboservice.exception.InventoryException;
import com.yatang.sc.inventory.dubboservice.flow.ItemLocInventoryDubboFlow;
import com.yatang.sc.inventory.dubboservice.service.ProdSellPriceInfoHelper;
import com.yatang.sc.inventory.service.InventoryLogService;
import com.yatang.sc.inventory.service.ItemLocSohService;
import com.yatang.sc.inventory.service.TranDataService;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.split.SplitOrderItemDto;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.split.SplitOrderSubGroupDto;
import com.yatang.sc.order.split.enums.SplitOrderFromType;
import com.yatang.sc.order.split.enums.SubOrderType;
import org.aspectj.apache.bcel.generic.RET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/28.
 */
@Service
public class ItemLocInventoryDubboFlowImpl implements ItemLocInventoryDubboFlow {
    public static final Integer RESERVE_ORDER_SUCCESS = 0;//所有库存预留成功

    public static final Integer RESERVE_ORDER_ALL_FAILURE = -1;//预留库存全部失败

    public static final Integer RESERVE_ORDER_PART_SUCCESS = 1;//部分预留库存

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemLocSohService itemLocSohService;

    @Autowired
    private TranDataService tranDataService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private OrderMessageSender orderMessageSender;

    @Autowired
    private ProdSellPriceInfoHelper mProdSellPriceInfoHelper;

    @Autowired
    private InventoryLogService inventoryLogService;

    public void reserveOrder(OrderInventoryDto orderInventoryDto, boolean shouldSplit) throws Exception {
        List<SplitOrderItemDto> stockLess = new ArrayList<>();
        List<SplitOrderItemDto> stockEnough = new ArrayList<>();
        boolean allInventoryReserved = true;//所有商品库存预留成功
        boolean allInventoryReservedFailure = true;//所有商品预留失败

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(300);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        InventoryLog inventoryLog;
        try {
            for (ItemInventoryDto itemInventoryDto : orderInventoryDto.getItemInventoryDtos()) {
                /**
                 * 判断为先销后采 按照正常流程，库存不足就无法采购
                 */
                List<String> idList = new ArrayList<>();

                boolean isPreHarvestPinStatus = false;
                idList.add(itemInventoryDto.getProductId());
                log.info("销售关系:{}", JSONObject.toJSON(itemInventoryDto));
                ProdSellPriceInfoDto prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(itemInventoryDto.getProductId(), itemInventoryDto.getBranchCompanyId());
                if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getPreHarvestPinStatus() == 1) {
                    isPreHarvestPinStatus = true;
                }

                inventoryLog = new InventoryLog();
                inventoryLog.setType(InventoryLog.Type.reserve.getVal());
                inventoryLog.setLoc(itemInventoryDto.getLoc());
                inventoryLog.setProductId(itemInventoryDto.getProductId());
                inventoryLog.setCommerceId(itemInventoryDto.getItemId());
                inventoryLog.setOrderId(orderInventoryDto.getId());
                inventoryLog.setDateTime(new Date());
                long num = itemLocSohService.reserveOrder(itemInventoryDto.getProductId(), itemInventoryDto.getLoc(), itemInventoryDto.getItemQty(), itemInventoryDto.getUnitQuantity(), isPreHarvestPinStatus);
                if (num == 0) {
                    inventoryLog.setQuantity(itemInventoryDto.getItemQty());
                    inventoryLog.setComment("库存全部预留成功");
                    allInventoryReservedFailure = false;
                    stockEnough.add(new SplitOrderItemDto(Long.valueOf(itemInventoryDto.getItemId()), itemInventoryDto.getItemQty()));
                    log.info("库存预留成功,orderId：" + orderInventoryDto.getId() + ",productId:" + itemInventoryDto.getProductId() + ",loc:" + itemInventoryDto.getLoc());
                } else {
                    allInventoryReserved = false;
                    stockLess.add(new SplitOrderItemDto(Long.valueOf(itemInventoryDto.getItemId()), num));
                    if (num != itemInventoryDto.getItemQty()) {//商品预留了部分库存
                        inventoryLog.setQuantity(itemInventoryDto.getItemQty() - num);
                        inventoryLog.setComment("库存部分预留成功");
                        stockEnough.add(new SplitOrderItemDto(Long.valueOf(itemInventoryDto.getItemId()), itemInventoryDto.getItemQty() - num));
                        allInventoryReservedFailure = false;
                    }
                }
                inventoryLogService.insert(inventoryLog);
            }

        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            log.error("库存预留异常", ex);
            throw ex;
        }

        if (allInventoryReserved || (shouldSplit && !allInventoryReserved && !allInventoryReservedFailure)) {
            //1.全部预留成功
            //2.允许拆单
            transactionManager.commit(txStatus);
        } else {
            transactionManager.rollback(txStatus);
        }

        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderId(orderInventoryDto.getId());
        ReservedInventoryResult reservedInventoryResult = new ReservedInventoryResult();
        reservedInventoryResult.setOrderId(orderInventoryDto.getId());
        if (allInventoryReserved) {
            log.info("订单所有商品库存预留成功：{}", orderInventoryDto.getId());
            reservedInventoryResult.setStaus(ReservedInventoryResult.ReservedInventoryStatus.RESERVED_ALL);
        }else if (allInventoryReservedFailure) {
            log.info("订单所有商品库存预留失败：{}", orderInventoryDto.getId());
            reservedInventoryResult.setStaus(ReservedInventoryResult.ReservedInventoryStatus.RESERVED_FAILURE);
        }else {
            log.info("订单部分商品库存充足：{}，{}", orderInventoryDto.getId(), JSON.toJSONString(stockLess));
            SplitOrderRequestDto splitOrderRequestDto = new SplitOrderRequestDto();
            splitOrderRequestDto.setParentOrderId(orderInventoryDto.getId());
            splitOrderRequestDto.setFromType(SplitOrderFromType.SPLIT_FROM_INVENTORY);

            SplitOrderSubGroupDto less = new SplitOrderSubGroupDto();
            less.setSubOrderType(SubOrderType.LESS_STOCK);
            less.getItems().addAll(stockLess);
            splitOrderRequestDto.getGroups().add(less);
            SplitOrderSubGroupDto enough = new SplitOrderSubGroupDto();
            enough.setSubOrderType(SubOrderType.ENOUGH_STOCK);
            enough.getItems().addAll(stockEnough);
            splitOrderRequestDto.getGroups().add(enough);

            if (shouldSplit) {
                log.info("{}-》基于库存拆单:{}", shouldSplit, orderInventoryDto.getId());
                reservedInventoryResult.setStaus(ReservedInventoryResult.ReservedInventoryStatus.RESERVED_PORTION_SUCCESS);
            } else {
                log.info("{}-》预留库存失败，更新订单库存状态:{}", shouldSplit, orderInventoryDto.getId());
                reservedInventoryResult.setStaus(ReservedInventoryResult.ReservedInventoryStatus.RESERVED_PORTION_FAIL);
            }
            reservedInventoryResult.setMsg(splitOrderRequestDto.toJson());
        }
        orderMessage.setMssageType(OrderMessageType.After_Reserved_Order_Inventory);
        orderMessage.setBody(JSON.toJSONString(reservedInventoryResult));
        orderMessageSender.sendMsg(orderMessage);
        log.info("库存处理结果消息已发送:{}", orderInventoryDto.getId());
       // return shouldSplit ? RESERVE_ORDER_PART_SUCCESS : RESERVE_ORDER_ALL_FAILURE;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saleOutOrder(OrderInventoryDto orderInventoryDto) {
        for (ItemInventoryDto itemInventoryDto : orderInventoryDto.getItemInventoryDtos()) {
            log.info("sale out order {} productId {} qty={}, stock={}", orderInventoryDto.getId(), itemInventoryDto.getProductId(), itemInventoryDto.getItemQty(), itemInventoryDto.getStock());
            int num = itemLocSohService.saleOutOrder(itemInventoryDto.getProductId(), itemInventoryDto.getLoc(), itemInventoryDto.getItemQty(), itemInventoryDto.getStock());
            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setType(InventoryLog.Type.saleOut.getVal());
            inventoryLog.setLoc(itemInventoryDto.getLoc());
            inventoryLog.setQuantity(itemInventoryDto.getItemQty());
            inventoryLog.setProductId(itemInventoryDto.getProductId());
            inventoryLog.setCommerceId(itemInventoryDto.getItemId());
            inventoryLog.setOrderId(orderInventoryDto.getId());
            inventoryLog.setDateTime(new Date());
            inventoryLogService.insert(inventoryLog);
            if (num <= 0) {
                throw new InventoryException("销售业务表操作失败,orderId：" + orderInventoryDto.getId() + ",productId:" + itemInventoryDto.getProductId() + ",loc:" + itemInventoryDto.getLoc());
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void orderArrived(OrderInventoryDto orderInventoryDto) {
        for (ItemInventoryDto itemInventoryDto : orderInventoryDto.getItemInventoryDtos()) {
            int numItemLocSoh = itemLocSohService.orderArrived(itemInventoryDto.getProductId(), itemInventoryDto.getLoc(), itemInventoryDto.getCompleteQty(), itemInventoryDto.getStock());
            if (numItemLocSoh <= 0) {
                throw new InventoryException("销售业务表数据操作失败,orderId：" + orderInventoryDto.getId() + ",productId:" + itemInventoryDto.getProductId() + ",loc:" + itemInventoryDto.getLoc());
            }
            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setType(InventoryLog.Type.arrived.getVal());
            inventoryLog.setLoc(itemInventoryDto.getLoc());
            inventoryLog.setProductId(itemInventoryDto.getProductId());
            inventoryLog.setCommerceId(itemInventoryDto.getItemId());
            inventoryLog.setOrderId(orderInventoryDto.getId());
            inventoryLog.setDateTime(new Date());
            inventoryLogService.insert(inventoryLog);

            TranData tranData = new TranData();
            tranData.setItem(itemInventoryDto.getProductId());

            tranData.setProductCode(itemInventoryDto.getProductCode());
            tranData.setVatRate(itemInventoryDto.getVatRate());
            tranData.setGroups(itemInventoryDto.getGroups());
            tranData.setDept(itemInventoryDto.getDept());
            tranData.setClasss(itemInventoryDto.getClasss());
            tranData.setSubclass(itemInventoryDto.getSubclass());

            //计算销售总成本
            Double totalCost = itemInventoryDto.getAvCost() * itemInventoryDto.getCompleteQty();// 销售总成本  2017/08/21 需要取移动平均价 乘上数量
            BigDecimal bigTotalCost = new BigDecimal(String.valueOf(totalCost)).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入处理

            //计算销售总金额
            Double totalRetailCost = itemInventoryDto.getActualPrice() * itemInventoryDto.getCompleteQty();//销售总金额
            BigDecimal totalRetail = new BigDecimal(String.valueOf(totalRetailCost)).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入处理

            tranData.setTotalCost(bigTotalCost.doubleValue());//销售总成本
            tranData.setTotalRetail(totalRetail.doubleValue());//销售总金额
            tranData.setLocation(itemInventoryDto.getLoc());//仓库编码

            tranData.setTranCode("1");//净销售额 代码
            tranData.setUnits(itemInventoryDto.getCompleteQty());//已签收数量
            tranData.setRefNo1(orderInventoryDto.getId());
            tranData.setTranDate(new Date());

            tranDataService.save(tranData);


        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void orderUnArrive(OrderInventoryDto orderInventoryDto) {

        for (ItemInventoryDto itemInventoryDto : orderInventoryDto.getItemInventoryDtos()) {
            int num = itemLocSohService.orderUnArrive(itemInventoryDto.getProductId(), itemInventoryDto.getLoc(), itemInventoryDto.getCompleteQty(), itemInventoryDto.getStock());
            if (num <= 0) {
                throw new InventoryException("销售业务表数据操作失败,orderId：" + orderInventoryDto.getId() + ",productId:" + itemInventoryDto.getProductId() + ",loc:" + itemInventoryDto.getLoc());
            }
            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setType(InventoryLog.Type.unArrive.getVal());
            inventoryLog.setLoc(itemInventoryDto.getLoc());
            inventoryLog.setProductId(itemInventoryDto.getProductId());
            inventoryLog.setCommerceId(itemInventoryDto.getItemId());
            inventoryLog.setOrderId(orderInventoryDto.getId());
            inventoryLog.setDateTime(new Date());
            inventoryLogService.insert(inventoryLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelReserveOrder(OrderInventoryDto orderInventoryDto) {

        for (ItemInventoryDto itemInventoryDto : orderInventoryDto.getItemInventoryDtos()) {
            int num = itemLocSohService.cancelReserveOrder(itemInventoryDto.getProductId(), itemInventoryDto.getLoc(), itemInventoryDto.getItemQty());
            if (num <= 0) {
                throw new InventoryException("取消库存预留失败,orderId：" + orderInventoryDto.getId() + ",productId:" + itemInventoryDto.getProductId() + ",loc:" + itemInventoryDto.getLoc());
            }
            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setType(InventoryLog.Type.cancel.getVal());
            inventoryLog.setLoc(itemInventoryDto.getLoc());
            inventoryLog.setProductId(itemInventoryDto.getProductId());
            inventoryLog.setCommerceId(itemInventoryDto.getItemId());
            inventoryLog.setOrderId(orderInventoryDto.getId());
            inventoryLog.setDateTime(new Date());
            inventoryLogService.insert(inventoryLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saleReturn(OrderInventoryDto orderInventoryDto) {

        for (ItemInventoryDto itemInventoryDto : orderInventoryDto.getItemInventoryDtos()) {
            int num = itemLocSohService.saleReturn(itemInventoryDto.getProductId(), itemInventoryDto.getLoc(), itemInventoryDto.getItemQty());
            if (num <= 0) {
                throw new InventoryException("销售退货库存失败,orderId：" + orderInventoryDto.getId() + ",productId:" + itemInventoryDto.getProductId() + ",loc:" + itemInventoryDto.getLoc());
            }
            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setType(InventoryLog.Type.saleReturn.getVal());
            inventoryLog.setLoc(itemInventoryDto.getLoc());
            inventoryLog.setProductId(itemInventoryDto.getProductId());
            inventoryLog.setCommerceId(itemInventoryDto.getItemId());
            inventoryLog.setOrderId(orderInventoryDto.getId());
            inventoryLog.setDateTime(new Date());
            inventoryLogService.insert(inventoryLog);

            TranData tranData = new TranData();
            tranData.setItem(itemInventoryDto.getProductId());

            tranData.setProductCode(itemInventoryDto.getProductCode());
            tranData.setVatRate(itemInventoryDto.getVatRate());
            tranData.setGroups(itemInventoryDto.getGroups());
            tranData.setDept(itemInventoryDto.getDept());
            tranData.setClasss(itemInventoryDto.getClasss());
            tranData.setSubclass(itemInventoryDto.getSubclass());

            //计算销售总成本
//            Double totalCost = itemInventoryDto.getAvCost() * itemInventoryDto.getCompleteQty();// 销售总成本  2017/08/21 需要取移动平均价 乘上数量
//            BigDecimal bigTotalCost = new BigDecimal(String.valueOf(totalCost)).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入处理

            //计算销售总金额
            Double totalRetailCost = itemInventoryDto.getActualPrice() * itemInventoryDto.getItemQty();//销售总金额
            BigDecimal totalRetail = new BigDecimal(String.valueOf(totalRetailCost)).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入处理

//            tranData.setTotalCost(bigTotalCost.doubleValue());//销售总成本
            tranData.setTotalRetail(-totalRetail.doubleValue());//销售总金额
            tranData.setLocation(itemInventoryDto.getLoc());//仓库编码

            tranData.setTranCode("1");//净销售额 代码
            tranData.setUnits(-itemInventoryDto.getItemQty());//已退货数量
            tranData.setRefNo1(orderInventoryDto.getId());
            tranData.setTranDate(new Date());

            tranDataService.save(tranData);

        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void purchaseStockIn(ItemTranDtoList data) throws Exception {

        if (StringUtils.isEmpty(data.getLoc())) {
            log.error("采购入库,仓库编码为空");
            throw new InventoryException("采购入库,仓库编码为空");
        }
        //dto2po
        ItemTranPoList poData = BeanConvertUtils.convert(data, ItemTranPoList.class);
        itemLocSohService.purchaseStockIn(poData);
    }


    public List<ItemLocSohDto> queryItemInventoryListByParam(ItemInventoryQueryParamPo queryParamPo) {

        List<ItemLocSoh> itemLocSohList = itemLocSohService.queryItemInventoryListByParam(queryParamPo);
        //po2dto
        List<ItemLocSohDto> itemLocSohDtos = BeanConvertUtils.convertList(itemLocSohList, ItemLocSohDto.class);
        return itemLocSohDtos;
    }
}
