package com.seckill.entity.vo;

import lombok.Data;

/**
 * 秒杀结果视图对象。
 */
@Data
public class SeckillResultVO {

    /** 订单 ID，秒杀成功时有值。 */
    private Long orderId;

    /** 秒杀状态码，0-排队中，1-秒杀成功，-1-秒杀失败。 */
    private Integer status;

    /** 商品 ID，用于前端定位当前轮询商品。 */
    private Long goodsId;

    /** 返回给前端的提示信息。 */
    private String message;
}
