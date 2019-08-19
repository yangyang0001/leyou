package com.inspur.sku.service.impl;


import com.inspur.category.mapper.BrandMapper;
import com.inspur.category.mapper.CategoryMapper;
import com.inspur.entity.Sku;
import com.inspur.entity.Stock;
import com.inspur.entity.bo.SpuBo;
import com.inspur.sku.mapper.SkuMapper;
import com.inspur.sku.mapper.SkuStockMapper;
import com.inspur.sku.service.SkuService;
import com.inspur.spu.mapper.SpuDetailMapper;
import com.inspur.spu.mapper.SpuMapper;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 22:17
 */
@Service
public class SkuServiceImpl implements SkuService {

    private static final Logger logger = LoggerFactory.getLogger(SkuServiceImpl.class);

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SkuStockMapper skuStockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Override
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        //新增 spu
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.insertSelective(spuBo);

        //新增 spu_detail
        spuBo.getSpuDetail().setSpuId(spuBo.getId());
        spuDetailMapper.insertSelective(spuBo.getSpuDetail());

        saveSkuAndStock(spuBo);
        try {
            sendMessage(spuBo.getId(), "insert");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    @Override
    public List<Sku> querySkuListBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        skuList.forEach(s -> {
            s.setStock(skuStockMapper.selectByPrimaryKey(s.getId()).getStock());
        });

        return skuList;
    }

    @Override
    @Transactional
    public void upateGoods(SpuBo spuBo) {
        //根据spuId 查询出要删除的 sku
        List<Sku> skuList = querySkuListBySpuId(spuBo.getId());
        skuList.forEach(s -> {
            //删除 sku_stock
            skuStockMapper.deleteByPrimaryKey(s.getId());
        });

        //删除 sku
        Sku record = new Sku();
        record.setSpuId(spuBo.getId());
        skuMapper.delete(record);

        saveSkuAndStock(spuBo);

        //修改 spu
        spuBo.setLastUpdateTime(new Date());
        spuBo.setCreateTime(null);
        spuMapper.updateByPrimaryKeySelective(spuBo);

        //修改 spu_detail
        spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
        try {
            sendMessage(spuBo.getId(), "update");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    /**
     * 新增sku 和 sku_stock
     * @param spuBo
     */
    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().stream().forEach(sku -> {
            //新增 sku
            sku.setId(null);
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insertSelective(sku);
            //新增 sku_stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            skuStockMapper.insertSelective(stock);
        });
    }


    private void sendMessage(Long id, String type){
        // 发送消息
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            logger.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }
}
