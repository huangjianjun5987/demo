package test.com.yatang.sc.facade;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.system.ResourceDto;
import com.yatang.sc.facade.dubboservice.ResourceDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestResourceDubboService {

    @Resource(name = "resourceDubboService1")
    private ResourceDubboService service;

    @Test
    public void testFindOne() {
        Response<ResourceDto>  dtoResponse = service.findOne(22L);
        int i = 0;
    }

    @Test
    public void testQueryAllRes() {
        Response<List<ResourceDto>> response = service.queryAllRes();
        int i = 0;
    }

    @Test
    public void testReadLeftMenuOrButtonByIdAndLoginName() {
        //查询左菜单,管理员权限
        Response<List<ResourceDto>> response1 = service.readLeftMenuOrButtonByIdAndLoginName(0L, "admin");
        //查询左菜单,供应商权限
        Response<List<ResourceDto>> response2 = service.readLeftMenuOrButtonByIdAndLoginName(0L, "zhangsan");
        //查询按钮,供应商权限
        Response<List<ResourceDto>> response3 = service.readLeftMenuOrButtonByIdAndLoginName(23L, "admin");
        //查询按钮,供应商权限
        Response<List<ResourceDto>> response4 = service.readLeftMenuOrButtonByIdAndLoginName(23L, "zhangsan");

        int i = 0;
    }

    @Test
    public void testIsRelatedWithRole() {
        Response<Boolean> response = service.isRelatedWithRole(22L);
        int i = 0;
    }

    @Test
    public void testDeleteRes() {
        List<Long> ids = new ArrayList<>();
        ids.add(45L);
        ids.add(44L);
        Response<Boolean> response = service.deleteRes(ids);
        int i = 0;
    }

    @Test
    public void updateRes() {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(46L);
        resourceDto.setDescription("testttt");
        Response<Boolean> response = service.updateRes(resourceDto);
        int i = 0;
    }

    @Test
    public void createRes() {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setPermissionCode("test");
        resourceDto.setName("test");
        resourceDto.setType(ResourceDto.ResourceType.button);
        resourceDto.setParentId(46L);
        resourceDto.setMenuUrl("I am another url..");
        resourceDto.setDisplayName("test");
        resourceDto.setDescription("testttt");
        Response<Boolean> response = service.createRes(resourceDto);
        int i = 0;
    }

}
