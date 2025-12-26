package com.vbenadmin.backend.rbac.service.impl;

import org.springframework.stereotype.Service;

import com.vbenadmin.backend.rbac.mapper.GroupRoleMapper;
import com.vbenadmin.backend.rbac.service.IGroupRoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroupRoleService implements IGroupRoleService{

    private final GroupRoleMapper groupRoleMapper;

    @Override
    public boolean removeByRoleId(String roleId) {
        int rowsAffected = groupRoleMapper.deleteByRoleId(roleId);
        return rowsAffected >0;
    }
    
}
