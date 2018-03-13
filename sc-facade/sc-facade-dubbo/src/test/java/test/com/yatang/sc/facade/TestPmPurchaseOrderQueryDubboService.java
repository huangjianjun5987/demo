package test.com.yatang.sc.facade;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderInfoDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseQueryParamDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderQueryDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @描述:
 * @类名:
 * @作者: 柳晓坤
 * @创建时间: 2017/12/1
 * @版本: v1.0
 */@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestPmPurchaseOrderQueryDubboService {
    @Autowired
    private PmPurchaseOrderQueryDubboService pmPurchaseOrderQueryDubboService;

    /**
     * 根据传入参数查询采购单管理列表，包括统采至子公司的采购单
     */
    @Test
    public void testQueryPurchaseOrderList(){
        PmPurchaseQueryParamDto pmPurchaseQueryParamDto = new PmPurchaseQueryParamDto();
        pmPurchaseQueryParamDto.setBranchCompanyId("10000");
        pmPurchaseQueryParamDto.setPageNum(1);
        pmPurchaseQueryParamDto.setPageSize(20);
        Response<PageResult<PmPurchaseOrderDto>> response = pmPurchaseOrderQueryDubboService.queryPurchaseOrderList(pmPurchaseQueryParamDto);
        System.out.println(JSON.toJSONString(response));
    }

    /**
     * 根据条件查询采购单打印管理列表详细信息，包括统采至子公司的采购单打印列表
     */
//    @Test
    public void testQueryPurchaseOrderListInfo(){
        PmPurchaseQueryParamDto pmPurchaseQueryParamDto = new PmPurchaseQueryParamDto();
        pmPurchaseQueryParamDto.setBranchCompanyId("10007");
        pmPurchaseQueryParamDto.setPageNum(1);
        pmPurchaseQueryParamDto.setPageSize(20);
        Response<PageResult<PmPurchaseOrderInfoDto>> response = pmPurchaseOrderQueryDubboService.queryPurchaseOrderListInfo(pmPurchaseQueryParamDto);
        System.out.println("");
    }

    @Test
    public void testGetPurchaseOrderInfoById(){
        Response<PmPurchaseOrderInfoDto> response = pmPurchaseOrderQueryDubboService.getPurchaseOrderInfoById(396L);
        System.out.println("");
    }


}
