package com.yatang.sc.xinyi.dto.orderNotice;

import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 心怡订单状态Dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/26 10:29
 * @版本: v1.0
 */
@Getter
@Setter
@XStreamAliasType("process")
public class XinyiOrderProcessInfoDto implements Serializable {


    private static final long serialVersionUID = 9206851306674307750L;
    private String processStatus;//单据状态 单据状态，string (50) , ACCEPT=仓库接单, PARTFULFILLED-部分收货完成,  FULFILLED-收货完成, PRINT = 打印,  PICK=捡货,  CHECK = 复核,  PACKAGE= 打包,  WEIGH= 称重, READY=待提货， DELIVERED=已发货,  REFUSE=买家拒签，EXCEPTION =异常 ，CLOSED= 关闭,  CANCELED= 取消,  REJECT=仓库拒单，SIGN=签收，TMSCANCELED=快递拦截，OTHER=其他，PARTDELIVERED=部分发货完成， TMSCANCELFAILED=快递拦截失败, 必填 (只传英文编码)


    private String operatorCode;//当前状态操作员编码

    private String operatorName;  //当前状态操作员姓名

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date operateTime;//当前状态操作时间

    private String operateInfo;//操作内容

    private String remark;//备注

}
