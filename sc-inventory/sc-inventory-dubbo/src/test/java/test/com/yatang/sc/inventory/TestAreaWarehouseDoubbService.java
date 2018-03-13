package test.com.yatang.sc.inventory;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.inventory.dto.AreawarehouseDto;
import com.yatang.sc.inventory.dubboservice.AreaWarehouseQueryDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext-test.xml"})
public class TestAreaWarehouseDoubbService {

    @Autowired
    AreaWarehouseQueryDubboService areaWarehouseQueryDubboService;

    @Test
    public void testQueryAreawarehouseByProvince(){
        Response<AreawarehouseDto> reponse = areaWarehouseQueryDubboService.queryAreawarehouseByProvince("四川");
        System.out.println(JSON.toJSONString(reponse));
    }

}
