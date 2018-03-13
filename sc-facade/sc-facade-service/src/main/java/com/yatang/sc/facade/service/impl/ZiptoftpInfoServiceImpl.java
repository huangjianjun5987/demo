package com.yatang.sc.facade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.ZiptoftpInfoDao;
import com.yatang.sc.facade.domain.ZiptoftpInfoPo;
import com.yatang.sc.facade.service.ZiptoftpInfoService;

@Service
public class ZiptoftpInfoServiceImpl implements ZiptoftpInfoService {
	@Autowired
	private ZiptoftpInfoDao ZiptoftpInfoDao;



	@Override
	public int deleteByPrimaryKey(Integer id) {
		return ZiptoftpInfoDao.deleteByPrimaryKey(id);
	}



	@Override
	public int insert(ZiptoftpInfoPo ziptoftpInfo) {
		return ZiptoftpInfoDao.insert(ziptoftpInfo);
	}



	@Override
	public ZiptoftpInfoPo selectByPrimaryKey(Integer id) {
		return ZiptoftpInfoDao.selectByPrimaryKey(id);
	}



	@Override
	public PageInfo<ZiptoftpInfoPo> selectZiptoftpInfoList(ZiptoftpInfoPo ziptoftpInfoPo, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ZiptoftpInfoPo> list = ZiptoftpInfoDao.selectZiptoftpInfoList(ziptoftpInfoPo);
		return new PageInfo<>(list);
	}



	@Override
	public int updateByPrimaryKey(ZiptoftpInfoPo ziptoftpInfoPo) {
		return ZiptoftpInfoDao.updateByPrimaryKey(ziptoftpInfoPo);
	}

}
