package com.vbenadmin.backend.rbac.models.request;


import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleCreateRequest {
    @NotNull(message = "roleName 不能为空")
    private String name;
    private Integer status;
    private String remark;
    private List<String> permissions;
}
