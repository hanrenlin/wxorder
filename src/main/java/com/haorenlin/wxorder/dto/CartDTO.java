package com.haorenlin.wxorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @Author Yan Junlin
 * @Date 2018/10/17 0:42
 * @Description (购物车)
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    /** 商品Id. */
    private String productId;

    /** 数量. */
    private Integer productQuantity;

}