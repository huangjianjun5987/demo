package com.yatang.sc.purchase.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.staticvalue.WishStatus;
import com.yatang.sc.common.utils.PricingUtil;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.order.domain.WishDetailPo;
import com.yatang.sc.order.service.WishService;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.purchase.dto.wish.ScanCodeDto;
import com.yatang.sc.purchase.dto.wish.WishListConditionDto;
import com.yatang.sc.purchase.dto.wish.WishListsDto;
import com.yatang.sc.purchase.dubboservice.WishQueryDubboService;
import com.yatang.sc.purchase.service.ProdSellPriceInfoHelper;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wishQueryDubboService")
public class WishQueryDubboServiceImpl implements WishQueryDubboService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductHelper productHelper;

    @Autowired
    WishService wishService;

    @Autowired
    private ProdSellPriceInfoHelper mProdSellPriceInfoHelper;

    @Autowired
    ItemLocInventoryDubboService inventoryDubboService;

    @Override
    public Response<WishListsDto> scanCode(ScanCodeDto scanCodeDto) {
        log.info("scanCode,param:{}.", JSON.toJSONString(scanCodeDto));
        Response<WishListsDto> response = new Response<>();
        WishListsDto wishListsDto = new WishListsDto();
        List<String> barCodes = new ArrayList<>();
        barCodes.add(scanCodeDto.getBarCode());
        Map<String,Object> condition = new HashMap<>();
        List<String> status = new ArrayList<>();
        status.add(WishStatus.init.getCode());
        condition.put("status",status);
        condition.put("barCode",scanCodeDto.getBarCode());
        condition.put("userId",scanCodeDto.getUserId());
        long count = wishService.queryWishDetailCountByCondition(condition);
        if(count > 0){
            log.info("scanCode,product exists,barCode:{}",scanCodeDto.getBarCode());
            response.setCode(CommonsEnum.RESPONSE_10049.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_10049.getName());
            response.setSuccess(false);
            return response;
        }
        log.info("scanCode,findProductByInternationalCodes,param:{}",JSON.toJSONString(barCodes));
        Map<String,ProductIndexDto> products = productHelper.findProductByInternationalCodes(barCodes);
        log.info("scanCode,findProductByInternationalCodes,size:{},result:{}",products.size(),JSON.toJSONString(products));
        if(!CollectionUtils.isEmpty(products) && products.get(scanCodeDto.getBarCode()) != null){
            ProductIndexDto product = products.get(scanCodeDto.getBarCode());
            log.info("scanCode,main data have product barCode:{}",scanCodeDto.getBarCode());
            if(checkEnable(product.getId(),scanCodeDto,1)){
                wishListsDto.setStatus("1");//有货
            }else{
                wishListsDto.setStatus("0");//无货
            }
            wishListsDto.setProductImg(product.getThumbnailImage());
            wishListsDto.setProductName(product.getName());
            wishListsDto.setProductCode(product.getProductCode());
        }
        wishListsDto.setBarCode(scanCodeDto.getBarCode());
        wishListsDto.setQuantity(1);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setSuccess(true);
        response.setResultObject(wishListsDto);
        return response;
    }

    private boolean checkEnable(String productId,ScanCodeDto scanCodeDto,long quantity) {
        ProdSellPriceInfoDto prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(productId,scanCodeDto.getBranchCompanyId());
        if(prodSellPriceInfoDto == null){
            log.info("scanCode,prodSellPriceInfoDto not exits,productId:{},branchCompanyId:{} barCode:{}",productId,scanCodeDto.getBranchCompanyId(),scanCodeDto.getBarCode());
            return false;
        }
        ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
        itemInventoryDto.setProductId(productId);
        itemInventoryDto.setBranchCompanyId(scanCodeDto.getBranchCompanyId());
        itemInventoryDto.setLoc(scanCodeDto.getWareHouseCode());
        itemInventoryDto.setItemQty(quantity);
        Response<Boolean> repCK = inventoryDubboService.checkInventory(itemInventoryDto);
        if (repCK == null || !repCK.isSuccess() || !repCK.getResultObject()) {
            log.info("buyItemsAgain purchaseAgain failed:库存不足 ,productId:{},warehouseCode:{},",productId,scanCodeDto.getWareHouseCode());
            return false;
        }
        return true;
    }

    @Override
    public Response<PageResult<WishListsDto>> wishLists(WishListConditionDto wishListConditionDto) {
        log.info("wishLists,userId:{}",wishListConditionDto.getFranchiserId());
        Response<PageResult<WishListsDto>> response = new Response<>();
        PageResult<WishListsDto> pageResult = new PageResult<>();
        List<WishListsDto> list = new ArrayList<>();
        Map<String,Object> condition = new HashMap<>();
        List<String> status = new ArrayList<>();
        status.add(WishStatus.init.getCode());
        status.add(WishStatus.close.getCode());
        condition.put("userId",wishListConditionDto.getFranchiserId());
        condition.put("status", status);

        long total = wishService.queryWishDetailCountByCondition(condition);
        condition.put("start",(wishListConditionDto.getPage()-1)*wishListConditionDto.getPageSize());
        condition.put("end",wishListConditionDto.getPageSize());
        List<WishDetailPo> wishDetailPoList = wishService.queryWishDetailByCondition(condition);
        for(WishDetailPo wishDetailPo : wishDetailPoList){
            WishListsDto wishListsDto = new WishListsDto();
            wishListsDto.setId(String.valueOf(wishDetailPo.getId()));
            wishListsDto.setQuantity(wishDetailPo.getQuantity());
            wishListsDto.setProductName(wishDetailPo.getProductName());
            wishListsDto.setProductImg(wishDetailPo.getProductImg());
            wishListsDto.setProductCode(wishDetailPo.getProductCode());
            wishListsDto.setBarCode(wishDetailPo.getGbCode());
            if(WishStatus.init.getCode().equals(wishDetailPo.getStatus())){
                wishListsDto.setStatus("1");
            }else if(WishStatus.close.getCode().equals(wishDetailPo.getStatus())){
                wishListsDto.setStatus("0");
            }
            list.add(wishListsDto);
        }
        pageResult.setPageSize(wishListConditionDto.getPageSize());
        pageResult.setPageNum(wishListConditionDto.getPage());
        pageResult.setTotal(total);
        pageResult.setData(list);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setSuccess(true);
        response.setResultObject(pageResult);
        return response;
    }

    @Override
    public Response<PageResult<WishListsDto>> wishRealizedList(WishListConditionDto wishListConditionDto) {
        log.info("wishRealizedList,param:{}",JSON.toJSONString(wishListConditionDto));
        Response<PageResult<WishListsDto>> response = new Response<>();
        PageResult<WishListsDto> pageResult = new PageResult<>();
        List<WishListsDto> list = new ArrayList<>();
        Map<String,Object> condition = new HashMap<>();
        condition.put("userId",wishListConditionDto.getFranchiserId());
        List<String> status = new ArrayList<>();
        status.add(WishStatus.complete.getCode());
        condition.put("status", status);
        long total = wishService.queryWishDetailCountByCondition(condition);
        condition.put("start",(wishListConditionDto.getPage()-1)*wishListConditionDto.getPageSize());
        condition.put("end",wishListConditionDto.getPageSize());
        List<WishDetailPo> wishDetailPoList = wishService.queryWishDetailByCondition(condition);
        for (WishDetailPo wishDetailPo : wishDetailPoList){
            WishListsDto wishListsDto = new WishListsDto();
            wishListsDto.setProductCode(wishDetailPo.getProductCode());
            wishListsDto.setProductName(wishDetailPo.getProductName());
            wishListsDto.setProductImg(wishDetailPo.getProductImg());
            wishListsDto.setBarCode(wishDetailPo.getGbCode());
            ProductIndexDto productIndexDto = productHelper.findProductByCode(wishListsDto.getProductCode());
            if(productIndexDto == null){
                continue;
            }
            ProdSellPriceInfoDto prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(productIndexDto.getId(),wishListConditionDto.getBranchCompanyId());
            if(prodSellPriceInfoDto == null){
                continue;
            }

            /**
             * 默认单个销售
             */
            double lowestPrice = prodSellPriceInfoDto.getLowestPrice().doubleValue();//最小单位销售价格
            String packingSpecifications = productIndexDto.getPackingSpecifications()+"/"+productIndexDto.getMinUnit();//规格，如：110g/个
//            String salePrice = lowestPrice+"/"+productIndexDto.getMinUnit();// 显示价格，如：200/个
            String minNumber = prodSellPriceInfoDto.getMinNumber()+productIndexDto.getMinUnit();// 起订量，如：2个起订
            String minUnit = productIndexDto.getMinUnit();
            if(1 == productIndexDto.getSellFullCase()){
                /**
                 * 按箱销售
                 */
                wishListsDto.setSellFullCase(1);
                int salesInsideNumber = prodSellPriceInfoDto.getSalesInsideNumber();//内装数
                lowestPrice = PricingUtil.roundPrice(salesInsideNumber*lowestPrice);
                packingSpecifications = salesInsideNumber+productIndexDto.getMinUnit()+"/"+productIndexDto.getFullCaseUnit();// 规格，如：6个/箱
//                salePrice = lowestPrice+"/"+productIndexDto.getFullCaseUnit();// 显示价格，如：1200/箱
                minNumber = (prodSellPriceInfoDto.getMinNumber()/salesInsideNumber)+productIndexDto.getFullCaseUnit();// 起订量，如：2箱起订
                minUnit = productIndexDto.getFullCaseUnit();
            }
            wishListsDto.setQuantity(wishDetailPo.getQuantity());
            Map<String, String> property = new HashMap<String, String>();
            property.put("packingSpecifications",packingSpecifications);
            property.put("minNumber",minNumber);
            property.put("salePrice",lowestPrice+"");
            property.put("minUnit",minUnit);  // 最小销售单位，如：个、支、双
//            property.put("unitExplanation",productIndexDto.getUnitExplanation());  // 标准包装单位参考值，例如：箱*盒*个'
//            property.put("fullCaseUnit",productIndexDto.getFullCaseUnit());//箱装单位：如箱、包、袋
            wishListsDto.setProperties(property);
            list.add(wishListsDto);
        }
        pageResult.setPageSize(wishListConditionDto.getPageSize());
        pageResult.setPageNum(wishListConditionDto.getPage());
        pageResult.setData(list);
        pageResult.setTotal(total);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setSuccess(true);
        response.setResultObject(pageResult);
        return response;
    }


}
