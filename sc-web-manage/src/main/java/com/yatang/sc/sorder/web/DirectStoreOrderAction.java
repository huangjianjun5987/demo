package com.yatang.sc.sorder.web;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.ProductType;
import com.yatang.sc.common.utils.PricingUtil;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.inventory.dto.DirectValidateInventoryDto;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.operation.util.excel.ExcelUtil2;
import com.yatang.sc.operation.util.excel.FileUtils;
import com.yatang.sc.operation.web.BaseAction;
import com.yatang.sc.purchase.dto.DirectCommitOrderDto;
import com.yatang.sc.purchase.dubboservice.DirectStoreOrdersDubboService;
import com.yatang.sc.purchase.dubboservice.PurchaseDubboService;
import com.yatang.sc.sorder.res.ActionResponse;
import com.yatang.sc.sorder.vo.DirectCommitOrderVo;
import com.yatang.sc.sorder.vo.DirectStoreCommerItemVo;
import com.yatang.sc.sorder.vo.DirectStoreInfoVo;
import com.yatang.sc.sorder.vo.DirectValidateInventoryVo;
import com.yatang.sc.sorder.vo.ImportExcelVo;
import com.yatang.sc.sorder.vo.QueryStoreScVo;
import com.yatang.sc.sorder.vo.SimpleProductQueryVo;
import com.yatang.sc.sorder.vo.SimpleProductVo;
import com.yatang.sc.sorder.vo.UpdateItemVo;
import com.yatang.sc.sorder.vo.UploadFailedVo;
import com.yatang.sc.sorder.vo.ValidateProductVo;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.org.sc.dto.QueryStoreScDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreScDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import com.yatang.xc.pi.solr.dto.PrdRecordDto;
import com.yatang.xc.pi.solr.dto.ScBackendPrdAddQueryDto;
import com.yatang.xc.pi.solr.dto.ScBackendResultDto;
import com.yatang.xc.pi.solr.dubboservice.ScBackendSearchDubboService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sc/directStore")
public class DirectStoreOrderAction extends BaseAction {
    @Autowired
    OrganizationService organizationService;

    @Autowired
    OrganizationSCService organizationSCService;

    @Autowired
    ScBackendSearchDubboService scBackendSearchDubboService;

    @Autowired
    GoodsPriceQueryDubboService goodsPriceDubboService;

    @Autowired
    DirectStoreOrdersDubboService directStoreOrdersDubboService;

    @Autowired
    ItemLocInventoryDubboService inventoryDubboService;

    @Autowired
    PurchaseDubboService purchaseDubboService;

    @Autowired
    ProductScIndexDubboService productScIndexDubboService;


