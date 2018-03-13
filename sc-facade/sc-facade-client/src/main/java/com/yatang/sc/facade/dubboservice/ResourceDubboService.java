package com.yatang.sc.facade.dubboservice;

import java.util.List;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.system.ResourceDto;

/**
 * @描述: 资源dubbo服务接口.
 * @作者: liuxiaokun
 * @创建时间: 2018年1月16日
 */
public interface ResourceDubboService {

    /**
     * 查询单个权限详细信息
     * @param resourceId
     * @return
     */
	Response<ResourceDto> findOne(Long resourceId);


    /**
     * 获取所有资源
     * @param
     * @return
     */
	Response<List<ResourceDto>> queryAllRes();


	/**
	 * 根据资源权限id和登录名获取左菜单或者按钮
	 * @param resParentId
	 * @param loginName
	 * @return
	 */
	Response<List<ResourceDto>> readLeftMenuOrButtonByIdAndLoginName(Long resParentId,String loginName);


	Response<Boolean> isRelatedWithRole(Long id);

	Response<Boolean> deleteRes(List<Long> ids);

	Response<Boolean> updateRes(ResourceDto resourceDto);

	Response<Boolean> createRes(ResourceDto resourceDto);
}
