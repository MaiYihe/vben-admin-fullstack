package com.vbenadmin.backend.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.rbac.entity.RoleResource;
import com.vbenadmin.backend.rbac.mapper.RoleResourceMapper;
import com.vbenadmin.backend.rbac.service.IRoleResourceService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-资源节点关系表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Service
@AllArgsConstructor
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource>
        implements IRoleResourceService {

    private final RoleResourceMapper roleResourceMapper;

    @Override
    public void bindByAuthCodes(String roleId, List<String> authCodes) {
        if (roleId == null || authCodes == null)
            throw new BizException(40000, "参数错误");

        roleResourceMapper.insertByAuthCodes(roleId, authCodes);
    }

}
