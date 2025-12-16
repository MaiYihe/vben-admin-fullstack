package com.vbenadmin.backend.commonrpc.models.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String password;
    private Byte status;

    private static final Byte ENABLED = 1;
    public boolean isEnabled() {
        return ENABLED.equals(status);
    }
}
