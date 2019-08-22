package com.inspur.sku.controller;


import com.inspur.entity.Sku;
import com.inspur.entity.Spu;
import com.inspur.entity.bo.SpuBo;
import com.inspur.sku.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 22:16
 */
@Controller
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 新增商品
     * @param spuBo
     * @return
     */
    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo) {
        skuService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新商品信息
     * @param spuBo
     * @return
     */
    @PutMapping("/goods")
    public ResponseEntity<Void> upateGoods(@RequestBody SpuBo spuBo) {
        skuService.upateGoods(spuBo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuListBySpuId(@RequestParam("id") Long spuId) {
        List<Sku> skuList = skuService.querySkuListBySpuId(spuId);
        if(CollectionUtils.isEmpty(skuList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skuList);
    }

    @GetMapping("/sku/{id}")
    public ResponseEntity<Sku> getSkuById(@RequestParam("id") Long skuId) {
        Sku sku = skuService.getSkuById(skuId);
        if(sku == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sku);
    }

}
