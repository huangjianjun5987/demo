package test.com.yatang.sc.wish;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.google.common.collect.Lists;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.WishDetailDto;
import com.yatang.sc.dto.WishListDto;
import com.yatang.sc.dto.WishListParamDto;
import com.yatang.sc.wish.dubboService.WishBackQueryDubboService;
import com.yatang.sc.wish.dubboService.WishBackWriteDubboService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: 测试心愿单 dubbo接口
 * @author: yinyuxin
 * @date: 2018/1/4 20:06
 * @version: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestWishDubboService {

	@Autowired
	private WishBackQueryDubboService wishBackQueryDubboService;

	@Autowired
	private WishBackWriteDubboService wishBackWriteDubboService;

	@Test
	public void testQueryWishListsByParam(){
		WishListParamDto wishListParamDto=new WishListParamDto();
		wishListParamDto.setBranchCompanyIds(Lists.<String>newArrayList("10000"));
		wishListParamDto.setCreateTimeStart(new DateTime(2018,1,1,20,45).toDate());
		wishListParamDto.setCreateTimeEnd(new DateTime(2018,1,5,20,45).toDate());
		Response<PageResult<WishListDto>> response = wishBackQueryDubboService.queryWishListsByParam(wishListParamDto);
		System.out.println("----"+ JSONObject.toJSONString(response));
	}

	@Test
	public void testQueryWishDetailsByWishListIdAndStoreId(){
		WishListParamDto wishListParamDto=new WishListParamDto();
		wishListParamDto.setWishListId(1L);
		Response<PageResult<WishDetailDto>> response = wishBackQueryDubboService.queryWishDetailsByWishListIdAndStoreId(wishListParamDto);
		System.out.println("----"+ JSONObject.toJSONString(response));
	}

	@Test
	public void testComleteOrCloseWishList(){
		wishBackWriteDubboService.comleteOrCloseWishList(1L,"complete");
	}
}
