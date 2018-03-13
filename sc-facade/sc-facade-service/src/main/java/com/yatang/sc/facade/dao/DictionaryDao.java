package com.yatang.sc.facade.dao;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.DictionaryContentPo;
import com.yatang.sc.facade.domain.DictionaryPo;

import java.util.List;
import java.util.Map;

/**
 * @描述:
 * @类名:DictionaryDao
 * @作者: kangdong
 * @创建时间: 2017/6/8 14:19
 * @版本: v1.0
 */
public interface DictionaryDao {

	/**
	 * 根据条件查询数据字典列表
	 * 
	 * @param keyword
	 * @return
	 */
	List<DictionaryPo> dictionaryListByParam(Map<String, Object> param);



	int checkByDictionaryAndCode(DictionaryPo dictionaryPo);



	int insertDictionary(DictionaryPo record);



	int updateByPrimaryKey(DictionaryPo record);



	DictionaryPo queryById(Integer id);



	int deleteByPrimaryKey(Integer id);



	/**
	 * 根据数据字典ID查询字典内容列表
	 * 
	 * @param dictionaryId
	 * @return
	 */
	List<DictionaryContentPo> queryDictionaryContentById(Integer dictionaryId);



	int checkByContent(DictionaryContentPo record);



	int insertDictionaryContent(DictionaryContentPo record);



	int updateByContentState(DictionaryContentPo record);



	int deleteContentById(Integer id);
}
