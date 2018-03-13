package test.com.yatang.sc.facade;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.pm.*;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptQueryDubboService;
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
public class TestPmPurchaseReceiptQueryDubboService {
    @Autowired
    private PmPurchaseReceiptQueryDubboService pmPurchaseReceiptQueryDubboService;

    @Test
    public void testQueryReceiptByPages(){
        PmPurchaseReceiptParamDto pmPurchaseReceiptParamDto = new PmPurchaseReceiptParamDto();
        pmPurchaseReceiptParamDto.setBranchCompanyId("10007");
        pmPurchaseReceiptParamDto.setPageNum(1);
        pmPurchaseReceiptParamDto.setPageSize(20);
        Response<PageResult<PmPurchaseReceiptDto>> response = pmPurchaseReceiptQueryDubboService.queryReceiptByPages(pmPurchaseReceiptParamDto);
        System.out.println("");
    }

}
