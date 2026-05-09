package com.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seckill.entity.SeckillOrder;

/**
 * 秒杀订单服务。
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 根据用户 ID 和商品 ID 查询秒杀订单。
     */
    SeckillOrder getByUserIdAndGoodsId(Long userId, Long goodsId);

    /**
     * 新增秒杀订单关系记录。
     */
    boolean insert(SeckillOrder seckillOrder);
}
