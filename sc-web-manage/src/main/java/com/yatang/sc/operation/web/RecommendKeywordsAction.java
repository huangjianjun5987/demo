package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.BaseDto;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.RecommendKeywordsDto;
import com.yatang.sc.facade.dubboservice.RecommendKeywordsQueryDubboService;
import com.yatang.sc.facade.dubboservice.RecommendKeywordsWriteDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.operation.common.BaseVo;
import com.yatang.sc.operation.vo.RecommendKeywordsVo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @描述:搜索推荐页action
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/6/9 10:43
 * @版本:v1.0
 */
@Log4j
@RequestMapping(value = "/sc/rk")
@RestController
public class RecommendKeywordsAction extends BaseAction{
    //注入服务
    @Autowired
    private RecommendKeywordsWriteDubboService recommendKeywordsWriteDubboService;

    @Autowired
    private RecommendKeywordsQueryDubboService recommendKeywordsQueryDubboService;

    /**
     * 保存或者修改输入框的搜索关键字
     * @param  recommendKeywordsVo
     * @return
     */
    @RequestMapping(value = "/saveInput",method = RequestMethod.POST)
    public Response<Boolean> saveOrUpdate( @RequestBody RecommendKeywordsVo  recommendKeywordsVo){
        RecommendKeywordsDto recommendKeywordsDto = BeanConvertUtils.convert(recommendKeywordsVo, RecommendKeywordsDto.class);
        recommendKeywordsDto.setInputKey(1);
        Response<Boolean> response=recommendKeywordsWriteDubboService.saveOrUpdate(recommendKeywordsDto);
        return response;
    }

    /**
     * 保存热门推荐的关键字
     * @param recommendKeywordsVo
     * @return
     */
    @RequestMapping(value = "/saveHot",method = RequestMethod.POST)
    public Response<Boolean> saveHotCommendKeyword(@RequestBody RecommendKeywordsVo recommendKeywordsVo){
        //vo2dto
        RecommendKeywordsDto recommendKeywordsDto = BeanConvertUtils.convert(recommendKeywordsVo, RecommendKeywordsDto.class);
        recommendKeywordsDto.setInputKey(2);
        Response<Boolean> response=recommendKeywordsWriteDubboService.saveHotCommendKeyword(recommendKeywordsDto);
        return response;
    }

    /**
     * 修改热门推荐关键字之回显
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Response<RecommendKeywordsVo> queryCommendKeywordById(@RequestParam Integer id){
        RecommendKeywordsDto responseDto=recommendKeywordsWriteDubboService.queryCommendKeywordById(id).getResultObject();
        RecommendKeywordsVo recommendKeywordsVo=BeanConvertUtils.convert(responseDto, RecommendKeywordsVo.class);
        Response<RecommendKeywordsVo> response = new Response<>();
        if(recommendKeywordsVo == null){
            response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
            response.setResultObject(null);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_400.getCode());
            return response;
        }
            response.setResultObject(recommendKeywordsVo);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return response;
    };

    /**
     * 修改热门推荐关键字
     * @param recommendKeywordsVo
     * @return
     */
    @RequestMapping(value = "/updateHot",method = RequestMethod.POST)
    public Response<Boolean> updateCommendKeyword(@RequestBody RecommendKeywordsVo recommendKeywordsVo){
        RecommendKeywordsDto recommendKeywordsDto = BeanConvertUtils.convert(recommendKeywordsVo, RecommendKeywordsDto.class);
        recommendKeywordsDto.setInputKey(2);
        Response<Boolean> response=recommendKeywordsWriteDubboService.updateCommendKeyword(recommendKeywordsDto);
        return response;
    };

    /**
     * 根据id删除对应的热门推荐关键字
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.GET)
    public Response<Boolean> deleteCommendKeyword(Integer id){
        Response<Boolean>  response=recommendKeywordsWriteDubboService.deleteCommendKeyword(id);
        return response;
    };

    /**
     * 查出所有的搜索框下方推荐关键字
     * @return
     */
    @RequestMapping(value = "/queryAllHot",method = RequestMethod.GET)
    public Response<PageResult<RecommendKeywordsVo>> selectAllRecommendKeywords(BaseVo baseVo){
        log.debug(baseVo);
        BaseDto baseDto=BeanConvertUtils.convert(baseVo, BaseDto.class);
        baseDto.setPageNum(baseVo.getPageNum() != null ? baseVo.getPageNum() : Integer.parseInt(PublicEnum.DEFAULT_PAGE_NUM.getCodeStr()));
        baseDto.setPageSize(baseVo.getPageSize() != null ? baseVo.getPageSize() : Integer.parseInt(PublicEnum.DEFAULT_PAGE_SIZE.getCodeStr()));

        Response response = new Response<>();
        Response<PageResult<RecommendKeywordsDto>> mapResponse = recommendKeywordsQueryDubboService.selectAllRecommendKeywords(baseDto);
        PageResult<RecommendKeywordsDto> resultObject = mapResponse.getResultObject();
        List<RecommendKeywordsDto> data = resultObject.getData();
        List<RecommendKeywordsVo> recommendKeywordsVos = BeanConvertUtils.convertList(data, RecommendKeywordsVo.class);
        PageResult convert = BeanConvertUtils.convert(resultObject, PageResult.class);
        convert.setData(recommendKeywordsVos);
        response = BeanConvertUtils.convert(mapResponse, Response.class);
        response.setResultObject(convert);
        return response;
    };

    /**
     * 搜索框中的关键字查询
     * @return
     */
    @RequestMapping(value = "/selectInputKeyword",method = RequestMethod.GET)
    public Response<RecommendKeywordsVo> selectInputKeyword(){
        RecommendKeywordsDto responseDto=recommendKeywordsQueryDubboService.selectInputKeyword().getResultObject();
        RecommendKeywordsVo recommendKeywordsVo=BeanConvertUtils.convert(responseDto, RecommendKeywordsVo.class);
        Response<RecommendKeywordsVo> response = new Response<>();
        if(recommendKeywordsVo == null){
            response.setErrorMessage("当前没有保存，请输入。。");
            response.setResultObject(null);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            return response;
        }
        response.setResultObject(recommendKeywordsVo);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setSuccess(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return response;
    };

}
