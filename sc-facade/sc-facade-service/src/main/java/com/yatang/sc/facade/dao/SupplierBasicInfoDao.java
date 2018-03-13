package com.yatang.sc.facade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yatang.sc.facade.domain.SpSearchBoxPo;
import com.yatang.sc.facade.domain.SupplierBasicInfoPo;

public interface SupplierBasicInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SupplierBasicInfoPo record);

    SupplierBasicInfoPo queryById(Integer id);

    int updateByPrimaryKeySelective(SupplierBasicInfoPo record);


    /**
     * 匹配查询是否已经存在的供应商
     * @param basicPo
     * @author yangshuang
     * @return
     */
    List<SupplierBasicInfoPo> checkSupplierBasicInfo(SupplierBasicInfoPo basicPo);
    
    /**
     * @Description: 查询最大编码
     * @author huangjianjun
     * @date 2017年7月19日下午3:59:27
     */
    String queryMaxNo();
    
    /**
     * @Description: 根据供应商编码或名称查询供应商列表
     * @author huangjianjun
     * @date 2017年7月19日下午3:59:27
     */
    List<SpSearchBoxPo> supplierSearchBox(@Param("condition")String condition);
}