package com.inspur.cart.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-21 18:57
 */
@Data
public class Cart implements Serializable {
    private static final long serialVersionUID = -334982896855735528L;

    private Long userId;// 用户id
    private Long skuId;// 商品id
    private String title;// 标题
    private String image;// 图片
    private Long price;// 加入购物车时的价格
    private Integer num;// 购买数量
    private String ownSpec;// 商品规格参数

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", skuId=" + skuId +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", ownSpec='" + ownSpec + '\'' +
                '}';
    }
}
