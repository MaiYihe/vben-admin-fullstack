package com.vbenadmin.backend.gateway.filter;

import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.vbenadmin.backend.commoncore.utils.JWTUtils;
import com.vbenadmin.backend.gateway.config.IgnoreUrlsConfig;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * 验证 JWT
 * - Gateway 仅验证 token，不管权限（只验证 Token 是否存在、是否合法、是否过期）
 */
@Component
@RequiredArgsConstructor
public class GatewayGlobalFilter implements GlobalFilter {

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        // ✅ 匹配到白名单路径放行
        if (ignoreUrlsConfig.getUrls().stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        // ✅ 检查 token（是否携带）
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // ✅ 验证 JWT（JWT 格式、JWT 是否有效），验证成功则携带新请求
        // 去掉前缀 "Bearer "
        token = token.substring(7);
        // 调用 JWT 工具类验证
        Map<String, Object> claims = JWTUtils.checkToken(token);
        if (claims == null) {
            // 无效或过期
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
}
