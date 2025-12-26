package com.vbenadmin.backend.user.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotNull(message = "用户名不能为空")
    private String username;
    
    @NotNull(message = "真实姓名不能为空")
    private String realName;
    private Byte status;
    private String remark;
}
