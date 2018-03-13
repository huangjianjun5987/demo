package com.yatang.sc.facade.service;

import com.yatang.sc.facade.domain.ServiceCommitmentsPo;

import java.util.List;

/**
 * @描述:服务承诺service接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/8 9:35
 * @版本: v1.0
 */
public interface ServiceCommitmentsService {
    /**
     * 获取所有有效服务承诺：后期可能会设定对应的商品和供应商
     * @author yanghsuang
     * @return
     */
    List<ServiceCommitmentsPo> queryAllServiceCommitmentsList();
}
