package com.yatang.sc.facade.dubboservice;

import java.util.List;
import java.util.Map;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.DictionaryContentDto;
import com.yatang.sc.facade.dto.DictionaryDto;

/**
 * @描述:字典列表dubbo服务接口
 * @类名:DictionaryWriteDubboService
 * @作者: kangdong
 * @创建时间: 2017/6/8 15:56
 * @版本: v1.0
 */
public interface DictionaryWriteDubboService {
    /**
     * @Description: 新增数据字典
     * @author kangdong
     * @date 2017年6月8日 下午15:57:06
     * @param record
     * @return
     */
    Response<Boolean> insertDictionaryInfo(DictionaryDto record);

    /**
     * @Description: 修改数据字典
     * @author kangdong
     * @date 2017年6月8日 下午17:29:30
     * @param record
     * @return
     */
    Response<Boolean> updateDictionary(DictionaryDto record);

    /**
     * @Description: 删除数据字典
     * @author kangdong
     * @date 2017年6月8日 下午20:37:35
     * @param dictionaryId
     * @return
     */
    Response<Boolean> deleteDictionaryInfo(Integer dictionaryId);

    /**
     * @Description: 根据条件检测数据字典是否存在
     * @author kangdong
     * @date 2017年6月8日 下午20:37:35
     * @param record
     * @return
     */
    Response<Boolean> checkDictionaryInfo(DictionaryDto record);

    /**
     * @Description: 根据条件检测字典内容是否存在
     * @author kangdong
     * @date 2017年6月8日 下午20:37:35
     * @param record
     * @return
     */
    Response<Boolean> checkDictionaryContentInfo(DictionaryContentDto record);

    /**
     * @Description: 新增数据字典内容
     * @author kangdong
     * @date 2017年6月9日 下午14:14:37
     * @param dictionaryContentDto
     * @return
     */
    Response<Boolean> insertDictionaryContentInfo(DictionaryContentDto dictionaryContentDto);

    /**
     * @Description: 修改数据字典内容
     * @author kangdong
     * @date 2017年6月9日 下午14:47:47
     * @param dictionaryContentDto
     * @return
     */
    Response<Boolean> updateContentInfo(DictionaryContentDto dictionaryContentDto);

    /**
     * @Description: 更新字典维护内容的状态
     * @author kangdong
     * @date 2017年6月9日 下午14:47:47
     * @param contentDto
     * @return
     */
    Response<Boolean> updateState(DictionaryContentDto contentDto);
}
