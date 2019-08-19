package com.inspur.sms.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-19 14:51
 */
@ConfigurationProperties(prefix = "leyou.sms")
@Data
public class SmsConfig {

    String accessKeyId;

    String accessKeySecret;

    String signName;

    String verifyCodeTemplate;

    @Override
    public String toString() {
        return "SmsConfig{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", signName='" + signName + '\'' +
                ", verifyCodeTemplate='" + verifyCodeTemplate + '\'' +
                '}';
    }
}
