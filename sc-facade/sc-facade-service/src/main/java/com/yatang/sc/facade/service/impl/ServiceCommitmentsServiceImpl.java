package com.yatang.sc.facade.service.impl;

import com.yatang.sc.facade.dao.ServiceCommitmentsDao;
import com.yatang.sc.facade.domain.ServiceCommitmentsPo;
import com.yatang.sc.facade.service.ServiceCommitmentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @描述: 服务承诺service实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/8 9:35
 * @版本: v1.0
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceCommitmentsServiceImpl implements ServiceCommitmentsService {

    private final ServiceCommitmentsDao serviceCommitmentsDao;//服务承诺dao
    @Override
    public List<ServiceCommitmentsPo> queryAllServiceCommitmentsList() {
        return serviceCommitmentsDao.queryAllServiceCommitmentsList();
    }
}
