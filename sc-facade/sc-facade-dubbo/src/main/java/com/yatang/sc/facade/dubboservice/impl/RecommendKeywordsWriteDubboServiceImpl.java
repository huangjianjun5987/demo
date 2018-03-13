package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.RecommendKeywordsPo;
import com.yatang.sc.facade.dto.RecommendKeywordsDto;
import com.yatang.sc.facade.dubboservice.RecommendKeywordsWriteDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.RecommendKeywordsService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述:搜索推荐页接口的实现类
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/8 14:31
 * @版本:v1.0
 */
@Service("recommendKeywordsWriteDubboService")
public class RecommendKeywordsWriteDubboServiceImpl implements RecommendKeywordsWriteDubboService {

    protected final Log log	= LogFactory.getLog(this.getClass());
    //这里需要注入facade-service里面的service
    @Autowired
    private RecommendKeywordsService recommendKeywordsService;
    /**
     * 保存或者修改输入框的搜索关键字
     * @param recommendKeywordsDto
     * @return
     */
    @Override
    public Response<Boolean> saveOrUpdate(RecommendKeywordsDto recommendKeywordsDto) {
        RecommendKeywordsPo recommendKeywordsPo = BeanConvertUtils.convert(recommendKeywordsDto,RecommendKeywordsPo.class);
        Response<Boolean> response = new Response<>();
        String content=recommendKeywordsDto.getContent();
        //判断传过来的这个参数是不是大于5个汉字
        if(content.length() > 5){
            response.setCode(CommonsEnum.RESPONSE_10036.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_10036.getName());
            response.setResultObject(false);
            return response;
        }
        //判断是否已存在这个搜索参数
        Integer count=recommendKeywordsService.queryKeywordByInputContent(content);
        //如果不存在  则正常执行
        if(count == 0){
            //则还需判断搜索框的值是否存在
            Integer inputKeyCount=recommendKeywordsService.queryKeywordByInput();
            if(inputKeyCount==0){
                //执行insert方法
                try{
                    response.setCode(CommonsEnum.RESPONSE_200.getCode());
                    response.setSuccess(true);
                    response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    response.setResultObject(recommendKeywordsService.insert(recommendKeywordsPo));
                    response.setResultObject(true);
                    return response;
                }catch (Exception e){
                    response.setCode(CommonsEnum.RESPONSE_500.getCode());
                    response.setSuccess(false);
                    response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                    response.setResultObject(false);
                    log.error(ExceptionUtils.getFullStackTrace(e));
                    return response;
                }
            }
            //否则的话  执行update方法
            try{
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setResultObject(recommendKeywordsService.update(recommendKeywordsPo));
                response.setResultObject(true);
                return response;
            }catch (Exception e){
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                response.setResultObject(false);
                log.error(ExceptionUtils.getFullStackTrace(e));
                return response;
            }
        }
      //如果存在  则不能使用这个搜索参数
        response.setCode(CommonsEnum.RESPONSE_10037.getCode());
        response.setSuccess(false);
        response.setErrorMessage(CommonsEnum.RESPONSE_10037.getName());
        response.setResultObject(false);
        return response;
    }

    /**
     * 保存热门推荐的关键字
     * @param recommendKeywordsDto
     * @return
     */

