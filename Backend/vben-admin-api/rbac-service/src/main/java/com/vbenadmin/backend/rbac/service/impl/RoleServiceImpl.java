package com.vbenadmin.backend.rbac.service.impl;

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
}
