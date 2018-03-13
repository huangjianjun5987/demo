package com.yatang.sc.common.staticvalue;
/**
 * 
 * @描述:业务常量
 * @作者 lvheping
 * @日期 2017年8月31日 下午16:23:11
 */
public class Constants {
    public static final String CURRENT_USER = "sessionInfo";
//    图片类型
    public static final String BASE64_IMAGE_JPEG="data:image/jpeg;";
    public static final String BASE64_IMAGE_X_ICON="data:image/x-icon;";
    public static final String BASE64_IMAGE_GIF="data:image/gif;";
    public static final String BASE64_IMAGE_PNG="data:image/png;";

    public static final   String OLD_SERIAL_NO_KEY = "oldSerialNo";//存放在reids的key
    
    /**供应商编号*/
    public static final   String SUPPLIER_NO = "supplier_no";
    
    /**供应商地点编号*/
    public static final   String SUPPLIER_ADR_NO = "supplier_adr_no";

    /**促销活动编号*/
    public static final   String PROMOTION_NO = "promotion_no";

    /**拒收退款交易流水号*/
    public static final   String RUNFUND_PAYNO = "pay_no";
    
    /**CGTH=采购退货单前缀*/ 
    public static String CGTH = "CGTH"; 
    /**CG=采购单前缀*/ 
    public static String CG = "CG"; 
    /**SPCG=商品采购定价单前缀*/ 
    public static String SPCG = "SPCG"; 
    /**SPXS=商品销售定价单前缀*/ 
    public static String SPXS = "SPXS"; 
    
    /**流程定义key(规则:child:总/子公司;CG:单据类型)*/ 
    //子公司采购流程key
    public static String PROCESS_CG = "CHILD.CG.PRO";
    //子公司采购流程key
    public static String PROCESS_T_CG = "TOTAL.CG.PRO";
    //子公司采购退货流程key
    public static String PROCESS_CGTH = "CHILD.CGTH.PRO";
    //子公司采购退货流程key
    public static String PROCESS_T_CGTH = "TOTAL.CGTH.PRO";
    //子公司采购退货(无采购主管)流程key
    public static String PROCESS_CGTH_2 = "CHILD.CGTH.PRO2";
    //总公司采购退货流程key
    public static String PROCESS_CGTH_3 = "TOTAL.CGTH.PRO";   
    //商品采购定价流程key
    public static String PROCESS_SPCG = "CHILD.SPCG.PRO";   
    //商品销售定价流程key
    public static String PROCESS_SPXS = "CHILD.SPXS.PRO"; 
    //子公司商品定价流程key
    public static String PROCESS_SP = "CHILD.SP.PRO"; 
    //审批通过常量
    public static String PASS ="pass";
    //流程结束常量
    public static String END ="END";
    //流程结束常量
    public static String USER_TASK ="userTask";
    //流程类型常量定义
    public static String PROCESS_TYPE="processType";
    //当前节点审批人
    public static String CURRENT_ASSIGNEE="currentAssignee";
}