package test.com.yatang.sc.facade;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.QueryStorePermissionPo;
import com.yatang.sc.facade.dto.QueryStorePermissionDto;
import com.yatang.sc.facade.dto.StorePermissionDto;
import com.yatang.sc.facade.dubboservice.StorePermissionDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @描述: 查询门店权限测试类
 * @作者: baiyun
 * @创建时间: 2017年8月9日-下午16:30:20 .
 * @版本: 1.0 .
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestStorePermissionDubboService {
    @Autowired
    private StorePermissionDubboService storePermissionDubboService;

    @Test
    public void test(){
        Response<StorePermissionDto> storePermissionDtoResponse=storePermissionDubboService.queryStorePermissionByStoreId("1");
        if (storePermissionDtoResponse.getResultObject() != null){
            System.out.println(storePermissionDtoResponse.getResultObject().isAccessible());
        }
    }

    @Test
    public void testPage(){
        QueryStorePermissionDto queryStorePermissionDto = new QueryStorePermissionDto();
        Response<PageResult<StorePermissionDto>> res = storePermissionDubboService.queryStorePermissionPage(queryStorePermissionDto);
        System.out.println(JSON.toJSONString(res));
    }

    @Test
    public void insertStorePermission(){
        StorePermissionDto storePermissionDto = new StorePermissionDto();
        storePermissionDto.setStoreId("test");
        storePermissionDto.setAccessibled(0);
        Response<Boolean> res = storePermissionDubboService.insertStorePermission(storePermissionDto);
        System.out.println(JSON.toJSONString(res));
    }

    @Test
    public void updateStorePermission(){
        Response<StorePermissionDto> res = storePermissionDubboService.updateStorePermission(208);
        System.out.println(JSON.toJSONString(res));
    }

    @Test
    public void updateStorePermission1(){
        StorePermissionDto storePermissionDto = new StorePermissionDto();
        storePermissionDto.setStoreId("test1");
        storePermissionDto.setId(210);
        Response<Boolean> res = storePermissionDubboService.updateStorePermission(storePermissionDto);
        System.out.println(JSON.toJSONString(res));
    }

    @Test
    public void deleteStorePermission(){
        Response<Boolean> res = storePermissionDubboService.deleteStorePermission(210);
        System.out.println(JSON.toJSONString(res));
    }
}
