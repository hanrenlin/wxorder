package com.haorenlin.wxorder.repository;

import com.haorenlin.wxorder.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /** 根据订单ID查询订单详情. */
    List<OrderDetail> findByOrderId(String orderId);

}
