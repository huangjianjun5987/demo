package test.com.yatang.sc.facade;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.WarehouseLogicDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
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
 * @创建时间: 2017/10/11 14:35
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestWarehouseLogicQueryDubboService {
    @Autowired
    private WarehouseLogicQueryDubboService service;

    @Test
    public void testQueryWarehouseLogicList() {
        Response<List<WarehouseLogicDto>> warehouseLogic = service.queryWarehouseLogicList();
        List<WarehouseLogicDto> warehouseLogicDtos = warehouseLogic.getResultObject();
        System.out.println("========="+warehouseLogicDtos.toString());
    }
}
