package com.yatang.sc.inventory.dubboservice.flow.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseExtDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseQueryParamDto;
import com.yatang.sc.facade.dubboservice.ProdPurchaseQueryDubboService;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.domain.ItemInventoryPageQueryParamPo;
import com.yatang.sc.inventory.domain.ItemLocSoh;
import com.yatang.sc.inventory.dto.ItemInventoryPageQueryParamDto;
import com.yatang.sc.inventory.dto.im.InventoryBIDto;
import com.yatang.sc.inventory.dubboservice.flow.InventoryBIFlowService;
import com.yatang.sc.inventory.service.ItemLocSohService;
import com.yatang.sc.inventory.service.TranDataService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 库存实时查询flow 接口定义
 */
@Service("inventoryBIFlowService")
public class InventoryBIFlowServiceImpl implements InventoryBIFlowService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductScIndexDubboService indexDubboService;//商品信息获取
    @Autowired
    private ItemLocSohService itemLocSohService;
    @Autowired
    private WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;//逻辑仓库
    @Autowired
    private TranDataService tranDataService;


    @Autowired
    private ProdPurchaseQueryDubboService prodPurchaseQueryDubboService;
    // private final

    @Override
    public Response<PageResult<InventoryBIDto>> queryInventoryBIByPageQueryParam(ItemInventoryPageQueryParamDto pageQueryParamDto) {

        log.info("flow--queryInventoryBIByPageQueryParam>>查询条件:{}", JSON.toJSONString(pageQueryParamDto));
        Response<PageResult<InventoryBIDto>> resultResponse = new Response<>();

        PageResult<InventoryBIDto> pageResult = new PageResult<>();
        ItemInventoryPageQueryParamPo pageQueryParamPo = BeanConvertUtils.convert(pageQueryParamDto, ItemInventoryPageQueryParamPo.class);
        PageInfo<ItemLocSoh> itemLocSohPageInfo = itemLocSohService.queryItemInventoryListByPageQueryParam(pageQueryParamPo);//分页查询
        List<ItemLocSoh> list = itemLocSohPageInfo.getList();
        List<InventoryBIDto> inventoryBIDtos = new ArrayList<>();
        //查询的结果有数据进行操作拼接
        if (list.size() > 0) {
            for (ItemLocSoh itemLocSoh : list) {
                InventoryBIDto inventoryBIDto = new InventoryBIDto();
                String productId = itemLocSoh.getItemId();
                inventoryBIDto.setProductId(productId);//商品id
                //库存数据查询
                String warehouseCode = itemLocSoh.getLoc();//仓库编码
                inventoryBIDto.setWarehouseCode(itemLocSoh.getLoc());
                Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(itemLocSoh.getLoc());
                log.info("BI报表根据仓库编号:{},查询商品信息:{}", itemLocSoh.getLoc(), JSON.toJSONString(logicWarehouseDtoResponse));
                if (CommonsEnum.RESPONSE_200.getCode().equals(logicWarehouseDtoResponse.getCode()) && null != logicWarehouseDtoResponse.getResultObject()) {
                    LogicWarehouseDto logicWarehouseDto = logicWarehouseDtoResponse.getResultObject();
                    String warehouseName = logicWarehouseDtoResponse.getResultObject().getWarehouseName();
                    inventoryBIDto.setWarehouseName(warehouseName);//仓库名称
                    // 分公司id和名称
                    inventoryBIDto.setBranchCompanyId(logicWarehouseDto.getBranchCompanyId());
                    inventoryBIDto.setBranchCompanyName(logicWarehouseDto.getBranchCompanyName());
                }
                Long orderReservedQty = itemLocSoh.getOrderReservedQty() == null ? 0 : itemLocSoh.getOrderReservedQty();//销售保留
                Long tsfReservedQty = itemLocSoh.getTsfReservedQty() == null ? 0 : itemLocSoh.getTsfReservedQty();///调拨预留
                Long rtvQty = itemLocSoh.getRtvQty() == null ? 0 : itemLocSoh.getRtvQty();//退货预留

                inventoryBIDto.setUnitCost(itemLocSoh.getUnitCost());

//                //查询采购关系是否有主供应商
//                if (StringUtils.isNotBlank(inventoryBIDto.getBranchCompanyId())) {//仓库的分公司编号为空
//                    ProdPurchaseQueryParamDto purchasePriceQueryParamDto = new ProdPurchaseQueryParamDto();
//                    purchasePriceQueryParamDto.setProductId(inventoryBIDto.getProductId());
//                    purchasePriceQueryParamDto.setBranchCompanyId(inventoryBIDto.getBranchCompanyId());
//                    purchasePriceQueryParamDto.setSupplierType(1);//主供应商
//                    Response<com.yatang.sc.facade.common.PageResult<ProdPurchaseExtDto>> pageResultResponse = prodPurchaseQueryDubboService.queryProdPurchaseExtByCondition(purchasePriceQueryParamDto);
//                    log.info("根据查询条件:{},返回商品采购关系结果:{}", JSON.toJSONString(purchasePriceQueryParamDto), JSON.toJSONString(pageResultResponse));
//                    if (CommonsEnum.RESPONSE_200.getCode().equals(pageResultResponse.getCode())) {
//                        com.yatang.sc.facade.common.PageResult<ProdPurchaseExtDto> resultObject = pageResultResponse.getResultObject();
//                        if (resultObject != null) {
//                            List<ProdPurchaseExtDto> data = resultObject.getData();
//                            if (data != null && data.size() == 1) {
//                                inventoryBIDto.setUnitCost(data.get(0).getPurchasePrice().doubleValue());//获取主供应商价格
//                            }
//                        }
//
//                    }
//
//                }

                inventoryBIDto.setAvCost(itemLocSoh.getAvCost());
                inventoryBIDto.setStockOnHand(itemLocSoh.getStockOnHand());
                inventoryBIDto.setInTransitQty(itemLocSoh.getInTransitQty());
                inventoryBIDto.setOrderReservedQty(orderReservedQty);
                inventoryBIDto.setTsfReservedQty(tsfReservedQty);
                inventoryBIDto.setRtvQty(rtvQty);

                //  可用库存=现有库存-销售保留-调拨预留-退货预留
                Long availableStock = itemLocSoh.getStockOnHand() - orderReservedQty - tsfReservedQty - rtvQty;
                inventoryBIDto.setAvailableStock(availableStock);


                //计算上周的销量
                String code = "1";//销售编码
                Long saleQty = tranDataService.getTranDataUnitsByProductIdAndDate(productId, warehouseCode, code, new Date());
                log.info("BI报表根据商品id:{}仓库编码:{},事务编码:{}查询销售流水数据:{}", productId, warehouseCode, code, JSON.toJSONString(saleQty));
                BigDecimal bigDecLastWeekSaleQty = new BigDecimal(saleQty == null ? 0 : saleQty).divide(new BigDecimal(7), 0, BigDecimal.ROUND_HALF_UP);//上周日均销量
                Long lastWeekSaleQty = Long.parseLong(String.valueOf(bigDecLastWeekSaleQty));//计算日均销售量 取整
                if (0 != lastWeekSaleQty) {
                    BigDecimal bigDecimalSafeStockDay = new BigDecimal(availableStock).divide(new BigDecimal(lastWeekSaleQty), 1, BigDecimal.ROUND_HALF_UP);//安全天数 保留一位小数
                    inventoryBIDto.setSafeStockDay(bigDecimalSafeStockDay.doubleValue());
                }
                inventoryBIDto.setLastWeekSaleQty(lastWeekSaleQty);
                //根据商品id查询商品信息
                Response<ProductIndexDto> productIndexDtoResponse = indexDubboService.queryByProductId(itemLocSoh.getItemId());
                log.info("BI报表根据商品id:{}查询商品信息={}", itemLocSoh.getItemId(), JSON.toJSONString(productIndexDtoResponse));
                if (CommonsEnum.RESPONSE_200.getCode().equals(productIndexDtoResponse.getCode()) && null != productIndexDtoResponse.getResultObject()) {
                    ProductIndexDto productIndexDto = productIndexDtoResponse.getResultObject();
                    inventoryBIDto.setProductCode(productIndexDto.getProductCode());//
                    inventoryBIDto.setProductName(productIndexDto.getSaleName());//
                    inventoryBIDto.setGroups(productIndexDto.getFirstLevelCategoryName());//部类
                    inventoryBIDto.setBrandName(productIndexDto.getBrand() == null ? null : productIndexDto.getBrand().getName());//获取
                    inventoryBIDto.setDept(productIndexDto.getSecondLevelCategoryName());//大类
                    inventoryBIDto.setClasss(productIndexDto.getThirdLevelCategoryName());//中类
                    inventoryBIDto.setSubclass(productIndexDto.getFourthLevelCategoryName());//小类
                    inventoryBIDto.setInternationalCode(productIndexDto.getInternationalCodes().get(0).getInternationalCode());

                }
                inventoryBIDtos.add(inventoryBIDto);
            }
        }
        pageResult.setData(inventoryBIDtos);
        pageResult.setTotal(itemLocSohPageInfo.getTotal());
        pageResult.setPageNum(itemLocSohPageInfo.getPageNum());
        pageResult.setPageSize(itemLocSohPageInfo.getPageSize());
        resultResponse.setResultObject(pageResult);
        resultResponse.setSuccess(true);
        return resultResponse;
    }


}
