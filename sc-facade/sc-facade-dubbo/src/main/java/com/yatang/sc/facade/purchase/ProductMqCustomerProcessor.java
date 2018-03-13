package com.yatang.sc.facade.purchase;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yatang.sc.kidd.service.ProductService;
import com.yatang.sc.kidd.dto.product.ProductSynchronizeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductSCDubboService;
import com.yatang.xc.mbd.pi.es.dto.InternationalCodeDto;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductWmsDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 消费主数据商品新增修改的mq
 *
 * @author: yinyuxin
 * @version: 1.0, 2017年8月8日
 */
@Service("productMqCustomerProcessor")
@Slf4j
public class ProductMqCustomerProcessor implements MQMsgProcessor {

    @Autowired
    private ProductScIndexDubboService indexDubboService;
    @Autowired
    private ProductSCDubboService      productSCDubboService;
    @Resource(name = "kiddProductService")
    private ProductService             productService;



    @Override
    public void process(String message) {

        log.info("---------商品同步功能开始启动---------");

        // 1：消费主数据商品的id

        List<String> productIds = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(message, JSONObject.class);
        JSONArray jsonArray = jsonObject.getJSONArray("productIds");
        for (Object object : jsonArray) {
            if (object != null) {
                productIds.add(object.toString());
            }
        }

        // 2:遍历id数组，查询商品详情
        if (productIds != null && productIds.size() > 0) {
            List<Map<String, String>> errorProducts = Lists.newArrayList();
            for (String id : productIds) {
                try {
                    syncProduct(id);
                } catch (Exception e) {
                    Map<String,String> errorMessage=Maps.newHashMap();
                    errorMessage.put(id,e.toString());
                    errorProducts.add(errorMessage);
                }
            }
            if (errorProducts!=null && errorProducts.size()>0){
                log.error("---------商品同步功能出现失败的商品及原因:"+JSONObject.toJSONString(errorProducts)+"---------");
            }
        }
    }



