package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.InvoiceDao;
import com.yatang.sc.order.domain.Invoice;
import com.yatang.sc.order.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiangyonghong on 2017/7/8.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDao dao;


    @Override
    public Invoice getInvoiceForId(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int deleteInvoiceById(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Invoice invoice) {
        dao.insert(invoice);
    }
}
