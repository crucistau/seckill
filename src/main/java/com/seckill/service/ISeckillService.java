package com.seckill.service;

import com.seckill.entity.User;
import com.seckill.entity.vo.OrderDetailVO;
import com.seckill.entity.vo.SeckillResultVO;

/**
 * 秒杀主流程服务。
 */
public interface ISeckillService {

    /**
     * 发起秒杀请求。
     */
    SeckillResultVO doSeckill(User user, Long goodsId);

    /**
     * 获取秒杀处理结果，供前端轮询。
     */
    SeckillResultVO getSeckillResult(Long userId, Long goodsId);

    /**
     * 获取订单详情。
     */
    OrderDetailVO getOrderDetail(Long orderId);
}
