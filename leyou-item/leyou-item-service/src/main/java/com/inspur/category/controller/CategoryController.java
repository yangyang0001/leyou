package com.inspur.category.controller;

import com.inspur.category.service.CategoryService;
import com.inspur.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Response;
import java.util.Collections;
import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 11:47
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询出当前父节点下的所有子节点
     * @param pid
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        List<Category> categoryList = this.categoryService.queryCategoryListByParentId(pid);
        if (CollectionUtils.isEmpty(categoryList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryList);
    }

    /**
     * 根据 分类的主键ids 查询分类的名称集合
     * @param ids
     * @return
     */
    @GetMapping
    public ResponseEntity<List<String>> queryCategoryNamesByIds(@RequestParam("ids") List<Long> ids) {
        List<String> names = this.categoryService.queryCategoryNamesByIds(ids);
        if(CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }
}
