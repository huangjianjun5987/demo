package test;

import test.testPo;

public interface testPoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(testPo record);

    int insertSelective(testPo record);

    testPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(testPo record);

    int updateByPrimaryKey(testPo record);
}