package com.yatang.sc.inventory.dubboservice;

import java.util.List;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.dto.TranDataDto;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
public interface TranDataDubboService {

    /**
     * 新增TranData
     * @param tranDataDto
     * @return
     */
    Response<Boolean> addTranData(TranDataDto tranDataDto);
    /**
     * 新增TranData
     * @author zhoubaiyun
     * @param tranDataDtoList
     * @return
     */
    Response<Boolean> addTranDataList(List<TranDataDto> tranDataDtoList);
}
