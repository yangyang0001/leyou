package com.inspur.config;

import com.inspur.common.util.RsaUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-20 14:43
 */
@ConfigurationProperties(prefix = "leyou.jwt")
@Data
public class AuthConfig {

    private static final Logger logger = LoggerFactory.getLogger(AuthConfig.class);

    public String secret;
    public String pubKeyPath;   //公钥地址
    public String priKeyPath;   //私钥地址
    public Integer expire;       //过期时间
    public String cookieName;


    public PublicKey publicKey;   //公钥
    public PrivateKey privateKey; //私钥

    @Override
    public String toString() {
        return "AuthConfig{" +
                "secret='" + secret + '\'' +
                ", pubKeyPath='" + pubKeyPath + '\'' +
                ", priKeyPath='" + priKeyPath + '\'' +
                ", expire='" + expire + '\'' +
                ", cookieName='" + cookieName + '\'' +
                '}';
    }

    /**
     * @PostContruct：在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init(){
        try {
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            logger.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException();
        }
    }
}
