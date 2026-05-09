package com.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体，对应表 {@code t_user}。
 */
@Data
@TableName("t_user")
public class User {

    /** 用户主键。 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户昵称。 */
    private String nickname;

    /** 数据库存储密码。 */
    private String password;

    /** 用户个性化盐值。 */
    private String salt;

    /** 头像地址。 */
    private String head;

    /** 注册时间。 */
    private LocalDateTime registerDate;

    /** 最后登录时间。 */
    private LocalDateTime lastLoginDate;

    /** 累计登录次数。 */
    private Integer loginCount;

    /** 创建时间。 */
    private LocalDateTime createTime;

    /** 更新时间。 */
    private LocalDateTime updateTime;
}
