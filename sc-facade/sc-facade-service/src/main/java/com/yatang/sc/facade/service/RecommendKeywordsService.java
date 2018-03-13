package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.BasePo;
import com.yatang.sc.facade.domain.RecommendKeywordsPo;

import java.util.List;
import java.util.Map;

/**
 * @描述:搜索推荐页service接口
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/8 14:37
 * @版本:v1.0
 */
public interface RecommendKeywordsService {
    /**
     * 保存或者修改输入框的搜索关键字
     * @param recommendKeywordsPo
     */
  Boolean saveOrUpdate(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 保存热门推荐的关键字
     * @param recommendKeywordsPo
     * @return
     */
    Boolean saveHotCommendKeyword(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 修改热门推荐关键字
     * @param recommendKeywordsPo
     * @return
     */
    Boolean updateCommendKeyword(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 根据id删除对应的热门推荐关键字
     * @param id
     * @return
     */
    Boolean deleteCommendKeyword(Integer id);

    /**
     * 查出所有的搜索框下方推荐关键字
     * @return
     */
   PageInfo<RecommendKeywordsPo> selectAllRecommendKeywords(BasePo basePo);

    /**
     * 查询搜索框参数是否存在
     * @param content
     * @return
     */
    Integer queryKeywordByInputContent(String content);

    /**
     * 根据排序值查询此排序值是否存在
     * @param recommendKeywordsPo
     * @return
     */
    Integer queryKeywordBySort(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 根据id查询所对应的热搜
     * @param id
     * @return
     */
    RecommendKeywordsPo queryCommendKeywordById(Integer id);

    Integer queryKeywordByInput();

    /**
     * 新增搜索框的值
     * @param recommendKeywordsPo
     * @return
     */
    Boolean insert(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 修改搜索框的值
     * @param recommendKeywordsPo
     * @return
     */
    Boolean update(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 添加的时候看是否存在推荐热搜字
     * @param recommendKeywordsPo
     * @return
     */
    Integer queryKeywordByContent(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 正常添加热门推荐的时候
     * @param content
     * @return
     */
    Integer queryKeywordByContentParam(String content);

    /**
     * 查询输入框的字段的实体类
     * @return
     */
    RecommendKeywordsPo selectInputKeyword();

    /**
     *
     * @param sort
     * @return
     */
    Integer queryKeywordBySort2(Integer sort);

    /**
     * 根据类型查出APP端所需要的实体类集合
     * @param type
     * @return
     */
    List<RecommendKeywordsPo> selectRecommendKeywordsByType(Integer type);
}
