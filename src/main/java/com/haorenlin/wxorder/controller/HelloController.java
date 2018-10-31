package com.haorenlin.wxorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author Yan Junlin
 * @date 2018/9/25-22:35
 */
@Controller
public class HelloController {

    @RequestMapping("/index")
    public String hello(){
        return "Hello, success!";
    }

    @GetMapping("/testFtl")
    public ModelAndView testFreeMarker(Map<String, Object> map){
        map.put("msg","test error");
        map.put("url", "sell/tesFtl");
        return new ModelAndView("common/error",map);
    }
}
