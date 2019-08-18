package com.inspur.client;

import com.inspur.entity.SpuDetail;
import com.inspur.entity.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-15 11:06
 */
@FeignClient(name = "item-service")
public interface GoodsClient extends GoodsApi {


}
