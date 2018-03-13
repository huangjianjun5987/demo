package com.yatang.sc.facade.dubboservice;

import java.math.BigDecimal;
import java.util.List;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.ProdBatchParameterDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;

/**
 * @描述:商品价格管理服务接口
 * @类名:GoodsPriceWriteDubboService
 * @作者: lvheping
 * @创建时间: 2017/5/22 16:29
 * @版本: v1.0
 */

public interface GoodsPriceWriteDubboService {


    /**
     * 添加销售价格
     * @param prodSellPriceInfoDto
     * @return
     */
    Response<Boolean> insertSellPrice(ProdSellPriceInfoDto prodSellPriceInfoDto);


    /**
     * 修改销售价格
     * @param prodSellPriceInfoDto
     * @return
     */
    Response<Boolean> updateSellPrice(ProdSellPriceInfoDto prodSellPriceInfoDto);

    /**
     * 审核完成修改状态为已完成
     * @param id
     * @param status
     * @return
     */
    Response<Boolean>updateAuditSellPrice(Long id,String userId,Boolean status);

    /**
     * 根据id删除销售区域价格
     * @param id 区域价格管理主表的id
     * @return
     */
    Response<Boolean> deleteSellPriceById(Long id,String productId,String userId);

    /**
     * 根据传入信息修改销售区间价状态
     * @param convert
     * @return
     */
    Response<Boolean> updateSellPriceStatus(ProdSellPriceInfoDto convert);

    /**
     * 根据传入的信息批量修改商品在该区间的状态为不启用
     * @param convert
     * @return
     */
    Response<Boolean> prodBatchUpdate(ProdBatchParameterDto convert);

    /**
     * 根据传入的信息批量的添加商品与子公司的销售关系
     * @param addList
     * @return
     */
    Response<Boolean> prodBatchUpdateInfo(ProdBatchParameterDto prodBatchParameterDto, List<ProdSellPriceInfoDto> addList);

    /**
     * 根据传入参数修改子表信息
     * @param prodSellSectionPriceDto
     * @return
     */
    Response<Boolean> prodSellSectionUpadte(ProdSellSectionPriceDto prodSellSectionPriceDto);

}
