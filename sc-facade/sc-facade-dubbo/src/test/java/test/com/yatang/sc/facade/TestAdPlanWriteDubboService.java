package test.com.yatang.sc.facade;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.AdPlanDto;
import com.yatang.sc.facade.dubboservice.AdPlanWriteDubboService;

/**
 * @描述: 404广告配置测试用例
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 13:36
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestAdPlanWriteDubboService {
    @Autowired
	private  AdPlanWriteDubboService adPlanWriteDubboService;


    /**
     * 添加广告配置测试用例
     */
	@Test
	public void TestAddAdPlan() {
        AdPlanDto adPlanDto = new AdPlanDto();
        adPlanDto.setPlanName("方案二");
        adPlanDto.setPicName1("图片1");
        adPlanDto.setLinkUrl1("链接地址1");
        adPlanDto.setPicUrl1("图片1存储路径");
        adPlanDto.setPicName2("图片2");
        adPlanDto.setLinkUrl2("链接地址2");
        adPlanDto.setPicUrl2("图片2存储路径");
        adPlanDto.setCreateTime(new Date());
        adPlanDto.setStatus(0);//默认停用
        Response<Void> response = adPlanWriteDubboService.addAdPlan(adPlanDto);

    }

    /**
     *删除广告方案的测试用例(永久删除)
     */
    @Test
    public void TestDeleteAdPlanById(){
        Response<Void> response = adPlanWriteDubboService.deleteAdPlanById(1);

    }

    /**
     *改变方案的启动状态
     */
    @Test
    public void TestChangeAdPlanState(){
       // Response<Void> response = adPlanWriteDubboService.changeAdPlanState(modifyParamDto);
       // System.out.println(response.getErrorMessage());
    }
    /**
     *更新广告方案
     */
    @Test
    public void TestUpdateAdPlan(){
        AdPlanDto adPlanDto = new AdPlanDto();
        adPlanDto.setId(3);
        adPlanDto.setPlanName("方案二");
        adPlanDto.setPicName1("图片1");
        adPlanDto.setLinkUrl1("链接地址111");
        adPlanDto.setPicUrl1("图片1存储路径");
        adPlanDto.setPicName2("图片2");
        adPlanDto.setLinkUrl2("链接地址2");
        adPlanDto.setPicUrl2("图片2存储路径");
        adPlanDto.setCreateTime(new Date());
        adPlanDto.setStatus(0);//默认停用
        Response<Void> response = adPlanWriteDubboService.updateAdPlan(adPlanDto);
        System.out.println(response.getErrorMessage());
    }


}
