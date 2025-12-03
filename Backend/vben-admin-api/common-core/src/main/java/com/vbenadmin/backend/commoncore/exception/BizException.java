package com.vbenadmin.backend.commoncore.exception;

public class BizException extends RuntimeException {
    // 业务状态码，例如：40101 表示“用户名或密码错误”
    private final int code;

    public BizException(int code, String message) {
        super(message); // 调用父类 RuntimeException 的 message 字段
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}