package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.RecommendKeywordsPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @描述:搜索推荐页的dao
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/8 14:52
 * @版本:v1.0
 */
public interface RecommendKeywordsDao {

    /**
     * 保存或者修改输入框的搜索关键字
     * @param recommendKeywordsPo
     */
    int saveOrUpdate(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 保存热门推荐的关键字
     * @param recommendKeywordsPo
     * @return
     */
    int saveHotCommendKeyword(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 修改热门推荐关键字
     * @param recommendKeywordsPo
     * @return
     */
    int updateCommendKeyword(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 根据id删除对应的热门推荐关键字
     * @param id
     * @return
     */
    int deleteCommendKeyword(Integer id);

    /**
     * 查出所有的搜索框下方推荐关键字
     * @return
     */
    List<RecommendKeywordsPo> selectAllRecommendKeywords();

    /**
     * 查询搜索框的参数是否存在
     * @param content
     * @return
     */
    Integer queryKeywordByInputContent(@Param("content") String content);

    /**
     * 根据排序值查询此排序值是否已存在  存在则返回1  不存在则返回0
     * @param recommendKeywordsPo
     * @return
     */
    Integer queryKeywordBySort(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 根据id查询所对应的热搜
     * @param id
     * @return
     */
    RecommendKeywordsPo queryByPrimaryKey(@Param("id") Integer id);

    /**
     * 判断是否存在搜索框
     * @return
     */
    Integer queryKeywordByInput();

    /**
     * 新增搜索框
     * @param recommendKeywordsPo
     * @return
     */
    Integer insertInputContent(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 修改搜索框的值
     * @param recommendKeywordsPo
     * @return
     */
    int updateInputContent(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 修改的时候查询是否存在推荐参数
     * @param recommendKeywordsPo
     * @return
     */
    Integer queryKeywordByContent(RecommendKeywordsPo recommendKeywordsPo);

    /**
     * 正常添加热门推荐时判断是否存在
     * @param content
     * @return
     */
    Integer queryKeywordByContentParam(String content);

    /**
     * 查询搜索框的关键字的实体类
     * @return
     */
    RecommendKeywordsPo selectInputKeyword();

    Integer queryKeywordBySort2(Integer sort);

    /**
     * 根据类型查出APP端所需的所有实体类
     * @param type
     * @return
     */
    List<RecommendKeywordsPo> selectRecommendKeywordsByType(Integer type);
}
