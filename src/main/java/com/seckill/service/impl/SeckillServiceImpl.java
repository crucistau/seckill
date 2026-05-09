package com.seckill.service.impl;

import com.seckill.entity.User;
import com.seckill.entity.dto.SeckillMessageDTO;
import com.seckill.entity.vo.OrderDetailVO;
import com.seckill.entity.vo.SeckillResultVO;
import com.seckill.mq.SeckillMQSender;
import com.seckill.service.ISeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀主流程实现。
 * 这里负责请求准入控制和异步下单调度，不直接落库创建订单。
 */
@Slf4j
@Service
public class SeckillServiceImpl implements ISeckillService {

    /** Redis 秒杀库存键前缀。 */
    private static final String STOCK_PREFIX = "seckill:stock:";
    /** Redis 用户秒杀判重键前缀。 */
    private static final String USER_PREFIX = "seckill:user:";
    /** Redis 秒杀结果键前缀。 */
    private static final String RESULT_PREFIX = "seckill:result:";

    /**
     * Lua 脚本用于在 Redis 中原子检查并扣减库存。
     * 返回剩余库存，小于 0 表示库存不足。
     */
    private static final String STOCK_LUA =
            "if redis.call('exists', KEYS[1]) == 1 then " +
            "  local stock = tonumber(redis.call('get', KEYS[1])); " +
            "  if stock > 0 then " +
            "    redis.call('decr', KEYS[1]); " +
            "    return stock - 1; " +
            "  else " +
            "    return -1; " +
            "  end; " +
            "else " +
            "  return -1; " +
            "end";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SeckillMQSender mqSender;

    @Override
    public SeckillResultVO doSeckill(User user, Long goodsId) {
        String stockKey = STOCK_PREFIX + goodsId;
        String userKey = USER_PREFIX + user.getId() + ":" + goodsId;

        // 先通过 SET NX 做用户级判重，避免同一用户重复请求。
        Boolean isFirst = redisTemplate.opsForValue()
                .setIfAbsent(userKey, "1", 60, TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(isFirst)) {
            return buildResult(null, goodsId, -1, "请勿重复秒杀");
        }

        // 再通过 Lua 脚本原子预减库存，减少数据库热点竞争。
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(STOCK_LUA, Long.class);
        Long remain = redisTemplate.execute(script, Collections.singletonList(stockKey));
        if (remain == null || remain < 0) {
            return buildResult(null, goodsId, -1, "库存不足");
        }

        // 将下单请求投递到 MQ，由消费者异步创建订单。
        SeckillMessageDTO dto = new SeckillMessageDTO();
        dto.setUserId(user.getId());
        dto.setGoodsId(goodsId);
        mqSender.sendSeckillMessage(dto);

        // 立即返回排队中，前端通过轮询接口继续查询最终结果。
        return buildResult(null, goodsId, 0, "排队中");
    }

    @Override
    public SeckillResultVO getSeckillResult(Long userId, Long goodsId) {
        String resultKey = RESULT_PREFIX + userId + ":" + goodsId;
        String val = redisTemplate.opsForValue().get(resultKey);

        if (val == null) {
            return buildResult(null, goodsId, 0, "排队中");
        }
        long orderId = Long.parseLong(val);
        if (orderId > 0) {
            return buildResult(orderId, goodsId, 1, "秒杀成功");
        }
        return buildResult(null, goodsId, -1, "秒杀失败");
    }

    @Override
    public OrderDetailVO getOrderDetail(Long orderId) {
        throw new UnsupportedOperationException("待实现");
    }

    /**
     * 统一封装秒杀结果对象。
     */
    private SeckillResultVO buildResult(Long orderId, Long goodsId, int status, String msg) {
        SeckillResultVO vo = new SeckillResultVO();
        vo.setOrderId(orderId);
        vo.setGoodsId(goodsId);
        vo.setStatus(status);
        vo.setMessage(msg);
        return vo;
    }
}
