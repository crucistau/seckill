package com.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品基础信息实体，对应表 {@code t_goods}。
 */
@Data
@TableName("t_goods")
public class Goods {

    /** 商品主键。 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 商品名称。 */
    private String goodsName;

    /** 商品标题。 */
    private String goodsTitle;

    /** 商品图片地址。 */
    private String goodsImg;

    /** 商品详情描述。 */
    private String goodsDetail;

    /** 商品原价。 */
    private BigDecimal goodsPrice;

    /** 普通商品库存。 */
    private Integer goodsStock;

    /** 创建时间。 */
    private LocalDateTime createTime;

    /** 更新时间。 */
    private LocalDateTime updateTime;
}
