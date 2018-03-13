package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.SupplierOperTaxInfoPo;

public interface SupplierOperTaxInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SupplierOperTaxInfoPo record);

    int insertSelective(SupplierOperTaxInfoPo record);

    SupplierOperTaxInfoPo queryById(Integer id);

    int updateByPrimaryKeySelective(SupplierOperTaxInfoPo record);

    int updateByPrimaryKey(SupplierOperTaxInfoPo record);

}