package com.vbenadmin.backend.commonweb.exception;

import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

// BizException 定义在 common-core 里面
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 专门处理 BizException（基础异常）
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthException(BizException ex) {
        // ✅ 按大厂规范：HTTP 状态码固定 200，由业务 code 表示错误
        return ResponseEntity.ok(ApiResponse.fail(ex.getCode(), ex.getMessage()));
    }

    /**
     * 处理参数校验异常（@Valid/@Validated）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {

        // 从 BindingResult 中提取所有校验错误信息
        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        // 返回统一格式
        return ResponseEntity.ok(ApiResponse.fail(400, errorMsg));
    }

    /**
     * 兜底异常处理（防止未捕获的异常直接返回 500 HTML）
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        // 打印日志（可选）
        ex.printStackTrace();

        // 返回统一错误格式
        return ResponseEntity.ok(ApiResponse.fail(500, "服务器内部错误：" + ex.getMessage()));
    }
}
