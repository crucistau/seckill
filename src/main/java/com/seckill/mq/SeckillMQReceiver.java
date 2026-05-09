package com.seckill.mq;

import com.alibaba.fastjson2.JSON;
import com.seckill.config.RabbitMQConfig;
import com.seckill.entity.User;
import com.seckill.entity.dto.SeckillMessageDTO;
import com.seckill.service.IOrderService;
import com.seckill.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 秒杀消息消费者。
 * 负责消费队列中的请求并异步创建订单，再把结果写回 Redis。
 */
@Slf4j
@Component
public class SeckillMQReceiver {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /** Redis 中秒杀结果键前缀。 */
    private static final String RESULT_PREFIX = "seckill:result:";

    /**
     * 消费秒杀消息并落库创建订单。
     */
    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("收到秒杀消息: {}", message);
        SeckillMessageDTO dto = JSON.parseObject(message, SeckillMessageDTO.class);
        Long userId = dto.getUserId();
        Long goodsId = dto.getGoodsId();
        String resultKey = RESULT_PREFIX + userId + ":" + goodsId;

        try {
            User user = userService.getById(userId);
            if (user == null) {
                redisTemplate.opsForValue().set(resultKey, "-1");
                return;
            }
            Long orderId = orderService.createOrder(user, goodsId);
            redisTemplate.opsForValue().set(resultKey, String.valueOf(orderId));
            log.info("秒杀成功 userId={}, goodsId={}, orderId={}", userId, goodsId, orderId);
        } catch (Exception e) {
            log.error("秒杀失败 userId={}, goodsId={}", userId, goodsId, e);
            redisTemplate.opsForValue().set(resultKey, "-1");
        }
    }
}
