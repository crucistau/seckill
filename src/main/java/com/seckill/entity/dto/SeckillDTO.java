package com.seckill.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 秒杀请求参数。
 */
@Data
public class SeckillDTO {

    /** 目标商品 ID。 */
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;
}
