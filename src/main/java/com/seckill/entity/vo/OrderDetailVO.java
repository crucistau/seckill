package com.seckill.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单详情视图对象。
 */
@Data
public class OrderDetailVO {

    /** 订单 ID。 */
    private Long orderId;

    /** 商品名称。 */
    private String goodsName;

    /** 商品图片地址。 */
    private String goodsImg;

    /** 成交价格。 */
    private BigDecimal goodsPrice;

    /** 购买数量。 */
    private Integer goodsCount;

    /** 订单状态。 */
    private Integer orderStatus;

    /** 下单时间。 */
    private LocalDateTime createDate;
}
