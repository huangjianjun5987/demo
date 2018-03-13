package test.com.yatang.sc.facade;

/**
 * Created by tangqi on 2018/1/9 20:28.
 */

import com.busi.common.datatable.PageResult;
import com.busi.common.resp.Response;
import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.facade.dto.MongoTestBeanDto;
import com.yatang.sc.facade.dto.QueryRequestDto;
import com.yatang.sc.facade.dubboservice.TestDubboService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestService {

    @Autowired
    private TestDubboService testDubboService;

    @Test
    public void findAll(){
        Response<List<MongoTestBeanDto>> response = testDubboService.findAll();
        List<MongoTestBeanDto> object = response.getResultObject();
        Assert.assertNotNull(object);
    }

    @Test
    public void insert(){
        MongoTestBeanDto bean = new MongoTestBeanDto();
        bean.setPassword("tangqi");
        bean.setName("asdf");
        bean.setAge(5);
        testDubboService.insert(bean);
    }

    @Test
    public void testUpate(){
        Response<MongoTestBeanDto> response = testDubboService.findById("5a55bc587ed6b3278cbd7e06");
        MongoTestBeanDto beanDto = response.getResultObject();
        beanDto.setAge(500);
        testDubboService.update(beanDto);
    }

    @Test
    public void testBulkInsert(){
        List<MongoTestBeanDto> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            MongoTestBeanDto bean = new MongoTestBeanDto();
            bean.setPassword(i+"tq");
            bean.setName(i+"tq");
            bean.setAge(22);
            bean.setStartDate(new Date(1208966400000L));
            bean.setEndDate(new Date(1208966400000L));
            bean.setStartLong(new Long(i+1));
            bean.setEndLong(new Long((i+1)*10));
            MongoTestBeanDto bean2 = new MongoTestBeanDto();
            bean2.setPassword(i+"");
            bean2.setName(i+"");
            bean2.setAge(3);
            bean2.setStartDate(new Date(2208966400000L));
            bean2.setEndDate(new Date(2208966400000L));
            bean2.setStartLong(new Long(i+1));
            bean2.setEndLong(new Long((i+1)*10));
            list.add(bean);
            list.add(bean2);

        }
        testDubboService.bulkInsert(list);

    }

    @Test
    public void testBulkDelete(){
        List<String> ids = new ArrayList<>();
        ids.add("5a55bc587ed6b3278cbd7e06");
        ids.add("5a55c4357ed6b32710c899ab");
        testDubboService.bulkDelete(ids);
    }

    @Test
    public void testFindById(){
        String id = "5a55bc587ed6b3278cbd7e06";
        Response<MongoTestBeanDto> response = testDubboService.findById(id);
        MongoTestBeanDto areaAdDto = response.getResultObject();
        System.out.println("findByIdasdf"+JSONUtils.toJson(areaAdDto));
    }

    @Test
    public void testqueryPage(){
        QueryRequestDto requestDto = new QueryRequestDto();
        StringBuilder sb = new StringBuilder("{\"$lte_startDate\":\"2008-06-14\"}");
        requestDto.setQueryJson(sb.toString());
        Response<PageResult<List<MongoTestBeanDto>>> response = testDubboService.queryPage(requestDto);
        PageResult<List<MongoTestBeanDto>> pageResult = response.getResultObject();
        List<MongoTestBeanDto> results = pageResult.getResultObject();
        Assert.assertTrue(results.size()>1);
    }

    @Test
    public void test() throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String str1="2008-4-24";
        java.util.Date date=sdf.parse(str1);
        String str2="2018-4-24";
        java.util.Date date2=sdf.parse(str1);
        System.out.println("riqi:"+date.getTime()+"----"+date2.getTime());
    }


    @Test
    public void query (){
        QueryRequestDto requestDto = new QueryRequestDto();
        requestDto.setPageNum(2);
        requestDto.setPageSize(6);
        String queryJson = "{\"$about_name\":\"tq\",\"age\":"+22+"}";
        requestDto.setQueryJson(queryJson);
        Response<PageResult<List<MongoTestBeanDto>>> response = testDubboService.queryPage(requestDto);
        PageResult<List<MongoTestBeanDto>> listPageResult = response.getResultObject();
        List<MongoTestBeanDto> beanDtos = listPageResult.getResultObject();
        Assert.assertNotNull(beanDtos.get(0));
    }

    @Test
    public void testQueryPageNumberLong(){
        QueryRequestDto requestDto = new QueryRequestDto();
        String queryJson = "{\"endLong\":"+new Long(3)+"}";
        requestDto.setQueryJson(queryJson);
        Response<PageResult<List<MongoTestBeanDto>>> response = testDubboService.queryPage(requestDto);
        PageResult<List<MongoTestBeanDto>> listPageResult = response.getResultObject();
        List<MongoTestBeanDto> beanDtos = listPageResult.getResultObject();
        Assert.assertNotNull(beanDtos.get(0));
    }

    @Test
    public void testQueryPageNumberInteger(){
        QueryRequestDto requestDto = new QueryRequestDto();
        String queryJson = "{\"age\":"+3+"}";
        requestDto.setQueryJson(queryJson);
        Response<PageResult<List<MongoTestBeanDto>>> response = testDubboService.queryPage(requestDto);
        PageResult<List<MongoTestBeanDto>> listPageResult = response.getResultObject();
        List<MongoTestBeanDto> beanDtos = listPageResult.getResultObject();
        Assert.assertNotNull(beanDtos.get(0));
    }

    @Test
    public void testSort(){
        QueryRequestDto requestDto = new QueryRequestDto();
        Map<String, String> sortParam = requestDto.getSortParam();
        if(sortParam == null){
            sortParam = new HashMap<>();
        }
        sortParam.put("age", "DESC");
        requestDto.setSortParam(sortParam);
        Response<PageResult<List<MongoTestBeanDto>>> response = testDubboService.queryPage(requestDto);
        PageResult<List<MongoTestBeanDto>> listPageResult = response.getResultObject();
        List<MongoTestBeanDto> beanDtos = listPageResult.getResultObject();
        Assert.assertNotNull(beanDtos.get(0));
    }
}
