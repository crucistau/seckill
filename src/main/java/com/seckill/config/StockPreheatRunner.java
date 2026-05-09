package com.seckill.config;

import com.seckill.entity.SeckillGoods;
import com.seckill.service.ISeckillGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 应用启动后预热秒杀库存到 Redis。
 * 这样秒杀请求可以优先在 Redis 层完成扣减，减少数据库瞬时压力。
 */
@Slf4j
@Component
public class StockPreheatRunner implements CommandLineRunner {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /** Redis 中秒杀库存键前缀。 */
    private static final String STOCK_PREFIX = "seckill:stock:";

    @Override
    public void run(String... args) {
        List<SeckillGoods> list = seckillGoodsService.list();
        for (SeckillGoods sg : list) {
            String key = STOCK_PREFIX + sg.getGoodsId();
            Boolean set = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(sg.getStockCount()));
            log.info("预热库存: goodsId={}, stock={}, setResult={}", sg.getGoodsId(), sg.getStockCount(), set);
        }
        log.info("库存预热完成，共 {} 条记录", list.size());
    }
}
