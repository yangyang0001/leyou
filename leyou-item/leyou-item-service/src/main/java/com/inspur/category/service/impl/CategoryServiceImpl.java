package com.inspur.category.service.impl;

import com.inspur.category.mapper.CategoryMapper;
import com.inspur.category.service.CategoryService;
import com.inspur.entity.Category;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 11:45
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点查询子节点
     * @param pid
     * @return
     */
    @Override
    public List<Category> queryCategoryListByParentId(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    @Override
    public List<String> queryCategoryNamesByIds(List<Long> ids) {
        List<Category> categoryList = categoryMapper.selectByIdList(ids);
        List<String> categoryNameList =
                categoryList.stream().map(category -> {
                    return category.getName();
                }).collect(Collectors.toList());
        return categoryNameList;
    }
}
