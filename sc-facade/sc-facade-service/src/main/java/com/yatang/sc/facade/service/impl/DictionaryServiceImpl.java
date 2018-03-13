package com.yatang.sc.facade.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.DictionaryDao;
import com.yatang.sc.facade.domain.DictionaryContentPo;
import com.yatang.sc.facade.domain.DictionaryPo;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.DictionaryService;

/**
 * @描述:数据字典
 * @类名:DictionaryServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/6/8 14:40
 * @版本: v1.0
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DictionaryDao dictionaryDao;



	@Override
	public PageInfo<DictionaryPo> findByPage(Map<String, Object> param) {
		String pageNum = param.get("pageNum")!=null ?  String.valueOf(param.get("pageNum")) :PublicEnum.DEFAULT_PAGE_NUM.getCodeStr();
		String pageSize = param.get("pageSize")!=null ?  String.valueOf(param.get("pageSize")) :PublicEnum.DEFAULT_PAGE_SIZE.getCodeStr();
		PageHelper.startPage(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
		List<DictionaryPo> dictionaryPos = dictionaryDao.dictionaryListByParam(param);
		PageInfo<DictionaryPo> pageInfo = new PageInfo<DictionaryPo>(dictionaryPos);
		return pageInfo;
	}



	@Override
	public int checkByDictionaryAndCode(DictionaryPo dictionaryPo) {
		int count = dictionaryDao.checkByDictionaryAndCode(dictionaryPo);
		return count;
	}



	@Override
	public Boolean insertDictionaryInfo(DictionaryPo record) {
		return dictionaryDao.insertDictionary(record) >= 1;
	}



	@Override
	public Boolean updateDictionaryInfo(DictionaryPo record) {
		return dictionaryDao.updateByPrimaryKey(record) >= 1;
	}



	@Override
	public DictionaryPo queryById(Integer id) {
		return dictionaryDao.queryById(id);
	}



	@Override
	public Boolean deleteDictionaryById(Integer id) {
		return dictionaryDao.deleteByPrimaryKey(id) >= 1;
	}



	@Override
	public List<DictionaryContentPo> queryDictionaryContentById(Integer dictionaryId) {
		return dictionaryDao.queryDictionaryContentById(dictionaryId);
	}



	@Override
	public int checkByContent(DictionaryContentPo record) {
		int count = dictionaryDao.checkByContent(record);
		return count;
	}



	@Override
	public Boolean insertDictionaryContentInfo(DictionaryContentPo record) {
		return dictionaryDao.insertDictionaryContent(record) >= 1;
	}



	@Override
	public Boolean updateContentInfo(DictionaryContentPo record) {
		return dictionaryDao.updateByContentState(record) >= 1;
	}



	@Override
	public Boolean deleteContentById(Integer id) {
		return dictionaryDao.deleteContentById(id) >= 1;
	}
}
