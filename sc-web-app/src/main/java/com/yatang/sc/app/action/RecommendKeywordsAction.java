package com.yatang.sc.app.action;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.app.vo.RecommendKeywordsVo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.RecommendKeywordsDto;
import com.yatang.sc.facade.dubboservice.RecommendKeywordsQueryDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/7 14:41
 * @版本:v1.0
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/sc/rk")
public class RecommendKeywordsAction {
    private final RecommendKeywordsQueryDubboService recommendKeywordsQueryDubboService;

    @RequestMapping(value = "/getHot",method = RequestMethod.GET)
    public Response<List<RecommendKeywordsVo>> getHot(){
        List<RecommendKeywordsDto> responseDtos=recommendKeywordsQueryDubboService.selectRecommendKeywordsByType(2).getResultObject();
        List<RecommendKeywordsVo> recommendKeywordsVos= BeanConvertUtils.convertList(responseDtos, RecommendKeywordsVo.class);
        Response<List<RecommendKeywordsVo>> response = new Response<>();
        response.setResultObject(recommendKeywordsVos);
        response.setCode(CommonsEnum.RESPONSE_200.getCode().toString());
        response.setSuccess(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName().toString());
        return response;
    };

    @RequestMapping(value = "/getSearchBox",method = RequestMethod.GET)
    public Response<RecommendKeywordsVo> getSearchBox(){
        Response<RecommendKeywordsVo> response = new Response<>();
        Response<RecommendKeywordsDto> recommendKeywordsDto=recommendKeywordsQueryDubboService.selectInputKeyword();
        if(recommendKeywordsDto.isSuccess()){
            RecommendKeywordsVo recommendKeywordsVo= BeanConvertUtils.convert(recommendKeywordsDto, RecommendKeywordsVo.class);
            response.setResultObject(recommendKeywordsVo);
            response.setCode(CommonsEnum.RESPONSE_200.getCode().toString());
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName().toString());
        }else{
            response.setResultObject(null);
            response.setCode(CommonsEnum.RESPONSE_500.getCode().toString());
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName().toString());
        }
        return response;
    };

}




