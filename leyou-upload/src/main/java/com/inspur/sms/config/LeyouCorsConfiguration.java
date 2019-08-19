package com.inspur.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 14:51
 * 添加跨域访问
 */
@Configuration
public class LeyouCorsConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        //1.创建cors配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 表示当前域名可以跨域访问, 如果要携带cookie 则不能写 * , 因为 * 表示所有域名都可以访问
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
        corsConfiguration.setAllowCredentials(true);    //表示允许携带cookie
        corsConfiguration.addAllowedMethod("*");        //允许任何方法都能跨域访问
        corsConfiguration.addAllowedHeader("*");        //允许携带任何的头信息

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        //2.添加映射路径，我们拦截一切请求
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(corsConfigurationSource);
    }
}
