package com.inspur.spu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inspur.category.mapper.BrandMapper;
import com.inspur.category.mapper.CategoryMapper;
import com.inspur.common.entity.PageResult;
import com.inspur.entity.Brand;
import com.inspur.entity.Category;
import com.inspur.entity.Spu;
import com.inspur.entity.SpuDetail;
import com.inspur.entity.bo.SpuBo;
import com.inspur.spu.mapper.SpuDetailMapper;
import com.inspur.spu.mapper.SpuMapper;
import com.inspur.spu.service.SpuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 22:17
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Override
    public PageResult<SpuBo> queryByPage(String key, Boolean saleable, Integer page, Integer rows) {

        // 添加查询条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }

        // 添加上架 和 下架的过滤条件
        //特别注意此处的Boolean 类型值的 传递问题
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        // 添加分页
        PageHelper.startPage(page, rows);

        // 执行查询 获取Spu集合
        List<Spu> spuList = spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<Spu>(spuList);

        // 返回指定的SpuBo的集合
        List<SpuBo> spuBoList =
            spuList.stream().map(spu -> {

                SpuBo spuBo = new SpuBo();
                BeanUtils.copyProperties(spu, spuBo);
                List<Long> categoryIds = Arrays.asList(spuBo.getCid1(), spuBo.getCid2(), spuBo.getCid3());
                List<Category> categoryList = categoryMapper.selectByIdList(categoryIds);

                List<String> categoryNames =
                        categoryList.stream()
                                .map(category -> {return category.getName();})
                                .collect(Collectors.toList());

                spuBo.setCname(StringUtils.join(categoryNames, "-"));
                Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
                spuBo.setBname(brand.getName());

                return spuBo;
            }).collect(Collectors.toList());

        return new PageResult<SpuBo>(pageInfo.getTotal(), spuBoList);
    }

    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    @Override
    public Spu getSpuBySpuId(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }
}
