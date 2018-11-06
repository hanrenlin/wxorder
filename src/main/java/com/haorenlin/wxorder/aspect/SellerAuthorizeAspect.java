package com.haorenlin.wxorder.aspect;

import com.haorenlin.wxorder.constant.CookieConstant;
import com.haorenlin.wxorder.constant.RedisConstant;
import com.haorenlin.wxorder.exception.SellerAuthorizeException;
import com.haorenlin.wxorder.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author Yan Junlin
 * @Date 2018/11/6 11:14
 * @Description (AOP切面拦截器)
 **/
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.haorenlin.wxorder.controller.Seller*.*(..))" +
            "&& !execution(public * com.haorenlin.wxorder.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify() {

        /** 获取http容器 */
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        /** cookie数据校验 */
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        /** redis数据校验 */
        String token = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isBlank(token)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }

    }
}