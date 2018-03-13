package com.yatang.sc.inventory.dubboservice.flow;

import java.util.List;

import com.yatang.sc.inventory.dto.TranDataDto;

/**
 * Created by xiangyonghong on 2017/7/31.
 */

public interface TranDataDubboFlow {


    void addTranData(TranDataDto tranDataDto);
    void addTranDataList(List<TranDataDto> tranDataDtoList);
}
