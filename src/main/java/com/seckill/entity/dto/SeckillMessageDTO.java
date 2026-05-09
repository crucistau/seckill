package com.seckill.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 秒杀消息体。
 * 作为 MQ 中的最小必要载荷，仅携带用户和商品标识。
 */
@Data
public class SeckillMessageDTO implements Serializable {

    /** 秒杀用户 ID。 */
    private Long userId;

    /** 秒杀商品 ID。 */
    private Long goodsId;
}
