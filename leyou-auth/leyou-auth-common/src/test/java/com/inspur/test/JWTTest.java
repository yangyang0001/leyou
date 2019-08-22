package com.inspur.test;

import com.inspur.common.entity.UserInfo;
import com.inspur.common.util.JwtUtils;
import com.inspur.common.util.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-20 14:14
 */
public class JWTTest {

    private static final String pubKeyPath = "E:\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "E:\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 生成公钥和私钥的方法
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU2NjI4MzEwNX0.N44J5ZzXakuEAAi7RrtgnrRyCBi8m3mtTWZ4FGmVzQHOM1W-zibN5MRDBFllJgJnfZ65h65R4r5Vn36Y3PquoNHp7z5WTKtOejo6PbtzhvIgeCgUaL5olck4VLWP5G1_CRp9-3VKP7IpBnj9PmS8wUiVzHc2hCyMgW43EIYf2UI";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
