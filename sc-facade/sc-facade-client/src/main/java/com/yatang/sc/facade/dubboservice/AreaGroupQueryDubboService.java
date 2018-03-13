package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.prod.place.AreaGroupDto;
import com.yatang.sc.facade.dto.prod.place.ParamAreaGroupDto;

/**
 * @描述: 区域组的QuerydubboService
 * @类名:
 * @作者: zhudanning
 * @创建时间: 2018/1/11 15:10
 * @版本: v1.0
 */
public interface AreaGroupQueryDubboService {

	/**
	 * 查询区域组列表
	 * @param paramAreaGroupDto
	 * @return
	 */
	Response<PageResult<AreaGroupDto>> queryAreaGroupList(ParamAreaGroupDto paramAreaGroupDto);
}
