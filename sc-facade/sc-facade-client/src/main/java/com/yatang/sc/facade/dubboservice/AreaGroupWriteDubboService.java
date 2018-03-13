package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.place.AreaGroupDto;

/**
 * @描述: 区域组的WritedubboService
 * @类名:
 * @作者: zhudanning
 * @创建时间: 2018/1/11 15:10
 * @版本: v1.0
 */
public interface AreaGroupWriteDubboService {

	/**
	 * 新增区域组
	 * @param areaGroupDto
	 * @return
	 */
	Response<Boolean> insertAreaGroup(AreaGroupDto areaGroupDto);

	/**
	 * 新增区域组里面的门店
	 * @param areaGroupCode
	 * @param storeId
	 * @return
	 */
	Response<Boolean> insertStoreToGroup(String areaGroupCode,String storeId);


	/**
	 * @Description: 删除区域组
	 * @author zhudanning
	 * @date 2018/1/11- 14:11
	 * @param
	 */
	Response<Boolean> deleteAreaGroup(String areaGroupCode);

	/**
	 * @Description: 删除区域组中的门店
	 * @author zhudanning
	 * @date 2018/1/11- 14:11
	 * @param
	 */
	Response<Boolean> deleteStoreFromArea(String areaGroupCode,String storeId);
}
