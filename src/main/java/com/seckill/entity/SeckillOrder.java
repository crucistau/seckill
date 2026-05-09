package com.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 秒杀订单关系实体，对应表 {@code t_seckill_order}。
 * 该表用于快速判断用户是否已成功秒杀某件商品。
 */
@Data
@TableName("t_seckill_order")
public class SeckillOrder {

    /** 主键。 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户 ID。 */
    private Long userId;

    /** 关联订单 ID。 */
    private Long orderId;

    /** 商品 ID。 */
    private Long goodsId;

    /** 创建时间。 */
    private Date createTime;
}
