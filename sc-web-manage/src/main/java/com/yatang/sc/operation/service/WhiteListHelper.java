package com.yatang.sc.operation.service;

import com.busi.common.resp.Response;
import com.yatang.sc.operation.vo.ParamScPurchaseVo;
import com.yatang.xc.mbd.biz.org.sc.dto.StorePurcaseResultDto;

import java.util.List;

public interface WhiteListHelper {
    Response<List<StorePurcaseResultDto>> whiteListBatchImport(List<ParamScPurchaseVo> paramScPurchaseVos);
}
