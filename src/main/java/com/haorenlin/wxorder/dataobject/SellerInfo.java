package com.haorenlin.wxorder.dataobject;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * @Author Yan Junlin
 * @Date 2018/11/5 23:50
 * @Description (后台用户信息)
 **/
@Entity
@DynamicUpdate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfo {

    /** */
    @Id
    private String id;

    /** 用户名 */
    private String username;

    /** */
    private String password;

    /** 微信openId */
    private String openid;

}