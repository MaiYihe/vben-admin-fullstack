package com.vbenadmin.backend.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRoleDTO{
    private String userId;
    private String roleName;
}
