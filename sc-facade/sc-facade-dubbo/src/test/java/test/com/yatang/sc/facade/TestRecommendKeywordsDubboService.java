package test.com.yatang.sc.facade;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.facade.dubboservice.RecommendKeywordsWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.RecommendKeywordsDto;
import com.yatang.sc.facade.dubboservice.RecommendKeywordsQueryDubboService;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/7 14:17
 * @版本:v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestRecommendKeywordsDubboService {
    @Autowired
    private RecommendKeywordsQueryDubboService service;

    @Autowired
    private RecommendKeywordsWriteDubboService recommendKeywordsWriteDubboService;

    @Test
    public void testFindOne(){
        Response<List<RecommendKeywordsDto>> response = service.selectRecommendKeywordsByType( 1 );
        List<RecommendKeywordsDto> dtos = response.getResultObject();
        for (RecommendKeywordsDto dto : dtos) {
            System.out.println(dto.toString());
        }
    }

    @Test
    public void testSaveHot(){
        RecommendKeywordsDto recommendKeywordsDto = new RecommendKeywordsDto();
        recommendKeywordsDto.setInputKey(2);
        recommendKeywordsDto.setContent("你是最");
        recommendKeywordsDto.setSort(35);
     /*   System.out.println(JSON.toJSONString(recommendKeywordsWriteDubboService.saveOrUpdate(recommendKeywordsDto)));*/

        System.out.println(JSON.toJSONString(recommendKeywordsWriteDubboService.saveHotCommendKeyword(recommendKeywordsDto)));

    }
}
