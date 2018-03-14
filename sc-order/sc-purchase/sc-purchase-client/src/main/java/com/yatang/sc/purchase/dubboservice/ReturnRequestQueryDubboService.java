package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.returned.ReturnRequestDetailDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestListDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestQueryParamDto;

/**
 * @描述:退换货dubboQuery服务定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/17 11:38
 * @版本: v1.0
 */
public interface ReturnRequestQueryDubboService {

    /**
     * 移动端获取退换货商品列表
     * @param param
     * @return
     */
    Response<PageResult<ReturnRequestListDto>> queryReturnRequestList(ReturnRequestQueryParamDto param);

    /**
     * 移动端获取退换货商详情
     * @param id
     * @param userId
     * @return
     */
    Response<ReturnRequestDetailDto> queryReturnRequestDetailById(String id,String userId);
}
