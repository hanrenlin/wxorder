package com.haorenlin.wxorder.service;

import com.haorenlin.wxorder.dataobject.ProductCategory;

import java.util.List;

/**
 * @author Yan Junlin
 * @date 2018/10/14-14:48
 *
 * 商品类目
 */
public interface ProductCategoryService {

    ProductCategory findOneById(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
