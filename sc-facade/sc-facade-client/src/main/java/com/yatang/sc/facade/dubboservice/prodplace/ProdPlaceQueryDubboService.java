package com.yatang.sc.facade.dubboservice.prodplace;

import com.busi.common.datatable.PageResult;
import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dto.ProdSpAdrSearchBoxDto;
import com.yatang.sc.facade.dto.ProdSpAdrSearchParamDto;
import com.yatang.sc.facade.dto.QueryRequestDto;

import java.util.List;

/*
*@Author tangqi
*@Date 2018/1/11 15:07
*@Desc 商品地点关系管理
*/
public interface ProdPlaceQueryDubboService {

    /*
    *@Author tangqi
    *@Date 2018/1/11 16:32
    *@Desc 多条件查询
    */
    Response<PageResult<List<ProdPlaceDto>>> queryPage(QueryRequestDto dto);

    /*
    *@Author tangqi
    *@Date 2018/1/11 16:55
    *@Desc 查询商品地点关系详情
    */
    Response<ProdPlaceDto> queryDetail(String id);

    /**
     * @Description: 批量查询物流模式为直送的商品地点关系
     * @author tankejia
     * @date 2018/1/16- 15:40
     * @param storeId 门店id
     * @param productIds 商品id集合
     * @return 直送的商品地点关系集合
     */
    Response<List<ProdPlaceDto>> queryDirectDeliveryProduct(String storeId, List<String> productIds);

    /**
     * @Description: 根据地点类型查询其对应子公司下面的所有供应商地点值清单
     * @author tankejia
     * @date 2018/1/18- 15:01
     * @param paramDto
     */
    Response<com.yatang.sc.facade.common.PageResult<ProdSpAdrSearchBoxDto>> prodSpAdrSearchBox(ProdSpAdrSearchParamDto paramDto);
}
