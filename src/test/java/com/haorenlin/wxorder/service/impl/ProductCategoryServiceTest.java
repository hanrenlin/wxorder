package com.haorenlin.wxorder.service.impl;

import com.haorenlin.wxorder.dataobject.ProductCategory;
import com.haorenlin.wxorder.service.ProductCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Yan Junlin
 * @date 2018/10/14-14:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService categoryService;

    @Test
    public void findOneById() {
        ProductCategory oneById = categoryService.findOneById(2);
        System.out.println(oneById);
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = categoryService.findAll();
        System.out.println(all);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(2, 3));
        System.out.println(byCategoryTypeIn);
    }

    @Test
    public void save() {
        ProductCategory productCategory = ProductCategory.builder().categoryName("主食").categoryType(3).build();
        ProductCategory save = categoryService.save(productCategory);
        System.out.println(save);
    }
}