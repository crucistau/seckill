package com.seckill.entity.vo;

import lombok.Data;

/**
 * 统一接口响应体。
 * 所有 Controller 默认使用该结构向前端返回 code、message 和 data。
 */
@Data
public class Result<T> {

    /** 业务状态码。 */
    private int code;

    /** 响应消息。 */
    private String message;

    /** 实际业务数据。 */
    private T data;

    private Result() {}

    /**
     * 构造成功响应。
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "success";
        result.data = data;
        return result;
    }

    /**
     * 构造无数据成功响应。
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 构造指定错误码的失败响应。
     */
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }

    /**
     * 构造默认 500 失败响应。
     */
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
}
