package com.haorenlin.wxorder.service;

import com.haorenlin.wxorder.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    PayResponse asyncNotify(String notifyData);

    RefundResponse refund (OrderDTO orderDTO);
}
