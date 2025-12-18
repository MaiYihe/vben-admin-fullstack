package com.vbenadmin.backend.rbac.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.rbac.entity.Role;
import com.vbenadmin.backend.rbac.mapper.RoleMapper;
import com.vbenadmin.backend.rbac.service.IRoleService;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    private final RoleMapper roleMapper;

    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        List<String> roleIds = roleMapper.selectRoleIdsByUserId(userId);

        if(roleIds.isEmpty()){
            return Collections.emptyList();
        }

        return roleIds;
    }

    @Override
    public List<String> getRoleNamesByUserId(String userId) {
        List<String> roleNames = roleMapper.selectRoleNamesByUserId(userId);

        if(roleNames.isEmpty()){
            return Collections.emptyList();
        }

        return roleNames;
    }

}
