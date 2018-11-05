package com.haorenlin.wxorder.from;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Yan Junlin
 * @Date 2018/11/4 15:25
 * @Description (产品表单)
 **/
@Data
public class ProductForm {

    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 类目编号. */
    private Integer categoryType;
}