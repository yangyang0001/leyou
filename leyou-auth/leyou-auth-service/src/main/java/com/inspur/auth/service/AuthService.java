package com.inspur.auth.service;

import com.inspur.client.UserClient;
import com.inspur.common.entity.UserInfo;
import com.inspur.common.util.JwtUtils;
import com.inspur.config.AuthConfig;
import com.inspur.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-20 15:00
 */
@Service
@EnableConfigurationProperties(AuthConfig.class)
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private AuthConfig authConfig;

    public String accredit(String username, String password) {
        //根据用户名查询 获取用户信息
        User user = userClient.queryUser(username, password);
        if(user == null) {
            return null;
        }
        //生成token
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        String token = null;
        try {
            token = JwtUtils.generateToken(userInfo, authConfig.getPrivateKey(), authConfig.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }
}
