package test.com.yatang.sc.purchase;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.returned.ReturnRequestDetailDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestListDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestQueryParamDto;
import com.yatang.sc.purchase.dubboservice.ReturnRequestQueryDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @描述: APP退换货测试类
 * @类名: TestQueryReturnRequestService
 * @作者: kangdong
 * @创建时间: 2017/11/7 11:18
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestQueryReturnRequestService {

    @Autowired
    private ReturnRequestQueryDubboService queryDubboService;

    @Test
    public void queryReturnRequestList() {
        ReturnRequestQueryParamDto paramDto = new ReturnRequestQueryParamDto();
        paramDto.setPageNum(1);
        paramDto.setPageSize(2);
        paramDto.setProfileId("jms_000521");
        Response<PageResult<ReturnRequestListDto>> resultResponse=  queryDubboService.queryReturnRequestList( paramDto );
        PageResult<ReturnRequestListDto> result = resultResponse.getResultObject();
        List<ReturnRequestListDto> requestListDto = result.getData();
        for(ReturnRequestListDto list:requestListDto) {
            System.out.print("========"+list.getId()+"===="+list.getOrderId()+"==="+list.getStateDetail());
        }
    }

    @Test
    public void returnRequestDetail() {
        String id= "100002017101100015H01";
        String userId = "jms_000521";
        Response<ReturnRequestDetailDto> resultResponse = queryDubboService.queryReturnRequestDetailById(id,userId);
        ReturnRequestDetailDto requestListDto = resultResponse.getResultObject();
        System.out.println("========" + requestListDto.getId() + "====" + requestListDto.getCreationTime() + "===" + requestListDto.getStateDetail());
    }

}
