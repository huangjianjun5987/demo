package com.yatang.sc.facade.flow.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.*;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceWriteDubboService;
import com.yatang.sc.facade.flow.ProdPurchaseInfoImportFlowService;
import com.yatang.sc.facade.flow.ProdPurchaseWriteFlowService;
import com.yatang.sc.facade.service.GoodPriceService;
import com.yatang.sc.facade.service.ProdPurchaseInfoImportService;
import com.yatang.sc.facade.service.ProdSellInfoImportService;

import lombok.RequiredArgsConstructor;

/**
 * @描述: 提交变价单
 * @类名: ProdPurchaseInfoImportFlowServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/12/12 10:32
 * @版本: v1.0
 */

@Service("prodPurchaseInfoImportFlowService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class ProdPurchaseInfoImportFlowServiceImpl implements ProdPurchaseInfoImportFlowService {
    private Logger log = LoggerFactory.getLogger(ProdPurchaseInfoImportFlowServiceImpl.class);

    private final ProdPurchaseInfoImportService prodPurchaseInfoImportService;

    private final ProdSellInfoImportService prodSellInfoImportService;

    private final GoodPriceService goodPriceService;

    private final ProdPurchaseWriteFlowService prodPurchaseWriteFlowService;

    private final GoodsPriceWriteDubboService goodsPriceWriteDubboService;


    /**
     * 更新采购变价单
     * @param id 上传id
     * @param userId 当前用户id
     * @return
     */
    @Override
    @Transactional
    public Response<Long> updateProdPurchase(Long id, String userId) {
        Response<Long> response = new Response<>();
        try {
            Preconditions.checkNotNull(id, "id不能为空");

            //取出采购导入的变价记录,查询最新的一条商品采购价导入表(调价单)
            ProdPurchaseInfoImportsPo responsePurchaseInfoImports = prodPurchaseInfoImportService.selectById(id);

            Preconditions.checkNotNull(responsePurchaseInfoImports, "数据为空");
            Preconditions.checkArgument(Objects.equals(responsePurchaseInfoImports.getCreateUserId(),userId),"无权限创建该变价单");
            Preconditions.checkArgument(responsePurchaseInfoImports.isAllVaild(),"调价单有未验证价格不能提交或已经提交状态不能重复创建。");

            List<ProdPurchaseInfoImportPo> listImport = responsePurchaseInfoImports.getImports();
            for(ProdPurchaseInfoImportPo list:listImport) {
                //Preconditions.checkArgument(!Objects.equals(list.getHandleResult(),2),"该变价单已经提交，不能重复创建。");
                //遍历更新采购变价单
                ProdPurchaseInfoDto purchaseInfoDto = new ProdPurchaseInfoDto();
                purchaseInfoDto.setId(list.getPriceId());
                purchaseInfoDto.setSpId(list.getSpId());
                purchaseInfoDto.setSpAdrId(String.valueOf(list.getSpAdrId()));
                purchaseInfoDto.setProductId(list.getProductId());
                purchaseInfoDto.setProductCode(list.getProductCode());
                purchaseInfoDto.setNewestPrice(list.getNewestPrice());
                purchaseInfoDto.setModifyUserId(responsePurchaseInfoImports.getCreateUserId());
                Response<Void> responsePurchase = prodPurchaseWriteFlowService.updateProdPurchase(purchaseInfoDto);
                if(responsePurchase.isSuccess()) {
                    //更改导入记录表的状态为已提交。
                    list.setHandleResult(2);
                    prodPurchaseInfoImportService.updateHandleResult(list);
                } else {
                    throw new RuntimeException("行号为"+list.getLineNumber()+"更新异常:" + responsePurchase.getErrorMessage());
                }
            }

            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(responsePurchaseInfoImports.getId());
        } catch (Exception e) {
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 更新销售变价单
     * @param id 上传id
     * @param userId 当前用户id
     * @return
     */
    @Override
    @Transactional
    public Response<Long> updateProdSell(Long id, String userId) {
        Response<Long> response = new Response<>();
        try {
            Preconditions.checkNotNull(id, "id不能为空");

            //取出售价导入的变价记录,查询最新的一条商品售价导入表(调价单)
            ProdSellInfoImportsPo responseSellInfoImports = prodSellInfoImportService.selectById(id);

            Preconditions.checkNotNull(responseSellInfoImports, "数据为空");
            Preconditions.checkArgument(Objects.equals(responseSellInfoImports.getCreateUserId(),userId),"无权限创建该变价单");
            Preconditions.checkArgument(responseSellInfoImports.isAllVaild(),"调价单有未验证价格不能提交或已经提交状态不能重复创建。");

            List<ProdSellInfoImportPo> listImport = responseSellInfoImports.getImports();
            Map<Long, ProdSellPriceInfoDto> prices = Maps.newHashMap();
            Map<Long, Map<String, ProdSellInfoImportPo>> priceImports = Maps.newHashMap();
            for(ProdSellInfoImportPo importPo:listImport) {
                //Preconditions.checkArgument(!Objects.equals(list.getHandleResult(),2),"该变价单已经提交，不能重复创建。");
                ProdSellPriceInfoDto priceInfoDto = prices.get(importPo.getPriceId());
                Map<String, ProdSellInfoImportPo> imports = priceImports.get(importPo.getPriceId());
                if (Objects.equals(priceInfoDto, null)) {
                    SellPriceInfoPo sellPriceInfoPo = goodPriceService.getGoodsSellPriceById(importPo.getPriceId());
                    priceInfoDto = BeanConvertUtils.convert(sellPriceInfoPo,ProdSellPriceInfoDto.class);
                    prices.put(importPo.getPriceId(), priceInfoDto);

                    imports = Maps.newHashMap();
                    priceImports.put(importPo.getPriceId(), imports);
                }
                String importKey = importPo.getSection();// importPo.getStartNumber() + "-" + importPo.getEndNumber()
                imports.put(importKey, importPo);

                for(ProdSellSectionPriceDto sellList:priceInfoDto.getSellSectionPrices()) {
                    if(!(Objects.equals(importPo.getStartNumber(),sellList.getStartNumber()) && Objects.equals(importPo.getEndNumber(),sellList.getEndNumber()))) {
                        continue;
                    }
                    if(Objects.equals(sellList.getPrice(),importPo.getNewestPrice())) {
                        break;
                    }
                    sellList.setPrice(importPo.getNewestPrice());
                }
            }

            for (ProdSellPriceInfoDto prodSellPriceInfoDto : prices.values()) {
                Response<Boolean> result = goodsPriceWriteDubboService.updateSellPrice(prodSellPriceInfoDto);
                Map<String, ProdSellInfoImportPo> imports = priceImports.get(prodSellPriceInfoDto.getId());
                if(result.isSuccess()) {
                    //更改导入记录表的状态为已提交。
                    for (ProdSellInfoImportPo importPo : imports.values()) {
                        importPo.setHandleResult(2);
                        prodSellInfoImportService.updateHandleResult(importPo);
                    }
                }else {
                    List<Long> lineNumbers = Lists.newArrayList();
                    for (ProdSellInfoImportPo importPo : imports.values()) {
                        lineNumbers.add(importPo.getLineNumber());
                    }
                    String errorLineNumbers = Joiner.on(",").join(lineNumbers);
					throw new RuntimeException("行号为" + errorLineNumbers + "更新异常:" + result.getErrorMessage());
                }
            }

            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(responseSellInfoImports.getId());
        } catch (Exception e) {
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
