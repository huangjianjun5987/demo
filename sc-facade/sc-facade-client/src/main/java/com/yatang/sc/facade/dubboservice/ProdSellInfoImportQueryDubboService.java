package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.prod.ProdSellInfoImportDto;
import com.yatang.sc.facade.dto.prod.ProdSellInfoImportsDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceUpdateParamDto;

import java.util.List;

/**
 * @描述: 商品售价导入记录的queryDubbo服务接口定义
 * @类名: ProdSellInfoImportQueryDubboService
 * @作者: kangdong
 * @创建时间: 2017/12/6 14:17
 * @版本: v1.0
 */
public interface ProdSellInfoImportQueryDubboService {

    /**
     * @Description: 根据id查询商品售价导入记录详情
     * @param id 记录id
     * @author kangdong
     * @date 2017/12/6 09:50
     * @return
     */
    Response<ProdSellInfoImportDto> getProdSellInfoImportById(Long id);

    /**
     * 根据条件查询商品售价导入记录列表
     * @param paramDto
     * @return
     */
    Response<PageResult<ProdSellInfoImportDto>> getProdSellInfoImportByParam(ProdSellPriceUpdateParamDto paramDto);
}
