package com.vbenadmin.backend.auth.contoller;

import com.vbenadmin.backend.auth.models.request.LoginRequest;
import com.vbenadmin.backend.auth.models.request.RegisterRequest;
import com.vbenadmin.backend.auth.service.IAuthService;
import com.vbenadmin.backend.commoncore.annotation.AccessCheck;
import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import com.vbenadmin.backend.auth.models.vo.TokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenVO>> login(@Validated @RequestBody LoginRequest request) {
        TokenVO tokens = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(tokens));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<TokenVO>> register(@Validated @RequestBody RegisterRequest request) {
        TokenVO tokens = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success(tokens));
    }

    @PostMapping("/refresh")
    public  ResponseEntity<ApiResponse<String>> refresh(@Validated @RequestBody String refreshToken) {
        String token = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResponse.success(token));
    }

    /**
     * 登出接口
     * AccessCheck：需要先登入才能登出（防止内部微服务调用）
     */
    @AccessCheck
    @PostMapping("logout")
    public ResponseEntity<ApiResponse<String>> logout(@Validated @CookieValue("refreshToken")  String refreshToken) {
        authService.logout(refreshToken); // 交给 service 处理
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 获取用户权限码
     * AccessCheck：需要先登入才能查权限码（防止内部微服务调用）
     */
    @AccessCheck
    @GetMapping("/codes")
    public ResponseEntity<ApiResponse<List<String>>> getAccessCodes(@RequestHeader("X-User-Id") Long userId) {
        List<String> codes = authService.getAccessCodes(userId);
        return ResponseEntity.ok(ApiResponse.success(codes));
    }
}
