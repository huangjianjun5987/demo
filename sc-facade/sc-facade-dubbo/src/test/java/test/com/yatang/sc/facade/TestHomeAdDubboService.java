package test.com.yatang.sc.facade;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.HomeAreaAdDto;
import com.yatang.sc.facade.dto.QueryHomePageAdDto;
import com.yatang.sc.facade.dubboservice.HomeAdQueryDubboService;
import com.yatang.sc.facade.dubboservice.HomeAdWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @描述: 首页配置读写测试类
 * @作者: liuxiaokun
 * @创建时间: 2017年12月25日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestHomeAdDubboService {
	@Autowired
	private HomeAdQueryDubboService homeAdQueryDubboService;
	@Autowired
	private HomeAdWriteDubboService homeAdWriteDubboService;

	@Test
	public void testQueryAreas(){
		QueryHomePageAdDto queryHomePageAdDto = new QueryHomePageAdDto();
		queryHomePageAdDto.setCompanyId("test");
		queryHomePageAdDto.setHomePageType(0);
		Response<List<HomeAreaAdDto>> result = homeAdQueryDubboService.queryAreas(queryHomePageAdDto);
		System.out.println("");
	}
}
