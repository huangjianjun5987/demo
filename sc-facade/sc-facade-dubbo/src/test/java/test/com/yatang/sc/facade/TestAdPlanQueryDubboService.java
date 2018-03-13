package test.com.yatang.sc.facade;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.AdPlanDto;
import com.yatang.sc.facade.dubboservice.AdPlanQueryDubboService;
import com.yatang.sc.facade.dubboservice.AdPlanWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 15:01
 * @版本: v1.0
 */@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestAdPlanQueryDubboService {
    @Autowired
    private AdPlanQueryDubboService adPlanQueryDubboService;

    /**
     * 查询所有的方案
     */
    @Test
    public void testQueryAllAdPlanList(){
        Response<List<AdPlanDto>> listResponse = adPlanQueryDubboService.queryAllAdPlanList();
        System.out.println(listResponse.getResultObject().toString());
    }

    /**
     * 查询通过id获取方案
     */
    @Test
    public void testGetAdPlanById(){
        Response<AdPlanDto> adPlanById = adPlanQueryDubboService.getAdPlanById(3);
        System.out.println(adPlanById.getResultObject().toString());
    }
}
