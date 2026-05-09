package com.seckill.util;

import java.security.MessageDigest;

/**
 * MD5 加密工具。
 * 按项目约定提供前端口令、服务端口令和数据库口令之间的两段式加密转换。
 */
public class MD5Util {

    /** 固定表单盐值。 */
    private static final String SALT = "1a2b";

    /**
     * 计算字符串 MD5 值。
     */
    public static String md5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(src.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }

    /**
     * 第一次加密。
     * 前端输入密码转换为服务端表单密码。
     */
    public static String inputPassToFormPass(String inputPass) {
        return md5(SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(1) + SALT.charAt(3));
    }

    /**
     * 第二次加密。
     * 服务端表单密码转换为数据库存储密码。
     */
    public static String formPassToDBPass(String formPass, String salt) {
        return md5(salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(1) + salt.charAt(3));
    }

    /**
     * 合并两次加密流程。
     */
    public static String inputPassToDBPass(String inputPass, String salt) {
        return formPassToDBPass(inputPassToFormPass(inputPass), salt);
    }
}
