package com.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seckill.entity.User;

/**
 * 用户服务。
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录。
     */
    User login(String nickname, String password);

    /**
     * 根据用户 ID 查询用户。
     */
    User getUserById(Long id);
}
