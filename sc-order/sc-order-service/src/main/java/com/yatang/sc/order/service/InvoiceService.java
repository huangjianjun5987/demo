package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.Invoice;

/**
 * Created by xiangyonghong on 2017/7/8.
 */
public interface InvoiceService {

    Invoice getInvoiceForId(Long id);

    int deleteInvoiceById(Long id);

    void save(Invoice invoice);
}
