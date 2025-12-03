package com.vbenadmin.backend.auth.models.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenVO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private long expiresIn = 3600;
}