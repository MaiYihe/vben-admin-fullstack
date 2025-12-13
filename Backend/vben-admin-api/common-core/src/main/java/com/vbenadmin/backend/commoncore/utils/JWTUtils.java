package com.vbenadmin.backend.commoncore.utils;

import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commoncore.models.others.jwt.TokenPayload;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JWTUtils {
    // 生成一个至少 256 位（32 字节）的密钥
    // private static final SecretKey key =
    // Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final SecretKey key = Keys.hmacShaKeyFor(
            "12345678901234567890123456789012".getBytes(StandardCharsets.UTF_8));

    // 生成 JWT（包含 AccessToken 和 RefreshToken 两种情况）
    public static String createToken(TokenPayload tokenPayload) {
        // Header （可选，但更规范）
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // Claims（自定义载荷）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", tokenPayload.getUserId());
        claims.put("accessCodes", tokenPayload.getAccessCodes());
        claims.put("jti", tokenPayload.getJti());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeader(header)
                .setClaims(claims) // 设置自定义数据
                .setIssuedAt(new Date(tokenPayload.getIssuedAt())) // 设置签发时间
                .setExpiration(new Date(tokenPayload.getExpireTime())) // 设置过期时间
                .signWith(key, SignatureAlgorithm.HS256); // 使用 HMAC-SHA256 签名

        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * JWT 解析出 TokenPayload
     **/
    public static TokenPayload parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key) // 🔐 校验签名
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.get("userId", String.class);
            String jti = claims.get("jti", String.class);

            // accessCodes 需要特殊处理（List 反序列化）
            List<?> rawList = claims.get("accessCodes", List.class);
            List<String> accessCodes = rawList == null
                    ? Collections.emptyList()
                    : rawList.stream()
                            .map(String::valueOf)
                            .toList();

            Date issuedAt = claims.getIssuedAt();
            Date expiration = claims.getExpiration();

            return TokenPayload.builder()
                    .userId(userId)
                    .accessCodes(accessCodes)
                    .jti(jti)
                    .issuedAt(issuedAt.getTime())
                    .expireTime(expiration.getTime())
                    .build();

        } catch (ExpiredJwtException e) {
            throw new BizException(401, "Token 已过期");
        } catch (JwtException e) {
            // 包含签名错误、格式错误等
            throw new BizException(401, "非法 Token");
        }
    }

    // 解析 JWT 并验证其合法性
    // 1. 验证合法性
    // 2. 验证正确性
    public static Map<String, Object> checkToken(String token) {
        log.info("JWTUtils 收到的 token ：" + token);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key) // 设置验证的 Key
                    .build()
                    .parseClaimsJws(token) // 解析并验证 JWT
                    .getBody();

            return claims;
        } catch (ExpiredJwtException e) { // 先抛出子类异常
            log.warn("Token 已过期");
            return null; // 外层决定抛出 BizException
        } catch (JwtException e) {
            log.warn("Token 不合法: {}", e.getMessage());
            return null;
        }
    }

    // // main 方法来验证
    // public static void main(String[] args) {
    // String token = JWTUtils.createToken(100L);
    // System.out.println(token);
    // Map<String,Object> map = JWTUtils.checkToken(token);
    // System.out.println(map.get("userId"));
    //
    // }
}
