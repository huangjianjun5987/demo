package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.DictionaryContentPo;
import com.yatang.sc.facade.domain.DictionaryPo;

import java.util.List;
import java.util.Map;

/**
 * @描述:数据字典服务接口
 * @类名:DictionaryService
 * @作者: kangdong
 * @创建时间: 2017/6/8 14:08
 * @版本: v1.0
 */
public interface DictionaryService {

	/**
	 * 
	 * 按条件分页查询数据字典
	 *
	 * @param parm
	 *            dictionaryType 0:业务级字典 1:系统级字典（下拉框等） keyword 查询参数：用于匹配编码或者名称
	 * @return
	 */
	PageInfo<DictionaryPo> findByPage(Map<String, Object> parm);



	/**
	 * @Description: 根据字典名称和字典编码关键字查询
	 * @author kangdong
	 * @date 2017年6月8日 下午11:10:15
	 */
	int checkByDictionaryAndCode(DictionaryPo dictionary);



	/**
	 * @Description: 新增数据字典
	 * @author kangdong
	 * @date 2017年6月8日 下午11:47:22
	 */
	Boolean insertDictionaryInfo(DictionaryPo dictionaryPo);



	/**
	 * @Description: 更新数据字典
	 * @author kangdong
	 * @date 2017年6月8日 下午14:57:06
	 */
	Boolean updateDictionaryInfo(DictionaryPo dictionaryPo);



	/**
	 * @Description: 根据ID查询数据字典
	 * @author kangdong
	 * @date 2017年6月8日 下午16:36:23
	 */
	DictionaryPo queryById(Integer dictionaryId);



	/**
	 * @Description: 根据ID删除一条数据字典
	 * @author kangdong
	 * @date 2017年6月8日 下午20:55:06
	 */
	Boolean deleteDictionaryById(Integer dictionaryId);



	/**
	 * @Description: 根据字典ID查询出维护的字典数据内容
	 * @author kangdong
	 * @date 2017年6月10日 下午17:55:06
	 */
	List<DictionaryContentPo> queryDictionaryContentById(Integer dictionaryId);



	/**
	 * @Description: 检测字典内容是否存在
	 * @author kangdong
	 * @date 2017年6月10日 下午17:57:22
	 */
	int checkByContent(DictionaryContentPo po);



	/**
	 * @Description: 新增维护的字典内容
	 * @author kangdong
	 * @date 2017年6月10日 下午18:02:45
	 */
	Boolean insertDictionaryContentInfo(DictionaryContentPo po);



	/**
	 * @Description: 更新字典内容
	 * @author kangdong
	 * @date 2017年6月10日 下午18:12:32
	 */
	Boolean updateContentInfo(DictionaryContentPo po);



	/**
	 * 删除数据字典时，同时删除下面的字典内容。
	 * 
	 * @param id
	 * @return
	 */
	Boolean deleteContentById(Integer id);
}
