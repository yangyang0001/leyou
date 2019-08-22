package com.inspur.interceptor;

import com.inspur.common.entity.UserInfo;
import com.inspur.common.util.CookieUtils;
import com.inspur.common.util.JwtUtils;
import com.inspur.config.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-21 17:53
 */
@Component
@EnableConfigurationProperties(AuthConfig.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    @Autowired
    private AuthConfig authConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            //首先获取 cookie 中的 token
            String token = CookieUtils.getCookieValue(request, authConfig.getCookieName(), "UTF-8");
            // 解析token 获取用户信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, authConfig.getPublicKey());
            THREAD_LOCAL.set(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 清空 当前线程的局部变量 必须清空,因为这里的线程是 tomcat 的线程池中的线程,线程是不会结束的!
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        THREAD_LOCAL.remove();
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 提供静态方法 获取userInfo
     * @return
     */
    public static UserInfo getUserInfo() {
        return THREAD_LOCAL.get();
    }

}
