package com.yatang.sc.purchase.dubboservice.util;

import com.busi.idgenerator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiangyonghong on 2017/7/13.
 */
@Service("idGeneratorUtil")
public class IdGeneratorUtil {

    @Autowired
    private IdGenerator idGenerator;

    public String getIdGeneratorId(String key){

        return idGenerator.generateId(key);
    }

    public String generateScSaleOrderId(String branchCompanyId, String date){
        return idGenerator.generateScSaleOrderId(branchCompanyId,date);
    }

}
