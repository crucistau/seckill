package com.seckill.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求参数。
 */
@Data
public class LoginDTO {

    /** 登录昵称。 */
    @NotBlank(message = "用户名不能为空")
    private String nickname;

    /** 明文密码或前端加密密码，取决于前后端约定。 */
    @NotBlank(message = "密码不能为空")
    private String password;
}
