package com.haorenlin.wxorder.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author Yan Junlin
 * @Date 2018/10/30 0:26
 * @Description (json工具)
 **/
public class JsonUtil {

    public static String toString(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}