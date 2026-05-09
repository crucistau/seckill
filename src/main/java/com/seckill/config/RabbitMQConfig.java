package com.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 基础设施配置。
 * 定义秒杀下单链路使用的队列、交换机和路由键。
 */
@Configuration
public class RabbitMQConfig {

    /** 秒杀请求队列。 */
    public static final String SECKILL_QUEUE = "seckill.queue";
    /** 秒杀主题交换机。 */
    public static final String SECKILL_EXCHANGE = "seckill.exchange";
    /** 秒杀消息路由键。 */
    public static final String SECKILL_ROUTING_KEY = "seckill.routing";

    /**
     * 持久化队列，避免 Broker 重启后丢失队列定义。
     */
    @Bean
    public Queue seckillQueue() {
        return QueueBuilder.durable(SECKILL_QUEUE).build();
    }

    /**
     * 主题交换机，便于后续扩展其他秒杀消息。
     */
    @Bean
    public TopicExchange seckillExchange() {
        return new TopicExchange(SECKILL_EXCHANGE);
    }

    /**
     * 将秒杀队列绑定到交换机。
     */
    @Bean
    public Binding seckillBinding() {
        return BindingBuilder.bind(seckillQueue()).to(seckillExchange()).with(SECKILL_ROUTING_KEY);
    }
}
