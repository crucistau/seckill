package com.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seckill.entity.Order;
import com.seckill.entity.User;
import com.seckill.entity.vo.GoodsVO;

/**
 * 订单服务。
 */
public interface IOrderService extends IService<Order> {

    /**
     * 根据订单 ID 查询订单。
     */
    Order getOrderById(Long orderId);

    /**
     * 旧版同步秒杀入口，当前已迁移到 MQ 异步下单链路。
     */
    Order seckill(User user, GoodsVO goods);

    /**
     * 创建秒杀订单及对应秒杀关系记录。
     */
    Long createOrder(User user, Long goodsId);
}
