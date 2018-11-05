package com.haorenlin.wxorder.service;

import com.haorenlin.wxorder.dataobject.ProductInfo;
import com.haorenlin.wxorder.dto.CartDTO;
import com.haorenlin.wxorder.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Yan Junlin
 * @date 2018/10/14-15:11
 *
 * 商品信息
 */
public interface ProductInfoService {

    /**根据ID查询*/
    ProductInfo findOneById(String productId);

    /**查询所有上架商品*/
    List<ProductInfo> findUpAll();

    /**查询所有*/
    Page<ProductInfo> findAll(Pageable pageable);

    /**保存*/
    ProductInfo save(ProductInfo productInfo);

    /**加库存*/
    void increaseStock(List<CartDTO> cartDTOList);

    /**减库存*/
    void decreaseStock(List<CartDTO> cartDTOList);

    /** 上架*/
    ProductInfo onSale(String productId);

    /** 下架*/
    ProductInfo offSale(String productId);
}
