package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Strings;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.FranchiseeConditionDto;
import com.yatang.sc.dto.FranchiseePaymentDto;
import com.yatang.sc.dto.FranchiseeSettlementDto;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.util.excel.ExcelUtil2;
import com.yatang.sc.operation.vo.FranchiseeConditionVo;
import com.yatang.sc.operation.vo.settlement.SupplierSettlementExcelModel;
import com.yatang.sc.operation.vo.settlement.SupplierSettlementExcelView;
import com.yatang.sc.operation.vo.settlement.SupplierSettlementMultQueryVo;
import com.yatang.sc.order.dubboservice.FranchiseeSimpleQueryDubboService;
import com.yatang.sc.settlement.dto.SupplierSettledDto;
import com.yatang.sc.settlement.dto.SupplierSettlementMultQueryDto;
import com.yatang.sc.settlement.dubboservice.SimpleSettlementQueryDubboService;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @描述: 简易结算控制层
 * @作者: tankejia
 * @创建时间: 2017/8/31-9:52 .
 * @版本: 1.0 .
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/settlement")
public class SimpleSettlementAction extends BaseAction {

    private final SimpleSettlementQueryDubboService settlementQueryDubboService;

    private final FranchiseeSimpleQueryDubboService franchiseeSimpleQueryDubboService;


    /**
     * @Description: 导出根据条件查询出来的供应商结算列表
     * @author tankejia
     * @date 2017/8/31- 11:13
     * @param record
     */
    @RequestMapping(value = "/exportSupplierSettlementList", method = RequestMethod.GET)
    public ModelAndView exportSupplierSettlementList(@Validated SupplierSettlementMultQueryVo record){
        log.info("params:{}", record);
        SupplierSettlementMultQueryDto dto = BeanConvertUtils.convert(record, SupplierSettlementMultQueryDto.class);
        Response<List<SupplierSettledDto>> listResponse = settlementQueryDubboService.listSupplierSettlementByMultParam(dto);
        if (null == listResponse || null == listResponse.getResultObject()) {
            throw new RuntimeException("出现不可预计的错误！");
        }

        List<SupplierSettlementExcelModel> models = SupplierSettlementExcelModel.ofList(listResponse.getResultObject());
        SupplierSettlementExcelView excelView = new SupplierSettlementExcelView(models);
        // 如果导出文件名不正确，请用其他浏览器试试
        return new ModelAndView(excelView);
    }


