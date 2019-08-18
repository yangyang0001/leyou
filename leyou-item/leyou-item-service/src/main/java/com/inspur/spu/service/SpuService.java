package com.inspur.spu.service;

import com.inspur.common.entity.PageResult;
import com.inspur.entity.Spu;
import com.inspur.entity.SpuDetail;
import com.inspur.entity.bo.SpuBo;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 22:17
 */
public interface SpuService {

    public PageResult<SpuBo> queryByPage(String key, Boolean saleable, Integer page, Integer rows);

    public SpuDetail querySpuDetailBySpuId(Long spuId);

    public Spu getSpuBySpuId(Long id);
}
