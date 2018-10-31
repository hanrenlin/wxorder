package com.haorenlin.wxorder.controller;

import com.haorenlin.wxorder.config.UrlConfig;
import com.haorenlin.wxorder.enums.ResultEnum;
import com.haorenlin.wxorder.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @Author Yan Junlin
 * @Date 2018/10/24 0:19
 * @Description (微信)
 **/

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private UrlConfig urlConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        //1. 配置
        //2. 调用方法
        String url = urlConfig.getWechatMpAuthorize() + "/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("authorize redirectUrl:{}", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            log.info("code:" + code);
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (Exception e) {
            log.error("【微信网页授权】{}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getMessage());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("openId:{}", openId);
        log.info("returnUrl:{}", returnUrl);
        return "redirect:" + returnUrl + "?openid=" + openId;
    }


    @GetMapping("/auth")
    public String auth(@RequestParam("code") String code) {
        log.info("weixin auth come in");
        log.info("code={}", code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx2cdc9155eaaa22e7&secret=b3ad29d0bd6d30f7cb40a52cfd0203d6&code=" + code + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response:" + response);
        return "success! code->" + code + "\nresponse->" + response;
    }

    @GetMapping("/authCode")
    public void authCode(HttpServletRequest request) {
        log.info("weixin authCode come in");
        log.info(request.getRequestURL().toString());
        log.info("code:" + request.getParameter("code"));

    }

}