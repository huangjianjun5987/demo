package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.BaseDto;
import com.yatang.sc.facade.common.BasePo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.RecommendKeywordsPo;
import com.yatang.sc.facade.dto.RecommendKeywordsDto;
import com.yatang.sc.facade.dto.system.UserDto;
import com.yatang.sc.facade.dubboservice.RecommendKeywordsQueryDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.RecommendKeywordsService;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述:推荐热搜的全部查询
 * @作者:baiyun
 * @创建时间:2017/6/9 9:46
 * @版本:v1.0
 */
@Service("recommendKeywordsQueryDubboService")
public class RecommendKeywordsQueryDubboServiceImpl implements RecommendKeywordsQueryDubboService {
    protected final Log log	= LogFactory.getLog(this.getClass());
    //这里需要注入facade-service里面的service
    @Autowired
    private RecommendKeywordsService recommendKeywordsService;

    /**
     * 查出所有的搜索框下方推荐关键字
     * @return
     */

    @Override
    public Response<PageResult<RecommendKeywordsDto>> selectAllRecommendKeywords(BaseDto baseDto) {
        Response<PageResult<RecommendKeywordsDto>> response = new Response<PageResult<RecommendKeywordsDto>>();
        try {
            BasePo basePo=BeanConvertUtils.convert(baseDto, BasePo.class);
            PageInfo<RecommendKeywordsPo> pageInfo = recommendKeywordsService.selectAllRecommendKeywords(basePo);
            List<RecommendKeywordsDto> dtoLists = BeanConvertUtils.convertList(pageInfo.getList(), RecommendKeywordsDto.class);
            PageResult<RecommendKeywordsDto> page= new PageResult<RecommendKeywordsDto>();
            page.setPageNum(pageInfo.getPageNum());
            page.setPageSize(pageInfo.getPageSize());
            page.setTotal(pageInfo.getTotal());
            page.setData(dtoLists);
            response.setSuccess(true);
            response.setResultObject(page);
            response.setCode(CommonsEnum.RESPONSE_200.getCode().toString());
            response.setErrorMessage("查询成功");
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode().toString());
            response.setSuccess(false);
            response.setErrorMessage("查询失败");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<RecommendKeywordsDto> selectInputKeyword() {
        Response<RecommendKeywordsDto> response = new Response<RecommendKeywordsDto>();
        try {
            RecommendKeywordsPo recommendKeywordsPo = recommendKeywordsService.selectInputKeyword();
            if(recommendKeywordsPo == null){
                response.setCode(CommonsEnum.RESPONSE_500.getCode().toString());
                response.setSuccess(false);
                response.setErrorMessage("后台没有配置热搜关键字");
                return response;
            }
            RecommendKeywordsDto  recommendKeywordsDto=BeanConvertUtils.convert(recommendKeywordsPo, RecommendKeywordsDto.class);
            response.setSuccess(true);
            response.setResultObject(recommendKeywordsDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode().toString());
            response.setErrorMessage("查询成功");
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode().toString());
            response.setSuccess(false);
            response.setErrorMessage("查询失败");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 根据类型查出APP端所需要的数据
     * @return
     */
    @Override
    public Response<List<RecommendKeywordsDto>> selectRecommendKeywordsByType(Integer type) {
        Response<List<RecommendKeywordsDto>> response = new Response<List<RecommendKeywordsDto>>();
        try {
            List<RecommendKeywordsPo> recommendKeywordsPos = recommendKeywordsService.selectRecommendKeywordsByType(type);
            List<RecommendKeywordsDto>  recommendKeywordsDtos=BeanConvertUtils.convertList(recommendKeywordsPos, RecommendKeywordsDto.class);
            response.setSuccess(true);
            response.setResultObject(recommendKeywordsDtos);
            response.setCode(CommonsEnum.RESPONSE_200.getCode().toString());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode().toString());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
