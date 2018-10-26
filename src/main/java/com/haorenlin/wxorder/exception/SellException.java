package com.haorenlin.wxorder.exception;

import com.haorenlin.wxorder.enums.ResultEnum;

/**
 * @Author Yan Junlin
 * @Date 2018/10/17 9:27
 * @Description (业务异常)
 **/
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message){
        super(message);
        this.code = code;
    }

}