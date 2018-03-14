package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.wish.ScanCodeDto;
import com.yatang.sc.purchase.dto.wish.WishListConditionDto;
import com.yatang.sc.purchase.dto.wish.WishListsDto;

public interface WishQueryDubboService {

    Response<WishListsDto> scanCode(ScanCodeDto scanCodeDto);

    Response<PageResult<WishListsDto>> wishLists(WishListConditionDto wishListConditionDto);

    Response<PageResult<WishListsDto>> wishRealizedList(WishListConditionDto wishListConditionDto);
}
