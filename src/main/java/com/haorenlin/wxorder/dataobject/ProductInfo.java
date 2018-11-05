package com.haorenlin.wxorder.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haorenlin.wxorder.enums.ProductStatusEnum;
import com.haorenlin.wxorder.utils.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Yan Junlin
 * @date 2018/10/14-15:03
 *
 * 商品信息
 */
@Entity
@DynamicUpdate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    @Id
    private String productId;//
    private String productName;//商品名称
    private BigDecimal productPrice;//单价
    private Integer productStock;//库存
    private String productDescription;//描述
    private String productIcon;//小图
    private Integer productStatus;//商品状态,0正常1下架
    private Integer categoryType;//类目编号
    @JsonSerialize
    private Date createTime;//创建时间',
    @JsonSerialize
    private Date updateTime;//

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getMessageByCode(productStatus, ProductStatusEnum.class);
    }
}
