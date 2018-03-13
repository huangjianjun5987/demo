package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.ProPurchaseBatchChangeStatusDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseModifyParamDto;

/**
 * @描述: 商品采购的writeDubbo服务接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/15 14:08
 * @版本: v1.0
 */
public interface ProdPurchaseWriteDubboService {
    /**
     * 新增商品采购关系
     *
     * @param prodPurchaseInfoDto
     * @return
     * @author yangshuang
     */
    Response<Void> addProdPurchase(ProdPurchaseInfoDto prodPurchaseInfoDto);

    /**
     * 更新商品采购关系
     *
     * @param prodPurchaseInfoDto
     * @return
     */
    Response<Void> updateProdPurchase(ProdPurchaseInfoDto prodPurchaseInfoDto);

    /**
     * 根据 id删除商品采购关系(伪删除)
     *
     * @param
     * @param prodPurchaseModifyParamDto
     * @return
     */
    Response<Void> deleteProdPurchaseById(ProdPurchaseModifyParamDto prodPurchaseModifyParamDto);

    /**
     * 根据 id改变当前的供应商类型
     *
     * @param ProdPurchaseModifyParamDto
     * @return
     */
    Response<Void> changeSupplierType(ProdPurchaseModifyParamDto ProdPurchaseModifyParamDto);

    /**
     * 根据 id改变当前的商品采购关系的启禁用状态
     *
     * @param ProdPurchaseModifyParamDto 包含(id和采购关系的状态:0,,失效,1启用)
     * @return
     */
    Response<Void> changeProPurchaseStatus(ProdPurchaseModifyParamDto ProdPurchaseModifyParamDto);

    /**
     * 批量更新商品的采购关系的启停用(采购关系的状态:0,,失效,1启用)
     *
     * @param proPurchaseBatchChangeStatusDto
     * @return
     */
    Response<Void> batchChangeProPurchaseStatus(ProPurchaseBatchChangeStatusDto proPurchaseBatchChangeStatusDto);




    /**
     * 更新商品采购关系的最终审批状态
     *
     * @param id          采购关系主键
     * @param auditStatus 审核状态   审核状态:true:已审核;false;已拒绝
     * @param auditUserId 审核人id
     * @return
     */
    Response<Void> updateProdPurchaseAuditStatus(Long id, Boolean auditStatus, String auditUserId);






}
