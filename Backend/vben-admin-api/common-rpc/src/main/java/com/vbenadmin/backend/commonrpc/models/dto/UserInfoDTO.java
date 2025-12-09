package com.vbenadmin.backend.commonrpc.models.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserInfoDTO implements Serializable {
    private String id;
    private String username;
    private String password;
}
