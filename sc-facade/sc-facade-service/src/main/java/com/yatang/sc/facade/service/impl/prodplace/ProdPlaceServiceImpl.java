package com.yatang.sc.facade.service.impl.prodplace;

import com.busi.common.datatable.PageResult;
import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.facade.domain.prodplace.ProdPlacePo;
import com.yatang.sc.facade.domain.prodplace.ProdPlaceUpdatePo;
import com.yatang.sc.facade.mongo.CommonQueryRequest;
import com.yatang.sc.facade.mongo.MongoQueryTemplate;
import com.yatang.sc.facade.service.prodplace.ProdPlaceService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
*@Author tangqi
*@Date 2018/1/11 15:07
*@Desc 商品地点关系管理
*/
@Service("prodPlaceService")
public class ProdPlaceServiceImpl implements ProdPlaceService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoQueryTemplate mongoQueryTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addProdPlace(List<ProdPlacePo> poList) {
        logger.info("添加不重复的商品地点关系：{}", JSONUtils.toJson(poList));
        mongoQueryTemplate.bulkInsert(JSONUtils.toJson(poList), ProdPlacePo.class);
    }

    @Override
    public PageResult<List<ProdPlacePo>> queryPage(CommonQueryRequest commonQueryRequest) {
        return mongoQueryTemplate.queryPage(commonQueryRequest, ProdPlacePo.class);
    }

    @Override
    public PageResult<List<ProdPlacePo>> distinct(List<ProdPlacePo> poList, int pageNum, int pageSize){
        PageResult pageResult = new PageResult();
        if(CollectionUtils.isEmpty(poList)){
            throw new RuntimeException("请选择有效的商品");
        }
        List<String> ids = new ArrayList<>();
        for(ProdPlacePo po : poList){
            ids.add(po.getId());
        }
        Query query = new Query();
        query.limit(pageSize);
        query.skip((pageNum - 1) * pageSize);
        query.addCriteria(Criteria.where("_id").in(ids).andOperator(Criteria.where("branchCompanyId").is(poList.get(0).getBranchCompanyId())));
        long count = mongoTemplate.count(query, ProdPlacePo.class);
        List<ProdPlacePo> prodPlacePos = mongoTemplate.find(query, ProdPlacePo.class);
        pageResult.setRecordsTotal(Integer.parseInt(new Long(count).toString()));
        pageResult.setResultObject(prodPlacePos);
        return pageResult;
    }

    @Override
    public void update(ProdPlaceUpdatePo po) {
        ProdPlacePo prodPlacePo = queryDetail(po.getId());
        prodPlacePo.setSupplierId(po.getSupplierId());
        prodPlacePo.setSupplierCode(po.getSupplierCode());
        prodPlacePo.setSupplierName(po.getSupplierName());
        prodPlacePo.setAdrSupId(po.getAdrSupId());
        prodPlacePo.setAdrSupCode(po.getAdrSupCode());
        prodPlacePo.setAdrSupName(po.getAdrSupName());
        prodPlacePo.setLogisticsModel(po.getLogisticsModel());
        prodPlacePo.setLastModifyDate(po.getLastModifyDate());
        prodPlacePo.setLastModifyUserId(po.getLastModifyUserId());
        mongoQueryTemplate.update(prodPlacePo.getId(), JSONUtils.toJson(prodPlacePo), ProdPlacePo.class);
    }

    @Override
    public ProdPlacePo queryDetail(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, ProdPlacePo.class);
    }

    @Override
    public void bulkDelete(List<String> ids) {
        mongoQueryTemplate.bulkDelete(JSONUtils.toJson(ids), ProdPlacePo.class);
    }
}
