package com.haorenlin.wxorder.service.impl;

import com.haorenlin.wxorder.dataobject.ProductInfo;
import com.haorenlin.wxorder.service.ProductInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Yan Junlin
 * @date 2018/10/14-15:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findOneById() {
        ProductInfo oneById = productInfoService.findOneById("000001");
        System.out.println(oneById);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productInfoService.findUpAll();
        System.out.println(upAll);

    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        List<ProductInfo> all = productInfoService.findAll(request);
        System.out.println(all);
    }

    @Test
    public void save() {
        ProductInfo productInfo = ProductInfo.builder()
                .productId("000004")
                .productName("香蕉")
                .productIcon("http://banana")
                .productStatus(0)
                .categoryType(2)
                .productPrice(new BigDecimal("1.50"))
                .productStock(300)
                .build();
        productInfoService.save(productInfo);
    }
}