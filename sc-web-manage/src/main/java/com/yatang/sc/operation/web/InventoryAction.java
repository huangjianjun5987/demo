package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Strings;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.dto.ItemInventoryPageQueryParamDto;
import com.yatang.sc.inventory.dto.im.InventoryBIDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.im.InventoryBIExcelModel;
import com.yatang.sc.operation.vo.im.InventoryBIVo;
import com.yatang.sc.operation.vo.im.InventoryExcelView;
import com.yatang.sc.operation.vo.im.ItemInventoryPageQueryParamVo;
import com.yatang.sc.operation.vo.prod.BranchCompanyInfoVo;
import com.yatang.xc.mbd.biz.system.dto.UserTypeCompanyDTO;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @描述: 库存action类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/31 下午12:28
 * @版本: v1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/sc/inventory")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryAction extends BaseAction{

    private final ItemLocInventoryDubboService queryDubboService;//库存查看


    /**
     * 分页查询库存信息
     *
     * @param pageQueryParam
     * @return
     */
    @RequestMapping(value = "/queryInventoryBIByPageQueryParam", method = RequestMethod.GET)
    public Response<PageResult<InventoryBIVo>> queryInventoryBIByPageQueryParam(ItemInventoryPageQueryParamVo pageQueryParam,HttpServletRequest req) {

        log.info("/queryInventoryBIByPageQueryParam,param{}",JSON.toJSONString(pageQueryParam));
        Response<PageResult<InventoryBIVo>> pageResultResponse = new Response<>();
        ItemInventoryPageQueryParamDto pageQueryParamDto = BeanConvertUtils.convert(pageQueryParam, ItemInventoryPageQueryParamDto.class);
        List<String> resultCompanyIds = new ArrayList<>();
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if(!StringUtils.isBlank(pageQueryParam.getBranchCompanyId())){
            resultCompanyIds.add(pageQueryParam.getBranchCompanyId());
        }else{
            resultCompanyIds = queryUserCompanyIds(currentUser.getUserId());
        }
        pageQueryParamDto.setBranchCompanyIds(resultCompanyIds);
        log.info("action---分页查询库存信息参数>>,dubbo,pageQueryParam:{}", JSON.toJSONString(pageQueryParamDto));
        //vo2Dto
        Response<PageResult<InventoryBIDto>> resultResponse = queryDubboService.queryInventoryBIByPageQueryParam(pageQueryParamDto);
//        log.info("action---分页查询库存信息结果返回>>resultResponse:{}", JSON.toJSONString(resultResponse));

        //错误的响应
        if (!CommonsEnum.RESPONSE_200.getCode().equals(resultResponse.getCode())) {
            return BeanConvertUtils.convert(resultResponse, Response.class);
        }

        PageResult<InventoryBIDto> resultObject = resultResponse.getResultObject();
        //dto2vo
        List<InventoryBIVo> itemLocSohVos = BeanConvertUtils.convertList(resultObject.getData(), InventoryBIVo.class);
        PageResult<InventoryBIVo> pageResult = new PageResult<>();
        pageResult.setData(itemLocSohVos);
        pageResult.setPageNum(resultObject.getPageNum());
        pageResult.setPageSize(resultObject.getPageSize());
        pageResult.setTotal(resultObject.getTotal());
        pageResultResponse.setResultObject(pageResult);
        pageResultResponse.setErrorMessage(resultResponse.getErrorMessage());
        pageResultResponse.setSuccess(resultResponse.isSuccess());
        return pageResultResponse;

    }


    /**
     * 根据查询条件导出库存信息
     *
     * @param pageQueryParam
     * @return
     */
    @RequestMapping(value = "excelInventoryBIRecords", method = RequestMethod.GET)
    public ModelAndView excelInventoryBIRecords(ItemInventoryPageQueryParamVo pageQueryParam,HttpServletRequest request) {
        log.info("action--excelInventoryRecords--导出库存报表--查询条件>>pageQueryParam:{}", pageQueryParam);

        //只能查询当前用户子公司的销售关系
        LoginInfoVO	userInfo = (LoginInfoVO)request.getSession().getAttribute(CURRENT_USER);
        List<String> branchCompanyIds=new ArrayList<>();
        if (null==userInfo){
            return null;
        }
        if (!Strings.isNullOrEmpty(pageQueryParam.getBranchCompanyId())){
            branchCompanyIds.add(pageQueryParam.getBranchCompanyId());
        }else {
            branchCompanyIds=this.queryUserCompanyIds(userInfo.getUserId());
        }


        pageQueryParam.setPageNum(1);//重置报表
        pageQueryParam.setPageSize(Integer.MAX_VALUE);
        ItemInventoryPageQueryParamDto pageQueryParamDto = BeanConvertUtils.convert(pageQueryParam, ItemInventoryPageQueryParamDto.class);
        //vo2Dto
        pageQueryParamDto.setBranchCompanyIds(branchCompanyIds);
        Response<PageResult<InventoryBIDto>> resultResponse = queryDubboService.queryInventoryBIByPageQueryParam(pageQueryParamDto);
        log.info("action---导出库存报表库存信息结果返回>>resultResponse:{}", JSON.toJSONString(resultResponse));
        List<InventoryBIExcelModel> models = InventoryBIExcelModel.of(resultResponse.getResultObject().getData());
        InventoryExcelView excelView = new InventoryExcelView(models);
        //如果导出文件名不正确，请用其他浏览器试试
        return new ModelAndView(excelView);
    }

}



