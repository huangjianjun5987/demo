/**
 * @(#) SingleUploadResult.java 1.0 2016-1-20 Ellison
 * <p>
 * Copyright (c) 2016 HCCN. All Rights Reserved.
 */
package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

/**
 * @描述: 上传文件返回的文件存储路径
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/5/20 16:17
 * @版本: v1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleUploadResult implements Serializable {

    private static final long serialVersionUID = 495049648054176651L;

    private String suffixUrl;//相对地址

    private String				fileOnServerUrl;//绝对地址

    private String imageDomain;//图片服务器地址

}
