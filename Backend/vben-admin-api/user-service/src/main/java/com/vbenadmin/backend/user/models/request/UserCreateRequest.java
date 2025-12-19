package com.vbenadmin.backend.user.models.request;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String realName;
    private Byte status;
    private String remark;
}
