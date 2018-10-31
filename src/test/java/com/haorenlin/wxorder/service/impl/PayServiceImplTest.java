package com.haorenlin.wxorder.service.impl;

import com.haorenlin.wxorder.dto.OrderDTO;
import com.haorenlin.wxorder.service.OrderService;
import com.haorenlin.wxorder.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Table;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void testCreate(){
        OrderDTO orderDTO = orderService.findOneByOrderId("1539879857699862339");
        payService.create(orderDTO);
    }

}