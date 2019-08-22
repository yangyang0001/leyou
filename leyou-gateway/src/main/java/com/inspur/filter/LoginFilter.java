package com.inspur.filter;

import com.inspur.LeyouGatewayApplication;
import com.inspur.common.entity.UserInfo;
import com.inspur.common.util.CookieUtils;
import com.inspur.common.util.JwtUtils;
import com.inspur.sms.config.AuthConfig;
import com.inspur.sms.config.LeyouAllowPathConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-21 01:00
 */
@Component
@EnableConfigurationProperties({AuthConfig.class, LeyouAllowPathConfig.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private LeyouAllowPathConfig leyouAllowPathConfig;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest req = ctx.getRequest();
        // 获取路径
        String requestURI = req.getRequestURI();
        // 判断白名单
        // 遍历允许访问的路径
        for (String path : this.leyouAllowPathConfig.getAllowPaths()) {
            // 然后判断是否是符合
            if(requestURI.startsWith(path)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //1.根据请求的上下文来获取Request对象
        RequestContext requestContext = RequestContext.getCurrentContext();

        //2.获取Request
        HttpServletRequest request = requestContext.getRequest();

        //3.获取Request中的Cookie value 就是 token值
        String token = CookieUtils.getCookieValue(request, authConfig.getCookieName(), "UTF-8");

        if(StringUtils.isBlank(token)){
            //如果为空
            requestContext.getResponse().setContentType("text/html;charset=UTF-8");
            //不转发请求
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseBody("token is null 身份未认证...");
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        //获取用户认证的信息,如果解析失败就不用过滤了
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, authConfig.getPublicKey());
            System.out.println("Zuul server userToken is :" + token);
        } catch (Exception e) {
            e.printStackTrace();
            //如果解析异常,不转发
            requestContext.getResponse().setContentType("text/html;charset=UTF-8");
            //不转发请求
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseBody("token is erro 身份认证有误...");
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        //上面的都没有问题的话,则调用服务处理业务,这里不用自己实现,框架自己帮助我们实现了!
        return null;

    }
}
