package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.pm.ProcessDefinitionPo;

import java.util.List;

public interface ProcessDefinitionDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProcessDefinitionPo record);

    int insertSelective(ProcessDefinitionPo record);

    ProcessDefinitionPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProcessDefinitionPo record);

    int updateByPrimaryKey(ProcessDefinitionPo record);

    /**
     * 全条件查询审批流程记录
     * @param processDefinitionPo
     * @return
     */
    List<ProcessDefinitionPo> queryBySelective(ProcessDefinitionPo processDefinitionPo);
}