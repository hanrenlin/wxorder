package com.haorenlin.wxorder.enums;

import lombok.Getter;

/**
 * @Author Yan Junlin
 * @Description 产品状态 
 * @Date 2018/10/17 9:56
 * @Param 
 * @return 
 **/
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
