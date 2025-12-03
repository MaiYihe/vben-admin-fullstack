package com.vbenadmin.backend.commoncore.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private Integer code;    // 状态码：200成功、400参数错误、500服务异常
    private String message;  // 消息：可读的提示语
    private T data;          // 泛型数据：任意返回对象

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(20000, "success", data);
    }

    public static <T> ApiResponse<T> fail(int code, String msg) {
        return new ApiResponse<>(code, msg, null);
    }
}