package com.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体，对应表 {@code t_order}。
 */
@Data
@TableName("t_order")
public class Order {

    /** 订单主键。 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 下单用户 ID。 */
    private Long userId;

    /** 商品 ID。 */
    private Long goodsId;

    /** 收货地址 ID，当前项目中可为空。 */
    private Long deliveryAddrId;

    /** 下单时的商品名称快照。 */
    private String goodsName;

    /** 购买数量。 */
    private Integer goodsCount;

    /** 下单时的商品价格快照。 */
    private BigDecimal goodsPrice;

    /** 下单渠道。 */
    private Integer orderChannel;

    /** 订单状态。 */
    private Integer status;

    /** 创建时间。 */
    private LocalDateTime createTime;

    /** 更新时间。 */
    private LocalDateTime updateTime;

    /** 支付时间。 */
    private LocalDateTime payTime;
}
