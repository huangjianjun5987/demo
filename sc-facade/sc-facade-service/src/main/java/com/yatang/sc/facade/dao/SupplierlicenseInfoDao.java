package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.SupplierlicenseInfoPo;

import java.util.List;

public interface SupplierlicenseInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SupplierlicenseInfoPo record);

    SupplierlicenseInfoPo queryById(Integer id);

    int updateByPrimaryKeySelective(SupplierlicenseInfoPo record);

    int updateByPrimaryKey(SupplierlicenseInfoPo record);
    /**
     * 通过营业执照编号判定是否重复
     * @param record
     * @author yangshuang
     */
    List<SupplierlicenseInfoPo> checkSupplierLicenseInfoByRegistLicenceNo(SupplierlicenseInfoPo record);
}