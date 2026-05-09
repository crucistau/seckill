package com.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seckill.entity.SeckillGoods;
import org.apache.ibatis.annotations.Param;

/**
 * 秒杀商品表 Mapper。
 */
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {

    /**
     * 原子扣减秒杀库存。
     *
     * @param goodsId 商品 ID
     * @return 影响行数，0 表示库存不足
     */
    int reduceStock(@Param("goodsId") Long goodsId);
}
