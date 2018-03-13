package com.yatang.sc.facade.dubboservice;

import java.util.List;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.WarehousePhysicalInfoDto;

/**
 * 
 * <class description> 物理仓库信息查询服务接口
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月17日
 */
public interface WarehousePhysicalQueryDubboService {
	/**
	 * 
	 * <method description>根据省份名称精确查询物理仓
	 *
	 * @param proviceName
	 * @return
	 */
	Response<List<WarehousePhysicalInfoDto>> getWarehouseByProviceName(String proviceName);
}
