package com.yatang.sc.facade.flow.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yatang.sc.facade.dto.BundleProductScDto;
import com.yatang.sc.inventory.dto.BathCheckInventoryDto;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.xc.mbd.pi.es.dto.BundleProductDto;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ProductDetailDto;
import com.yatang.sc.facade.dto.ProductListForAppDto;
import com.yatang.sc.facade.dto.ProductListForAppQueryParamDto;
import com.yatang.sc.facade.dto.ServiceCommitmentsDto;
import com.yatang.sc.facade.dto.prod.AttributeDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.facade.dubboservice.ServiceCommitmentsQueryDubboService;
import com.yatang.sc.facade.flow.ProductQueryFlowService;
import com.yatang.sc.facade.flow.QueryProductSellPriceHelper;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.xc.mbd.pi.es.dto.AttributeValueIndexDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

/**
 * @描述: 商品查询的dubbo
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/7 19:32
 * @版本: v1.0
 */

@Service("productQueryFlowService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductQueryFlowServiceImpl implements ProductQueryFlowService {
    private Logger log = LoggerFactory.getLogger(ProductQueryFlowServiceImpl.class);
    @Autowired
    private final ServiceCommitmentsQueryDubboService serviceCommitmentsQueryDubboService;// 服务承诺查询DubboService
    @Autowired
    private final GoodsPriceQueryDubboService goodsPriceQueryDubboService;// 销售价格查询
    @Autowired
    private final QueryProductSellPriceHelper queryProductSellPriceHelper;
    @Autowired
    private final ProductScIndexDubboService indexDubboService;// 商品查询dubboservice（供应链）
    @Autowired
    private final ItemLocInventoryDubboService itemLocInventoryDubboService;//查询仓库库存信息

    @Override
    public Response<ProductDetailDto> getProductDetail(String productCode, String branchCompanyId, String deliveryWarehouseCode) {

        log.info("flow--getProductDetail>>查询商品详情参数信息 productCode:{}, branchCompanyId:{},deliveryWarehouseCode:{}", productCode, branchCompanyId, deliveryWarehouseCode);
        Response<ProductDetailDto> response = new Response<>();
        try {
            // 根据id查询商品信息
            Response<ProductIndexDto> productDtoResponse = indexDubboService.queryByProductCode(productCode);
            log.info("flow--getProductDetail>>从主数据根据productCode：{},读取返回结果：{}", productCode, JSONObject.toJSONString(productDtoResponse));
            if (!productDtoResponse.isSuccess()) {// 响应出错
                log.error("查询商品信息失败" + JSONObject.toJSONString(productDtoResponse));
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }
            ProductIndexDto resultObject = productDtoResponse.getResultObject();
            if (null == resultObject) {// 为空
                log.error("查询商品信息为空" + JSONObject.toJSONString(productDtoResponse));
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }
            // 数据转换
            ProductDetailDto detailDto = BeanConvertUtils.convert(resultObject, ProductDetailDto.class);

            List<AttributeValueIndexDto> attributeValues = productDtoResponse.getResultObject().getAttributeValuleList();
            List<AttributeDto> attributeVos = new ArrayList<>();
            if (attributeValues != null && attributeValues.size() > 0) {
                // 处理属性值集合，用于页面展示
                // 1. 找出所有属性及属性值
                Map<String, String> nameMap = Maps.newHashMap(); // key-属性ID；val-属性名称
                Map<String, StringBuffer> valMap = Maps.newHashMap(); // key-属性ID；val-属性值名称字符串，多个以“，”分隔
                List<String> attrIdIndex = Lists.newArrayList(); // 属性ID索引集合
                for (AttributeValueIndexDto valueDto : attributeValues) {
                    if (!nameMap.containsKey(valueDto.getAttributeName())) { // 属性不存在
                        nameMap.put(valueDto.getAttributeName(), valueDto.getAttributeName());
                    }
                    if (valMap.containsKey(valueDto.getValue())) { // 属性值存在
                        StringBuffer currValBuf = valMap.get(valueDto.getAttributeName()); // 找到之前的属性值名称集合
                        currValBuf.append("，");
                        currValBuf.append(valueDto.getValue());
                        valMap.put(valueDto.getAttributeName(), currValBuf);
                    } else { // 属性不存在
                        StringBuffer valBuf = new StringBuffer();
                        valBuf.append(valueDto.getValue());
                        valMap.put(valueDto.getAttributeName(), valBuf);
                    }
                    if (!attrIdIndex.contains(valueDto.getAttributeName())) { // 索引集合中不存在
                        attrIdIndex.add(valueDto.getAttributeName());
                    }
                }
                // 2. 组装页面所需 商品参数 对象
                for (String aid : attrIdIndex) {
                    AttributeDto attrDto = new AttributeDto();
                    attrDto.setName(nameMap.get(aid));
                    attrDto.setValues(valMap.get(aid).toString());
                    attributeVos.add(attrDto);
                }
            }
            detailDto.setAttributes(attributeVos);

            ProdSellPriceInfoDto sellPriceInfoDto = queryProductSellPriceHelper.queryProdSellPriceInfo(detailDto.getId(), branchCompanyId);
            if (sellPriceInfoDto == null) {
                log.error("查询销售信息失败:" + detailDto.getId() + "--" + branchCompanyId);
                response.setCode(CommonsEnum.RESPONSE_10040.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10040.getName());
                return response;
            }
            detailDto.setSuggestPrice(sellPriceInfoDto.getSuggestPrice());

            //若商品是按箱销售
            if (1 == detailDto.getSellFullCase()) {
                //起订量=起订数量除以销售内装数
                sellPriceInfoDto.setMinNumber((sellPriceInfoDto.getMinNumber() == null ? 0 : sellPriceInfoDto.getMinNumber()) / sellPriceInfoDto.getSalesInsideNumber());
                sellPriceInfoDto.setMaxNumber((sellPriceInfoDto.getMaxNumber() == null ? 0 : sellPriceInfoDto.getMaxNumber()) / sellPriceInfoDto.getSalesInsideNumber());
                //价格需要换算成箱价
                sellPriceInfoDto.setLowestPrice(sellPriceInfoDto.getLowestPrice().multiply(new BigDecimal(sellPriceInfoDto.getSalesInsideNumber())));
                //对区间价格也得做处理
                List<ProdSellSectionPriceDto> sellSectionPrices = sellPriceInfoDto.getSellSectionPrices();
                if (sellSectionPrices != null && sellSectionPrices.size() > 0) {
                    for (Iterator<ProdSellSectionPriceDto> prodSellSectionPriceDtos = sellSectionPrices.iterator(); prodSellSectionPriceDtos.hasNext(); ) {
                        ProdSellSectionPriceDto prodSellSectionPriceDto = prodSellSectionPriceDtos.next();
                        prodSellSectionPriceDto.setStartNumber(
                                (int) Math.ceil((prodSellSectionPriceDto.getStartNumber() + 0.0) / sellPriceInfoDto.getSalesInsideNumber()));
                        prodSellSectionPriceDto.setEndNumber((int) Math.floor((prodSellSectionPriceDto.getEndNumber() + 0.0) / sellPriceInfoDto.getSalesInsideNumber()));
                        prodSellSectionPriceDto.setPrice(prodSellSectionPriceDto.getPrice().multiply(new BigDecimal(sellPriceInfoDto.getSalesInsideNumber())));
                        if (prodSellSectionPriceDto.getStartNumber() > prodSellSectionPriceDto.getEndNumber()) {
                            prodSellSectionPriceDtos.remove();
                        }
                    }
                }
            }
            // 避免运营批量导入销售关系时，起订量和区域价格起始数量不等导致页面显示问题
            if (sellPriceInfoDto.getSellSectionPrices() != null && sellPriceInfoDto.getSellSectionPrices().size() > 0) {
                ProdSellSectionPriceDto prodSellSectionPriceDto = sellPriceInfoDto.getSellSectionPrices().get(0);
                if (sellPriceInfoDto.getMinNumber() != prodSellSectionPriceDto.getStartNumber()) {
                    prodSellSectionPriceDto.setStartNumber(sellPriceInfoDto.getMinNumber());
                }
            }
            detailDto.setProdSellPriceInfo(sellPriceInfoDto);

            // 2.校验库存是否充足  yinyuxin
            detailDto.setStock(0L);
            if (deliveryWarehouseCode != null) {
                List<ItemInventoryDto> itemInventoryDtos=new ArrayList<>();
                if (!"2".equals(detailDto.getProductType())){
                    ItemInventoryDto itemInventoryDto=new ItemInventoryDto();
                    itemInventoryDto.setLoc(deliveryWarehouseCode);
                    itemInventoryDto.setProductId(detailDto.getId());
                    itemInventoryDtos.add(itemInventoryDto);
                    Response<BathCheckInventoryDto> bathCheckInventoryDtoResponse = itemLocInventoryDubboService
                            .batchCheckInventory(itemInventoryDtos);
                    log.info("productQueryFlowService--getProductDetail()--调用batchCheckInventory()--result:{}",JSONObject.toJSONString(bathCheckInventoryDtoResponse));
                    if (bathCheckInventoryDtoResponse.isSuccess()&&null!=bathCheckInventoryDtoResponse.getResultObject()
                            && CollectionUtils.isEmpty(bathCheckInventoryDtoResponse.getResultObject().getOutOfStock())){
                        detailDto.setStock(Long.MAX_VALUE);
                    }
                }else{
                    List<BundleProductScDto> bundleProducts=detailDto.getBundleProducts();
                    if (CollectionUtils.isNotEmpty(bundleProducts)){
                        for (BundleProductScDto bundleProductScDto:bundleProducts){
                            ItemInventoryDto itemInventoryDto=new ItemInventoryDto();
                            itemInventoryDto.setLoc(deliveryWarehouseCode);
                            itemInventoryDto.setProductId(bundleProductScDto.getProductId());
                            itemInventoryDtos.add(itemInventoryDto);
                        }
                        Response<BathCheckInventoryDto> bathCheckInventoryDtoResponse = itemLocInventoryDubboService
                                .batchCheckInventory(itemInventoryDtos);
                        log.info("productQueryFlowService--getProductDetail()--调用batchCheckInventory()--result:{}",JSONObject.toJSONString(bathCheckInventoryDtoResponse));
                        if (bathCheckInventoryDtoResponse.isSuccess()&&null!=bathCheckInventoryDtoResponse.getResultObject()
                                && CollectionUtils.isEmpty(bathCheckInventoryDtoResponse.getResultObject().getOutOfStock())){
                            detailDto.setStock(Long.MAX_VALUE);
                        }
                    }
                }
            }

            // 3.获取服务承诺
            Response<List<ServiceCommitmentsDto>> queryAllServiceCommitmentsListResponse = serviceCommitmentsQueryDubboService
                    .queryAllServiceCommitmentsList();
            if (!queryAllServiceCommitmentsListResponse.isSuccess()) {// 服务承诺返回失败
                log.error("服务承诺返回失败" + JSONObject.toJSONString(queryAllServiceCommitmentsListResponse));
            }
            detailDto.setServiceCommitments(queryAllServiceCommitmentsListResponse.getResultObject());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(detailDto);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<PageResult<ProductListForAppDto>> queryProductList(ProductListForAppQueryParamDto dto) {
        Response<PageResult<ProductListForAppDto>> response = new Response<PageResult<ProductListForAppDto>>();
        PageResult<ProductListForAppDto> pageResult = new PageResult<ProductListForAppDto>();
        List<ProductListForAppDto> list = new ArrayList<ProductListForAppDto>();
       /* try {
            dto.setPageNum((dto.getPageNum() == null) ? Integer.valueOf(CommonsEnum.RESPONSE_PAGE_NUM.getCode())
                    : dto.getPageNum());
            dto.setPageSize((dto.getPageSize() == null) ? Integer.valueOf(CommonsEnum.RESPONSE_PAGE_SIZE.getCode())
                    : dto.getPageSize());
            ProductQueryDto productQueryDto = new ProductQueryDto();
            productQueryDto = BeanConvertUtils.convert(dto, ProductQueryDto.class);
            Response<QueryProductSCResultDto> queryShelfProduct = productSCDubboService
                    .queryShelfProduct(productQueryDto);
            QueryProductSCResultDto resultObject = queryShelfProduct.getResultObject();
            if (resultObject == null || resultObject.getRecords() == null || resultObject.getRecords().size() <= 0) {
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setSuccess(false);
                return response;
            }
            List<QueryProductSCDto> records = resultObject.getRecords();
            for (QueryProductSCDto queryProductSCDto : records) {
                ProductListForAppDto productListForAppDto = new ProductListForAppDto();
                productListForAppDto = BeanConvertUtils.convert(queryProductSCDto, ProductListForAppDto.class);
                // TODO 查询阶梯价
                ProdSellPriceQueryParamDto queryParamDto = new ProdSellPriceQueryParamDto();
                queryParamDto.setCityCode(dto.getCityCode());
                queryParamDto.setProductId(queryProductSCDto.getId());
                Response<List<GoodsSellPriceInfoDto>> goodsPriceInfoResponse = goodsPriceQueryDubboService
                        .getGoodsSellPriceByCondition(queryParamDto);
                if (CommonsEnum.RESPONSE_200.getCode().equals(goodsPriceInfoResponse.getCode())) {
                    List<GoodsSellPriceInfoDto> goodsPriceInfoDtoList = goodsPriceInfoResponse.getResultObject();
                    if (1 == goodsPriceInfoDtoList.size()) {
                        productListForAppDto.setPrice(goodsPriceInfoDtoList.get(0).getPrice3());
                    }
                }
                list.add(productListForAppDto);
            }
            //TODO 查询销量
            //TODO 查询好评率
            pageResult.setData(list);
            //分页信息
            pageResult.setPageNum(resultObject.getPageNum());
            pageResult.setPageSize(resultObject.getPageSize());
            pageResult.setTotal(productSCDubboService.queryShelfProductCount(productQueryDto).getResultObject().longValue());

            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(pageResult);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }*/
        return response;
    }
}
