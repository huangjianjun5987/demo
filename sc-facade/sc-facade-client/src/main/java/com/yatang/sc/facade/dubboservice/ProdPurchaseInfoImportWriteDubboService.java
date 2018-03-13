package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoImportDto;
import com.yatang.sc.facade.dto.prod.ProdPurchasePriceUpdateDto;

import java.util.List;

/**
 * @描述: 商品采购价导入记录的writeDubbo服务接口定义
 * @类名: ProdPurchaseInfoImportWriteDubboService
 * @作者: kangdong
 * @创建时间: 2017/12/6 09:43
 * @版本: v1.0
 */
public interface ProdPurchaseInfoImportWriteDubboService {

    /**
     * @Description: 导入商品采购价导入记录列表
     * @param prodPurchasePriceUpdateDtos
     * @author kangdong
     * @date 2017/12/6 10:04
     * @return
     */
    Response<Long> prodPurchaseListImport(List<ProdPurchasePriceUpdateDto> prodPurchasePriceUpdateDtos, String userId, List<String> branchCompanyIds);

    /**
     * @Description: 更新商品采购价导入记录列表
     * @author kangdong
     * @date 2017/12/6 10:04
     * @return
     */
    Response<Long> updateProdPurchaseInfoImport(Long id, String userId);
}
