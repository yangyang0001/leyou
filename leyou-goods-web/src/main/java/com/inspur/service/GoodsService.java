package com.inspur.service;

import com.inspur.client.BrandClient;
import com.inspur.client.CategoryClient;
import com.inspur.client.GoodsClient;
import com.inspur.client.SpecificationClient;
import com.inspur.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-16 22:28
 */
@Service
public class GoodsService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;


    /**
     * 点击大图片 跳转链接后需要重组的数据
     * @return
     */
    public Map<String ,Object> loadData(Long spuId) {
        Map<String, Object> model = new HashMap<String, Object>();

        //根据spuId 查询 SPU
        Spu spu = this.goodsClient.getSpuBySpuId(spuId);

        //查询 spuDetail 根据 spuId
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);

        //查询分类 List<Map<String, Object>>
        List<Map<String, Object>> categories = new ArrayList<>();
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> categoryNames = this.categoryClient.queryCategoryNamesByIds(cids);
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("id", cids.get(i));
            categoryMap.put("name", categoryNames.get(i));
            categories.add(categoryMap);
        }

        //查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        //查询Skus
        List<Sku> skus = this.goodsClient.querySkuListBySpuId(spu.getId());

        //查询规格参数组
        List<SpecGroup> groups = this.specificationClient.queryGroupWithParams(spu.getCid3());

        //查询特殊的规格参数
        List<SpecParam> params = this.specificationClient.queryParamList(null, spu.getCid3(), false, null);
        Map<Long, String> paramMap = new HashMap<>();
        params.forEach(param -> {
            paramMap.put(param.getId(), param.getName());
        });

        model.put("spu", spu);
        model.put("spuDetail", spuDetail);
        model.put("categories", categories);
        model.put("brand", brand);
        model.put("skus", skus);
        model.put("groups", groups);
        model.put("params", paramMap);

        return model;
    }



}
