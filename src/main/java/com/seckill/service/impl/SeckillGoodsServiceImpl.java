package com.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.dao.SeckillGoodsMapper;
import com.seckill.entity.SeckillGoods;
import com.seckill.service.ISeckillGoodsService;
import org.springframework.stereotype.Service;

/**
 * 秒杀商品服务实现。
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {

    /**
     * 查询商品对应的秒杀活动配置。
     */
    @Override
    public SeckillGoods getByGoodsId(Long goodsId) {
        return baseMapper.selectOne(
            new LambdaQueryWrapper<SeckillGoods>().eq(SeckillGoods::getGoodsId, goodsId)
        );
    }

    /**
     * 调用自定义 SQL 扣减秒杀库存。
     */
    @Override
    public boolean reduceStock(Long goodsId) {
        return baseMapper.reduceStock(goodsId) > 0;
    }
}
