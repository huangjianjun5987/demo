package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ServiceCommitmentsPo;

import java.util.List;

public interface ServiceCommitmentsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceCommitmentsPo record);

    int insertSelective(ServiceCommitmentsPo record);

    ServiceCommitmentsPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceCommitmentsPo record);

    int updateByPrimaryKey(ServiceCommitmentsPo record);
    List<ServiceCommitmentsPo> queryAllServiceCommitmentsList();
}