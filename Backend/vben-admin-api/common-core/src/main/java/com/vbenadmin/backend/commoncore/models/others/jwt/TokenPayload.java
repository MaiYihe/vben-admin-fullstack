package com.vbenadmin.backend.commoncore.models.others.jwt;

import lombok.Builder;
import lombok.Data;

/**
 * Token 携带的数据部分
 */
@Data
@Builder
public class TokenPayload {
    private Long userId;
    private Long issuedAt; //签发时间
    private Long expireTime; //过期时间
    private String jti;
}