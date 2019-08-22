package com.inspur.entity.api;


import com.inspur.common.entity.PageResult;
import com.inspur.entity.Sku;
import com.inspur.entity.Spu;
import com.inspur.entity.SpuDetail;
import com.inspur.entity.bo.SpuBo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-15 11:13
 */
public interface GoodsApi {

    /**
     * 根据 spuId 获取 spu的详情
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId") Long spuId);

    /**
     * @param key
     * @param page
     * @param rows
     * @param saleable
     * @return
     */
    @GetMapping("/spu/page")
    public PageResult<SpuBo> queryByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows );

    @GetMapping("/spu/{id}")
    public Spu getSpuBySpuId(@PathVariable("id") Long id);



    @GetMapping("/sku/list")
    public List<Sku> querySkuListBySpuId(@RequestParam("id") Long spuId);

    @GetMapping("/sku/{id}")
    public Sku getSkuById(@RequestParam("id") Long skuId);
}
