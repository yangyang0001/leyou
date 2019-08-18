package com.inspur.entity.api;


import com.inspur.entity.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 17:03
 */
@RequestMapping("/brand")
public interface BrandApi {

    @GetMapping("/{id}")
    public Brand queryBrandById(@PathVariable("id") Long id);
}
