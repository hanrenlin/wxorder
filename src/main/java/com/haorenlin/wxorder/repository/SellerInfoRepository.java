package com.haorenlin.wxorder.repository;

import com.haorenlin.wxorder.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid) ;
}
