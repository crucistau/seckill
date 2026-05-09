package com.seckill.controller;

import com.seckill.entity.User;
import com.seckill.entity.dto.LoginDTO;
import com.seckill.entity.vo.Result;
import com.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 登录接口。
 * 登录成功后会把用户信息写入 Session，供后续秒杀请求复用。
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录。
     */
    @PostMapping
    public Result<Void> login(@Valid @RequestBody LoginDTO dto, HttpSession session) {
        User user = userService.login(dto.getNickname(), dto.getPassword());
        session.setAttribute("user", user);
        return Result.success();
    }
}
