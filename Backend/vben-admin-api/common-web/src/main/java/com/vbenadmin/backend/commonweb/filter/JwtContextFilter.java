package com.vbenadmin.backend.commonweb.filter;

import java.io.IOException;
import java.util.HashSet;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vbenadmin.backend.commoncore.models.others.jwt.TokenPayload;
import com.vbenadmin.backend.commoncore.utils.JWTUtils;
import com.vbenadmin.backend.commonweb.context.UserContext;
import com.vbenadmin.backend.commonweb.security.UserContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtContextFilter extends OncePerRequestFilter {

    // 不建立 JWT → UserContext 这套上下文的请求（这类请求不属于「认证请求」）
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/login")
                || path.startsWith("/auth/register")
                || path.startsWith("/auth/refresh");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);

            try {
                TokenPayload payload = JWTUtils.parseToken(token);

                UserContext context = new UserContext(
                        payload.getUserId(),
                        new HashSet<>(payload.getAuthCodes()),
                        token);

                UserContextHolder.set(context);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clear(); // ⚠️ 非常重要
        }
    }
}
