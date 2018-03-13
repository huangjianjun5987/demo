package com.yatang.sc.facade.dubboservice;


import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.RecommendKeywordsDto;

import java.util.List;

/**
 * @描述:搜索推荐页接口
 * @作者:baiyun
 * @创建时间:2017/6/8 13:52
 * @版本:v1.0
 */
public interface RecommendKeywordsWriteDubboService {

    /**
     * 保存或者修改输入框的搜索关键字
     * @param recommendKeywordsDto
     * @return
     */
    Response<Boolean> saveOrUpdate(RecommendKeywordsDto recommendKeywordsDto);

    /**
     * 保存热门推荐的关键字
     * @param recommendKeywordsDto
     * @return
     */
    Response<Boolean> saveHotCommendKeyword(RecommendKeywordsDto recommendKeywordsDto);

    /**
     * 修改热门推荐关键字之回显
     * @param id
     * @return
     */
    Response<RecommendKeywordsDto> queryCommendKeywordById(Integer id);
    /**
     * 修改热门推荐关键字
     * @param recommendKeywordsDto
     * @return
     */
    Response<Boolean> updateCommendKeyword(RecommendKeywordsDto recommendKeywordsDto);
    /**
     * 根据id删除对应的热门推荐关键字
     * @param id
     * @return
     */
    Response<Boolean> deleteCommendKeyword(Integer id);


}
