package com.vbenadmin.backend.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.rbac.entity.RoleResource;
import com.vbenadmin.backend.rbac.mapper.RoleResourceMapper;
import com.vbenadmin.backend.rbac.service.IResourceService;
import com.vbenadmin.backend.rbac.service.IRoleResourceService;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final IResourceService resourceService;

    @Override
    public boolean bindByAuthCodes(String roleId, List<String> authCodes) {
        if (roleId == null || authCodes == null)
            throw new BizException(40000, "参数错误");

        int rowsAffected = roleResourceMapper.insertByAuthCodes(roleId, authCodes);

        return rowsAffected > 0;
    }

    @Override
    @Transactional
    public boolean updateByAuthCodes(String roleId, List<String> authCodes) {
        if (roleId == null || authCodes == null)
            throw new BizException(40000, "参数错误");

        // step 1: 旧集合 A
        Set<String> oldSet = new HashSet<>(
                roleResourceMapper.getResourceIdsByRoleId(roleId));

        // step 2: 新集合 B
        Set<String> newSet = new HashSet<>(
                resourceService.getIdsByAuthCodes(authCodes));

        // step 3: A-B= 要删除的；B-A = 要新增的
        Set<String> toDelete = new HashSet<>(oldSet);
        toDelete.removeAll(newSet);

        Set<String> toInsert = new HashSet<>(newSet);
        toInsert.removeAll(oldSet);

        int l0 = -1, l1 = -1;
        // step 4: SQL 执行
        if (!toDelete.isEmpty()) {
            l0 = roleResourceMapper.deleteByRoleIdAndResourceIds(roleId, new ArrayList<>(toDelete));
        }

        if (!toInsert.isEmpty()) {
            l1 = roleResourceMapper.insertByRoleIdAndResourceIds(roleId, new ArrayList<>(toInsert));
        }

        boolean deleted = l0 > 0, inserted = l1 > 0;
        return deleted || inserted;
    }

    @Override
    public boolean removeByRoleId(String roleId) {
        int rowsAffected = roleResourceMapper.deleteByRoleId(roleId);
        return rowsAffected > 0;
    }

}
