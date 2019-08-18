package com.inspur.client;

import com.inspur.entity.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-15 11:06
 */
@FeignClient(name = "item-service")
public interface GoodsClient extends GoodsApi {


}
