package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.SupplierBankInfoPo;

import java.util.List;

public interface SupplierBankInfoDao {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(SupplierBankInfoPo record);

    SupplierBankInfoPo queryById(Integer id);

    int updateByPrimaryKeySelective(SupplierBankInfoPo record);

    /**
     * 通过银行账户判定是否重复
     * @param record
     * @author yangshuang
     */
    List<SupplierBankInfoPo> checkSupplierBankInfoByBankAccount(SupplierBankInfoPo record);

}