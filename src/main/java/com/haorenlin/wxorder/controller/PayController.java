package com.haorenlin.wxorder.controller;

import com.haorenlin.wxorder.dto.OrderDTO;
import com.haorenlin.wxorder.enums.ResultEnum;
import com.haorenlin.wxorder.exception.SellException;
import com.haorenlin.wxorder.service.OrderService;
import com.haorenlin.wxorder.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author Yan Junlin
 * @Date 2018/10/29 23:39
 * @Description (支付)
 **/
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    /**
     *
     *发起支付请求
     *
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl, Map<String, Object> map) {
        /** 查询订单 */
        OrderDTO order = orderService.findOneByOrderId(orderId);
        if (order == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        /** 发起支付 */
        PayResponse payResponse = payService.create(order);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        /** 返回结果 */
        return new ModelAndView("pay/create",map);
    }

    public ModelAndView asyncNotify(String nofifyData) {
        PayResponse payResponse = payService.asyncNotify(nofifyData);
        return new ModelAndView("pay/success");
    }
}