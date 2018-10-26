package com.haorenlin.wxorder.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yan Junlin
 * @date 2018/9/25-22:35
 */
@RestController
public class HelloController {

    @RequestMapping("/index")
    public String hello(){
        return "Hello, success!";
    }

}
