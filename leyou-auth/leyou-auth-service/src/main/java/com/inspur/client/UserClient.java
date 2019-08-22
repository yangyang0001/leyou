package com.inspur.client;

import com.inspur.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-20 15:25
 */
@FeignClient(serviceId = "user-service")
public interface UserClient extends UserApi {
}
