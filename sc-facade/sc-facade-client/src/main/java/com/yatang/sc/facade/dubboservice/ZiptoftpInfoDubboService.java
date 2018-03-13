package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ZiptoftpInfoDto;

/**
 * 
 * <class description>ZIP文件上传信息
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月7日
 */
public interface ZiptoftpInfoDubboService {

	Response<Integer> insert(ZiptoftpInfoDto ziptoftpInfoDto);



	Response<ZiptoftpInfoDto> selectByPrimaryKey(Integer id);



	Response<Boolean> deleteByPrimaryKey(Integer id);



	Response<PageResult<ZiptoftpInfoDto>> selectZiptoftpInfoList(ZiptoftpInfoDto ziptoftpInfoDto, Integer pageNum,
			Integer pageSize);



	Response<Integer> updateIfAbsent(ZiptoftpInfoDto ziptoftpInfoDto);

}