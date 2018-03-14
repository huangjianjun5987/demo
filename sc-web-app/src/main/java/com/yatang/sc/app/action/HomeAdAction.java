package com.yatang.sc.app.action;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.collect.Maps;
import com.yatang.sc.app.vo.HomeAreaAdVo;
import com.yatang.sc.facade.dto.HomeAreaAdAppDto;
import com.yatang.sc.facade.dto.RecommendKeywordsDto;
import com.yatang.sc.facade.dubboservice.HomeAdQueryDubboService;
import com.yatang.sc.facade.dubboservice.RecommendKeywordsQueryDubboService;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @描述: 首页广告 HTTP API
 * @作者: yipeng
 * @创建时间: 2017年06月09日11:05:01
 * @版本: 1.0 .
 */
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/sc/homeAd")
public class HomeAdAction extends AppBaseAction{
    private final HomeAdQueryDubboService homeAdQueryDubboService;
    private final RecommendKeywordsQueryDubboService recommendKeywordsQueryDubboService;


    /**
     * @Description: 首页区域列表，包含广告
     * @author yipeng
     * @date 2017年07月08日15:17:33
     */
    @RequestMapping(value = "areaList", method = RequestMethod.GET)
    public Response<Map<String, Object>> areaList() {
        Response<RecommendKeywordsDto> keywordsResponse = recommendKeywordsQueryDubboService.selectInputKeyword();
        Response<List<HomeAreaAdAppDto>> areaListResponse = homeAdQueryDubboService.appAreaList();

        Response<Map<String, Object>> response = new Response<>();
        Map<String, Object> result = Maps.newHashMap();
        result.put("inputKeyword", keywordsResponse.getResultObject() != null ? keywordsResponse.getResultObject().getContent() : StringUtils.EMPTY);
        result.put("areas", BeanConvertUtils.convertList(areaListResponse.getResultObject(), HomeAreaAdVo.class));
        response.setResultObject(result);
        return response;
    }

    @RequestMapping(value = "queryAppAreas", method = RequestMethod.GET)
    public Response<Map<String, Object>> queryAppAreas(HttpServletRequest request){
        Response<Map<String, Object>> response = new Response<>();
        String companyId = getBranchCompanyId(request);
        if(StringUtils.isEmpty(companyId)){
            companyId = "headquarters";
        }
        Response<RecommendKeywordsDto> keywordsResponse = recommendKeywordsQueryDubboService.selectInputKeyword();
        Response<List<HomeAreaAdAppDto>> areaListResponse = homeAdQueryDubboService.queryAppAreas(companyId);
        Map<String, Object> result = Maps.newHashMap();
        result.put("inputKeyword", keywordsResponse.getResultObject() != null ? keywordsResponse.getResultObject().getContent() : StringUtils.EMPTY);
        result.put("areas", BeanConvertUtils.convertList(areaListResponse.getResultObject(), HomeAreaAdVo.class));
        response.setResultObject(result);
        return response;
    }

}
