package com.inspur;

import com.inspur.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-15 13:53
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
