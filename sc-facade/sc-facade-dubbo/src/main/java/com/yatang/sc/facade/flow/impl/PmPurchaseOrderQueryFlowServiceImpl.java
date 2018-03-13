package com.yatang.sc.facade.flow.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dto.pm.PmProductDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderInfoDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemQueryParamDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseQueryParamDto;
import com.yatang.sc.facade.dto.supplier.SpAdrBasicDto;
import com.yatang.sc.facade.dto.supplier.SupplierAdrInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dubboservice.ProdPurchaseQueryDubboService;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.facade.enums.PmPurchaseOrderType;
import com.yatang.sc.facade.enums.PurchaseModeType;
import com.yatang.sc.facade.flow.PmPurchaseOrderQueryFlowService;
import com.yatang.sc.facade.service.SupplierService;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述: 商品采购单的flowservice服务实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:29
 * @版本: v1.0
 */
@Slf4j
@Service("pmPurchaseOrderQueryFlowService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PmPurchaseOrderQueryFlowServiceImpl implements PmPurchaseOrderQueryFlowService {


    private final ProductScIndexDubboService indexDubboService;//商品的dubo服务

    private final ProdPurchaseQueryDubboService prodPurchaseQueryDubboService;

    private final SupplierQueryDubboService supplierQueryDubboService;//供应商主表


    private final OrganizationService organizationService;//门店信息


    private final WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;//仓库信息
    private final SupplierService service;//供应商service

    @Override
    public Response<PmPurchaseOrderItemDto> getNewPmPurchaseOrderItem(PmPurchaseOrderItemQueryParamDto queryParamDto) {

        Response<PmPurchaseOrderItemDto> response = new Response<>();
        String productId = queryParamDto.getProductId();//商品名称
        try {
            log.info("start----getNewPmPurchaseOrderItem---queryParamDto:{}", JSON.toJSONString(queryParamDto));
            //1.查询商品的信息
            Response<ProductIndexDto> productIndexDtoResponse = indexDubboService.queryByProductId(productId);
            log.info("flow--getNewPmPurchaseOrderItem>>根据商品id:{}查询商品返回结果:{}", productId, JSON.toJSONString(productIndexDtoResponse));
            if (!CommonsEnum.RESPONSE_200.getCode().equals(productIndexDtoResponse.getCode())) {// 响应出错
                return BeanConvertUtils.convert(productIndexDtoResponse, Response.class);
            }
            ProductIndexDto resultObject = productIndexDtoResponse.getResultObject();
            if (null == resultObject) {// 为空
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }
            PmProductDto pmProductDto = BeanConvertUtils.convert(resultObject, PmProductDto.class);

   /*         Integer horizontalProductNum = resultObject.getHorizontalProductNum() == null ? 0 : resultObject.getHorizontalProductNum();
            Integer heightProductNum = resultObject.getHeightProductNum() == null ? 0 : resultObject.getHeightProductNum();
            Integer verticalProductNum = resultObject.getVerticalProductNum() == null ? 0 : resultObject.getVerticalProductNum();
            int defaultPurchaseInsideNumber = horizontalProductNum * heightProductNum * verticalProductNum;//默认采购内装数
            pmProductDto.setPurchaseInsideNumber(defaultPurchaseInsideNumber);*/
            pmProductDto.setBrandName(resultObject.getBrand() == null ? null : resultObject.getBrand().getName());//获取品牌名

            //2.查询采购关系
            ProdPurchaseQueryParamDto prodPurchaseQueryParamDto = BeanConvertUtils.convert(queryParamDto, ProdPurchaseQueryParamDto.class);//采购关系查询po
            Response<ProdPurchaseInfoDto> prodPurchaseInfoResponse = prodPurchaseQueryDubboService.getProdPurchaseByParam(prodPurchaseQueryParamDto);
            if (!CommonsEnum.RESPONSE_200.getCode().equals(prodPurchaseInfoResponse.getCode())) {// 响应出错
                return BeanConvertUtils.convert(prodPurchaseInfoResponse, Response.class);
            }
            if (null == prodPurchaseInfoResponse.getResultObject()) {
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                return response;
            }
            ProdPurchaseInfoDto purchaseInfoDto = prodPurchaseInfoResponse.getResultObject();
            BigDecimal purchasePrice = purchaseInfoDto.getPurchasePrice();
            if (purchasePrice==null) {

                log.error("dubbo---getNewPmPurchaseOrderItem>>采购价格为空:{}",JSON.toJSONString(purchaseInfoDto));
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                response.setCode("采购价格为空请核实");
                return response;
            }
            PmPurchaseOrderItemDto pmPurchaseOrderItemDto = new PmPurchaseOrderItemDto();


            //3.拼接数据
            //1.商品的信息
            pmPurchaseOrderItemDto.setInputTaxRate(pmProductDto.getInputTaxRate());//进项税
            pmPurchaseOrderItemDto.setProductId(productId);//商品id
            pmPurchaseOrderItemDto.setProductName(pmProductDto.getSaleName());//商品名
            pmPurchaseOrderItemDto.setProductCode(pmProductDto.getProductCode());//商品编号
            pmPurchaseOrderItemDto.setPackingSpecifications(pmProductDto.getPackingSpecifications());//包装规格
            pmPurchaseOrderItemDto.setUnitExplanation(pmProductDto.getMinUnit());//包装单位参考值
            pmPurchaseOrderItemDto.setProducePlace(pmProductDto.getProducePlace());//产地
            if (resultObject.getBrand() != null) {
                pmPurchaseOrderItemDto.setProductBrandId(resultObject.getBrand().getId());
                pmPurchaseOrderItemDto.setProductBrandName(resultObject.getBrand().getName());
            }


            //2.采购价格关系的信息
            pmPurchaseOrderItemDto.setPurchaseInsideNumber(purchaseInfoDto.getPurchaseInsideNumber());//采购内装数
            pmPurchaseOrderItemDto.setProdPurchaseId(String.valueOf(purchaseInfoDto.getId()));//采购关系主键
            pmPurchaseOrderItemDto.setPurchasePrice(purchaseInfoDto.getPurchasePrice());//采购价格
            pmPurchaseOrderItemDto.setInternationalCode(purchaseInfoDto.getInternationalCode());
            pmPurchaseOrderItemDto.setProdPurchaseId(String.valueOf(purchaseInfoDto.getId()));

            response.setResultObject(pmPurchaseOrderItemDto);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);

            log.info("end----getNewPmPurchaseOrderItem");
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<PmPurchaseOrderExtDto> checkAndResetPmPurchaseOrderExt(PmPurchaseOrderExtDto pmPurchaseOrderExtDto) {
        Response<PmPurchaseOrderExtDto> response = new Response<>();
        try {

            log.info("start----checkAndResetPmPurchaseOrderExt---pmPurchaseOrderExtDto:{}", JSON.toJSONString(pmPurchaseOrderExtDto));
            StringBuilder errBuilder = new StringBuilder();//错误信息

            //主表
            PmPurchaseOrderDto pmPurchaseOrder = pmPurchaseOrderExtDto.getPmPurchaseOrder();
            //1检查主表 商品校验,供应商状态,供应商地点状态
            String spAdrId = pmPurchaseOrder.getSpAdrId();//供应商地点
            //1.1检查主表 供应商状态并填充仓库相关信息,
            Response<String> rep = addMoreSupplierInfo(pmPurchaseOrder, spAdrId);
            if (!CommonsEnum.RESPONSE_200.getCode().equals(rep.getCode())) {// 响应出错
                return BeanConvertUtils.convert(rep, Response.class);
            }

            //2.校验商品信息
            //校验订单是否有重复
            Set<String> prodPurchaseIdSet = new HashSet<>();
            //采购类型判定 类型:0:普通采购单;1:赠品采购;2:促销釆购
            Integer purchaseOrderType = pmPurchaseOrder.getPurchaseOrderType();//订单采购类型
            Integer businessMode = pmPurchaseOrder.getBusinessMode();
            for (PmPurchaseOrderItemDto orderItemDto : pmPurchaseOrderExtDto.getPmPurchaseOrderItems()) {

                //2.1校验商品是否有用  2017/07/28
                Response<ProductIndexDto> productIndexDtoResponse = indexDubboService.queryByProductId(orderItemDto.getProductId());
                log.info("flow--checkAndResetPmPurchaseOrderExt>>根据商品id:{}查询商品返回结果:{}", orderItemDto.getProductId(), JSON.toJSONString(productIndexDtoResponse));
                if (!CommonsEnum.RESPONSE_200.getCode().equals(productIndexDtoResponse.getCode())) {// 响应出错
                    return BeanConvertUtils.convert(productIndexDtoResponse, Response.class);
                }
                ProductIndexDto resultObject = productIndexDtoResponse.getResultObject();
                if (null == resultObject) {// 为空
                    log.info("商品编号为:{}的商品无效", orderItemDto.getProductId());
                    errBuilder.append("商品编号为:").append(orderItemDto.getProductId()).append("的商品无效").append("\br");
                    break;
                }
                //有效的情况
                PmProductDto pmProductDto = BeanConvertUtils.convert(resultObject, PmProductDto.class);
                // 校验订单的销售模式 	// 经营模式:0:经销;1:代销;2寄售
                if (pmPurchaseOrder.getSaleOrderId()==null){//
                    Integer distributionStatus = resultObject.getDistributionStatus() == null ? 0 : resultObject.getDistributionStatus();

                    if (!businessMode.equals(distributionStatus)) {
                        log.error("商品编号为:{}的商品的经营模式与订单的不一致,", orderItemDto.getProductCode());
                        errBuilder.append("商品编号为:").append(orderItemDto.getProductCode()).append("经营模式与订单的不一致").append("\br");
                        break;
                    }
                }else {//直送默认为寄售
                    businessMode=2;
                    pmPurchaseOrderExtDto.getPmPurchaseOrder().setCurrencyCode("CNY");
                }

                //判定采购的否一致
                //2.2 判定商品采购关系是否有效  2017/07/28
                if (prodPurchaseIdSet.contains(orderItemDto.getProductId())) {//判定是否有重复
                    log.error("商品编号为:{}的商品订单重复", orderItemDto.getProductId());
                    errBuilder.append("商品编号为:").append(orderItemDto.getProductId()).append("的商品订单重复").append("\br");
                } else {
                    prodPurchaseIdSet.add(orderItemDto.getProductId());
                }

                ProdPurchaseQueryParamDto prodPurchaseQueryParamDto = new ProdPurchaseQueryParamDto();//查询采购关系条件(商品id和供应商地点id)
                prodPurchaseQueryParamDto.setProductId(orderItemDto.getProductId());//商品id
                prodPurchaseQueryParamDto.setSpAdrId(spAdrId);//供应商地点id
                Response<ProdPurchaseInfoDto> prodPurchaseInfoResponse = prodPurchaseQueryDubboService.getProdPurchaseByParam(prodPurchaseQueryParamDto);
                if (!CommonsEnum.RESPONSE_200.getCode().equals(prodPurchaseInfoResponse.getCode())) {// 采购关系查询失败的情况
                    return BeanConvertUtils.convert(prodPurchaseInfoResponse, Response.class);
                }

                ProdPurchaseInfoDto purchaseInfoDto = prodPurchaseInfoResponse.getResultObject();//商品采购关系
                log.info("商品采购关系>>purchaseInfoDto:{}", purchaseInfoDto);
                if (null == purchaseInfoDto) {
                    log.error("商品id为:{}的采购关系失效", orderItemDto.getProductId());
                    errBuilder.append("商品id为:").append(orderItemDto.getProductId()).append("采购关系失效").append("\br");
                    break;
                }

                BigDecimal purchasePrice = purchaseInfoDto.getPurchasePrice();
                if (purchasePrice==null) {
                    log.error("商品id为:{}的采购价格为空", orderItemDto.getProductId());
                    errBuilder.append("商品id为:").append(orderItemDto.getProductId()).append("采购价格为空").append("\br");
                    break;
                }

                //获取采购关系
                if (purchaseInfoDto.getDeleteStatus() == 1 || purchaseInfoDto.getStatus() == 0) {
                    log.error("商品id为:{}的采购关系失效", orderItemDto.getProductId());
                    errBuilder.append("商品id为:").append(orderItemDto.getProductId()).append("的采购关系失效").append("\br");
                    break;
                }
                Integer defaultPurchaseInsideNumber = purchaseInfoDto.getPurchaseInsideNumber();//默认采购内装数
                if (pmPurchaseOrder.getSaleOrderId()!=null){//该采购单为供应商直送（lvheping）
                    //新增字段
                    orderItemDto.setProdPurchaseId(purchaseInfoDto.getId().toString());//采购关系id
                    orderItemDto.setPurchasePrice(purchaseInfoDto.getPurchasePrice());//设置采购价格
                }else {//直送不需要校验采购内装数()
                    //先判定商品的采购关系id
                    if (!orderItemDto.getProdPurchaseId().equals(String.valueOf(purchaseInfoDto.getId()))) {
                        log.info("商品id为:{}的采购关系失效", orderItemDto.getProductId());
                        errBuilder.append("商品id为:").append(orderItemDto.getProductId()).append("的采购关系失效").append("\br");
                        break;
                    }
                //2.3.判定商品的内装数是否有效(商品采购数不满足采购内装数的整数倍)  2017/07/28
                Integer purchaseNumber = orderItemDto.getPurchaseNumber();//采购数量
                if (purchaseNumber == 0 || purchaseNumber % defaultPurchaseInsideNumber != 0) {
                    log.error("商品编号为:{}商品采购数不满足采购内装数的整数倍", orderItemDto.getProductCode());
                    errBuilder.append("商品编号为:").append(orderItemDto.getProductCode()).append("商品采购数不满足采购内装数的整数倍").append("\br");
                    break;
                }
                }


                //拼接数据
                //3.1.商品的信息
                orderItemDto.setInputTaxRate(pmProductDto.getInputTaxRate());//进项税
                orderItemDto.setProductName(pmProductDto.getSaleName());//商品名
                orderItemDto.setProductCode(pmProductDto.getProductCode());
                orderItemDto.setProducePlace(pmProductDto.getProducePlace());//产地
                //新加字段封装（lvheping）
                orderItemDto.setGroups(pmProductDto.getFirstLevelCategoryId());//部类（一级分类）
                orderItemDto.setDept(pmProductDto.getSecondLevelCategoryId());//大类（二级分类）
                orderItemDto.setClasss(pmProductDto.getThirdLevelCategoryId());//中类（三级分类）
                orderItemDto.setSubclass(pmProductDto.getFourthCategoryId());//小类（四级分类）
                orderItemDto.setProductCode(pmProductDto.getProductCode());//商品code

                //3.2商品采购关系
                orderItemDto.setPackingSpecifications(pmProductDto.getPackingSpecifications());//包装规格
                orderItemDto.setUnitExplanation(pmProductDto.getMinUnit());//包装单位参考值
                orderItemDto.setPurchaseInsideNumber(defaultPurchaseInsideNumber);//采购内装数
                orderItemDto.setInternationalCode(purchaseInfoDto.getInternationalCode());//国际码

                //3.3
                if (PmPurchaseOrderType.GIFT_PURCHASE.getCode().equals(purchaseOrderType)) {//促销采购
                    orderItemDto.setPurchasePrice(new BigDecimal(String.valueOf(0)));//采购价格

                } else if ((PmPurchaseOrderType.NORMAL_PURCHASE.getCode().equals(purchaseOrderType))) {//正常采购
                    orderItemDto.setPurchasePrice(purchaseInfoDto.getPurchasePrice());//采购价格
                } else {//促销采购
                    if (purchasePrice.compareTo(BigDecimal.ZERO) >= 1) {//大于零
                        if (purchasePrice.compareTo(purchaseInfoDto.getPurchasePrice()) >= 1) {//小于
                            log.error("商品编号为:{}商品采购促销价不能大于商品采购价:{}", orderItemDto.getProductCode(), purchaseInfoDto.getPurchasePrice());
                            errBuilder.append("商品编号为:").append(orderItemDto.getProductCode()).append("商品采购促销价不能大于商品采购价").append(purchaseInfoDto.getPurchasePrice()).append("\br");
                        }
                    } else {//小于等于零
                        log.error("商品编号为:{}商品采购促销价不能等于零", orderItemDto.getProductCode());
                        errBuilder.append("商品编号为:").append(orderItemDto.getProductCode()).append("商品采购促销价不能等于零").append("\br");
                    }
                }
            }
            if (errBuilder.toString().length() == 0) {//没有错误信息
                response.setSuccess(true);
                response.setResultObject(pmPurchaseOrderExtDto);
            } else {//错误信息
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_10031.getCode());
                response.setErrorMessage(errBuilder.toString());
            }
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 校验草稿状态订单的有效性质并返回数据(供应商是否有效,商品是否有效,供应商地点是否有效,采购关系是否有效,判定商品采购数量和采购内装数之间的关系)
     *
     * @param pmPurchaseOrderInfoDto
     * @return
     */
    @Override
    public Response<PmPurchaseOrderInfoDto> checkAndResetPmPurchaseOrderInfo(PmPurchaseOrderInfoDto pmPurchaseOrderInfoDto) {
        Response<PmPurchaseOrderInfoDto> response = new Response<>();
        try {
            log.info("start----checkAndResetPmPurchaseOrderInfo---pmPurchaseOrderInfoDto:{}", pmPurchaseOrderInfoDto);
            //1检查主表 商品校验,供应商状态,供应商地点状态
            String spId = pmPurchaseOrderInfoDto.getSpId();//供应商id
            String spAdrId = pmPurchaseOrderInfoDto.getSpAdrId();//供应商地点
            //判定供应商和供应商状态是否有效 todo 2017/07/28
            //1.1检查主表 供应商状态,
            Response<SupplierInfoDto> supplierInfoDtoResponse = supplierQueryDubboService.queryMainById(spId);
            if (!supplierInfoDtoResponse.isSuccess()) {// 响应出错
                return BeanConvertUtils.convert(supplierInfoDtoResponse, Response.class);
            }
            if (2 != supplierInfoDtoResponse.getResultObject().getStatus()) {//不是正常状态
                pmPurchaseOrderInfoDto.setIsValid(0);
            }
            //1.2 供应商地点状态

            Response<SupplierAdrInfoDto> supplierAdrInfoDtoResponse = supplierQueryDubboService.queryMainAddById(Integer.valueOf(spAdrId));
            if (!CommonsEnum.RESPONSE_200.getCode().equals(supplierAdrInfoDtoResponse.getCode())) {// 响应出错
                return BeanConvertUtils.convert(supplierAdrInfoDtoResponse, Response.class);
            }
            if (2 != supplierAdrInfoDtoResponse.getResultObject().getStatus()) {//供应商地点暂不可用
                pmPurchaseOrderInfoDto.setIsValid(0);
            }
            pmPurchaseOrderInfoDto.setIsValid(1);
            //2.校验商品信息
            for (PmPurchaseOrderItemDto orderItem : pmPurchaseOrderInfoDto.getPmPurchaseOrderItems()) {

                //2.1校验商品是否有用

                Response<ProductIndexDto> productIndexDtoResponse = indexDubboService.queryByProductId(orderItem.getProductId());
                if (!productIndexDtoResponse.isSuccess()) {// 响应出错
                    return BeanConvertUtils.convert(productIndexDtoResponse, Response.class);
                }
                ProductIndexDto resultObject = productIndexDtoResponse.getResultObject();
                if (null == resultObject) {// 为空
                    orderItem.setIsValid(0);
                    break;
                }
                //有效的情况
                PmProductDto pmProductDto = BeanConvertUtils.convert(resultObject, PmProductDto.class);

                //2.2 判定商品采购关系是否有效
                ProdPurchaseQueryParamDto prodPurchaseQueryParamDto = new ProdPurchaseQueryParamDto();//查询采购关系条件(商品id和供应商地点id)
                prodPurchaseQueryParamDto.setProductId(orderItem.getProductId());//商品id
                prodPurchaseQueryParamDto.setSpAdrId(spAdrId);//供应商地点id
                prodPurchaseQueryParamDto.setSpId(spId);//设置供应商id
                Response<ProdPurchaseInfoDto> prodPurchaseInfoResponse = prodPurchaseQueryDubboService.getProdPurchaseByParam(prodPurchaseQueryParamDto);
                if (!CommonsEnum.RESPONSE_200.getCode().equals(prodPurchaseInfoResponse.getCode())) {// 采购关系查询失败的情况
                    return BeanConvertUtils.convert(prodPurchaseInfoResponse, Response.class);
                }
                ProdPurchaseInfoDto purchaseInfoDto = prodPurchaseInfoResponse.getResultObject();//商品采购关系
                //获取采购关系
                if (purchaseInfoDto == null) {
                    orderItem.setIsValid(0);
                    break;
                }
                Integer defaultPurchaseInsideNumber = purchaseInfoDto.getPurchaseInsideNumber();//默认采购内装数

                //2.3.判定商品的内装数是否有效(商品采购数不满足采购内装数的整数倍) todo 2017/07/28
                Integer purchaseNumber = orderItem.getPurchaseNumber();//采购数量
                if (purchaseNumber == 0 || purchaseNumber % defaultPurchaseInsideNumber != 0) {
                    orderItem.setIsValid(0);
                    break;
                }


                //拼接数据
                //3.1.商品的信息
                orderItem.setInputTaxRate(pmProductDto.getInputTaxRate());//进项税
                orderItem.setProductName(pmProductDto.getSaleName());//商品名

                //3..2商品采购关系
                orderItem.setIsValid(1);
                orderItem.setPackingSpecifications(pmProductDto.getPackingSpecifications());//包装规格
                orderItem.setUnitExplanation(pmProductDto.getMinUnit());//包装单位参考值
                orderItem.setPurchaseInsideNumber(defaultPurchaseInsideNumber);//采购内装数
                orderItem.setPurchasePrice(purchaseInfoDto.getPurchasePrice());//采购价格
                orderItem.setInternationalCode(purchaseInfoDto.getInternationalCode());//国际码
            }
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setResultObject(pmPurchaseOrderInfoDto);
            log.info("end----checkAndResetPmPurchaseOrderInfo");
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 校验预计送货日期是否等于订单创建日期+供应商提前期
     *
     * @param convert
     * @return
     */
    @Override
    public Response<PmPurchaseOrderDto> auditPurchaseOrderInfo(PmPurchaseOrderDto convert) {
        //查询订单信息

        log.info("start----auditPurchaseOrderInfo---convert:{}", convert);
        String spAdrId = convert.getSpAdrId();
        Response<SpAdrBasicDto> spAdrBasicDtoResponse = supplierQueryDubboService.queryByAdrInfoId(Integer.valueOf(spAdrId));
        if (!spAdrBasicDtoResponse.isSuccess()) {// 响应出错
            return BeanConvertUtils.convert(spAdrBasicDtoResponse, Response.class);
        }
        Integer goodsArrivalCycle1 = spAdrBasicDtoResponse.getResultObject().getGoodsArrivalCycle();
        if (goodsArrivalCycle1 == null) {
            Response convert1 = BeanConvertUtils.convert(spAdrBasicDtoResponse, Response.class);
            convert1.setCode(CommonsEnum.RESPONSE_10032.getCode());
            convert1.setErrorMessage(CommonsEnum.RESPONSE_10032.getName());
            convert1.setSuccess(false);
            return convert1;

        }
        Date currdate = convert.getCreateTime();
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, goodsArrivalCycle1);// num为增加的天数，可以改变的
        currdate = ca.getTime();//加好送货日期后的日期
        Date estimatedDeliveryDate = convert.getEstimatedDeliveryDate();
        if (currdate.compareTo(estimatedDeliveryDate) == 0) {
            Date auditTime = convert.getAuditTime();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, goodsArrivalCycle1);// num为增加的天数，可以改变的
            currdate = calendar.getTime();
            convert.setEstimatedDeliveryDate(currdate);//把预计到货日期设为当前审核日期加上供应商送货日期
        }
        Response response = new Response();
        response.setSuccess(true);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        response.setResultObject(convert);
        log.info("end----auditPurchaseOrderInfo");
        return response;
    }


    private Response<String> addMoreSupplierInfo(PmPurchaseOrderDto pmPurchaseOrder, String spAdrId) {


        Response<String> response = new Response<>();
        StringBuilder errBuilder = new StringBuilder();
        SupplierAdrInfoPo supplierAdrInfoDto = service.queryProviderPlace(Integer.valueOf(spAdrId));//供应商地点
        log.info("查询的供应商地点信息:supplierInfo:{}", JSON.toJSONString(supplierAdrInfoDto));
        if(supplierAdrInfoDto == null) {
            log.error("查询供应商地点失败");
            return BeanConvertUtils.convert(supplierAdrInfoDto, Response.class);
        }
        SupplierInfoPo supplierInfoDto = service.queryById(supplierAdrInfoDto.getParentId());//供应商信息
        log.info("查询的供应商信息:supplierInfo:{}", JSON.toJSONString(supplierInfoDto));
        if (2 != supplierInfoDto.getStatus()) {//不是正常状态
            log.error(":{}供应商暂不可用:{}",supplierInfoDto.getId(),supplierInfoDto.getStatus());
            errBuilder.append("供应商暂不可用\br");
        }else {

            //供应商信息设定
            pmPurchaseOrder.setSpNo(supplierInfoDto.getSupplierBasicInfo().getSpNo());//获取编号
            pmPurchaseOrder.setSpName(supplierInfoDto.getSupplierBasicInfo().getCompanyName());//获取公司名称
            pmPurchaseOrder.setSpId(supplierInfoDto.getId());//获取供应商id //需要确认
        }

        if (2 != supplierAdrInfoDto.getStatus()) {//供应商地点暂不可用
            log.error(":{}供应商地点暂不可用:{}",supplierAdrInfoDto.getId(),supplierAdrInfoDto.getStatus());
            errBuilder.append("供应商地点暂不可用\br");
        }else {

            pmPurchaseOrder.setSpAdrName(supplierAdrInfoDto.getSpAdrBasic().getProviderName());//设置供应商地点名称
            pmPurchaseOrder.setSpAdrNo(supplierAdrInfoDto.getSpAdrBasic().getProviderNo());//设置供应商地点编号

            pmPurchaseOrder.setSettlementPeriod(supplierAdrInfoDto.getSpAdrBasic().getSettlementPeriod());//获取账期
        }

    /*    if (!pmPurchaseOrder.getBranchCompanyId().equals(supplierAdrInfoDto.getSpAdrBasic().getOrgId())) {//获取分公司id)
            log.error("选择的供应商地点跟当前子公司暂无签约关系");
            errBuilder.append("选择的供应商地点跟当前子公司暂无签约关系\br");
        }*/
        //供应商地点信息设定
        //新增加字段（lvheping）
        if (pmPurchaseOrder.getSaleOrderId()!=null){//说明为供应商直送订单
            Response<SpAdrBasicDto> spAdrBasicDtoResponse = supplierQueryDubboService.queryByAdrInfoId(Integer.valueOf(spAdrId));
            SpAdrBasicDto spAdrBasicDto = spAdrBasicDtoResponse.getResultObject();
            Integer goodsArrivalCycle1 = spAdrBasicDto.getGoodsArrivalCycle();
            Date currdate = new Date();
            Calendar ca = Calendar.getInstance();
            ca.setTime(currdate);
            ca.add(Calendar.DATE, goodsArrivalCycle1);//创建日期加上供应商提前期
            currdate = ca.getTime();//加好送货日期后的日期
            pmPurchaseOrder.setEstimatedDeliveryDate(currdate);//预计送货日期
            pmPurchaseOrder.setPayType(spAdrBasicDto.getPayType());//设置支付方式
            pmPurchaseOrder.setPayCondition(spAdrBasicDto.getPayCondition());//设置支付条件
        }
        //查询仓库和门店信息
        Integer adrType = pmPurchaseOrder.getAdrType();//获取 地点类型:0:仓库;1:门店

        String adrTypeCode = pmPurchaseOrder.getAdrTypeCode();//地点编码
        //获取地点详细地址
        if (0 == adrType) {//0:仓库;
            //查寻仓库接口
            Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(adrTypeCode);
            if (!CommonsEnum.RESPONSE_200.getCode().equals(logicWarehouseDtoResponse.getCode())) {// 响应出错
                return BeanConvertUtils.convert(logicWarehouseDtoResponse, Response.class);
            }

            if (null == logicWarehouseDtoResponse.getResultObject()) {
                log.error("仓库信息暂不可用");
                errBuilder.append("仓库信息暂不可用\br");

            }
            LogicWarehouseDto warehouseDto = logicWarehouseDtoResponse.getResultObject();

            log.info("仓库信息>>warehouseDto:{}", JSON.toJSONString(warehouseDto));
            pmPurchaseOrder.setAdrName(warehouseDto.getPhysicalWarehouseDto().getAddress());//详细地址
            pmPurchaseOrder.setAdrTypeName(warehouseDto.getWarehouseName());//地点名称
            pmPurchaseOrder.setPhone(warehouseDto.getPhysicalWarehouseDto().getContactMode());//获取联系方式

            //判定当前的供应商的所属公司和仓库的公司id

            String wareHouseBranchCompanyId = warehouseDto.getBranchCompanyId();//获取分公司id

            String spAdrBranchCompanyId = supplierAdrInfoDto.getSpAdrBasic().getOrgId();
            if (StringUtils.isNotBlank(wareHouseBranchCompanyId) && StringUtils.isNotBlank(spAdrBranchCompanyId)) {
                if (wareHouseBranchCompanyId.equals(spAdrBranchCompanyId)) {//地采模式
                    pmPurchaseOrder.setPurchasingMode(PurchaseModeType.LOCAL_PUARCHASE.getCode());
                }//统采模式
                else {
                    pmPurchaseOrder.setPurchasingMode(PurchaseModeType.UNIFY_PUARHCASE.getCode());
                }
            } else {
                if (StringUtils.isBlank(wareHouseBranchCompanyId)) {
                    log.error("仓库的分公司编号为空");
                    errBuilder.append("仓库的分公司编号为空\br");
                }

                if (StringUtils.isBlank(spAdrBranchCompanyId)) {
                    log.error("供应商地点的分公司编号为空");
                    errBuilder.append("供应商地点的分公司编号为空\br");
                }
            }
        } else {// 1:门店
            //查寻门店接口
            Response<StoreDto> storeDtoResponse = organizationService.queryStoreById(adrTypeCode);
            if (!CommonsEnum.RESPONSE_200.getCode().equals(storeDtoResponse.getCode())) {// 响应出错
                return BeanConvertUtils.convert(storeDtoResponse, Response.class);
            }
            if (null == storeDtoResponse.getResultObject()) {
                log.error("门店信息暂不可用");
                errBuilder.append("门店信息暂不可用\br");

            } else {
                log.info("门店的基本信息>>StoreDto:{}", JSON.toJSONString(storeDtoResponse.getResultObject()));
                pmPurchaseOrder.setAdrName(storeDtoResponse.getResultObject().getAddress());//详细地点
                pmPurchaseOrder.setAdrTypeName(storeDtoResponse.getResultObject().getName());
                pmPurchaseOrder.setPhone(storeDtoResponse.getResultObject().getMobilePhone());//获取联系方式
            }

        }
        String s = errBuilder.toString();
        if (StringUtils.isBlank(s)) {
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        } else {
            response.setCode(CommonsEnum.RESPONSE_402.getCode());
            response.setErrorMessage(s);
            response.setSuccess(false);
        }
        return response;
    }
}
