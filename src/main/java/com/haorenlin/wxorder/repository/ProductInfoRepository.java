package com.haorenlin.wxorder.repository;

import com.haorenlin.wxorder.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Yan Junlin
 * @date 2018/10/14-15:10
 *
 * 商品信息
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
