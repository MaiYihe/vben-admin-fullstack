package com.vbenadmin.backend.commonweb.aop;

import com.vbenadmin.backend.commoncore.annotation.AccessCheck;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commoncore.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
@Aspect
@RequiredArgsConstructor
public class AccessCheckAspect {
    private final RedisUtils redisUtils;

    @Around("@annotation(accessCheck) || @within(accessCheck)")
    public Object checkAccess(ProceedingJoinPoint joinPoint, AccessCheck accessCheck) throws Throwable {

        // @within(accessCheck)的情况：如果方法上没有（比如来自类上的注解），需要手动取（方法->类->注解）
        if (accessCheck == null) {
            // 获取类上的注解
            Class<?> targetClass = joinPoint.getTarget().getClass();
            accessCheck = targetClass.getAnnotation(AccessCheck.class);
        }

        // 拿到所有的 required 权限
        String[] requiredCodes = accessCheck.value();

        // 获取 Request
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new BizException(40000,"非 HTTP 请求，无法鉴权");
        }
        HttpServletRequest request = attrs.getRequest();

        // 获取 userId
        String userId = request.getHeader("X-User-Id");
        if (userId == null) {
            throw new BizException(40000,"请求头缺失 X-User-Id");
        }

        // 从 Redis 拿用户权限
        List<String> accessCodes = redisUtils.get(
                "user:access:" + userId,
                List.class
        );

        if (accessCodes == null) {
            throw new BizException(40300,"无权限访问");
        }

        // 权限校验：required ⊆ userAccess
        for (String required : requiredCodes) {
            if (!accessCodes.contains(required)) {
                throw new BizException(40300,"无权限访问：" + required);
            }
        }

        return joinPoint.proceed();
    }

}
