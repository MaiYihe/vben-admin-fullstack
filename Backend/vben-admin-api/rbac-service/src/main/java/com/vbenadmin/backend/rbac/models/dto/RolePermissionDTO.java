package com.vbenadmin.backend.rbac.models.dto;

import lombok.Data;

@Data
public class RolePermissionDTO {
    private String roleId;
    private String permission;
}
