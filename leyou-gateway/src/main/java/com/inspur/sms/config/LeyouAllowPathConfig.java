package com.inspur.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-21 01:23
 */
@ConfigurationProperties(prefix = "leyou.filter")
@Data
public class LeyouAllowPathConfig {

    private List<String> allowPaths;

    @Override
    public String toString() {
        return "LeyouAllowPathConfig{" +
                "allowPaths=" + allowPaths +
                '}';
    }
}
