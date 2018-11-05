package com.haorenlin.wxorder.controller;

import com.haorenlin.wxorder.dataobject.ProductCategory;
import com.haorenlin.wxorder.exception.SellException;
import com.haorenlin.wxorder.from.CategoryForm;
import com.haorenlin.wxorder.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author Yan Junlin
 * @Date 2018/11/4 16:08
 * @Description (卖家 - 商品类目)
 **/
@Controller
@RequestMapping("seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService categoryService;


    /**
     * 类目列表
     *
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 展示
     *
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOneById(categoryId);
            map.put("category", productCategory);
        }

        return new ModelAndView("category/index", map);
    }

    /**
     * 保存/更新
     *
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        map.put("url", "sell/seller/category/index");
        String modelKey =  "common/error";
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(modelKey, map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.findOneById(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
            modelKey = "common/success";
        } catch (SellException e) {
            map.put("msg", e.getMessage());
        }

        return new ModelAndView(modelKey, map);
    }


}