package com.inspur.test;

import com.inspur.entity.Goods;
import com.inspur.GoodsRepository;
import com.inspur.LeyouSearchApplication;
import com.inspur.client.GoodsClient;
import com.inspur.common.entity.PageResult;
import com.inspur.entity.bo.SpuBo;
import com.inspur.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-15 13:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LeyouSearchApplication.class})
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    /**
     * 创建索引Index和 Mapping映射的关系
     */
    @Test
    public void testCreateIndexAndMapping() {
        this.elasticsearchTemplate.createIndex(Goods.class);
        this.elasticsearchTemplate.putMapping(Goods.class);
    }

    /**
     * 导入数据
     */
    @Test
    public void testImportData() {
        //分批次 获取所有的SPU的数据
        for (int page = 1,rows = 100; ;page ++) {
            PageResult<SpuBo> spuBoPageResult = goodsClient.queryByPage(null, null, page, rows);
            if (spuBoPageResult == null ||
                spuBoPageResult.getItems() == null ||
                spuBoPageResult.getItems().size() == 0){
                break;
            } else {
                List<Goods> goodsList = spuBoPageResult.getItems().stream().map(spuBo -> {
                    try {
                        return searchService.buildGoods(spuBo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
                goodsRepository.saveAll(goodsList);
            }
        }
    }
}