    public Boolean syncProduct(String productId) {
        try {
            List<ProductWmsDto> productSyncSuccess = new ArrayList<ProductWmsDto>();
            Response<ProductIndexDto> response = indexDubboService.queryByProductId(productId);
            ProductIndexDto productDto = response.getResultObject();
            if (productDto != null) {
                log.info("---------code=" + productDto.getProductCode() + " 商品开始同步---------");
                //if ("1".equals(productDto.getWmsFlag()) || "3".equals(productDto.getWmsFlag())) {

                    // 调用际链接口，同步商品
                    //CommodityDto commodityDto = new CommodityDto();
                   // 系统重复订单处理方式:1、直接返回错误；2、重复商品跳过(skip)；3、重复商品修改(replace)
                    //	commodityDto.setType("3");
                    //List<CommodityItemDto> list = new ArrayList<CommodityItemDto>();

                if (StringUtils.isEmpty(productDto.getProductCode())) {
                    log.debug("---------code=" + productDto.getProductCode() + " 商品code为空 跳过同步---------");
                    return true;
                }

                ProductSynchronizeDto productSynchronizeDto = new ProductSynchronizeDto();
                productSynchronizeDto.setItemCode(productDto.getProductCode());
                productSynchronizeDto.setItemName(productDto.getSaleName());
                // TODO 雅堂小超货主编码
                productSynchronizeDto.setOwnerCode("YTXC");
                // TODO 际链给定特殊值，用于同步至所有wms
                productSynchronizeDto.setWarehouseCode("other");
                List<InternationalCodeDto> internationalCodes = productDto.getInternationalCodes();
                if (internationalCodes != null && internationalCodes.size() > 0) {
                    StringBuffer barCode = new StringBuffer();
                    for (InternationalCodeDto internationalCodeDto : internationalCodes) {
                        barCode.append(internationalCodeDto.getInternationalCode() + ";");
                    }
                    productSynchronizeDto.setBarCode(barCode.substring(0, barCode.length() - 1));
                }
                productSynchronizeDto.setItemType("ZC");
                productSynchronizeDto.setSkuProperty(productDto.getStorageCondition());

                productSynchronizeDto.setLength(this.numFormat(
                        (Integer.parseInt((productDto.getLength() != null) ? productDto.getLength() : "0")) / 10.00,
                        2));
                productSynchronizeDto.setHeight(this.numFormat(
                        (Integer.parseInt((productDto.getHeight() != null) ? productDto.getHeight() : "0")) / 10.00,
                        2));
                productSynchronizeDto.setWidth(this.numFormat(
                        (Integer.parseInt((productDto.getWidth() != null) ? productDto.getWidth() : "0")) / 10.00, 2));
                if (productDto.getWeight() != null && !"".equals(productDto.getWeight())) {
                    String weightString = productDto.getWeight().toLowerCase();
                    double weight = 0.000;
                    if (weightString.endsWith("kg")) {
                        weight = Double.parseDouble(weightString.substring(0, weightString.length() - 2)) * 1000;
                    } else if (weightString.endsWith("g")) {
                        weight = Double.parseDouble(weightString.substring(0, weightString.length() - 2));
                    }
                    productSynchronizeDto.setGrossWeight(this.numFormat(weight / 1000.000, 3));
                }

                productSynchronizeDto.setPcs((productDto.getHorizontalProductNum() != null ?
                        productDto.getHorizontalProductNum() :
                        0) + "*" + (productDto.getVerticalProductNum() != null ?
                        productDto.getVerticalProductNum() :
                        0) + "*" + (productDto.getHeightProductNum() != null ? productDto.getHeightProductNum() : 0));

                productSynchronizeDto.setStockUnit(productDto.getMinUnit());
                if (productDto.getBrand() != null) {
                    productSynchronizeDto.setBrandCode(productDto.getBrand().getId());
                    productSynchronizeDto.setBrandName(productDto.getBrand().getName());
                }
                productSynchronizeDto.setOriginAddress(productDto.getProducePlace());
                productSynchronizeDto.setCategoryId(productDto.getFourthCategoryId());
                productSynchronizeDto.setCategoryName(productDto.getFourthLevelCategoryName());

                productSynchronizeDto.setIsShelfLifeMgmt("N");
                if (productDto.getQualityGuaranteePeriod() != null) {
                    productSynchronizeDto.setIsShelfLifeMgmt("Y");
                    String guaranteePeriodUnit = productDto.getGuaranteePeriodUnit();
                    int qualityGuaranteePeriod = Integer.parseInt(productDto.getQualityGuaranteePeriod() != null ?
                            productDto.getQualityGuaranteePeriod() :
                            "0");
                    if ("1".equals(guaranteePeriodUnit)) {
                        // 年
                        productSynchronizeDto.setShelfLife(365 * 24 * qualityGuaranteePeriod);
                        productSynchronizeDto.setRejectLifecycle(
                                (int) (this.numFormat(365 * 2.0 / 3 * qualityGuaranteePeriod, 0)));
                        productSynchronizeDto.setLockupLifecycle(
                                (365 * qualityGuaranteePeriod > 45) ? 365 * qualityGuaranteePeriod - 45 : 0);
                        productSynchronizeDto.setAdventLifecycle(
                                (365 * qualityGuaranteePeriod > 90) ? 365 * qualityGuaranteePeriod - 90 : 0);
                    } else if ("2".equals(guaranteePeriodUnit)) {
                        // 月
                        productSynchronizeDto.setShelfLife(30 * 24 * qualityGuaranteePeriod);
                        productSynchronizeDto.setRejectLifecycle(
                                (int) (this.numFormat(30 * 2.0 / 3 * qualityGuaranteePeriod, 0)));
                        productSynchronizeDto.setLockupLifecycle(
                                (30 * qualityGuaranteePeriod > 45) ? 30 * qualityGuaranteePeriod - 45 : 0);
                        productSynchronizeDto.setAdventLifecycle(
                                (30 * qualityGuaranteePeriod > 90) ? 30 * qualityGuaranteePeriod - 90 : 0);
                    } else if ("3".equals(guaranteePeriodUnit)) {
                        // 日
                        productSynchronizeDto.setShelfLife(24 * qualityGuaranteePeriod);
                        productSynchronizeDto
                                .setRejectLifecycle((int) (this.numFormat(2.0 / 3 * qualityGuaranteePeriod, 0)));
                        productSynchronizeDto.setLockupLifecycle(
                                (qualityGuaranteePeriod > 45) ? qualityGuaranteePeriod - 45 : 0);
                        productSynchronizeDto.setAdventLifecycle(
                                (qualityGuaranteePeriod > 90) ? qualityGuaranteePeriod - 90 : 0);
                    }
                }
                if ("1".equals(productDto.getWmsFlag())) {
                    productSynchronizeDto.setActionType("1");
                } else {
                    productSynchronizeDto.setActionType("2");
                }

                // 处理仓库接口最大值过滤
                productSynchronizeDto.setRejectLifecycle(productSynchronizeDto.getRejectLifecycle() > 36500 ? 36500 : productSynchronizeDto.getRejectLifecycle());
                productSynchronizeDto.setLockupLifecycle(productSynchronizeDto.getLockupLifecycle() > 36500 ? 36500 : productSynchronizeDto.getLockupLifecycle());
                productSynchronizeDto.setAdventLifecycle(productSynchronizeDto.getAdventLifecycle() > 36500 ? 36500 : productSynchronizeDto.getAdventLifecycle());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (productDto.getUpdateTime() != null) {

                    Date updateTimeERP = dateFormat.parse(productDto.getUpdateTime());
                    productSynchronizeDto.setUpdateTime(updateTimeERP);
                }
                if (productDto.getCreateTime() != null) {

                    Date createTimeERP = dateFormat.parse(productDto.getCreateTime());
                    productSynchronizeDto.setCreateTime(createTimeERP);
                }
                productSynchronizeDto.setPicUrl(productDto.getMainImage());
                Response kiddResponse = productService.synchronizeProduct(productSynchronizeDto);
                if (kiddResponse.isSuccess()) {
                    // 同步成功
                    ProductWmsDto productWmsDto = new ProductWmsDto();
                    productWmsDto.setProductId(productDto.getId());
                    productWmsDto.setWmsFlag(2);
                    productSyncSuccess.add(productWmsDto);
                    log.info("---------code=" + productDto.getProductCode() + " 商品同步成功---------");
                } else {
                    throw new RuntimeException("---------code=" + productDto.getProductCode() + " 商品同步失败:"+kiddResponse.getErrorMessage()+"---------");
                }
                productSCDubboService.updateProductWms(productSyncSuccess);
                //} else {
                //    log.info("---------code=" + productDto.getProductCode() + " 商品状态为已同步，跳过此次同步---------");
                //}
            } else {
                throw new RuntimeException(" 商品同步失败:查询商品信息失败---------");
            }
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
        return true;
    }





    /**
     * 四舍五入，保留两位小数
     *
     * @param num   待处理的数字
     * @param scola 保留的小数位
     * @return
     */
    public double numFormat(double num, int scola) {
        BigDecimal decimal = new BigDecimal(num);
        return decimal.setScale(scola, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public String dateFormat2(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }



}
