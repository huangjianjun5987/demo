package com.yatang.sc.app.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.app.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.app.web.jackson.ImageUrlSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述: 图片信息
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/8 14:26
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class ImageVo implements Serializable{
    private static final long serialVersionUID = 3415214542027396904L;
    private String				id;
    @JsonSerialize(using = ImageUrlSerializer.class)
    @JsonDeserialize(using = ImageUrlDeserializer.class)
    private String				url;
    private String				imageType;
    private String				entityId;

    private Integer sortOrder;
}
