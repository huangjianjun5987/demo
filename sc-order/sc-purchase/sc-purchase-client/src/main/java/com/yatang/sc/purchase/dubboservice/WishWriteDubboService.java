package com.yatang.sc.purchase.dubboservice;


import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.wish.SaveWishDto;

public interface WishWriteDubboService {

    Response<Boolean> saveWishLists(SaveWishDto saveWishDto);
}
