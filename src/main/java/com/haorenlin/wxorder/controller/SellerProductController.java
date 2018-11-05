package com.haorenlin.wxorder.controller;

import com.haorenlin.wxorder.dataobject.ProductCategory;
import com.haorenlin.wxorder.dataobject.ProductInfo;
import com.haorenlin.wxorder.exception.SellException;
import com.haorenlin.wxorder.from.ProductForm;
import com.haorenlin.wxorder.service.ProductCategoryService;
import com.haorenlin.wxorder.service.ProductInfoService;
import com.haorenlin.wxorder.utils.JsonUtil;
import com.haorenlin.wxorder.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 * @Date 2018/11/4 14:12
 * @Description (卖家 - 商品列表)
 **/
@Controller
@RequestMapping("seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 产品列表
     * @param pageNumber
     * @param pageSize
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer pageNumber,
                              @RequestParam(value = "size",defaultValue = "10") Integer pageSize,
                              Map<String,Object> map) {
        Page<ProductInfo> productInfoPage = productInfoService.findAll(new PageRequest(pageNumber - 1, pageSize));
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",pageNumber);
        map.put("size", pageSize);
        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId, Map<String, Object> map) {
        String modelKey = null;
        try {
            productInfoService.onSale(productId);
            modelKey = "common/success";
        } catch (SellException e) {
            log.error("【卖家商品】商品上架出错,prductId={}",productId,e);
            map.put("msg", e.getMessage());
            modelKey = "common/error";
        }
        map.put("url","sell/seller/product/list");
        return new ModelAndView(modelKey,map) ;
    }

    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId, Map<String, Object> map) {
        String modelKey = null;
        try {
            productInfoService.offSale(productId);
            modelKey = "common/success";
        } catch (SellException e) {
            log.error("【卖家商品】商品下架出错,prductId={}",productId,e);
            map.put("msg", e.getMessage());
            modelKey = "common/error";
        }
        map.put("url","sell/seller/product/list");
        return new ModelAndView(modelKey,map) ;
    }

    /**
     * 产品详情（新增/修改）
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId, Map<String, Object> map) {
        if (StringUtils.isNotBlank(productId)) {
            ProductInfo productInfo = productInfoService.findOneById(productId);
            map.put("productInfo", productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("product/index", map);

    }

    /**
     * 商品 新增/更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("save")
    public ModelAndView save (@Valid ProductForm form, BindingResult bindingResult, Map<String, Object> map) {
        map.put("url", "sell/seller/product/index");
        String modelKey = "common/error";
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(modelKey, map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            //如果productId为空, 说明是新增
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productInfoService.findOneById(form.getProductId());
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productInfoService.save(productInfo);
            modelKey = "common/success" ;
        } catch (SellException e) {
            log.error("【卖家商品】保存商品信息出错,productInfo={}", JsonUtil.toString(form),e);
            map.put("msg", e.getMessage());
        }

        return new ModelAndView(modelKey, map);
    }


}