package com.inspur.sms.listener;

import com.inspur.sms.config.SmsConfig;
import com.inspur.sms.util.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-19 15:43
 */
@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private SmsConfig smsConfig;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SMS.QUEUE", durable = "true"),
            exchange = @Exchange(
                    value = "LEYOU.SMS.EXCHANGE",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"sms.code"}))
    public void sendCodeSms(Map<String, String> msg) {

        if(CollectionUtils.isEmpty(msg)) {
            return ;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");
        if(StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            return ;
        }
        try {
            smsUtil.sendSms(phone, code, smsConfig.getSignName(), smsConfig.getVerifyCodeTemplate());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
}
