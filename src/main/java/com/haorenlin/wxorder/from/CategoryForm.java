package com.haorenlin.wxorder.from;

import lombok.Data;

import java.util.Date;

/**
 * @Author Yan Junlin
 * @Date 2018/11/4 16:11
 * @Description (商品类目表单)
 **/
@Data
public class CategoryForm {

    /** 类目 */
    private Integer categoryId;
    /** 类目名字 */
    private String categoryName;
    /** 类目编号 */
    private Integer categoryType;
}