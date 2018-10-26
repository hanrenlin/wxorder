package com.haorenlin.wxorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Yan Junlin
 * @Date 2018/10/24 23:34
 * @Description (公众号账户)
 **/
@Component
@ConfigurationProperties("wechat")
@Data
public class WechatAccountConfig {
    /**
     * 公众平台id
     */
    private String mpAppId;

    /**
     * 公众平台密钥
     */
    private String mpAppSecret;
}