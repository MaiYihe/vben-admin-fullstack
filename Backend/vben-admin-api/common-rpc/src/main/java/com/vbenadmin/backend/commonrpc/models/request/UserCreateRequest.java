package com.vbenadmin.backend.commonrpc.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateRequest {
    private String username;
    private String password;
}
