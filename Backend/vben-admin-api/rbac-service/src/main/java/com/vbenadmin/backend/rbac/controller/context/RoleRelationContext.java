package com.vbenadmin.backend.rbac.controller.context;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleRelationContext {
    private final Map<String,List<String>> permissionMap;

    public List<String> getPermissions(String roleId){
        return permissionMap.getOrDefault(roleId, List.of());
    }
}
