package com.inspur.entity.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 11:47
 */
@RequestMapping("/category")
public interface CategoryApi {

    /**
     * 根据 分类的主键ids 查询分类的名称集合
     * @param ids
     * @return
     */
    @GetMapping
    public List<String> queryCategoryNamesByIds(@RequestParam("ids") List<Long> ids);
}
