package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.OrderRefundDao;
import com.yatang.sc.order.domain.OrderRefundPo;
import com.yatang.sc.order.service.OrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @描述:操作退款单退货单关系service实现
 * @类名:OrderRefundServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/11/14 14:45
 * @版本: v1.0
 */
@Service
@Transactional
public class OrderRefundServiceImpl implements OrderRefundService {
    @Autowired
    private OrderRefundDao orderRefundDao;
    /**
     * 根据退货单号查询退款记录条数
     * @param returnOrderId
     * @return
     */
    @Override
    public int selectByReturnOrderId(String returnOrderId) {
        int i = orderRefundDao.selectByReturnOrderId(returnOrderId);
        return i;
    }

    /**
     * 插入退款退货单关系记录
     *
     * @param record
     * @return
     */
    @Override
    public void insert(OrderRefundPo record) {
        int insert = orderRefundDao.insert(record);
    }

    /**
     * 通过退换货ID查询退款记录ID
     * @param id
     * @return
     */
    @Override
    public OrderRefundPo queryByReturnId(String id){
        return orderRefundDao.queryByReturnId(id);
    }
}
