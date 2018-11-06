package com.haorenlin.wxorder.constant;

/**
 * @Author Yan Junlin
 * @Date 2018/11/6 10:02
 * @Description (Redis常量)
 **/
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";
    /** 过期时间 */
    Integer EXPIRE = 7200; //2小时
}