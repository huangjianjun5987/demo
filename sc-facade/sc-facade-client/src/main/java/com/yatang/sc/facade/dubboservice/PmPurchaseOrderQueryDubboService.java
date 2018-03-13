package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.pm.*;

import java.util.List;

/**
 * @描述: 采购单的查询dubbo接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:18
 * @版本: v1.0
 */
public interface PmPurchaseOrderQueryDubboService {

    /**
     * 根据查询条件查询 采购单iem信息
     *
     * @param queryParamDto 查询条件（productId,spId,spAdrId）
     * @return
     */
    Response<PmPurchaseOrderItemDto> getNewPmPurchaseOrderItem(PmPurchaseOrderItemQueryParamDto queryParamDto);




    /**1
     * 根据条件查询采购单打印管理列表详细信息
     * @param pmPurchaseQueryParamDto
     * @return
     */
    Response<PageResult<PmPurchaseOrderInfoDto>> queryPurchaseOrderListInfo(PmPurchaseQueryParamDto pmPurchaseQueryParamDto);

    /**
     * 根据条件查询采购单管理列表
     * @param pmPurchaseQueryParamDto
     * @return
     */
    Response<PageResult<PmPurchaseOrderDto>> queryPurchaseOrderList(PmPurchaseQueryParamDto pmPurchaseQueryParamDto);

    /**
     * 根据采购单id查询采购单详情（失败原因）
     * @param id
     * @return
     */
    Response<PmPurchaseOrderInfoDto> getPurchaseOrderInfoById(Long id);

    /**
     * 根据采购单号查询采购订单详情
     * @param purchaseOrderNo
     * @return
     */
    Response<PmPurchaseOrderInfoDto> getPurchaseOrderInfoByOrderNo(String purchaseOrderNo);



    /**
     * 根据采购单号和品牌名称查询品牌值清单
     * @param pmPurchaseOrderItemQueryParamDto
     * @author yinyuxin
     * @return
     */
    Response<PageResult<PmPurchaseOrderItemDto>> selectBrandsByOrderNo(PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto);

    /**
     * 根据采购单号和商品编号或者条码或者名称查询商品清单
     * @author yinyuxin
     * @param pmPurchaseOrderItemQueryParamDto
     * @return
     */
    Response<PageResult<PmPurchaseOrderItemDto>> selectProductByOrderNo(PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto);

    /**
     * 根据采购单号、商品code、品牌id查询采购商品清单
     * @author yinyuxin
     * @param pmPurchaseOrderItemQueryParamDto
     * @return
     */
    Response<List<PmPurchaseRefundItemDto>> addRefundProducts(PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto);


    /**
     * 根据条件查询供应商采购单列表
     * @author kangdong
     * @param pmSupplierPurchaseOrderQueryParamDto
     * @return
     */
    Response<PageResult<PmSupplierPurchaseOrderItemDto>> supplierPmPurchaseOrderList(PmSupplierPurchaseOrderQueryParamDto pmSupplierPurchaseOrderQueryParamDto);


    /**
     * 根据ID查询供应商采购单详情
     * @author kangdong
     * @param supplierPurchaseOrderId
     * @return
     */
    Response<PmSupplierPurchaseOrderDetailDto> supplierPmPurchaseOrderDetail(Long supplierPurchaseOrderId);
}
