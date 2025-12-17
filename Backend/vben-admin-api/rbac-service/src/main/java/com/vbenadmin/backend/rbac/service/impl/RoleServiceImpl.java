package com.vbenadmin.backend.rbac.service.impl;

import com.vbenadmin.backend.rbac.entity.Role;
import com.vbenadmin.backend.rbac.mapper.RoleMapper;
import com.vbenadmin.backend.rbac.service.IRoleService;

import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

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

        if(roleIds == null || roleIds.isEmpty()){
            return Collections.emptyList();
        }

        return roleIds.stream()
            .filter(Objects::nonNull)
            .distinct()
            .toList();
    }

    @Override
    public List<String> getRoleNamesByUserId(String userId) {
        List<String> roles = roleMapper.selectRoleNamesByUserId(userId);

        if(roles == null || roles.isEmpty()){
            return Collections.emptyList();
        }

        return roles.stream()
            .filter(Objects::nonNull)
            .distinct()
            .toList();
    }

}
