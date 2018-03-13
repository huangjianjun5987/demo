package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.QueryStorePermissionDto;
import com.yatang.sc.facade.dto.StorePermissionDto;

/**
 * @描述:是否可进入门店
 * @类名:StorePermissionDubboService
 * @作者: baiyun
 * @创建时间: 2017/8/9 16:05
 * @版本: v1.0
 */
public interface StorePermissionDubboService {
    /**
     * 根据storeId查询白名单信息
     * @param storeId
     * @return
     */
    Response<StorePermissionDto> queryStorePermissionByStoreId(String storeId);

    /**
     * 查询所有白名单信息并且分页
     */
    Response<PageResult<StorePermissionDto>> queryStorePermissionPage(QueryStorePermissionDto queryStorePermissionDto);

    /**
     * 新增白名单
     */
    Response<Boolean> insertStorePermission(StorePermissionDto storePermissionDto);

    /**
     * 修改白名单值回显
     */
    Response<StorePermissionDto> updateStorePermission(Integer id);

    /**
     * 修改白名单
     * @param storePermissionDto
     * @return
     */
    Response<Boolean> updateStorePermission(StorePermissionDto storePermissionDto);

    /**
     * 删除白名单
     * @param id
     * @return
     */
    Response<Boolean> deleteStorePermission(Integer id);
}
