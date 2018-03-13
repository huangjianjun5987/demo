package com.yatang.sc.facade.service.prodplace;

import com.busi.common.datatable.PageResult;
import com.yatang.sc.facade.domain.prodplace.ProdPlacePo;
import com.yatang.sc.facade.domain.prodplace.ProdPlaceUpdatePo;
import com.yatang.sc.facade.mongo.CommonQueryRequest;

import java.util.List;

/*
*@Author tangqi
*@Date 2018/1/11 14:08
*@Desc 商品地点关系
*/
public interface ProdPlaceService {

    /*
    *@Author tangqi
    *@Date 2018/1/11 16:01
    *@Desc 去重添加商品地点关系
    */
    void addProdPlace(List<ProdPlacePo> poList);

    /*
    *@Author tangqi
    *@Date 2018/1/16 10:28
    *@Desc 添加前去重
    */
    PageResult<List<ProdPlacePo>> distinct(List<ProdPlacePo> poList, int pageNum, int pageSize);

    /*
    *@Author tangqi
    *@Date 2018/1/11 16:32
    *@Desc 多条件查询
    */
    PageResult<List<ProdPlacePo>> queryPage(CommonQueryRequest commonQueryRequest);

    /*
    *@Author tangqi
    *@Date 2018/1/11 16:34
    *@Desc 修改单个商品地点关系
    */
    void update(ProdPlaceUpdatePo po);

    /*
    *@Author tangqi
    *@Date 2018/1/11 16:55
    *@Desc 查询商品地点关系详情
    */
    ProdPlacePo queryDetail(String id);

    /*
    *@Author tangqi
    *@Date 2018/1/11 17:23
    *@Desc 批量删除文档
    */
    void bulkDelete(List<String> ids);
}
