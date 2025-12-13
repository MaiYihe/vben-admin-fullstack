package com.vbenadmin.backend.auth.contoller;

import com.vbenadmin.backend.auth.models.dto.TokenPairDTO;
import com.vbenadmin.backend.auth.models.request.LoginRequest;
import com.vbenadmin.backend.auth.models.request.RegisterRequest;
import com.vbenadmin.backend.auth.service.IAuthService;
import com.vbenadmin.backend.commoncore.annotation.AccessCheck;
import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import com.vbenadmin.backend.commonweb.security.UserContextHolder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
    public ResponseEntity<ApiResponse<TokenVO>> login(@Validated @RequestBody LoginRequest request,
            HttpServletResponse response) {
        TokenPairDTO tokenPair = authService.login(request);

        // 为 response 的 http 写入 cookie（refreshToken）
        addRefreshTokenCookie(response, tokenPair);
        // 生成 tokenVO
        TokenVO tokenVO = TokenVO.builder()
                .accessToken(tokenPair.getAuthToken())
                .build();
        return ResponseEntity.ok(ApiResponse.success(tokenVO));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<TokenVO>> register(@Validated @RequestBody RegisterRequest request,
            HttpServletResponse response) {
        TokenPairDTO tokenPair = authService.register(request);

        addRefreshTokenCookie(response, tokenPair);
        TokenVO tokenVO = TokenVO.builder()
                .accessToken(tokenPair.getAuthToken())
                .build();

        return ResponseEntity.ok(ApiResponse.success(tokenVO));
    }

    /**
     * 刷新 authToken
     * 
     * @return String authToken
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refresh(@Validated @RequestBody String refreshToken) {
        String token = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResponse.success(token));
    }

    /**
     * 登出接口
     * AccessCheck：需要先登入才能登出（防止内部微服务绕过网关调用）
     */
    @AccessCheck
    @PostMapping("logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @Validated @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response) {
        // 交给 service 处理
        authService.logout(refreshToken);
        // response 删除 refreshToken 的 cookie
        delRefreshTokenCookie(response);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 获取用户权限码
     * AccessCheck：需要先登入才能查权限码（防止内部微服务绕过网关调用）
     */
    @AccessCheck
    @GetMapping("/codes")
    public ResponseEntity<ApiResponse<List<String>>> getAccessCodes() {
        String userId = UserContextHolder.get().getUserId();
        List<String> codes = authService.getAccessCodes(userId);
        return ResponseEntity.ok(ApiResponse.success(codes));
    }

    private void addRefreshTokenCookie(HttpServletResponse response, TokenPairDTO tokenPair) {
        Cookie cookie = new Cookie("refreshToken", tokenPair.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 才能传输（生产环境必须开启）
        cookie.setPath("/"); // 所有接口都可带上 Cookie
        cookie.setMaxAge(tokenPair.getRefreshExipre().intValue()); // 告诉浏览器何时自动删除 refreshToken Cookie
        response.addCookie(cookie);

        // 单独追加 SameSite 属性（None 的时候允许跨域访问）
        String cookieValue = String.format(
                "refreshToken=%s; HttpOnly; Secure; Path=/; SameSite=None; Max-Age=%d",
                tokenPair.getRefreshToken(),
                tokenPair.getRefreshExipre().intValue());

        response.setHeader("Set-Cookie", cookieValue);
    }

    private void delRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 才能传输（生产环境必须开启）
        cookie.setPath("/"); // 所有接口都可带上 Cookie
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
