package com.yatang.sc.inventory.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.MyBeanUtils;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.inventory.domain.CfAreaWarehouse;
import com.yatang.sc.inventory.domain.ItemLocSoh;
import com.yatang.sc.inventory.dto.AvcostConditionDto;
import com.yatang.sc.inventory.dto.AvcostResultDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryQueryDubboService;
import com.yatang.sc.inventory.dubboservice.service.ProdSellPriceInfoHelper;
import com.yatang.sc.inventory.dubboservice.service.ProductHelper;
import com.yatang.sc.inventory.service.AreaWarehouseService;
import com.yatang.sc.inventory.service.ItemLocSohService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * xiangyonghong on 2017/11/15.
 */
@Service("itemLocInventoryQueryDubboService")
public class ItemLocInventoryQueryDubboServiceImpl implements ItemLocInventoryQueryDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemLocSohService itemLocSohService;

    @Autowired
    private ProdSellPriceInfoHelper mProdSellPriceInfoHelper;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private AreaWarehouseService areaWarehouseService;

    @Override
    public Response<List<AvcostResultDto>> queryBatchItemLocInventory(List<AvcostConditionDto> param) {
        log.info("queryBatchItemLocInventory param{}", JSON.toJSONString(param));
        List<AvcostResultDto> list = new ArrayList<AvcostResultDto>();
        Response<List<AvcostResultDto>> response = new Response<List<AvcostResultDto>>();
        try{
            for(AvcostConditionDto avcostConditionDto:param){
                Map<String,Object> map = MyBeanUtils.beanToMap(avcostConditionDto);
                ItemLocSoh itemLocSoh = itemLocSohService.getItemLocInventory(map);
                AvcostResultDto avcostResultDto = BeanConvertUtils.convert(itemLocSoh,AvcostResultDto.class);
                if(avcostResultDto !=null){
                    list.add(avcostResultDto);
                }
            }
            if(CollectionUtils.isEmpty(list)){
                response.setCode(CommonsEnum.RESPONSE_20005.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20005.getName());
                response.setSuccess(false);
            }
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(list);

        }catch (Exception e){
            e.printStackTrace();
            log.error("queryBatchItemLocInventory Exception:"+e.getMessage(),e);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            return response;
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
//            String logicWarehouseCode = AreaToWarehouseConfig.getWarehouseCode(province);
//            String branchCompanyId = AreaToWarehouseConfig.getBranchCompanyId(logicWarehouseCode);

            CfAreaWarehouse areaWarehouse = areaWarehouseService.queryAreawarehouseByProvince(province);
            String logicWarehouseCode = null;
            String branchCompanyId = null;
            if(areaWarehouse != null){
                logicWarehouseCode = areaWarehouse.getWarehouseCode();
                branchCompanyId = areaWarehouse.getBranchCompanyId();
            }
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
}
