package test.com.yatang.sc.facade;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.DictionaryContentDto;
import com.yatang.sc.facade.dto.DictionaryDto;
import com.yatang.sc.facade.dubboservice.DictionaryQueryDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @描述:
 * @类名:
 * @作者: kangdong
 * @创建时间: 2017/6/12 13:48
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-consumer.xml" })
public class TestDictionaryQueryDubboService {
    @Autowired
    DictionaryQueryDubboService service;

    @Test
    public void testQueryById(){
        Response<DictionaryDto> response = service.queryById(1);
        DictionaryDto dto = response.getResultObject();
        System.out.println("=========查询结果=========="+dto.toString());
    }

    @Test
    public void dictionaryListPage(){
        Response<PageResult<DictionaryDto>> ss = service.findByPage("",1,3);
        PageResult<DictionaryDto> dd = ss.getResultObject();
        for (DictionaryDto dto : dd.getData()) {
            System.out.println(dto.toString());
        }
    }


    //-------------------------维护字典内容----------------------------------------
    @Test
    public void testQueryContentList(){
        Response<List<DictionaryContentDto>> response = service.queryDictionaryContentList(1);
        List<DictionaryContentDto> dc =  response.getResultObject();
        for (DictionaryContentDto dto : dc) {
            System.out.println(dto.toString());
        }
    }
}
