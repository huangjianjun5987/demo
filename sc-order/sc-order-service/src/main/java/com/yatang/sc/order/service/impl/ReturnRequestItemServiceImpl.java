package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ReturnRequestItemDao;
import com.yatang.sc.order.domain.returned.AppQueryReturnRequestItemPo;
import com.yatang.sc.order.domain.returned.ReturnRequestItemPo;
import com.yatang.sc.order.service.ReturnRequestItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @描述: 退换货商品项service接口定义
 * @类名: ReturnRequestItemServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/10/17 11:21
 * @版本: v1.0
 */
@Service
@Transactional
public class ReturnRequestItemServiceImpl implements ReturnRequestItemService{

    @Autowired
    private ReturnRequestItemDao returnRequestItemDao;

    @Override
    public List<ReturnRequestItemPo> queryReturnRequestItem(String returnId) {
        List<ReturnRequestItemPo> returnRequestList = returnRequestItemDao.queryReturnRequestItem(returnId);
        return returnRequestList;
    }

    @Override
    public List<AppQueryReturnRequestItemPo> queryReturnRequestItemByReturnId(String returnId) {
        List<AppQueryReturnRequestItemPo> returnRequestList = returnRequestItemDao.queryReturnRequestItemByReturnId(returnId);
        return returnRequestList;
    }

    @Override
    public ReturnRequestItemPo queryById(Long id) {
        return returnRequestItemDao.selectByPrimaryKey(id);
    }
}
