package com.inspur.category.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inspur.category.mapper.BrandMapper;
import com.inspur.category.service.BrandService;
import com.inspur.common.entity.PageResult;
import com.inspur.entity.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.beans.Transient;
import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 17:02
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询,并排序分页信息
     * @param key   根据name 或首字母 进行查询
     * @param page  添加分页条件 当前页
     * @param rows  添加分页条件 多上行
     * @param sortBy 排序字段
     * @param desc  排序规则
     * @return
     */
    @Override
    public PageResult<Brand> queryByPage(String key, Integer page, Integer rows, String sortBy, boolean desc) {
        //初始化一个 Example 对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        //
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name", "%" + key +"%").orEqualTo("letter", key);
        }
        if(StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        PageHelper.startPage(page, rows);

        List<Brand> brandList = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<Brand>(brandList);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    @Transactional
    public void addBrandWithCategory(Brand brand, List<Long> cids) {
        int insertCount = this.brandMapper.insertSelective(brand);
        if(insertCount == 1) {
            cids.forEach(cid -> {
                brandMapper.insertBrandWithCategories(cid, brand.getId());
            });
        }
    }

    @Override
    public List<Brand> queryBrandListWithCategoryId(Long cid) {
        return this.brandMapper.queryBrandListWithCategoryId(cid);
    }

    @Override
    public Brand queryBrandById(Long id) {
        return this.brandMapper.selectByPrimaryKey(id);
    }
}
