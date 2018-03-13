package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryDto;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryResponseDto;
import com.yatang.sc.kidd.dto.product.ProductSynchronizeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ResultEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("kiddProductService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ResultEventPublisher publisher;

    public Response<String> synchronizeProduct(ProductSynchronizeDto productSynchronizeDto) {
        List<Response<String>> results = (List<Response<String>>) publisher.publishEvent(productSynchronizeDto, true);

        Response<String> response = new Response<>();
        // 如果有错误则返回错误
        StringBuffer errors = new StringBuffer();
        for (Response<String> result : results) {
            if (!result.isSuccess()) {
                errors.append("[" + result.getErrorMessage() + "]");
            }
        }

        String errorMsg = errors.toString();
        if (StringUtils.isNotEmpty(errorMsg)) {
            response.setSuccess(false);
            response.setErrorMessage(errorMsg);
        } else {
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public Response<KiddInventoryQueryResponseDto> inventoryQuery(KiddInventoryQueryDto kiddInventoryQueryDto) {
        return (Response<KiddInventoryQueryResponseDto>) publisher.publishEvent(kiddInventoryQueryDto);
    }

}


