package com.haorenlin.wxorder.service.impl;

import com.haorenlin.wxorder.config.UrlConfig;
import com.haorenlin.wxorder.constant.CookieConstant;
import com.haorenlin.wxorder.constant.RedisConstant;
import com.haorenlin.wxorder.constant.UrlConstant;
import com.haorenlin.wxorder.dataobject.SellerInfo;
import com.haorenlin.wxorder.enums.ResultEnum;
import com.haorenlin.wxorder.repository.SellerInfoRepository;
import com.haorenlin.wxorder.service.SellerService;
import com.haorenlin.wxorder.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author Yan Junlin
 * @Date 2018/11/6 9:19
 * @Description (卖家端)
 **/
@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UrlConfig urlConfig;

    /**
     * 登录
     * @param openid
     * @param response
     * @param map
     * @return
     */
    public ModelAndView login (String openid, HttpServletResponse response, Map<String, Object> map) {
        /** openid和数据库匹配*/
        SellerInfo sellerInfo = repository.findByOpenid(openid);
        if (StringUtils.isBlank(openid) || sellerInfo == null || openid.equals(sellerInfo.getOpenid())) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", UrlConstant.SELLER_ORDER_LIST);
            return new ModelAndView(UrlConstant.COMMON_ERROR);
        }
        /** 生成token*/
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        /** 存储token至redis*/
        // 方法参数 key、value、过期时间、时间单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token), openid, expire, TimeUnit.SECONDS);
        /** 存储token至cookie*/
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
        return new ModelAndView("redirect".concat(urlConfig.getSell()).concat(UrlConstant.SELLER_ORDER_LIST));
    }

    /**
     * 登出
     * @param
     * @return
     */
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if ( cookie != null ) {
            /** redis数据移除 */
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            /** cookie数据移除 */
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", UrlConstant.SELLER_ORDER_LIST);
        return new ModelAndView(UrlConstant.COMMON_SUCCESS, map);

    }

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}