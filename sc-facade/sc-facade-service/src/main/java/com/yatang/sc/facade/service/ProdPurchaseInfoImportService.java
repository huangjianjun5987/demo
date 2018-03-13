package com.yatang.sc.facade.service;


import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoImportPo;
import com.yatang.sc.facade.domain.ProdPurchasePriceUpdateParamPo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoImportsPo;

import java.util.List;

/**
 * @描述: 商品采购价导入记录Service接口定义
 * @类名: ProdPurchaseInfoImportService
 * @作者: kangdong
 * @创建时间: 2017/12/6 09:30
 * @版本: v1.0
 */
public interface ProdPurchaseInfoImportService {

    /**
     * 根据id查询商品采购价导入记录详情
     * @param id 采购关系主键
     * @return
     */
    ProdPurchaseInfoImportPo selectByPrimaryKey(Long id);


    /**
     * 根据条件查询商品售价导入记录列表
     * @param paramPo
     * @return
     */
    PageInfo<ProdPurchaseInfoImportPo> getProdPurchaseInfoImportByParam(ProdPurchasePriceUpdateParamPo paramPo);

    /**
     * 导入商品采购价导入记录文档
     * @param prodPurchaseInfoImportsPo
     * @return
     */
    Long insertImportList(ProdPurchaseInfoImportsPo prodPurchaseInfoImportsPo);


    /**
     * 根据id更新处理结果:0:错误;1:已验证;2:已提交
     * @param prodPurchaseInfoImportPo
     * @return
     */
    Boolean updateHandleResult(ProdPurchaseInfoImportPo prodPurchaseInfoImportPo);

    /**
     * 查询最新的一条商品采购价导入表(调价单)
     * @return
     */
    ProdPurchaseInfoImportsPo selectById(Long id);

}
