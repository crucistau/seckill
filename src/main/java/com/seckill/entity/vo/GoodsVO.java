package com.seckill.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情视图对象。
 * 在商品基础信息之上附带秒杀活动字段。
 */
@Data
public class GoodsVO {

    /** 商品 ID。 */
    private Long id;

    /** 商品名称。 */
    private String goodsName;

    /** 商品标题。 */
    private String goodsTitle;

    /** 商品图片地址。 */
    private String goodsImg;

    /** 商品详情。 */
    private String goodsDetail;

    /** 商品原价。 */
    private BigDecimal goodsPrice;

    /** 普通商品库存。 */
    private Integer goodsStock;

    /** 秒杀价。 */
    private BigDecimal seckillPrice;

    /** 秒杀库存。 */
    private Integer stockCount;

    /** 秒杀开始时间。 */
    private String startDate;

    /** 秒杀结束时间。 */
    private String endDate;

    /** 秒杀状态，0-未开始，1-进行中，2-已结束。 */
    private Integer seckillStatus;

    /** 距离秒杀开始或结束的剩余秒数。 */
    private Integer remainSeconds;
}