    @RequestMapping(value = "/getAllStores" , method = RequestMethod.GET)
    public Response<PageResult<StoreScDto>> getAllStores (QueryStoreScVo queryStoreScVo ){
        QueryStoreScDto queryStoreScDto = BeanConvertUtils.convert(queryStoreScVo,QueryStoreScDto.class);
       // queryStoreScDto.setStoreType(1);
        logger.debug("/getAllStores:request param,", JSONObject.toJSON(queryStoreScDto));
        Response<PageResult<StoreScDto>> resultResponse = new Response<PageResult<StoreScDto>>();
        PageResult<StoreScDto> pageResult = new PageResult<StoreScDto>();
        Response<Integer> total = organizationSCService.queryCountSCStoreInfo(queryStoreScDto);
        if (total.isSuccess()) {
            if (0 == total.getResultObject()) {
                resultResponse.setResultObject(pageResult);
                return ActionResponse.wrap(resultResponse, "未匹配到符合条件的结果！");
            }
        } else {
            return ActionResponse.wrap(resultResponse, total.getErrorMessage());
        }
        Response<List<StoreScDto>> result = organizationSCService.queryPageSCStoreInfo(queryStoreScDto);
        if (!result.isSuccess()) {
            return ActionResponse.wrap(resultResponse, result.getErrorMessage());
        }
        Long pageTotal = total.getResultObject().longValue();
        List<StoreScDto> resultObject = result.getResultObject();
        pageResult.setData(resultObject);
        pageResult.setPageNum(queryStoreScDto.getPageNo());
        pageResult.setPageSize(queryStoreScDto.getPageSize());
        pageResult.setTotal(pageTotal);
        resultResponse.setSuccess(true);
        resultResponse.setResultObject(pageResult);
        return ActionResponse.wrap(resultResponse);
    }
    /**
     * 根据门店id查询所需要的信息
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/getDirectInfo" , method = RequestMethod.GET)
    public Response<DirectStoreInfoVo> getStoreRes(String storeId) {
        Response<DirectStoreInfoVo> directStoreInfoVoResponse = new Response<>();
        Response<StoreDto> storeRes = organizationService.queryStoreById(storeId);
        logger.info("查询出来的门店信息:{}",JSONObject.toJSONString(storeRes));
        if (!storeRes.isSuccess() || storeRes.getResultObject() == null){
            logger.error("查询门店信息失败");
            directStoreInfoVoResponse.setSuccess(false);
            directStoreInfoVoResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            directStoreInfoVoResponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            directStoreInfoVoResponse.setResultObject(null);
            return directStoreInfoVoResponse;
        }
        DirectStoreInfoVo directStoreInfoVo = convertStoreToVo(storeRes.getResultObject());
        logger.info("查询门店信息成功:{}",JSONObject.toJSONString(directStoreInfoVo));
        directStoreInfoVoResponse.setResultObject(directStoreInfoVo);
        directStoreInfoVoResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        directStoreInfoVoResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        directStoreInfoVoResponse.setSuccess(true);
        return  directStoreInfoVoResponse;
    }

    /**
     * 获取所有的商品id和商品名字
     * @return
     */
    @RequestMapping(value = "/getItemsInfo" , method = RequestMethod.GET)
    public Response<PageResult<SimpleProductVo>> getAllItem(SimpleProductQueryVo simpleProductQueryVo ){
        logger.info("获取所有的商品id和商品名字条件是:{}",JSONObject.toJSONString(simpleProductQueryVo));
       // simpleProductQueryVo.setSalesInfo(simpleProductQueryVo.getSalesInfo()+"-0");
        simpleProductQueryVo.setSalesInfo(null);
        Response<PageResult<SimpleProductVo>> simpleProductVoResponse = new Response<>();
        ScBackendPrdAddQueryDto scPrdAddQueryDto = BeanConvertUtils.convert(simpleProductQueryVo,ScBackendPrdAddQueryDto.class);
        Response<ScBackendResultDto> scBackendResultDtoResponse = scBackendSearchDubboService.prdAddSearch(scPrdAddQueryDto);
        logger.info("获取所有的商品id和商品名字结果是:{}",JSONObject.toJSONString(scBackendResultDtoResponse));
        if (!scBackendResultDtoResponse.isSuccess() || scBackendResultDtoResponse.getResultObject() == null){
            logger.error("查询所有商品失败");
            simpleProductVoResponse.setSuccess(false);
            simpleProductVoResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            simpleProductVoResponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            simpleProductVoResponse.setResultObject(null);
            return simpleProductVoResponse;
        }
        PageResult<SimpleProductVo> simpleProductPageVo = convertScBackToSimpleProductPageVo(scBackendResultDtoResponse.getResultObject());
        simpleProductPageVo.setPageSize(simpleProductQueryVo.getPageSize());
        simpleProductVoResponse.setSuccess(true);
        simpleProductVoResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        simpleProductVoResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        simpleProductVoResponse.setResultObject(simpleProductPageVo);
        return  simpleProductVoResponse;
    }

    /**
     * 获取单个商品的信息
     * @param productCode
     * @param branchCompanyId
     * @param quantity
     * @param deliveryWarehouseCode
     * @return
     */
    @RequestMapping(value = "/getItemInfo" , method = RequestMethod.GET)
    public Response<DirectStoreCommerItemVo> getItemInfo(String productCode,String branchCompanyId,long quantity,String deliveryWarehouseCode ){
        logger.info("获取单个商品信息条件是,productCode:{},branchCompanyId:{},quantity:{},deliveryWarehouseCode:{}",productCode,branchCompanyId,quantity,deliveryWarehouseCode);
        Response<DirectStoreCommerItemVo> directStoreCommerItemVoResponse = new Response<DirectStoreCommerItemVo>();
        DirectStoreCommerItemVo directStoreCommerItemVo =  getItem(productCode,branchCompanyId,quantity,deliveryWarehouseCode);
        logger.info("获取单个商品信息结果是:{}",JSONObject.toJSONString(directStoreCommerItemVo));
        directStoreCommerItemVoResponse.setResultObject(directStoreCommerItemVo);
        directStoreCommerItemVoResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        directStoreCommerItemVoResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        directStoreCommerItemVoResponse.setSuccess(true);
        return directStoreCommerItemVoResponse;
    }

