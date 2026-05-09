package com.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seckill.entity.SeckillGoods;

/**
 * 秒杀商品服务。
 */
public interface ISeckillGoodsService extends IService<SeckillGoods> {

    /**
     * 根据商品 ID 查询秒杀商品配置。
     */
    SeckillGoods getByGoodsId(Long goodsId);

    /**
     * 扣减秒杀库存。
     */
    boolean reduceStock(Long goodsId);
}
