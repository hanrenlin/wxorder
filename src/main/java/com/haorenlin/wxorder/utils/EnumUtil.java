package com.haorenlin.wxorder.utils;

import com.haorenlin.wxorder.enums.CodeEnum;

/**
 * @Author Yan Junlin
 * @Date 2018/11/4 11:16
 * @Description (枚举工具类)
 **/
public class EnumUtil {

    public static <T extends CodeEnum> T getMessageByCode(Integer code, Class<T> enumClass) {
        for (T t : enumClass.getEnumConstants()) {
            if (code.equals(t.getCode())) {
                return t;
            }
        }
        return null;
    }

}