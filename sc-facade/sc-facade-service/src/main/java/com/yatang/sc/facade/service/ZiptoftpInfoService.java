package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ZiptoftpInfoPo;

/**
 * 
 * <class description>ZIP文件上传信息
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月7日
 */
public interface ZiptoftpInfoService {
	int deleteByPrimaryKey(Integer id);



	int insert(ZiptoftpInfoPo record);



	ZiptoftpInfoPo selectByPrimaryKey(Integer id);



	PageInfo<ZiptoftpInfoPo> selectZiptoftpInfoList(ZiptoftpInfoPo ziptoftpInfoPo, Integer pageNum, Integer pageSize);



	int updateByPrimaryKey(ZiptoftpInfoPo ziptoftpInfoPo);

}