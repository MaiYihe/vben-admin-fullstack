package com.vbenadmin.backend.auth.service;

import java.util.List;

import com.vbenadmin.backend.auth.models.dto.TokenPairDTO;
import com.vbenadmin.backend.auth.models.request.LoginRequest;
import com.vbenadmin.backend.auth.models.request.RegisterRequest;

public interface IAuthService {
    /**
     * 去数据库中，根据用户名查询加密密码；** (sql 数据库读操作)**
     *  用户存在，继续
     *  用户不存在，抛出异常，异常返回
     * 比对查询密码和请求密码
     *  密码匹配：生成双 token
     *  - 写入内存 ** (Redis 操作)**
     *  - 正常返回双 token
     *  密码不匹配：抛出异常，异常返回
     * @param request
     * @return JWT token
     */
    TokenPairDTO login(LoginRequest request);

    /**
     * 去数据库中，根据用户名查询用户是否存在 ** (sql 数据库读操作)**
     *  用户存在：抛出异常，异常返回
     *  用户不存在：
     *  - 将密码加密，把用户和密码一起写入数据库 **（sql 数据库写操作）**
     *  - 生成 JWT(双 Token),写入内存 ** (Redis 操作)**
     *  
     *  - jti 写入 Redis
     *  - 正常返回双 token
     * @param request
     * @return JWT token
     */
    TokenPairDTO register(RegisterRequest request);

    /**
     * 已经到这里，说明 Access Token 是没问题的（前端会在 Access Token 过期前提前发送请求来更新）
     * 解析验证 refreshToken 是否正确
     *  如果正确：继续
     *  如果不正确：抛出异常
     * 校验 jti 的 Redis 状态 ** (Redis 操作)**
     *  如果 jti 存在：继续
     *  如果 jti 不存在：说明被吊销，抛出异常
     * 从 refreshToken 的 Claims 中提取 userId，重新生成新的 Access Token；
     * @param refreshToken
     * @return Access Token
     */
    String refreshToken(String refreshToken);

    /**
     * 检查 refreshToken
     *  如果正确：继续
     *  如果不正确：抛出异常（前端可能清除 cookie 了，或太久没用过期了）
     * 解析 token，拿到 jti
     * 根据 jti，手动吊销 Redis 当中的 RefreshToken ** (Redis 操作)**
     *  如果删除成功：继续
     *  如果删除失败（说明 Redis 里面已经没有 jti 了）：不用抛出异常
     */
    void logout(String refreshToken);

    /**
     * 用途：返回当前登录用户的权限码（permission codes / access codes）列表
     * 根据 userId 查询得到角色->权限 **（sql 数据库操作）**
     * @return
     */
    List<String> getAuthCodes();
}
