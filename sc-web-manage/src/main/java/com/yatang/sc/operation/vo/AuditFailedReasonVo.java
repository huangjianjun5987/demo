package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:审核失败原因接收类
 * @类名:AuditFailedReasonVo
 * @作者: lvheping
 * @创建时间: 2017/6/14 21:26
 * @版本: v1.0
 */
@Getter
@Setter
public class AuditFailedReasonVo implements Serializable {
	private static final long	serialVersionUID	= -1298473240705517519L;
	private String				auditUser;									// 审核人
	private Date				auditTime;									// 审核时间
	private String				failedReason;								// 审核失败原因
}
