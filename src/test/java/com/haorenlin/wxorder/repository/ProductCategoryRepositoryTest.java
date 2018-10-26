package com.haorenlin.wxorder.repository;

import com.haorenlin.wxorder.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


/**
 * @author Yan Junlin
 * @date 2018/10/14-11:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        if (repository==null) {
            System.out.println(">>>>>>>>>>> repository is null !!!");
            return;
        }
        ProductCategory productCategory = repository.findById(1).orElse(null);
        System.out.println("productCategory:"+productCategory);
    }

    @Test
    public void saveOneTest(){
        ProductCategory productCategory = ProductCategory.builder().categoryId(2).categoryName("水果2").categoryType(2).build();
        System.out.println(productCategory);
        repository.save(productCategory);
    }

    @Test
    public void findByIds() {
        List<ProductCategory> allById = repository.findAllById(Arrays.asList(2, 3));
        System.out.println(allById);
    }



}