    @Override
    public Response<Boolean> saveHotCommendKeyword(RecommendKeywordsDto recommendKeywordsDto) {
        Response<Boolean> response = new Response<>();
        RecommendKeywordsPo recommendKeywordsPo = BeanConvertUtils.convert(recommendKeywordsDto,RecommendKeywordsPo.class);
        //判断传过来的这个参数是不是大于5个字数
        String content=recommendKeywordsDto.getContent();
        if(content.length() > 5){
            response.setCode(CommonsEnum.RESPONSE_10036.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_10036.getName());
            response.setResultObject(false);
            return response;
        }
        //校验非空
        if(recommendKeywordsDto.getSort()== null||recommendKeywordsDto.getContent()== null ||recommendKeywordsDto.getContent().trim().length() == 0){
            response.setCode(CommonsEnum.RESPONSE_10038.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_10038.getName());
            response.setResultObject(false);
            return response;
        }
        //在做真正的添加热门推荐之前 先判断一下输入的热门推荐是否已经存在了
        Integer countContent=recommendKeywordsService.queryKeywordByContentParam(recommendKeywordsDto.getContent());
        if(countContent == 0){
            //如果没有这个这个热搜参数  则需要看下有没有它设置的排序号
            Integer countSort=recommendKeywordsService.queryKeywordBySort2(recommendKeywordsDto.getSort());
            if(countSort == 0){
                try {

                    //证明没有这个排序值  这个排序值可以用 执行这个方法
                    response.setCode(CommonsEnum.RESPONSE_200.getCode());
                    response.setSuccess(recommendKeywordsService.saveHotCommendKeyword(recommendKeywordsPo));
                    response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    response.setResultObject(true);
                    return response;
                }catch (Exception e){
                    response.setCode(CommonsEnum.RESPONSE_500.getCode());
                    response.setSuccess(false);
                    response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                    response.setResultObject(false);
                    log.error(ExceptionUtils.getFullStackTrace(e));
                    return response;
                }
            }
            //这是有这个排序值的 这需要给前端返回一个错误的提示信息
            response.setErrorMessage(CommonsEnum.RESPONSE_10001.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_10001.getCode());
            response.setResultObject(false);
            return response;
        }
        response.setErrorMessage(CommonsEnum.RESPONSE_10037.getName());
        response.setSuccess(false);
        response.setCode(CommonsEnum.RESPONSE_10037.getCode());
        response.setResultObject(false);
        return response;
    }
    /**
     * 修改热门推荐关键字之回显
     * @param id
     * @return
     */
    @Override
    public Response<RecommendKeywordsDto> queryCommendKeywordById(Integer id) {
        Response<RecommendKeywordsDto> response=new Response<>();
        try {
            RecommendKeywordsPo recommendKeywordsPo=recommendKeywordsService.queryCommendKeywordById(id);
            RecommendKeywordsDto recommendKeywordsDto = BeanConvertUtils.convert(recommendKeywordsPo,RecommendKeywordsDto.class);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(recommendKeywordsDto);
            response.setSuccess(true);
        }catch (Exception e){
            //查询失败的时候
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setResultObject(null);
            log.error(ExceptionUtils.getFullStackTrace(e));
            return response;
        }
        return response;
    }

    /**
     * 修改推荐
     * @param recommendKeywordsDto
     * @return
     */
    @Override
    public Response<Boolean> updateCommendKeyword(RecommendKeywordsDto recommendKeywordsDto) {
        Response<Boolean> response = new Response<>();
        //判断传过来的这个参数是不是大于5个字数
        String content=recommendKeywordsDto.getContent();
        if(content.length() > 5){
            response.setCode(CommonsEnum.RESPONSE_10036.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_10036.getName());
            response.setResultObject(false);
            return response;
        }
        //校验非空
        if(recommendKeywordsDto.getSort()== null||recommendKeywordsDto.getContent()== null||recommendKeywordsDto.getContent().trim().length()== 0){
            response.setCode(CommonsEnum.RESPONSE_10038.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_10038.getName());
            response.setResultObject(false);
            return response;
        }
        RecommendKeywordsPo recommendKeywordsPo=BeanConvertUtils.convert(recommendKeywordsDto,RecommendKeywordsPo.class);
        //在做真正的修改热门推荐之前 先判断一下输入的热门推荐是否已经存在了
        Integer countContent=recommendKeywordsService.queryKeywordByContent(recommendKeywordsPo);
        if(countContent == 0){
            //如果没有这个这个热搜参数  则需要看下有没有它设置的排序号
            Integer countSort=recommendKeywordsService.queryKeywordBySort(recommendKeywordsPo);
            if(countSort == 0){
                try {
                    //证明没有这个排序值  这个排序值可以用 执行这个方法
                    response.setCode(CommonsEnum.RESPONSE_200.getCode());
                    response.setSuccess(recommendKeywordsService.updateCommendKeyword(recommendKeywordsPo));
                    response.setResultObject(true);
                    response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    return response;
                }catch (Exception e){
                    response.setCode(CommonsEnum.RESPONSE_500.getCode());
                    response.setSuccess(false);
                    response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                    response.setResultObject(false);
                    log.error(ExceptionUtils.getFullStackTrace(e));
                    return response;
                }
            }
            //这是有这个排序值的 这需要给前端返回一个错误的提示信息
            response.setErrorMessage(CommonsEnum.RESPONSE_10001.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_10001.getCode());
            response.setResultObject(false);
            return response;
        }
        response.setErrorMessage(CommonsEnum.RESPONSE_10037.getName());
        response.setSuccess(false);
        response.setCode(CommonsEnum.RESPONSE_10037.getCode());
        response.setResultObject(false);
        return response;
    }

    /**
     * 根据id删除对应的热门推荐关键字
     * @param id
     * @return
     */
    @Override
    public Response<Boolean> deleteCommendKeyword(Integer id) {
        Response<Boolean> response = new Response<>();
        try {
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(recommendKeywordsService.deleteCommendKeyword(id));
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(true);
        }catch (Exception e){
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setResultObject(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
            return response;
        }
        return response;
    }

}
