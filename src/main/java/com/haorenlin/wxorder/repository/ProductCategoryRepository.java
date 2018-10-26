package com.haorenlin.wxorder.repository;

import com.haorenlin.wxorder.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yan Junlin
 * @date 2018/10/14-11:17
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

}
