package com.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.dao.SeckillOrderMapper;
import com.seckill.entity.SeckillOrder;
import com.seckill.service.ISeckillOrderService;
import org.springframework.stereotype.Service;

/**
 * 秒杀订单服务实现。
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    /**
     * 根据用户和商品查询秒杀订单，用于防重判断。
     */
    @Override
    public SeckillOrder getByUserIdAndGoodsId(Long userId, Long goodsId) {
        return baseMapper.selectOne(
            new LambdaQueryWrapper<SeckillOrder>()
                .eq(SeckillOrder::getUserId, userId)
                .eq(SeckillOrder::getGoodsId, goodsId)
        );
    }

    /**
     * 插入秒杀订单关系记录。
     */
    @Override
    public boolean insert(SeckillOrder seckillOrder) {
        return baseMapper.insert(seckillOrder) > 0;
    }
}
