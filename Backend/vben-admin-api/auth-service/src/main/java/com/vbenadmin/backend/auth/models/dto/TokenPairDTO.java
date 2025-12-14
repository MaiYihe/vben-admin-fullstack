
package com.vbenadmin.backend.auth.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenPairDTO {
    private String accessToken;
    private String refreshToken;
    private Long refreshExipre;
}
