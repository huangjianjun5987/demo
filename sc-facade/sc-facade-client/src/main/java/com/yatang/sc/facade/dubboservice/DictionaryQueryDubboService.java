package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.DictionaryContentDto;
import com.yatang.sc.facade.dto.DictionaryDto;

import java.util.List;
import java.util.Map;

/**
 * @描述:字典列表dubbo服务接口
 * @类名:DictionaryQueryDubboService
 * @作者: kangdong
 * @创建时间: 2017/6/8 11:31
 * @版本: v1.0
 */
public interface DictionaryQueryDubboService {

	Response<PageResult<DictionaryDto>> findByPage(String keyword, Integer pageNum, Integer pageSize);



	Response<DictionaryDto> queryById(Integer dictionaryId);



	Response<List<DictionaryContentDto>> queryDictionaryContentList(Integer dictionaryId);



	/**
	 * 
	 * 通过字典名称查询字典对应的字典内容：用于系统级下拉框（尹玉新）
	 *
	 * @param dictionaryName
	 * @return
	 */
	Response<List<DictionaryContentDto>> queryDictionContentForSelect(String dictionaryName);
}
