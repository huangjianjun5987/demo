package com.yatang.sc.facade.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ProductDetailDto;
import com.yatang.sc.facade.dto.ProductListForAppDto;
import com.yatang.sc.facade.dto.ProductListForAppQueryParamDto;

/**
 * @描述:提交变价单
 * @类名:ProdPurchaseInfoImportFlowService
 * @作者: kangdong
 * @创建时间: 2017/12/12 09:29
 * @版本: v1.0
 */
public interface ProdPurchaseInfoImportFlowService {

    /**
     * 更新采购变价单
     * @author kangdong
     * @return
     */
    Response<Long> updateProdPurchase(Long id, String userId);

    /**
     * 更新售价变价单
     * @author kangdong
     * @return
     */
    Response<Long> updateProdSell(Long id, String userId);
}
