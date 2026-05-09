package com.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 秒杀商品实体，对应表 {@code t_seckill_goods}。
 * 用于维护秒杀活动价格、库存和时间窗口。
 */
@Data
@TableName("t_seckill_goods")
public class SeckillGoods {

    /** 主键。 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联的普通商品 ID。 */
    private Long goodsId;

    /** 秒杀价格。 */
    private BigDecimal seckillPrice;

    /** 秒杀库存。 */
    private Integer stockCount;

    /** 秒杀开始时间。 */
    private LocalDateTime startDate;

    /** 秒杀结束时间。 */
    private LocalDateTime endDate;

    /** 创建时间。 */
    private LocalDateTime createTime;

    /** 更新时间。 */
    private LocalDateTime updateTime;
}
