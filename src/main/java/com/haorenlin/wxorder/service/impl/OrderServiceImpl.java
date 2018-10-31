package com.haorenlin.wxorder.service.impl;

import com.haorenlin.wxorder.converter.OrderMesterToOrderDTOConvert;
import com.haorenlin.wxorder.dataobject.OrderDetail;
import com.haorenlin.wxorder.dataobject.OrderMaster;
import com.haorenlin.wxorder.dataobject.ProductInfo;
import com.haorenlin.wxorder.dto.CartDTO;
import com.haorenlin.wxorder.dto.OrderDTO;
import com.haorenlin.wxorder.enums.OrderStatusEnum;
import com.haorenlin.wxorder.enums.PayStatusEnum;
import com.haorenlin.wxorder.enums.ResultEnum;
import com.haorenlin.wxorder.exception.SellException;
import com.haorenlin.wxorder.repository.OrderDetailRepository;
import com.haorenlin.wxorder.repository.OrderMasterRepository;
import com.haorenlin.wxorder.repository.ProductInfoRepository;
import com.haorenlin.wxorder.service.OrderService;
import com.haorenlin.wxorder.service.ProductInfoService;
import com.haorenlin.wxorder.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Yan Junlin
 * @Date 2018/10/17 09:02
 * @Description (买家订单)
 **/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        /** 订单总金额*/
        BigDecimal orderAmount =  BigDecimal.ZERO;
        //1.查询商品（数量、价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOneById(orderDetail.getProductId());
            //2.计算价格
            orderAmount = orderAmount.add(productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity() + "")));
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUUIDKey());
            //保存订单详情
            orderDetailRepository.save(orderDetail);
        }
        //3.写入订单数据
        OrderMaster orderMaster =  new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderAmount(orderAmount);

        orderMasterRepository.save(orderMaster);
        //4.库存修改
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(x -> new CartDTO(x.getProductId(), x.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOneByOrderId(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(Example.of(OrderMaster.builder().orderId(orderId).build())).orElse(null);
        if (orderMaster==null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMesterToOrderDTOConvert.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        /** 判断订单状态*/
        if (OrderStatusEnum.NEW.getCode() != orderDTO.getOrderStatus()) {
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /** 修改订单状态*/
        OrderMaster orderMester = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMester);
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMester);
        if (orderMasterResult == null) {
            log.error("【取消订单】更新订单状态失败，orderMaster={}",orderMester);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /** 修改库存*/
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream().map(x -> new CartDTO(x.getProductId(), x.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOS);

        /** 已支付的订单进行回款*/
        if (orderDTO.getPayStatus() == PayStatusEnum.SUCCESS.getCode()) {
            //TODO
        }
        /** 消息推送*/

        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        /** 判断订单状态*/
        if (OrderStatusEnum.NEW.getCode() != orderDTO.getOrderStatus()) {
            log.error("【完结订单】订单状态有误，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /** 修改订单状态*/
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
        if (orderMasterResult == null) {
            log.error("【完结订单】更新数据失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /** 消息推送*/

        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        /** 判断订单状态*/
        if (OrderStatusEnum.NEW.getCode() != orderDTO.getOrderStatus()) {
            log.error("【订单支付完成】订单状态有误，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /** 判断支付状态*/
        if (PayStatusEnum.WAIT.getCode() != orderDTO.getOrderStatus()) {
            log.error("【订单支付完成】支付状态有误，orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        /** 修改订单状态*/
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
        if (orderMasterResult == null) {
            log.error("【订单支付完成】修改支付状态失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMesterToOrderDTOConvert.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}