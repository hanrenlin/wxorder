package com.haorenlin.wxorder.controller;

import com.haorenlin.wxorder.from.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author Yan Junlin
 * @Date 2018/10/22 11:34
 * @Description (买家订单)
 **/
@Slf4j
@RestController("/buyer/order")
public class BuyerOrderController {
    /** 创建订单*/
    //@Validated是@Valid的一次封装 有分组功能
    public void create(@Valid OrderForm orderFrom) {

    }
    /** 订单列表*/

    /** 订单详情*/

    /** 取消订单*/
}