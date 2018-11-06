package com.haorenlin.wxorder.controller;

import com.haorenlin.wxorder.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author Yan Junlin
 * @Date 2018/11/5 23:49
 * @Description (后台用户)
 **/
@Controller
@RequestMapping("seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("login")
    public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response, Map<String, Object> map) {
        return sellerService.login(openid, response, map);
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        return sellerService.logout(request, response, map);
    }
}