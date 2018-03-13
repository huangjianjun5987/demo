package com.yatang.sc.facade.dubboservice;

import java.util.List;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dto.WarehouseInfoDto;
import com.yatang.sc.facade.dto.WarehouseLogicDto;

/**
 * 
 * <class description> 逻辑仓库信息查询服务接口
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月17日
 */
public interface WarehouseLogicQueryDubboService {
	/**
	 * 
	 * <method description>根据or条件分页查询逻辑仓库
	 *
	 * @param warehouseLogicDto
	 * @param pageNum
	 * @param pageSize
     * @return
	 */
	Response<PageResult<WarehouseLogicDto>> getWarehouseByOrCondition(WarehouseLogicDto warehouseLogicDto,
                                                                      Integer supplierAddressId, Integer pageNum, Integer pageSize);



	/**
	 * @Description: 查询所有逻辑仓
	 * @author: kangdong
	 * @date 2017/10/11- 14:13
	 * @return
	 */
	Response<List<WarehouseLogicDto>> queryWarehouseLogicList();



	Response<WarehouseInfoDto> getWarehouseInfoByWarehouseLogicId(Integer warehouseLogicId);



	/**
	 * 
	 * <method description>根据逻辑仓code查询(不是根据id查询，因为都在用了，所以就不改方法名了)
	 *
	 * @param warehouseLogicCode
	 * @return
	 */
	Response<LogicWarehouseDto> selectLogicWarehouseByPrimaryKey(String warehouseLogicCode);



	/**
	 * 
	 * <method description>根据省份名称精确查询逻辑仓
	 *
	 * @param proviceName
	 * @return
	 */
	Response<List<LogicWarehouseDto>> getWarehouseByProviceName(String proviceName);

	/**
	 * 根据分公司查询逻辑仓
	 * @param companyId
	 * @return
	 */
	Response<List<LogicWarehouseDto>> getWarehouseByBranchCompanyId(String companyId);
}