    @RequestMapping(value = "downloadFranchiseeSettlement",method = RequestMethod.GET)
    public void downloadFranchiseeSettlement(@Validated(GroupOne.class) FranchiseeConditionVo conditionVo , HttpServletResponse response){
        log.debug("/downloadFranchiseeSettlement:request param{}", JSONObject.toJSON(conditionVo));
        try{
            List<String> resultCompanyIds = new ArrayList<>();
            LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
            if (null==currentUser){
                log.error("session中用户信息为空");
            }
            if (Strings.isNullOrEmpty(conditionVo.getBranchCompanyId())){
                resultCompanyIds=this.queryUserCompanyIds(currentUser.getUserId());
            }else {
                resultCompanyIds.add(conditionVo.getBranchCompanyId());
            }

            FranchiseeConditionDto conditionDto = BeanConvertUtils.convert(conditionVo,FranchiseeConditionDto.class);
            covertFranchiseeConditionDate(conditionDto,conditionVo);
            conditionDto.setBranchCompanyIds(resultCompanyIds);
            log.info("/downloadFranchiseeSettlement,queryFranchiseeSettlement,param{}", JSON.toJSONString(conditionDto));
            Response<PageResult<FranchiseeSettlementDto>> reponse = franchiseeSimpleQueryDubboService.queryFranchiseeSettlement(conditionDto);
            if(reponse != null && reponse.isSuccess() && !CollectionUtils.isEmpty(reponse.getResultObject().getData())){
                if (!reponse.isSuccess() && reponse.getCode().equals(CommonsEnum.RESPONSE_10023.getCode())) {
                    response.setHeader("Content-type", "text/html;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("<script>alert('导出订单条数大于最大条数【"+reponse.getErrorMessage()+"】,请重新选择条件再导出')</script>");
                    response.getWriter().flush();
                    return;
                }
                ExcelUtil2.listToExcel(reponse.getResultObject().getData(),response);
            }else{
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<script>alert('当前条件下无记录。')</script>");
                response.getWriter().flush();
                return;
            }
        }catch (Exception e){
            log.error("加盟商结算数据导出异常", e);
        }

    }

    @RequestMapping(value = "downloadFranchiseePayment",method = RequestMethod.GET)
    public void downloadFranchiseePayment(@Validated(GroupTwo.class) FranchiseeConditionVo conditionVo , HttpServletResponse response){
        log.debug("/downloadFranchiseePayment:request param{}", JSONObject.toJSON(conditionVo));
        try{
            List<String> resultCompanyIds = new ArrayList<>();
            LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
            if (null==currentUser){
                log.error("session中用户信息为空");
            }
            if (Strings.isNullOrEmpty(conditionVo.getBranchCompanyId())){
                resultCompanyIds=this.queryUserCompanyIds(currentUser.getUserId());
            }else {
                resultCompanyIds.add(conditionVo.getBranchCompanyId());
            }
            FranchiseeConditionDto conditionDto = BeanConvertUtils.convert(conditionVo,FranchiseeConditionDto.class);
            covertFranchiseeConditionDate(conditionDto,conditionVo);
            conditionDto.setBranchCompanyIds(resultCompanyIds);
            log.info("/downloadFranchiseePayment,queryFranchiseePayment,param{}", JSON.toJSONString(conditionDto));
            Response<PageResult<FranchiseePaymentDto>> reponse = franchiseeSimpleQueryDubboService.queryFranchiseePayment(conditionDto);
            if(reponse != null && reponse.isSuccess() && !CollectionUtils.isEmpty(reponse.getResultObject().getData())){
                if (!reponse.isSuccess() && reponse.getCode().equals(CommonsEnum.RESPONSE_10023.getCode())) {
                    response.setHeader("Content-type", "text/html;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("<script>alert('导出订单条数大于最大条数【"+reponse.getErrorMessage()+"】,请重新选择条件再导出')</script>");
                    response.getWriter().flush();
                    return;
                }
                ExcelUtil2.listToExcel(reponse.getResultObject().getData(),response);
            }else{
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<script>alert('当前条件下无记录。')</script>");
                response.getWriter().flush();
                return;
            }
        }catch (Exception e){
            log.error("加盟商付款数据导出异常", e);
        }
    }

    private void covertFranchiseeConditionDate(FranchiseeConditionDto conditionDto,FranchiseeConditionVo conditionVo)throws Exception {
        SimpleDateFormat sb1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sb2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        if(conditionVo.getCompleteDateStart()!=null){
            conditionDto.setCompleteDateStart(sb1.format(conditionVo.getCompleteDateStart()));
        }
        if(conditionVo.getCompleteDateEnd()!=null){
            conditionDto.setCompleteDateEnd(sb2.format(conditionVo.getCompleteDateEnd()));
        }
        if(conditionVo.getPayDateStart()!=null){
            conditionDto.setPayDateStart(sb1.format(conditionVo.getPayDateStart()));
        }
        if(conditionVo.getPayDateEnd()!=null){
            conditionDto.setPayDateEnd(sb2.format(conditionVo.getPayDateEnd()));
        }
        if(conditionVo.getRefundDateStart()!=null){
            conditionDto.setRefundDateStart(sb1.format(conditionVo.getRefundDateStart()));
        }
        if(conditionVo.getRefundDateEnd()!=null){
            conditionDto.setRefundDateEnd(sb2.format(conditionVo.getRefundDateEnd()));
        }
    }


}
