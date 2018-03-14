package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.UserInvoiceInfo;

/**
 * Created by liusongjie on 2017/7/11.
 */
public interface InvoiceInfoService {

    void insertSelective(UserInvoiceInfo invoiceInfo);

    void updateByPrimaryKeySelective(UserInvoiceInfo invoiceInfo);

    UserInvoiceInfo getInvoiceByProfileId(String profileId);
}
