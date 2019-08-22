package com.inspur.auth.controller;

import com.inspur.auth.service.AuthService;
import com.inspur.common.entity.UserInfo;
import com.inspur.common.util.CookieUtils;
import com.inspur.common.util.JwtUtils;
import com.inspur.config.AuthConfig;
import com.netflix.client.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-20 14:59
 */
@Controller
@EnableConfigurationProperties(AuthConfig.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthConfig authConfig;

    @PostMapping("/accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        String token = this.authService.accredit(username, password);

        if(StringUtils.isBlank(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CookieUtils.setCookie(request, response, authConfig.getCookieName(), token, authConfig.getExpire() * 60);
        return ResponseEntity.ok().build();
    }

    /**
     * 解析token 获取 payload中的载荷
     * @param token
     * @return
     */
    @GetMapping("/verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN") String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, authConfig.getPublicKey());
            if(userInfo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            //刷新token
            token = JwtUtils.generateToken(userInfo, authConfig.getPrivateKey(), authConfig.getExpire());
            //刷新cookie
            CookieUtils.setCookie(request, response, authConfig.getCookieName(), token, authConfig.getExpire() * 60);

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
