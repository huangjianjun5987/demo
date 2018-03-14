package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.Invoice;

public interface InvoiceDao {
    int deleteByPrimaryKey(Long invoiceId);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    Invoice selectByPrimaryKey(Long invoiceId);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);
}