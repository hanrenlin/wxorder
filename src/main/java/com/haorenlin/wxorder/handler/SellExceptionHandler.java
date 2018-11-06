package com.haorenlin.wxorder.handler;

import com.haorenlin.wxorder.config.UrlConfig;
import com.haorenlin.wxorder.constant.UrlConstant;
import com.haorenlin.wxorder.exception.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Yan Junlin
 * @Date 2018/11/6 11:26
 * @Description (异常拦截器)
 **/
@ControllerAdvice
@Slf4j
public class SellExceptionHandler {

    @Autowired
    private UrlConfig urlConfig;

    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        String redirectUrl = "redirect:"
                .concat(urlConfig.getWechatOpenAuthorize())
                .concat(UrlConstant.SELLER_WECHAT_QRAUTHORIZE)
                .concat("?returnUrl=")
                .concat(urlConfig.getSell())
                .concat(UrlConstant.SELLER_LOGIN);
        log.info("异常拦截，redirectUrl={}",redirectUrl);
        return new ModelAndView(redirectUrl);
    }
}