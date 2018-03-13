package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.ProdSellInfoImportDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceUpdateDto;

import java.util.List;

/**
 * @描述: 商品售价导入记录的writeDubbo服务接口定义
 * @类名: ProdSellInfoImportWriteDubboService
 * @作者: kangdong
 * @创建时间: 2017/12/6 14:27
 * @版本: v1.0
 */
public interface ProdSellInfoImportWriteDubboService {

    /**
     * @Description: 导入商品售价导入记录列表
     * @param prodSellPriceUpdateDto
     * @param userId
     * @author kangdong
     * @date 2017/12/6 10:04
     * @return
     */
    Response<Long> prodSellListImport(List<ProdSellPriceUpdateDto> prodSellPriceUpdateDto, String userId, List<String> branchCompanyIds);

    /**
     * @Description: 更新商品售价导入记录
     * @author kangdong
     * @date 2017/12/6 10:04
     * @return
     */
    Response<Long> updateProdSellInfoImport(Long id, String userId);
}
