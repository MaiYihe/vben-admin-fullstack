package com.vbenadmin.backend.auth.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vbenadmin.backend.auth.models.dto.TokenPairDTO;
import com.vbenadmin.backend.auth.models.request.LoginRequest;
import com.vbenadmin.backend.auth.models.request.RegisterRequest;
import com.vbenadmin.backend.auth.service.IAuthService;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commoncore.models.others.jwt.TokenPayload;
import com.vbenadmin.backend.commoncore.utils.JWTUtils;
import com.vbenadmin.backend.commoncore.utils.RedisUtils;
import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserCreateRequest;
import com.vbenadmin.backend.commonrpc.rpc.IRbacRpcService;
import com.vbenadmin.backend.commonrpc.rpc.IUserRpcService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {

    @DubboReference
    private IUserRpcService userRpcService;
    @DubboReference
    private IRbacRpcService rbacRpcService;

    private final RedisUtils redisUtils;

    public static final long ACCESS_EXPIRE = 30 * 60; // 30 min，单位秒
    public static final long REFRESH_EXPIRE = 7 * 24 * 60 * 60; // 7 days，单位秒

    @Override
    public TokenPairDTO login(LoginRequest request) {
        UserInfoDTO userInfoDTO = userRpcService.getUserInfoByUserName(request.getUsername());

        // 用户不存在，抛出异常
        if (userInfoDTO == null) {
            throw new BizException(40401, "用户不存在");
        }

        // 对比请求密码和查询密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = request.getPassword(); // 明文密码
        String hashedPassword = userInfoDTO.getPassword();

        if (!encoder.matches(rawPassword, hashedPassword)) {
            throw new BizException(40102, "用户名或密码错误");
        }

        // 创建 TokenPairDTO
        Long userId = userInfoDTO.getId();
        return generateTokenPair(userId);
    }

    @Override
    public TokenPairDTO register(RegisterRequest request) {
        String username = request.getUsername();
        // 根据用户名查询用户是否存在
        if (userRpcService.existUser(username)) {
            throw new BizException(40901, "用户名已存在");
        }

        // BCrypt 算法将密码加密
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(request.getPassword());

        // 把用户名和密码一起写入数据库
        UserCreateRequest userCreateRequest = new UserCreateRequest(username, hashedPassword);
        Long userId = userRpcService.createUser(userCreateRequest);

        // 创建 TokenPairDTO
        return generateTokenPair(userId);
    }

    @Override
    public String refreshToken(String refreshToken) {

        // 调用 JWT 工具类验证
        Map<String, Object> claims = JWTUtils.checkToken(refreshToken);
        if (claims == null) {
            // refresh token 无效或过期
            throw new BizException(40101, "Token 无效或已过期，请重新登录");
        }

        // 校验 jti 的 Redis 状态
        String jti = (String) claims.get("jti");
        String key = "refreshToken:" + jti;
        String flag = redisUtils.get(key, String.class);
        if (flag == null) { // 拿不到 value，说明已经过期了
            throw new BizException(40101, "Token 无效或已过期，请重新登录");
        }

        // 从 JWT 中取出 userId（如果有的话）
        Long userId = ((Number) claims.get("userId")).longValue();
        // 返回新的 Access Token
        return createToken(userId, ACCESS_EXPIRE, UUID.randomUUID().toString());
    }

    @Override
    public void logout(String refreshToken) {
        Map<String, Object> claims = JWTUtils.checkToken(refreshToken);
        if (claims == null) { // token 格式不对，或者过期了（）
            throw new BizException(40101, "Token 无效或已过期");
        }

        String jti = (String) claims.get("jti");
        boolean deleted = redisUtils.delete("RefreshToken:" + jti); // 可能 Redis 自动过期，导致删除失败了，但是不重要
        log.info("Logout: refreshToken jti={}, deleted={}", jti, deleted);
    }

    @Override
    public List<String> getAccessCodes(Long userId) {
        return rbacRpcService.getAccessCodes(userId);
    }

    // 生成出 JWT
    private String createToken(Long userId, Long expireTime, String jti) {
        long now = System.currentTimeMillis(); // 返回一个 long 类型的毫秒时间戳

        TokenPayload Payload = TokenPayload.builder()
                .userId(userId)
                .issuedAt(now)
                .expireTime(now + expireTime * 1000) // expireTime 默认是秒，要转为毫秒
                .jti(jti)
                .build();
        return JWTUtils.createToken(Payload); // Java 的 jjwt api，要求时间必须是毫秒
    }

    // 生成 tokenPair（含写入 Redis 逻辑）
    private TokenPairDTO generateTokenPair(Long userId) {
        String accessJti = UUID.randomUUID().toString();
        String accessToken = createToken(userId, ACCESS_EXPIRE, accessJti);

        String refreshJti = UUID.randomUUID().toString();
        String refreshToken = createToken(userId, REFRESH_EXPIRE, refreshJti);

        redisUtils.set("refreshToken:" + refreshJti, userId, REFRESH_EXPIRE);

        return TokenPairDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshExipre(REFRESH_EXPIRE)
                .build();
    }
}
