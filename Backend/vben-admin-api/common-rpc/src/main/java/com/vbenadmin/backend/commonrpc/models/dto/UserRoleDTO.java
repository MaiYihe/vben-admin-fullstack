package com.vbenadmin.backend.commonrpc.models.dto;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRoleDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private String userId;
    private String roleName;
}
