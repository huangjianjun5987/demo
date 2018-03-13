package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.SupplierAdrInfoPo;

import java.util.List;

public interface SupplierAdrInfoDao {
	int deleteByPrimaryKey(String id);



	int insertSelective(SupplierAdrInfoPo record);



	SupplierAdrInfoPo selectByPrimaryKey(Integer id);



	int updateByPrimaryKeySelective(SupplierAdrInfoPo record);



	/**
	 * @Description: 根据供应商信息id修改供应商地点状态
	 * @author tankejia
	 * @date 2017/7/17- 9:00
	 */
	int updateByParentId(SupplierAdrInfoPo record);



	/**
	 * 
	 * <method description>根据供应商ID全表更新
	 *
	 * @param record
	 * @return
	 */
	int updateSupplierAdrByParentId(SupplierAdrInfoPo record);



	/**
	 * @Description: 查询供应商地点主表信息
	 * @author tankejia
	 * @date 2017/7/28- 18:02
	 * @param id
	 */
	SupplierAdrInfoPo queryMainAddById(Integer id);
    
    /**
     * @Description: 根据供应商信息id查询供应商地点列表
     * @author tankejia
     * @date 2017/8/4- 15:33
     * @param parentId
     */
    List<SupplierAdrInfoPo> querySupplierAddInfoListByParentId(String parentId);

	/**
	 * @Description: 根据供应商地点编码查询
	 * @author kangdong
	 * @date 2017/12/7- 15:33
	 * @param providerNo
	 */
	SupplierAdrInfoPo queryByProviderNo(String providerNo);
}