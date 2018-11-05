package com.haorenlin.wxorder.service.impl;

import com.google.gson.Gson;
import com.haorenlin.wxorder.dataobject.OrderDetail;
import com.haorenlin.wxorder.dto.OrderDTO;
import com.haorenlin.wxorder.enums.OrderStatusEnum;
import com.haorenlin.wxorder.enums.PayStatusEnum;
import com.haorenlin.wxorder.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "1101110";
    private final String ORDER_ID = "1497183332311989948";

    private static Gson gson = new Gson();
    @Test
    public void create() {
        OrderDTO orderDTO = OrderDTO.builder()
                .buyerName("闫二娃test")
                .buyerAddress("世纪大道1229号")
                .buyerPhone("17700000067")
                .buyerOpenid(BUYER_OPENID).build();
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("000003");
        o1.setProductQuantity(5);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("000004");
        o2.setProductQuantity(10);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);
        orderService.create(orderDTO);

    }

    @Test
    public void findOneByOrderId() {
        OrderDTO orderDTO = orderService.findOneByOrderId("1539879857699862339");
        System.out.println(orderDTO);
    }

    @Test
    public void findList() {
        Page<OrderDTO> page = orderService.findList("1101110", new PageRequest(0, 2));
        System.out.println(">>>"+gson.toJson(page));
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOneByOrderId("1539879857699862339");
        OrderDTO orderDTOCancel = orderService.cancel(orderDTO);
        System.out.println("orderCancel ---> "+gson.toJson(orderDTOCancel));
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOneByOrderId("1539879857699862339");
        OrderDTO orderDTOFinish = orderService.finish(orderDTO);
        System.out.println("orderFinsh ---> "+gson.toJson(orderDTO));
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), orderDTOFinish.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOneByOrderId("1539879857699862339");
        OrderDTO orderPaid = orderService.paid(orderDTO);
        System.out.println("orderPaid ---> "+gson.toJson(orderPaid));
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), orderPaid.getPayStatus());
    }

    @Test
    public void findList1() {
        Page<OrderDTO> list = orderService.findList(new PageRequest(0, 2));
        System.out.println(gson.toJson(list));
    }
}