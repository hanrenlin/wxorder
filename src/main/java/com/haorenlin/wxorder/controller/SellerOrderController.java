package com.haorenlin.wxorder.controller;


import com.haorenlin.wxorder.dto.OrderDTO;
import com.haorenlin.wxorder.enums.ResultEnum;
import com.haorenlin.wxorder.exception.SellException;
import com.haorenlin.wxorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author Yan Junlin
 * @Date 2018/10/31 15:27
 * @Description (卖家订单)
 **/
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param pageNumber
     * @param pageSize
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer pageNumber,
                              @RequestParam(value = "size",defaultValue = "10") Integer pageSize,
                              Map<String,Object> map) {
        Page<OrderDTO> orderDTOPage = orderService.findList(new PageRequest(pageNumber.intValue() - 1, pageSize.intValue()));
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",pageNumber);
        map.put("size", pageSize);
        return new ModelAndView("order/list",map);
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail (@RequestParam("orderId") String orderId, Map<String,Object> map) {
        try {
             map.put("orderDTO",orderService.findOneByOrderId(orderId));
        } catch (SellException e) {
            log.error("【买家订单】查看详情出错，orderId={}",orderId,e);
            map.put("msg", e.getMessage());
            map.put("url", "sell/seller/order/detail");
        }
        return new ModelAndView("order/detail", map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("cancel")
    public ModelAndView cancel (@RequestParam("orderId") String orderId, Map<String,Object> map) {
        String modelKey = null;
        try {
            orderService.cancel(orderService.findOneByOrderId(orderId));
            map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
            modelKey = "common/success";
        } catch (SellException e) {
            log.error("【买家订单】取消订单出错，orderId={}",orderId,e);
            map.put("msg",e.getMessage());
            modelKey = "common/error";
        }
        map.put("url","sell/seller/order/list");
        return new ModelAndView(modelKey, map);
    }

    /**
     * 结束订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("finish")
    public ModelAndView finish (@RequestParam("orderId") String orderId, Map<String,Object> map) {
        String modelKey = null;
        try {
            orderService.finish(orderService.findOneByOrderId(orderId));
            map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
            modelKey = "common/success";
        } catch (SellException e) {
            log.error("【买家订单】完结订单出错，orderId={}",orderId,e);
            map.put("msg",e.getMessage());
            modelKey = "common/error";
        }
        map.put("url","sell/seller/order/list");
        return new ModelAndView(modelKey, map);
    }

}