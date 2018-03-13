package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.ServiceCommitmentsPo;
import com.yatang.sc.facade.dto.ServiceCommitmentsDto;
import com.yatang.sc.facade.dubboservice.ServiceCommitmentsQueryDubboService;
import com.yatang.sc.facade.service.ServiceCommitmentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 服务承诺QueryDubboService实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/8 9:18
 * @版本: v1.0
 */
@Slf4j
@Service("serviceCommitmentsQueryDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceCommitmentsQueryDubboServiceImpl implements ServiceCommitmentsQueryDubboService {

    private final ServiceCommitmentsService serviceCommitmentsService;
  //  private final
    @Override
    public Response<List<ServiceCommitmentsDto>> queryAllServiceCommitmentsList() {
        Response<List<ServiceCommitmentsDto>> response=new  Response<>();
        try {
            List<ServiceCommitmentsPo> poList= serviceCommitmentsService.queryAllServiceCommitmentsList();
            if (poList.size()==0) {
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setSuccess(false);
                return response;
            }
            //po2dto
            List<ServiceCommitmentsDto> dtoList = BeanConvertUtils.convertList(poList, ServiceCommitmentsDto.class);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
