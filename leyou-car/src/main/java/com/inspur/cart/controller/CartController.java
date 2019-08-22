package com.inspur.cart.controller;

import com.inspur.cart.entity.Cart;
import com.inspur.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-21 19:01
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
        this.cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> queryCarts() {
        List<Cart> carts = this.cartService.queryCarts();
        if(CollectionUtils.isEmpty(carts)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(carts);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCarts(@RequestBody Cart cart) {
        this.cartService.updateCarts(cart);
        return ResponseEntity.ok().build();
    }
}
