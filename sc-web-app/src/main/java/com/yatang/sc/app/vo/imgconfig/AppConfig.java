package com.yatang.sc.app.vo.imgconfig;

/**
 * @描述:
 * @类名:
 * @作者: baiyun
 * @创建时间: 2017/8/14 10:00
 * @版本: v1.0
 */
public class AppConfig {

    private  String	 imageServerUrl;

    public  String getAppImageUrl(String subffixUrl) {
        if (subffixUrl == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if(subffixUrl.startsWith("http:") || subffixUrl.startsWith("https:")){
            sb.append(subffixUrl);
        }else{
            sb.append(imageServerUrl).append("/").append(subffixUrl);
        }
        return sb.toString();
    }

    public String getImageServerUrl() {
        return imageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.imageServerUrl = imageServerUrl;
    }
}
