package com.inspur.cart.service;

import com.inspur.cart.entity.Cart;
import com.inspur.client.GoodsClient;
import com.inspur.common.entity.UserInfo;
import com.inspur.common.util.JsonUtils;
import com.inspur.entity.Sku;
import com.inspur.interceptor.LoginInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-21 19:02
 */
@Service
public class CartService {
    private static final String CART_PREFIX = "user:cart:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;


    public void addCart(Cart cart) {

        //查询当前用户的购物车记录
        UserInfo userInfo = LoginInterceptor.getUserInfo();

        //如果当前cart 是否在 存在的购物车中
        BoundHashOperations<String, Object, Object> hashOps
                = this.redisTemplate.boundHashOps(CART_PREFIX + userInfo.getId());

        String skuId = cart.getSkuId().toString();
        Boolean hasSku = hashOps.hasKey(skuId);
        //如果存在
        if(hasSku) {
            String cartJSONString = hashOps.get(skuId).toString();
            Cart mycart = JsonUtils.parse(cartJSONString, Cart.class);
            mycart.setNum(mycart.getNum() + cart.getNum());
            //这里特别注意 hashOps的操作
            hashOps.put(skuId, JsonUtils.serialize(mycart));
        } else {
            Sku sku = goodsClient.getSkuById(cart.getSkuId());

            cart.setUserId(userInfo.getId());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            hashOps.put(skuId, JsonUtils.serialize(cart));
        }

    }

    public List<Cart> queryCarts() {
        //获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        if(this.redisTemplate.hasKey(CART_PREFIX + userInfo.getId())) {
            //查询购物车信息
            BoundHashOperations<String, Object, Object> hashOps
                    = this.redisTemplate.boundHashOps(CART_PREFIX + userInfo.getId());
            List<Object> cartJsonList = hashOps.values();
            if(CollectionUtils.isEmpty(cartJsonList)) {
                return null;
            }
            return cartJsonList.stream().map(o -> {
                return JsonUtils.parse(o.toString(), Cart.class);
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public void updateCarts(Cart cart) {
        //查询当前用户的购物车记录
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //如果当前cart 是否在 存在的购物车中
        BoundHashOperations<String, Object, Object> hashOps
                = this.redisTemplate.boundHashOps(CART_PREFIX + userInfo.getId());
        String skuId = cart.getSkuId().toString();
        Boolean hasSku = hashOps.hasKey(skuId);
        //如果存在
        if(hasSku) {
            String cartJSONString = hashOps.get(skuId).toString();
            Cart mycart = JsonUtils.parse(cartJSONString, Cart.class);
            mycart.setNum(cart.getNum());
            //这里特别注意 hashOps的操作
            hashOps.put(skuId, JsonUtils.serialize(mycart));
        } else {
            Sku sku = goodsClient.getSkuById(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            hashOps.put(skuId,JsonUtils.serialize(cart));
        }
    }
}
