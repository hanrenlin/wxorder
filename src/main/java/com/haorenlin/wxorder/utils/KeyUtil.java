package com.haorenlin.wxorder.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @Author Yan Junlin
 * @Date 2018/10/17 0:23
 * @Description (主键生成)
 **/
public class KeyUtil {

    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

    public static synchronized String genUUIDKey() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static void main(String[] args) {
        for (int i=0;i<10;i++) {
            System.out.println(genUniqueKey());
            System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        }
    }
}