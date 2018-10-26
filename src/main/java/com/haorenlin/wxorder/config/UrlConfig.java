package com.haorenlin.wxorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Yan Junlin
 * @Date 2018/10/24 23:48
 * @Description (路径配置)
 **/
@Component
@Data
@ConfigurationProperties("url-config")
public class UrlConfig {
    /**
     * 微信公众平台授权url
     */
    public String wechatMpAuthorize;

    /**
     * 微信开放平台授权url
     */
    public String wechatOpenAuthorize;

    /**
     * 点餐系统
     */
    public String sell;
}