package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Strings;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.WarehouseInfoDto;
import com.yatang.sc.facade.dto.WarehouseLogicDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.WarehouseInfoVo;
import com.yatang.sc.operation.vo.WarehouseLogicQueryVo;
import com.yatang.sc.operation.vo.WarehouseLogicSimpleResultVo;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * <class description>仓库信息not REST服务
 *
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月17日
 */
@RestController
@RequestMapping(value = "/sc/warehouse")
public class WarehouseAction extends BaseAction {
    @Autowired
    public WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    private static final Logger log= LoggerFactory.getLogger(WarehouseAction.class);
    /**
     * <method description>根据条件查询仓库编码和仓库名称
     *
     * @return
     */
    @RequestMapping(value = "/getWarehouseLogicInfo", method = RequestMethod.GET)
    public Response<PageResult<WarehouseLogicSimpleResultVo>> getWarehouseLogicInfo(
            WarehouseLogicQueryVo warehouseLogicQueryVo, HttpServletRequest request) {

        log.info("/getWarehouseLogicInfo,param:{}", JSON.toJSONString(warehouseLogicQueryVo));
        WarehouseLogicDto warehouseLogicDto = new WarehouseLogicDto();
        if (null != warehouseLogicQueryVo && StringUtils.isNotBlank(warehouseLogicQueryVo.getParam())) {
            warehouseLogicDto.setWarehouseCode(warehouseLogicQueryVo.getParam());
            warehouseLogicDto.setWarehouseName(warehouseLogicQueryVo.getParam());
        }
        List<String> resultCompanyIds = new ArrayList<>();
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (null==currentUser){
            log.error("session中用户信息为空");
            return this.getFailResponse();
        }
        if (Strings.isNullOrEmpty(warehouseLogicQueryVo.getBranchCompanyId())){
            resultCompanyIds=this.queryUserCompanyIds(currentUser.getUserId());
        }else {
            resultCompanyIds.add(warehouseLogicQueryVo.getBranchCompanyId());
        }
        warehouseLogicDto.setBranchCompanyIds(resultCompanyIds);
        log.info("/getWarehouseLogicInfo,dubbo,param:{}", JSON.toJSONString(warehouseLogicDto));
        Response<PageResult<WarehouseLogicDto>> result = warehouseLogicQueryDubboService.getWarehouseByOrCondition(
                warehouseLogicDto, warehouseLogicQueryVo.getSupplierAddressId(), warehouseLogicQueryVo.getPageNum(),
                warehouseLogicQueryVo.getPageSize());

        if (result.isSuccess()) {
            if (null != result.getResultObject() && !result.getResultObject().getData().isEmpty()) {
                PageResult<WarehouseLogicSimpleResultVo> pageResult = new PageResult<>();
                pageResult.setTotal(result.getResultObject().getTotal());
                pageResult.setPageNum(result.getResultObject().getPageNum());
                pageResult.setPageSize(result.getResultObject().getPageSize());
                List<WarehouseLogicSimpleResultVo> list = BeanConvertUtils
                        .convertList(result.getResultObject().getData(), WarehouseLogicSimpleResultVo.class);
                pageResult.setData(list);
                return getSuccessResponse(pageResult);
            }
            log.info("确实没有查询到数据。^_^");
            return getSuccessResponse();
        }
        log.info("调用dubbo服务返回false了。(ㄒoㄒ)");
        return getFailResponse();
    }


    /**
     * <method description>根据主键查询仓库信息
     *
     * @param warehouseLogicId
     * @return
     */
    @ParamValid
    @RequestMapping(value = "/getWarehousePhysicalInfo", method = RequestMethod.GET)
    public Response<WarehouseInfoVo> getWarehousePhysicalInfoById(@NotNull Integer warehouseLogicId) {
        Response<WarehouseInfoDto> result = warehouseLogicQueryDubboService
                .getWarehouseInfoByWarehouseLogicId(warehouseLogicId);
        if (result.isSuccess()) {
            WarehouseInfoDto warehousePhysicalDto = result.getResultObject();
            if (null != warehousePhysicalDto) {
                WarehouseInfoVo warehousePhysicalInfoVo = BeanConvertUtils.convert(warehousePhysicalDto,
                        WarehouseInfoVo.class);
                return getSuccessResponse(warehousePhysicalInfoVo);
            }
            log.info("确实没有查询到数据。^_^");
            return getSuccessResponse();

        }
        log.info("调用dubbo服务返回false了。(ㄒoㄒ)");
        return getFailResponse();
    }

}
