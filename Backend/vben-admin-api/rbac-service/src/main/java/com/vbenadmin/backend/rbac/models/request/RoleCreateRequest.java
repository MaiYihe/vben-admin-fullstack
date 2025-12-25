package com.vbenadmin.backend.rbac.models.request;


import java.util.List;

import lombok.Data;

@Data
public class RoleCreateRequest {
    private String name;
    private Integer status;
    private String remark;
    private List<String> permissions;
}
