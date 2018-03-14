package com.yatang.sc.order;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.dto.ShippingMethodDto;
import com.yatang.sc.order.domain.ShippingMethod;
import com.yatang.sc.order.dubboservice.ShippingMethodDubboService;
import com.yatang.sc.order.service.ShippingMethodService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
@Service("shippingMethodDubboService")
public class ShippingMethodDubboServiceImpl implements ShippingMethodDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShippingMethodService shippingMethodService;

    @Override
    public Response<List<ShippingMethodDto>> getAllShippingMethod() {

        Response<List<ShippingMethodDto>> response = new Response<List<ShippingMethodDto>>();
        try {
            List<ShippingMethod> list = shippingMethodService.getAllShippingMethod();
            List<ShippingMethodDto> listDto = BeanConvertUtils.convertList(list,ShippingMethodDto.class);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(listDto);
        }catch (Exception e){
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
