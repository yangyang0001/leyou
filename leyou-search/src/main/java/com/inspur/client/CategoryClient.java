package com.inspur.client;

import com.inspur.entity.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-15 11:32
 */
@FeignClient(name = "item-service")
public interface CategoryClient extends CategoryApi {
}
