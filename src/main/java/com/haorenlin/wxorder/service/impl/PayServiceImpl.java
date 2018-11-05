package com.haorenlin.wxorder.service.impl;

import com.haorenlin.wxorder.dto.OrderDTO;
import com.haorenlin.wxorder.enums.ResultEnum;
import com.haorenlin.wxorder.exception.SellException;
import com.haorenlin.wxorder.service.OrderService;
import com.haorenlin.wxorder.service.PayService;
import com.haorenlin.wxorder.utils.JsonUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author Yan Junlin
 * @Date 2018/10/29 23:43
 * @Description (支付)
 **/
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;

    /**
     * 支付请求
     * @param orderDTO
     * @return
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        log.info("【微信支付】PayRequest={}", JsonUtil.toString(payRequest));
        PayResponse response = bestPayService.pay(payRequest);
        log.info("【微信支付】PayResponse={}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 异步通知
     * @param notifyData
     * @return
     */
    public PayResponse asyncNotify(String notifyData) {
        /** 异步通知*/
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}",payResponse);
        /** 订单信息*/
        OrderDTO orderDTO = orderService.findOneByOrderId(payResponse.getOrderId());
        if (orderDTO == null) {
            log.info("【微信支付】异步通知，订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (orderDTO.getOrderAmount().setScale(4).compareTo(new BigDecimal(payResponse.getOrderAmount()).setScale(4)) != 0) {
            log.info("【微信支付】异步通知，订单金额不一致，orderId={},系统金额={}，异步通知金额={}"
                    ,payResponse.getOrderId(), orderDTO.getOrderAmount(), payResponse.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        /** 修改订单状态 */
        orderService.paid(orderDTO);
        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    public RefundResponse refund (OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】退款，request={}", JsonUtil.toString(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信支付】退款，response={}",JsonUtil.toString(refundResponse));
        return refundResponse;
    }
}