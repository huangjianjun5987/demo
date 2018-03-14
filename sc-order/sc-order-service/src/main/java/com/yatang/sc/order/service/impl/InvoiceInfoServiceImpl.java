package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.InvoiceInfoDao;
import com.yatang.sc.order.domain.UserInvoiceInfo;
import com.yatang.sc.order.service.InvoiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liusongjie on 2017/7/11.
 */
@Service
public class InvoiceInfoServiceImpl implements InvoiceInfoService {

    @Autowired
    InvoiceInfoDao invoiceInfoDao;

    @Override
    public void insertSelective(UserInvoiceInfo invoiceInfo) {
        invoiceInfoDao.insertSelective(invoiceInfo);
    }

    @Override
    public void updateByPrimaryKeySelective(UserInvoiceInfo invoiceInfo) {
        invoiceInfoDao.updateByPrimaryKeySelective(invoiceInfo);
    }

    @Override
    public UserInvoiceInfo getInvoiceByProfileId(String profileId) {
        return invoiceInfoDao.selectByProfileId(profileId);
    }
}
