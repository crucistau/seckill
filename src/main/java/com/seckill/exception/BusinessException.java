package com.seckill.exception;

import lombok.Getter;

/**
 * 业务异常。
 * 用于表达库存不足、重复秒杀等可预期业务失败。
 */
@Getter
public class BusinessException extends RuntimeException {

    /** 业务错误码。 */
    private final int code;

    /**
     * 使用默认错误码构造异常。
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    /**
     * 使用自定义错误码构造异常。
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
