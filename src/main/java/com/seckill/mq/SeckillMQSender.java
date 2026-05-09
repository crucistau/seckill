package com.seckill.mq;

import com.alibaba.fastjson2.JSON;
import com.seckill.config.RabbitMQConfig;
import com.seckill.entity.dto.SeckillMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 秒杀消息发送器。
 * 将秒杀请求封装后投递到 RabbitMQ，削峰并异步下单。
 */
@Slf4j
@Component
public class SeckillMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀消息。
     */
    public void sendSeckillMessage(SeckillMessageDTO dto) {
        String msg = JSON.toJSONString(dto);
        log.info("发送秒杀消息: {}", msg);
        Message message = MessageBuilder
                .withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        rabbitTemplate.send(
                RabbitMQConfig.SECKILL_EXCHANGE,
                RabbitMQConfig.SECKILL_ROUTING_KEY,
                message
        );
    }
}
