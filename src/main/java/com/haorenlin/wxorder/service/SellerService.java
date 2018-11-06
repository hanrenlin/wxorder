package com.haorenlin.wxorder.service;

import com.haorenlin.wxorder.dataobject.SellerInfo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface SellerService {

    /** 根据openid查询买家端信息*/
    SellerInfo findSellerInfoByOpenid(String openid);

    /** 登录 */
    ModelAndView login (String openid, HttpServletResponse response, Map<String, Object> map);

    /** 登出 */
    ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map);
}
