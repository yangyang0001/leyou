package com.inspur.user.service;

import com.inspur.util.CodecUtils;
import com.inspur.common.util.NumberUtils;
import com.inspur.entity.User;
import com.inspur.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-19 12:12
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CODE_PREFIX = "user:code.";



    public Boolean checkDataType(String data, Integer type) {
        User user = new User();
        if (data == null || type == null) {
            return null;
        }
        if (type == 1){
            user.setUsername(data);
        } else if (type == 2) {
            user.setPhone(data);
        } else {
            return null;
        }
        return this.userMapper.selectCount(user) == 0;
    }

    public void sendCode(String phone) {
        if(StringUtils.isBlank(phone)) {
            return ;
        }
        //生成验证码
        String code = NumberUtils.generateCode(6);
        //发送消息到RabbitMQ

        Map<String, String> codeMap = new HashMap<String, String>();
        codeMap.put("phone", phone);
        codeMap.put("code", code);
        amqpTemplate.convertAndSend("LEYOU.SMS.EXCHANGE", "sms.code", codeMap);

        //把验证码保存到redis中
        redisTemplate.opsForValue().set(CODE_PREFIX + phone, code, 5, TimeUnit.MINUTES);
    }

    public void register(String code, User user) {
        if(StringUtils.isBlank(code) ||
           StringUtils.isBlank(user.getUsername()) ||
           StringUtils.isBlank(user.getPassword()) ||
           StringUtils.isBlank(user.getPhone())) {
            return ;
        }

        //1.获取验证码进行比对
        String redisCode = redisTemplate.opsForValue().get(CODE_PREFIX + user.getPhone());
        if(redisCode == null || !code.equals(redisCode)) {
            return ;
        }

        //2.根据code生成slat
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        //3.加盐加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        user.setId(null);
        user.setCreated(new Date());

        //4.新增用户
        this.userMapper.insertSelective(user);

    }

    public User queryUser(String username, String password) {
        User user = new User();
        user.setUsername(username);

        //根据username返回数据库中的user
        User dbUser = this.userMapper.selectOne(user);
        if(dbUser == null) {
            return null;
        }

        //将输入的password 根据 数据库中的 slat 进行加密和 dbUser 的password 进行比较
        // 校验密码
        if (!dbUser.getPassword().equals(CodecUtils.md5Hex(password, dbUser.getSalt()))) {
            return null;
        }
        // 用户名密码都正确
        return dbUser;

    }
}
