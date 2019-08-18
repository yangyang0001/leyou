package com.inspur.client;

import com.inspur.entity.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-15 11:33
 */
@FeignClient(name = "item-service")
public interface SpecificationClient extends SpecificationApi {
}
