package com.yatang.sc.app.action;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.AdPlanDto;
import com.yatang.sc.facade.dubboservice.AdPlanQueryDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/6 17:24
 * @版本: v1.0
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/test")
public class TestAction {

    private final AdPlanQueryDubboService adPlanQueryDubboService;

    @RequestMapping(value = "/getTest",method = RequestMethod.GET)
    public Response<AdPlanDto> getTest() {
        Response<AdPlanDto> re = adPlanQueryDubboService.getAdPlanById(58);
        return re;
    }
}
