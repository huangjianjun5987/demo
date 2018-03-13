package com.yatang.sc.facade.dubboservice.prodplace;

import com.busi.common.datatable.PageResult;
import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceAddDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceUpdateDto;

import java.util.List;

/*
*@Author tangqi
*@Date 2018/1/11 15:07
*@Desc 商品地点关系管理
*/
public interface ProdPlaceWriteDubboService {

    /*
    *@Author tangqi
    *@Date 2018/1/11 16:01
    *@Desc 去重添加商品地点关系
    */
    Response addProdPlace(ProdPlaceAddDto prodPlaceAddDto);

    /*
    *@Author tangqi
    *@Date 2018/1/16 10:26
    *@Desc 添加前去重
    */
    Response<PageResult<List<ProdPlaceDto>>> distinct(ProdPlaceAddDto dto);

    /*
    *@Author tangqi
    *@Date 2018/1/11 16:34
    *@Desc 修改单个商品地点关系
    */
    Response update(ProdPlaceUpdateDto dto);

    /*
    *@Author tangqi
    *@Date 2018/1/11 17:23
    *@Desc 删除文档
    */
    Response bulkDelete(List<String> ids);
}
