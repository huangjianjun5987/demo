package com.yatang.sc.facade.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.BasePo;
import com.yatang.sc.facade.dao.RecommendKeywordsDao;
import com.yatang.sc.facade.domain.RecommendKeywordsPo;
import com.yatang.sc.facade.service.RecommendKeywordsService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.yatang.sc.facade.enums.PublicEnum.*;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/8 14:47
 * @版本:v1.0
 */
@Service
@Transactional
public class RecommendKeywordsServiceImpl implements RecommendKeywordsService {
    //这里需要注入RecommendKeywordsDao
    @Autowired
    private RecommendKeywordsDao recommendKeywordsDao;
    /**
     * 保存或者修改输入框的搜索关键字
     * @param recommendKeywordsPo
     * @return
     */
    @Override
    public Boolean saveOrUpdate(RecommendKeywordsPo recommendKeywordsPo) {
       return recommendKeywordsDao.saveOrUpdate(recommendKeywordsPo) >= 1;
    }
    /**
     * 保存热门推荐的关键字
     * @param recommendKeywordsPo
     * @return
     */
    @Override
    public Boolean saveHotCommendKeyword(RecommendKeywordsPo recommendKeywordsPo) {
        return recommendKeywordsDao.saveHotCommendKeyword(recommendKeywordsPo) >= 1;
    }
    /**
     * 修改热门推荐关键字
     * @param recommendKeywordsPo
     * @return
     */
    @Override
    public Boolean updateCommendKeyword(RecommendKeywordsPo recommendKeywordsPo) {
        return recommendKeywordsDao.updateCommendKeyword(recommendKeywordsPo) >= 1;
    }
    /**
     * 根据id删除对应的热门推荐关键字
     * @param id
     * @return
     */
    @Override
    public Boolean deleteCommendKeyword(Integer id) {

        return recommendKeywordsDao.deleteCommendKeyword(id) >= 1;
    }
    /**
     * 查出所有的搜索框下方推荐关键字
     * @return
     */
    @Override
    public PageInfo<RecommendKeywordsPo> selectAllRecommendKeywords(BasePo basePo) {
        PageHelper.startPage(basePo.getPageNum(), basePo.getPageSize());
        List<RecommendKeywordsPo> list = recommendKeywordsDao.selectAllRecommendKeywords();
        PageInfo<RecommendKeywordsPo> pageInfo = new PageInfo<RecommendKeywordsPo>(list);
        return pageInfo;
    }

    /**
     * 查询搜索框的参数是否存在
     * @param content
     * @return
     */
    @Override
    public Integer queryKeywordByInputContent(String content) {

        return recommendKeywordsDao.queryKeywordByInputContent(content);
    }

    /**
     * 根据排序值查询是否此排序值是否已存在 存在则返回1 不存在则返回0
     * @param recommendKeywordsPo
     * @return
     */
    @Override
    public Integer queryKeywordBySort(RecommendKeywordsPo recommendKeywordsPo) {

        return recommendKeywordsDao.queryKeywordBySort(recommendKeywordsPo);
    }

    /**
     * 根据id查询所对应的热搜
     * @param id
     * @return
     */
    @Override
    public RecommendKeywordsPo queryCommendKeywordById(Integer id) {

        return recommendKeywordsDao.queryByPrimaryKey(id);
    }

    /**
     * 判断是否存在搜索框
     * @return
     */
    @Override
    public Integer queryKeywordByInput() {
        return recommendKeywordsDao.queryKeywordByInput();
    }

    /**
     * 新增搜索框的值
     * @param recommendKeywordsPo
     * @return
     */
    @Override
    public Boolean insert(RecommendKeywordsPo recommendKeywordsPo) {

        return recommendKeywordsDao.insertInputContent(recommendKeywordsPo)>=1;
    }

    @Override
    public Boolean update(RecommendKeywordsPo recommendKeywordsPo) {

        return recommendKeywordsDao.updateInputContent(recommendKeywordsPo)>=1;
    }

    /**
     * 在修改热门推荐的时候
     * @param recommendKeywordsPo
     * @return
     */
    @Override
    public Integer queryKeywordByContent(RecommendKeywordsPo recommendKeywordsPo) {

        return recommendKeywordsDao.queryKeywordByContent(recommendKeywordsPo);
    }

    /**
     * 在保存热门推荐的时候
     * @param content
     * @return
     */
    @Override
    public Integer queryKeywordByContentParam(String content) {

        return recommendKeywordsDao.queryKeywordByContentParam(content);
    }

    /**
     * 查询输入框的关键字的字段的实体类
     * @return
     */
    @Override
    public RecommendKeywordsPo selectInputKeyword() {
        return recommendKeywordsDao.selectInputKeyword();
    }

    @Override
    public Integer queryKeywordBySort2(Integer sort) {
        return recommendKeywordsDao.queryKeywordBySort2(sort);
    }

    /**
     * 根据类型查出APP端所需的实体类
     * @param type
     * @return
     */
    @Override
    public List<RecommendKeywordsPo> selectRecommendKeywordsByType(Integer type) {
        return recommendKeywordsDao.selectRecommendKeywordsByType(type);
    }

}
