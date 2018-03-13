package com.yatang.sc.facade.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;

/**
 * @描述:  商品采购关系flowService
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/26 15:07
 * @版本: v1.0
 */
public interface ProdPurchaseWriteFlowService {


    Response<Void> addProdPurchase(ProdPurchaseInfoDto prodPurchaseInfoDto);

    Response<Void> updateProdPurchase(ProdPurchaseInfoDto prodPurchaseInfoDto);
}
