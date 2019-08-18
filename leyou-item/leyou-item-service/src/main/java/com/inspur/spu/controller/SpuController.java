package com.inspur.spu.controller;

import com.inspur.common.entity.PageResult;
import com.inspur.entity.Spu;
import com.inspur.entity.SpuDetail;
import com.inspur.entity.bo.SpuBo;
import com.inspur.spu.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 22:16
 */
@Controller
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    //http://api.leyou.com/api/item/spu/page?key=&saleable=true&page=1&rows=5
    /**
     * @param key
     * @param page
     * @param rows
     * @param saleable
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<SpuBo>> queryByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows ) {
        PageResult<SpuBo> spuBoPageResult = spuService.queryByPage(key, saleable, page, rows);

        return ResponseEntity.ok(spuBoPageResult);
    }

    /**
     * 根据 spuId 获取 spu的详情
     * @param spuId
     * @return
     */
    @GetMapping("/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetail = this.spuService.querySpuDetailBySpuId(spuId);
        if(spuDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spu> getSpuBySpuId(@PathVariable("id") Long id) {
        Spu spu = this.spuService.getSpuBySpuId(id);
        if(spu == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spu);
    }

}