    @ParamValid
    @RequestMapping(value="/fileUpload",method= RequestMethod.POST)
    public Response<List<DirectStoreCommerItemVo>> fileUpload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
           @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String branchCompanyId, String deliveryWarehouseCode) throws IOException {
        Response<List<DirectStoreCommerItemVo>> res = new Response<>();

        if(multipartFile == null || multipartFile.isEmpty()){
            logger.error("文件不存在");
            res.setErrorMessage(CommonsEnum.RESPONSE_10045.getName());
            res.setCode(CommonsEnum.RESPONSE_10045.getCode());
            res.setSuccess(false);
            return res;
        }

        InputStream in = multipartFile.getInputStream();

        try {
            List<List<Object>> records = FileUtils.getBankListByExcel(in,multipartFile.getOriginalFilename());
            if(CollectionUtils.isEmpty(records) || records.size() == 1){
                res.setErrorMessage(CommonsEnum.RESPONSE_10047.getName());
                res.setCode(CommonsEnum.RESPONSE_10047.getCode());
                res.setSuccess(false);
                return res;
            }
            logger.info("Excel文件中的行数是:{}",records.size());

            List<DirectStoreCommerItemVo> itemVos = new ArrayList<>();
            for (int i = 1; i < records.size(); i++) {
                List<Object> lo = records.get(i);
                // 排除表格中的空行、空字符串及不完整数据
                if(lo == null || lo.get(0)== null || StringUtils.isEmpty(String.valueOf(lo.get(0)))||lo.size()<2){
                    continue;
                }
                ImportExcelVo vo = new ImportExcelVo();
                vo.setProductCode(String.valueOf(lo.get(0)));
                vo.setQuantity(Integer.valueOf(String.valueOf(lo.get(1))));
//                将Excel中的数据转换成直营店订单中的列表类
                DirectStoreCommerItemVo itemVo = getItem(vo.getProductCode(), branchCompanyId, vo.getQuantity(), deliveryWarehouseCode);
                itemVos.add(itemVo);
                logger.info("Excel导入的商品是:{}",JSONObject.toJSONString(itemVo));
            }
            res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            res.setCode(CommonsEnum.RESPONSE_200.getCode());
            res.setSuccess(true);
            res.setResultObject(itemVos);
        } catch (NumberFormatException ne){
            logger.error("数字格式化异常",ne.getMessage());
            ne.printStackTrace();
            res.setErrorMessage("请输入正确的数字格式");
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setResultObject(null);
        }catch (Exception e) {
            logger.error("文件导入失败",e.getMessage());
            e.printStackTrace();
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setResultObject(null);
        } finally {
            in.close();
            return res;
        }
    }

    /**
     * 修改商品数量
     * @param updateItemVo
     * @return
     */
    @RequestMapping(value = "/updateItem" , method = RequestMethod.POST)
    public Response<DirectStoreCommerItemVo> updateItem(@RequestBody UpdateItemVo updateItemVo ){
        logger.info("updateItem时传入的参数是:{}",JSONObject.toJSONString(updateItemVo));
        Response<DirectStoreCommerItemVo> directStoreCommerItemVoResponse = new Response<DirectStoreCommerItemVo>();
        DirectStoreCommerItemVo directStoreCommerItemVo =  getItem(updateItemVo.getProductCode(), updateItemVo.getBranchCompanyId(),updateItemVo.getQuantity(),updateItemVo.getDeliveryWarehouseCode());
        logger.info("updateItem的结果是:{}",JSONObject.toJSONString(directStoreCommerItemVo));
        directStoreCommerItemVoResponse.setResultObject(directStoreCommerItemVo);
        directStoreCommerItemVoResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        directStoreCommerItemVoResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        directStoreCommerItemVoResponse.setSuccess(true);
        return directStoreCommerItemVoResponse;
    }

    @RequestMapping(value="/downloadExcelModel",method = RequestMethod.GET)
    public void downloadExcelModel (HttpServletResponse response){
        List<ImportExcelVo> importExcelVos = new ArrayList<>();
        ImportExcelVo importExcelVo = new ImportExcelVo();
        importExcelVos.add(importExcelVo);
        try{
            ExcelUtil2.listToExcel2(importExcelVos, response);
        }catch (Exception e){
            logger.error("导出异常", e);
        }
    }

    /**
     * 转换成前端所需要的字段
     * @param storeDto
     * @return
     */
    public DirectStoreInfoVo convertStoreToVo(StoreDto storeDto){
        DirectStoreInfoVo directStoreInfoVo = new DirectStoreInfoVo();
        directStoreInfoVo.setConsignee(storeDto.getContact());
        directStoreInfoVo.setPhone(storeDto.getMobilePhone());
        directStoreInfoVo.setReceivingAddress(storeDto.getAddress());
        directStoreInfoVo.setBranchCompanyId(storeDto.getBranchCompanyId());
        directStoreInfoVo.setDeliveryWarehouseCode(storeDto.getDeliveryWarehouseCode());
        directStoreInfoVo.setStoreId(storeDto.getId());
        return directStoreInfoVo;
    }

    public PageResult<SimpleProductVo> convertScBackToSimpleProductPageVo(ScBackendResultDto scBackendResultDto){
        PageResult<SimpleProductVo> simpleProductVoPageResult = new PageResult<SimpleProductVo>();
        simpleProductVoPageResult.setPageNum(scBackendResultDto.getPageNum());
        simpleProductVoPageResult.setTotal((long)scBackendResultDto.getTotalCount());
        List<SimpleProductVo>  simpleProductVos = new ArrayList<SimpleProductVo>();
        SimpleProductVo simpleProductVo;
        for(PrdRecordDto prdRecordDto:scBackendResultDto.getRecords()){
            simpleProductVo = new SimpleProductVo();
            if (StringUtils.isEmpty(prdRecordDto.getCouponId())){
                simpleProductVo.setProductCode(prdRecordDto.getProductCode());
                simpleProductVo.setProductId(prdRecordDto.getProductId());
                simpleProductVo.setProductName(prdRecordDto.getName());
                simpleProductVos.add(simpleProductVo);
            }
        }
        simpleProductVoPageResult.setData(simpleProductVos);
        return  simpleProductVoPageResult;
    }

    /**
     * 通过商品id查出商品的信息
     */
    public DirectStoreCommerItemVo getItem(String productCode, String branchCompanyId, long quantity, String deliveryWarehouseCode) {
        DirectStoreCommerItemVo itemVo = new DirectStoreCommerItemVo();
        Response<ProductIndexDto> dtoResponse = productScIndexDubboService.queryByProductCode(productCode);
        if (!dtoResponse.isSuccess() || dtoResponse.getResultObject() == null){
            UploadFailedVo failedVo = new UploadFailedVo("无商品信息",productCode);
            itemVo.setUploadFailedVos(failedVo);
            itemVo.setAvailable(false);
            itemVo.setMessage("此商品编码对应的商品信息不存在："+productCode);
            logger.error("查询商品服务返回的数据{}",JSONObject.toJSONString(dtoResponse));
            logger.error("此商品对应的商品信息不存在,productCode{}",productCode);
            return itemVo;
        }
        ProductIndexDto productDto = dtoResponse.getResultObject();
        itemVo.setFirstLevelCategoryName(productDto.getFirstLevelCategoryName());
        itemVo.setSecondLevelCategoryName(productDto.getSecondLevelCategoryName());
        itemVo.setThirdLevelCategoryName(productDto.getThirdLevelCategoryName());
        itemVo.setSellFullCase(productDto.getSellFullCase());
        itemVo.setFullCaseUnit(productDto.getFullCaseUnit());
        itemVo.setInternationalCodes(productDto.getInternationalCodes());
        itemVo.setPackingSpecifications(productDto.getPackingSpecifications());
        itemVo.setProductCode(productDto.getProductCode());
        itemVo.setProductName(productDto.getSaleName());
        itemVo.setProductId(productDto.getId());
        itemVo.setUnitExplanation(productDto.getUnitExplanation());
        itemVo.setSaleQuantity(quantity);
        itemVo.setQuantity(quantity);
        itemVo.setMinUnit(productDto.getMinUnit());
        itemVo.setCouponId(productDto.getCouponId());

        boolean isElectricProd = ProductType.PRODUCT_TYPE_COUPON.equals(productDto.getProductType());

        Response<ProdSellPriceInfoDto> prodSellPriceInfoDtoResponse = goodsPriceDubboService.getGoodsSellPrice(productDto.getId(),branchCompanyId);
        ProdSellPriceInfoDto prodSellPriceInfoDto = prodSellPriceInfoDtoResponse.getResultObject();
        if (!prodSellPriceInfoDtoResponse.isSuccess() || prodSellPriceInfoDto == null) {
            UploadFailedVo failedVo = new UploadFailedVo(itemVo.getProductName(), itemVo.getProductCode());
            itemVo.setUploadFailedVos(failedVo);
            itemVo.setAvailable(false);
            itemVo.setMessage("商品[" + itemVo.getProductName() + "]销售关系不存在");
            return itemVo;
        }
        if (prodSellPriceInfoDto.getStatus() != null && prodSellPriceInfoDto.getStatus() == 0 && !isElectricProd) {//虚拟商品不关心销售关系
            UploadFailedVo failedVo = new UploadFailedVo(itemVo.getProductName(), itemVo.getProductCode());
            itemVo.setUploadFailedVos(failedVo);
            itemVo.setAvailable(false);
            itemVo.setMessage("商品[" + itemVo.getProductName() + "]不支持当前区域销售");
            logger.error("该商品不支持当前区域销售:{},{}", productDto.getId(), productDto.getProductCode());
            return itemVo;
        }
        if (prodSellPriceInfoDto.getMinNumber() != null && prodSellPriceInfoDto.getMinNumber() > 0) {
            itemVo.setMinNumber(prodSellPriceInfoDto.getMinNumber());
        }
        if (prodSellPriceInfoDto.getSalesInsideNumber() != null && prodSellPriceInfoDto.getSalesInsideNumber() > 0) {
            itemVo.setSalesInsideNumber(prodSellPriceInfoDto.getSalesInsideNumber());
        }
        if (itemVo.getSellFullCase() != null && itemVo.getSellFullCase() == 1
                && itemVo.getSalesInsideNumber() != null && itemVo.getSalesInsideNumber() != 0) {
            itemVo.setMinNumber(itemVo.getMinNumber() / itemVo.getSalesInsideNumber());
        }
        if (itemVo.getSellFullCase() != null && itemVo.getSellFullCase()==1){
            itemVo.setQuantity(itemVo.getSaleQuantity()*itemVo.getSalesInsideNumber());
        }
        //如果销售数量小于起订量的话  就把销售数量改为起订量
        if (itemVo.getQuantity() < itemVo.getMinNumber()){
            itemVo.setQuantity(itemVo.getMinNumber());
            itemVo.setSaleQuantity(itemVo.getQuantity());
            if (itemVo.getSellFullCase() != null && itemVo.getSellFullCase()==1){
                itemVo.setSaleQuantity(itemVo.getQuantity()/itemVo.getSalesInsideNumber());
            }
        }
        if (itemVo.getQuantity()%itemVo.getSalesInsideNumber()!=0){
            itemVo.setIntegerTimes(false);
        }
        //检查库存
        ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
        itemInventoryDto.setProductId(productDto.getId());
        itemInventoryDto.setItemQty(itemVo.getQuantity());
        itemInventoryDto.setLoc(deliveryWarehouseCode);
        itemInventoryDto.setBranchCompanyId(branchCompanyId);
        Response<Boolean> repCK = inventoryDubboService.checkInventory(itemInventoryDto);
        logger.info("库存检查:{},product:{},预留数量{}", JSONObject.toJSONString(repCK), JSONObject.toJSONString(itemInventoryDto), quantity);
        itemVo.setEnough(repCK.getResultObject());
        //通过商品数量找单价
        Response<Double> priceResponse = directStoreOrdersDubboService.getPrice(productDto.getId(),branchCompanyId,itemVo.getQuantity());
        if (priceResponse.isSuccess() && priceResponse.getResultObject() != null){
            itemVo.setSalePrice(PricingUtil.roundPrice(priceResponse.getResultObject()));
            if (itemVo.getSellFullCase() != null && itemVo.getSellFullCase()==1){
                itemVo.setSalePrice(PricingUtil.roundPrice(itemVo.getSalePrice()*itemVo.getSalesInsideNumber()));
            }
        }
        if (!priceResponse.isSuccess()&&priceResponse.getCode().equals("10044")){
            UploadFailedVo failedVo = new UploadFailedVo(itemVo.getProductName(),itemVo.getProductCode());
            itemVo.setUploadFailedVos(failedVo);
            itemVo.setAvailable(false);
            itemVo.setMessage(itemVo.getProductName()+",不支持该区域销售");
        }
        return itemVo;
    }

    @RequestMapping(value = "directCommitOrder",method= RequestMethod.POST)
    public Response<String> directCommitOrder(@RequestBody @Validated DirectCommitOrderVo directCommitOrderVo){
        logger.info("/directCommitOrder start:param{}",JSONObject.toJSON(directCommitOrderVo));
        DirectCommitOrderDto directCommitOrderDto = BeanConvertUtils.convert(directCommitOrderVo,DirectCommitOrderDto.class);
        Response<String> rep = purchaseDubboService.directCommitOrder(directCommitOrderDto);
        logger.info("/directCommitOrder end:",JSONObject.toJSON(rep));
        return rep;
    }

    /**
     * 直营店下单  提交订单之前的校验
     * @param directValidateInventoryVo
     * @return
     */
    @RequestMapping(value = "validateDirectOrder",method= RequestMethod.POST)
    public Response<List<String>> validateDirectOrder(@RequestBody DirectValidateInventoryVo directValidateInventoryVo ){
        logger.info("/validateDirectOrder start:param{}",JSONObject.toJSON(directValidateInventoryVo));
        Response<List<String>> response = new Response<>();
        List<String> unEnoughProductIds = new ArrayList<>();
        List<DirectValidateInventoryDto> directValidateInventoryDtos = new ArrayList<>();
        if (directValidateInventoryVo != null && !CollectionUtils.isEmpty(directValidateInventoryVo.getProducts())){
            for (ValidateProductVo validateProductVo : directValidateInventoryVo.getProducts()){
                DirectValidateInventoryDto directValidateInventoryDto = new DirectValidateInventoryDto();
                directValidateInventoryDto.setBranchCompanyId(directValidateInventoryVo.getBranchCompanyId());
                directValidateInventoryDto.setLoc(directValidateInventoryVo.getLoc());
                directValidateInventoryDto.setProductId(validateProductVo.getProductId());
                directValidateInventoryDto.setItemQty(validateProductVo.getQuantity());
                Response<ProductIndexDto> dtoResponse = productScIndexDubboService.queryByProductId(directValidateInventoryDto.getProductId());
                logger.info("/validateDirectOrder :ProductIndexDto{}",JSONObject.toJSON(dtoResponse));
                if (dtoResponse.isSuccess() && dtoResponse.getResultObject()!= null && dtoResponse.getResultObject().getSellFullCase() != null && dtoResponse.getResultObject().getSellFullCase() == 1){
                    Response<ProdSellPriceInfoDto> prodSellPriceInfoDtoResponse = goodsPriceDubboService.getGoodsSellPrice(validateProductVo.getProductId(),directValidateInventoryVo.getBranchCompanyId());
                    if (prodSellPriceInfoDtoResponse.isSuccess() && prodSellPriceInfoDtoResponse.getResultObject()!= null && prodSellPriceInfoDtoResponse.getResultObject().getSalesInsideNumber()!= null && prodSellPriceInfoDtoResponse.getResultObject().getSalesInsideNumber()> 0){
                        directValidateInventoryDto.setItemQty(directValidateInventoryDto.getItemQty()*prodSellPriceInfoDtoResponse.getResultObject().getSalesInsideNumber());
                        logger.info("/validateDirectOrder :商品是按箱销售的*内装数之后的值:{}",directValidateInventoryDto.getItemQty());
                    }
                }
                directValidateInventoryDtos.add(directValidateInventoryDto);
            }
        }
        List<ItemInventoryDto> itemInventoryDtos = BeanConvertUtils.convertList(directValidateInventoryDtos,ItemInventoryDto.class);
        Response<Map<String, Long>> availableInventoryMaps = inventoryDubboService.queryAllAvailableInventory(itemInventoryDtos);
        logger.info("/validateDirectOrder :查询库存的结果{}",JSONObject.toJSON(availableInventoryMaps));
        //如果查询库存没有成功或者返回的是null值 那么直接返回
        if (!availableInventoryMaps.isSuccess()||availableInventoryMaps.getResultObject()== null){
            logger.error("validateDirectOrder:查询库存服务没有成功");
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setResultObject(unEnoughProductIds);
            return response;
        }
        Iterator<Map.Entry<String, Long>> it = availableInventoryMaps.getResultObject().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            for (DirectValidateInventoryDto directValidateInventoryDto : directValidateInventoryDtos){
                if (directValidateInventoryDto.getProductId().equals(entry.getKey())){
                    if (entry.getValue()<directValidateInventoryDto.getItemQty()){
                        unEnoughProductIds.add(directValidateInventoryDto.getProductId());
                    }
                }
            }
        }
        logger.info("/validateDirectOrder :库存不足的商品id{}",JSONObject.toJSON(unEnoughProductIds));
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setSuccess(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        response.setResultObject(unEnoughProductIds);
        return  response;
    }

}
