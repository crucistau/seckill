package com.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.dao.OrderMapper;
import com.seckill.entity.Goods;
import com.seckill.entity.Order;
import com.seckill.entity.SeckillGoods;
import com.seckill.entity.SeckillOrder;
import com.seckill.entity.User;
import com.seckill.entity.vo.GoodsVO;
import com.seckill.exception.BusinessException;
import com.seckill.service.IGoodsService;
import com.seckill.service.IOrderService;
import com.seckill.service.ISeckillGoodsService;
import com.seckill.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 订单服务实现。
 * 真正的秒杀订单创建由 MQ 消费端异步调用本服务完成。
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Override
    public Order getOrderById(Long orderId) {
        return baseMapper.selectById(orderId);
    }

    /**
     * 同步秒杀入口已废弃，保留接口兼容性。
     */
    @Override
    public Order seckill(User user, GoodsVO goods) {
        throw new UnsupportedOperationException("已迁移至 MQ 异步下单");
    }

    /**
     * 创建普通订单记录和秒杀订单关系记录。
     * 事务保证扣库存、建订单、建秒杀订单三个动作要么全部成功，要么全部回滚。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(User user, Long goodsId) {
        Goods goods = goodsService.getById(goodsId);
        SeckillGoods seckillGoods = seckillGoodsService.getByGoodsId(goodsId);

        // 原子扣减秒杀库存，作为 Redis 预减之后的数据库兜底校验。
        boolean reduced = seckillGoodsService.reduceStock(goodsId);
        if (!reduced) {
            throw new BusinessException("库存不足");
        }

        // 创建正式订单，价格使用秒杀价而不是原价。
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsId);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(order);

        // 记录秒杀订单关系，用于快速判断是否重复购买。
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsId);
        seckillOrder.setCreateTime(new Date());
        seckillOrderService.insert(seckillOrder);

        return order.getId();
    }
}
