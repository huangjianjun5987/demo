package test.com.yatang.sc.purchase;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.wish.*;
import com.yatang.sc.purchase.dubboservice.WishQueryDubboService;
import com.yatang.sc.purchase.dubboservice.WishWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestWishDubboService {


    @Autowired
    WishQueryDubboService wishQueryDubboService;

    @Autowired
    WishWriteDubboService wishWriteDubboService;

    @Test
    public void testScanCode(){
        ScanCodeDto scanCodeDto = new ScanCodeDto();
        scanCodeDto.setBarCode("6948195860136");
        scanCodeDto.setBranchCompanyId("10000");
        scanCodeDto.setWareHouseCode("YT901");
        scanCodeDto.setUserId("jms_000541");
        Response<WishListsDto> response = wishQueryDubboService.scanCode(scanCodeDto);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testWishLists(){
        WishListConditionDto wishListConditionDto = new WishListConditionDto();
        wishListConditionDto.setFranchiserId("jms_000541");
        Response<PageResult<WishListsDto>> response = wishQueryDubboService.wishLists(wishListConditionDto);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testWishRealizedList(){
        WishListConditionDto wishListConditionDto = new WishListConditionDto();
        wishListConditionDto.setBranchCompanyId("10000");
        wishListConditionDto.setFranchiserId("jms_001217");
        wishListConditionDto.setPage(1);
        wishListConditionDto.setPageSize(100);
        Response<PageResult<WishListsDto>> response = wishQueryDubboService.wishRealizedList(wishListConditionDto);
        System.out.println("response:"+JSON.toJSONString(response));
    }

    @Test
    public void testSaveWishLists(){
        SaveWishDto saveWishDto = new SaveWishDto();
        saveWishDto.setStoreId("A900003");
        saveWishDto.setStoreName("test");
        saveWishDto.setBranchCompanyId("10000");
        saveWishDto.setFranchiserId("jms_000541");
        List<SaveWishListsDto> saveWishListsDtoList = new ArrayList<>();
        SaveWishListsDto saveWishListsDto = new SaveWishListsDto();
        saveWishListsDto.setBarCode("6948195860136");
        saveWishListsDto.setQuantity(10);
        saveWishListsDtoList.add(saveWishListsDto);
        saveWishDto.setSaveWishListsDtoList(saveWishListsDtoList);
        Response<Boolean> response = wishWriteDubboService.saveWishLists(saveWishDto);
        System.out.println(JSON.toJSONString(response));
    }

}
