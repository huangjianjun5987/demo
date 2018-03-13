package com.yatang.sc.inventory.dubboservice.flow.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.inventory.domain.TranData;
import com.yatang.sc.inventory.dto.TranDataDto;
import com.yatang.sc.inventory.dubboservice.flow.TranDataDubboFlow;
import com.yatang.sc.inventory.service.TranDataService;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
@Service
public class TranDataDubboFlowImpl implements TranDataDubboFlow{

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TranDataService tranDataService;
    @Autowired
    private ProductScIndexDubboService productScIndexDubboService;


    @Transactional
    public void addTranData(TranDataDto tranDataDto) {
        TranData tranData = BeanConvertUtils.convert(tranDataDto,TranData.class);
        tranDataService.save(tranData);
    }


	@Override
	public void addTranDataList(List<TranDataDto> tranDataDtoList) {
		 List<TranData> tranDataList = BeanConvertUtils.convertList(tranDataDtoList,TranData.class);
	     tranDataService.saveList(tranDataList);
		
	}



}
