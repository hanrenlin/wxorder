package com.haorenlin.wxorder.service.impl;

import com.haorenlin.wxorder.dataobject.ProductInfo;
import com.haorenlin.wxorder.dto.CartDTO;
import com.haorenlin.wxorder.enums.ProductStatusEnum;
import com.haorenlin.wxorder.enums.ResultEnum;
import com.haorenlin.wxorder.exception.SellException;
import com.haorenlin.wxorder.repository.ProductInfoRepository;
import com.haorenlin.wxorder.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yan Junlin
 * @date 2018/10/14-15:26
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOneById(String productId) {
        return repository.findOne(Example.of(ProductInfo.builder().productId(productId).build())).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll();
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        if (cartDTOList == null) {
            return;
        }
        cartDTOList.stream().forEach(x -> {
            ProductInfo productInfo = repository.findOne(Example.of(ProductInfo.builder().productId(x.getProductId()).build())).get();
            if (productInfo == null) {
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(productInfo.getProductStock()+x.getProductQuantity());
            repository.save(productInfo);
        });


    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        if (cartDTOList == null) {
            return;
        }
        cartDTOList.stream().forEach(x -> {
            ProductInfo productInfo = repository.findOne(Example.of(ProductInfo.builder().productId(x.getProductId()).build())).get();
            if (productInfo == null) {
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(productInfo.getProductStock()-x.getProductQuantity());
            if (productInfo.getProductStock() < 0) {
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            repository.save(productInfo);
        });
    }
}
