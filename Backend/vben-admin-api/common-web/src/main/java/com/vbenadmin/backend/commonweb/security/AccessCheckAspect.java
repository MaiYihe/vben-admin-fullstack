package com.vbenadmin.backend.commonweb.security;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.vbenadmin.backend.commoncore.annotation.AccessCheck;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonweb.context.UserContext;

import lombok.RequiredArgsConstructor;

@Component
@Aspect
@RequiredArgsConstructor
public class AccessCheckAspect {

    @Around("@annotation(accessCheck) || @within(accessCheck)")
    public Object checkAccess(ProceedingJoinPoint joinPoint, AccessCheck accessCheck) throws Throwable {

        // @within(accessCheck)的情况：如果方法上没有（比如来自类上的注解），需要手动取（方法->类->注解）
        if (accessCheck == null) {
            // 获取类上的注解
            Class<?> targetClass = joinPoint.getTarget().getClass();
            accessCheck = targetClass.getAnnotation(AccessCheck.class);
        }

        // 还是没有 @AccessCheck → 完全放行
        if (accessCheck == null) {
            return joinPoint.proceed();
        }

        // 拿到所有注解里的 required 权限
        // @AccessCheck  ✔ value = {}；@AccessCheck() ✔ value = {}
        String[] requiredCodes = accessCheck.value();
        // 如果 @AccessCheck 没有写任何 value 就直接放行
        if(requiredCodes.length == 0){
            return joinPoint.proceed();
        }

        // 从上下文中拿用户信息（关键）
        UserContext context = UserContextHolder.get();
        if (context == null) {
            throw new BizException(401, "未登录或登录状态失效");
        }

        Set<String> userCodes = context.getAccessCodes();
        if (userCodes == null || userCodes.isEmpty()) {
            throw new BizException(403, "无任何权限");
        }

        // 权限校验：required ⊆ userCodes
        for (String required : requiredCodes) {
            if (!userCodes.contains(required)) {
                throw new BizException(403, "无权限访问：" + required);
            }
        }

        return joinPoint.proceed();
    }

}
