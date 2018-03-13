package com.yatang.sc.facade.dto.pm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>@description:小票上传</p>
 *
 * @author yangshuang
 * @version v1.0
 * @date 2018/1/17 11:44
 */
@Getter
@Setter
public class PmUploadTicketParamDto implements Serializable {
    private static final long serialVersionUID = -2509621784400075282L;



    private Long pmReceiptId;


    private String ticketUrl;


    private Integer status;

    private String modifyUserId;

}
