package com.inspur.category.service;

import com.inspur.entity.Category;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 11:44
 */
public interface CategoryService {
    public List<Category> queryCategoryListByParentId(Long pid);

    public List<String> queryCategoryNamesByIds(List<Long> ids);
}
