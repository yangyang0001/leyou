package com.inspur.category.controller;

import com.inspur.category.service.BrandService;
import com.inspur.common.entity.PageResult;
import com.inspur.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 17:03
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") boolean desc) {
        PageResult<Brand> brandPageResult = brandService.queryByPage(key, page, rows, sortBy, desc);

        return ResponseEntity.ok(brandPageResult);
    }

    @PostMapping
    public ResponseEntity<Void> addBrandWithCategory(Brand brand, @RequestParam("cids") List<Long> cids) {
        this.brandService.addBrandWithCategory(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandListWithCategoryId (@PathVariable("cid") Long cid) {
        List<Brand> brandList = this.brandService.queryBrandListWithCategoryId(cid);
        if(CollectionUtils.isEmpty(brandList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id) {
        Brand brand = this.brandService.queryBrandById(id);
        if(brand == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }

}
