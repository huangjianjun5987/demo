package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoImportDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoImportsDto;
import com.yatang.sc.facade.dto.prod.ProdPurchasePriceUpdateParamDto;

import java.util.List;

/**
 * @描述: 商品采购价导入记录的queryDubbo服务接口定义
 * @类名: ProdPurchaseInfoImportQueryDubboService
 * @作者: kangdong
 * @创建时间: 2017/12/6 09:43
 * @版本: v1.0
 */
public interface ProdPurchaseInfoImportQueryDubboService {

    /**
     * @Description: 根据id查询商品采购价导入记录详情
     * @param id 记录id
     * @author kangdong
     * @date 2017/12/6 09:50
     * @return
     */
    Response<ProdPurchaseInfoImportDto> getProdPurchaseInfoImportById(Long id);

    /**
     * 根据条件查询商品采购价导入记录列表
     * @param paramDto
     * @return
     */
    Response<PageResult<ProdPurchaseInfoImportDto>> getProdPurchaseInfoImportByParam(ProdPurchasePriceUpdateParamDto paramDto);
}
