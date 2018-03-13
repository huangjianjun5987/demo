package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.BaseDto;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.RecommendKeywordsDto;

import java.util.List;
import java.util.Map;

/**
 * @描述:查询所有的推荐热搜
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/9 9:44
 * @版本:v1.0
 */
public interface RecommendKeywordsQueryDubboService {

    /**
     * 查出所有的搜索框下方推荐关键字
     * @return
     */
    Response<PageResult<RecommendKeywordsDto>> selectAllRecommendKeywords(BaseDto baseDto);

    /**
     *回显搜索框的热门推荐关键字
     */
    Response<RecommendKeywordsDto> selectInputKeyword();

    /**
     * App端的接口 根据类型查出所需的实体类
     * @return
     */
    Response<List<RecommendKeywordsDto>>  selectRecommendKeywordsByType(Integer type);

}
