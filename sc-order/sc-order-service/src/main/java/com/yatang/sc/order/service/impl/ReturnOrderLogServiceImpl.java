package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ReturnOrderLogDao;
import com.yatang.sc.order.domain.ReturnOrderLogPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatang.sc.order.service.ReturnOrderLogService;

/**
 * @描述:记录退货单操作日志实现类
 * @类名:ReturnOrderLogServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/11/27 16:29
 * @版本: v1.0
 */
@Service
public class ReturnOrderLogServiceImpl implements ReturnOrderLogService {
    @Autowired
    private ReturnOrderLogDao returnOrderLogDao;
    @Override
    public void save(ReturnOrderLogPo returnOrderLogPo) {
        returnOrderLogDao.insert(returnOrderLogPo);
    }
}
