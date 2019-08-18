package com.inspur.category.service;

import com.inspur.common.entity.PageResult;
import com.inspur.entity.Brand;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 17:02
 */
public interface BrandService {

    public PageResult<Brand> queryByPage(String key, Integer page, Integer rows, String sortBy, boolean desc);

    public void addBrandWithCategory(Brand brand, List<Long> cids);

    public List<Brand> queryBrandListWithCategoryId(Long cid);

    public Brand queryBrandById(Long id);
}
