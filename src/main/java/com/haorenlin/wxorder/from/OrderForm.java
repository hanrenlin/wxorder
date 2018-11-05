package com.haorenlin.wxorder.from;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author Yan Junlin
 * @Date 2018/10/22 11:39
 * @Description (买家 订单创建 入参)
 **/
@Data
public class OrderForm {

    /** 姓名*/
    @NotBlank(message = "姓名不能为空")
    private String name;
    /** 手机*/
    @NotBlank(message = "手机号不能为空")
    private String phone;
    /** 地址*/
    @NotBlank(message = "地址不能为空")
    private String address;
    /** openId*/
    @NotBlank(message = "openid不能为空")
    private String openid;
    /** 购物车*/
    @NotBlank(message = "购物车不能为空")
    private String items;
}