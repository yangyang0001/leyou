package com.inspur.api;

import com.inspur.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-20 15:21
 */
public interface UserApi {

    @GetMapping("/query")
    public User queryUser(@RequestParam("username") String username,
                          @RequestParam("password") String password);
}
