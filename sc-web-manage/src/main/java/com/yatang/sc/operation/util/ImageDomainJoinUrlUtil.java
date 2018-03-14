package com.yatang.sc.operation.util;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/11 17:56
 * @版本: v1.0
 */

public class ImageDomainJoinUrlUtil {

    @Getter
    @Setter
    private String imageViewDomain;//图片服务器地址

    public String getImageUrl(String subffixUrl) {
        return new StringBuilder().append(imageViewDomain).append("/").append(subffixUrl).toString();
    }

    /**
     * 截取相对路径
     *
     * @param absolutePath 绝对路径
     * @return
     */
    public String getRelativePath(String absolutePath) {

        if (StringUtils.isNotEmpty(absolutePath)) {//非空校验
            String[] split = absolutePath.split(imageViewDomain +"/");//正则匹配
            if (split.length == 2) {
                return split[1];
            } else {
                return null;
            }
        }
        return null;
    }

/*    public static void main(String[] args) {
        String url = "http://sit.image.com/group1/M00/01/32/rB4KPFmOcUuAGzkzAAABPkImKz8523.png";
        System.out.println(new ImageDomainJoinUrlUtil().getRelativePath(url));
    }*/


}
