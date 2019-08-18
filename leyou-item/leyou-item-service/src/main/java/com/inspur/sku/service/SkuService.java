package com.inspur.sku.service;


import com.inspur.entity.Sku;
import com.inspur.entity.bo.SpuBo;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 22:17
 */
public interface SkuService {

    public void saveGoods(SpuBo spuBo);

    public List<Sku> querySkuListBySpuId(Long spuId);

    public void upateGoods(SpuBo spuBo);
}
