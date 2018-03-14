package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.UserInvoiceInfo;

public interface InvoiceInfoDao {
    int deleteByPrimaryKey(Long invoiceId);

    int insert(UserInvoiceInfo record);

    int insertSelective(UserInvoiceInfo record);

    UserInvoiceInfo selectByPrimaryKey(Long invoiceId);

    UserInvoiceInfo selectByProfileId(String profileId);

    int updateByPrimaryKeySelective(UserInvoiceInfo record);

    int updateByPrimaryKey(UserInvoiceInfo record);
}