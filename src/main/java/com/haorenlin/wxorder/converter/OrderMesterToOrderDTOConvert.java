package com.haorenlin.wxorder.converter;

import com.haorenlin.wxorder.dataobject.OrderMaster;
import com.haorenlin.wxorder.dto.OrderDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Yan Junlin
 * @Date 2018/10/21 11:20
 * @Description (类型转换OrderMaster - - - > OrderDTO)
 **/
public class OrderMesterToOrderDTOConvert {

    public static OrderDTO  convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
        orderMasterList.stream().forEach(x->{
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(x, orderDTO);
            orderDTOList.add(orderDTO);
        });
        return orderDTOList;
    }
}