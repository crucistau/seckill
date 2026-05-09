package com.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.dao.GoodsMapper;
import com.seckill.dao.SeckillGoodsMapper;
import com.seckill.entity.Goods;
import com.seckill.entity.vo.GoodsVO;
import com.seckill.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务实现。
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询商品列表及秒杀附加信息。
     * 当前为骨架代码，后续需关联 t_goods 与 t_seckill_goods。
     */
    @Override
    public List<GoodsVO> listGoodsVO() {
        throw new UnsupportedOperationException("待实现");
    }

    /**
     * 查询单个商品详情及秒杀状态。
     */
    @Override
    public GoodsVO getGoodsVOById(Long goodsId) {
        throw new UnsupportedOperationException("待实现");
    }

    /**
     * 通过数据库条件更新兜底扣减普通商品库存。
     * 即便 Redis 预减成功，这里仍需要防止最终超卖。
     */
    @Override
    public boolean reduceStock(Long goodsId) {
        return goodsMapper.update(null,
            new LambdaUpdateWrapper<Goods>()
                .setSql("goods_stock = goods_stock - 1")
                .eq(Goods::getId, goodsId)
                .gt(Goods::getGoodsStock, 0)
        ) > 0;
    }
}
