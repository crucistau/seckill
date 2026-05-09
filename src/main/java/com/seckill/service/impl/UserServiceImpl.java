package com.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.dao.UserMapper;
import com.seckill.entity.User;
import com.seckill.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现。
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 登录逻辑尚未实现，后续应补充密码校验和登录次数更新。
     */
    @Override
    public User login(String nickname, String password) {
        throw new UnsupportedOperationException("待实现");
    }

    @Override
    public User getUserById(Long id) {
        return baseMapper.selectById(id);
    }
}
