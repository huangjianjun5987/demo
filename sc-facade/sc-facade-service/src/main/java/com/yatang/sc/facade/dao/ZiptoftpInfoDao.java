package com.yatang.sc.facade.dao;

import java.util.List;

import com.yatang.sc.facade.domain.ZiptoftpInfoPo;

/**
 * 
 * <class description>zip活动页上传
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月8日
 */
public interface ZiptoftpInfoDao {
	int deleteByPrimaryKey(Integer id);



	int insert(ZiptoftpInfoPo ziptoftpInfoPo);



	int insertSelective(ZiptoftpInfoPo ziptoftpInfoPo);



	ZiptoftpInfoPo selectByPrimaryKey(Integer id);



	int updateByPrimaryKeySelective(ZiptoftpInfoPo ziptoftpInfoPo);



	int updateByPrimaryKey(ZiptoftpInfoPo ziptoftpInfoPo);



	List<ZiptoftpInfoPo> selectZiptoftpInfoList(ZiptoftpInfoPo ziptoftpInfoPo);
}