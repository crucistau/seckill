package com.seckill.controller;

import com.seckill.entity.User;
import com.seckill.entity.vo.OrderDetailVO;
import com.seckill.entity.vo.Result;
import com.seckill.entity.vo.SeckillResultVO;
import com.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀核心接口。
 * 提供发起秒杀、轮询结果和查询订单详情三个入口。
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private ISeckillService seckillService;

    /**
     * 发起秒杀请求。
     * 请求会先在 Redis 层做判重和预减库存，再通过 MQ 异步创建订单。
     */
    @PostMapping("/{goodsId}")
    public Result<SeckillResultVO> doSeckill(User user, @PathVariable Long goodsId) {
        if (user == null) {
            return Result.error(401, "请先登录");
        }
        return Result.success(seckillService.doSeckill(user, goodsId));
    }

    /**
     * 轮询秒杀结果。
     * 前端通过返回状态区分排队中、成功或失败。
     */
    @GetMapping("/result")
    public Result<SeckillResultVO> result(User user, Long goodsId) {
        if (user == null) {
            return Result.error(401, "请先登录");
        }
        return Result.success(seckillService.getSeckillResult(user.getId(), goodsId));
    }

    /**
     * 查询订单详情。
     */
    @GetMapping("/order/{orderId}")
    public Result<OrderDetailVO> orderDetail(User user, @PathVariable Long orderId) {
        if (user == null) {
            return Result.error(401, "请先登录");
        }
        return Result.success(seckillService.getOrderDetail(orderId));
    }
}
