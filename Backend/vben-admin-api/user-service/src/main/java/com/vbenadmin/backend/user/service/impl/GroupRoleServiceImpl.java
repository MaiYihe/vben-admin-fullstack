package com.vbenadmin.backend.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.user.entity.GroupRole;
import com.vbenadmin.backend.user.mapper.GroupRoleMapper;
import com.vbenadmin.backend.user.service.IGroupRoleService;

import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组-角色关系表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-29
 */
@Service
public class GroupRoleServiceImpl extends ServiceImpl<GroupRoleMapper, GroupRole> implements IGroupRoleService {

    @Override
    public void bindGroupWithRoles(String groupId, List<String> roleIds) {

        List<GroupRole> entities = roleIds.stream()
                .map(roleId -> new GroupRole(groupId, roleId))
                .toList();

        this.saveBatch(entities);
    }

    @Override
    public void updateByRoleIds(String groupId, List<String> roleIds) {
        // select input_groupId related roleIds
        List<String> oldRoleIds = this.lambdaQuery()
                .eq(GroupRole::getGroupId, groupId)
                .list().stream()
                .map(GroupRole::getRoleId)
                .toList();
        // make sets
        Set<String> oldSet = new HashSet<>(oldRoleIds);
        Set<String> newSet = new HashSet<>(roleIds);

        Set<String> toDelete = new HashSet<>(oldSet);
        toDelete.removeAll(newSet);

        Set<String> toInsert = new HashSet<>(newSet);
        toInsert.removeAll(oldSet);

        if(!toDelete.isEmpty()){
            this.lambdaUpdate()
                .eq(GroupRole::getGroupId,groupId)
                .in(GroupRole::getRoleId,toDelete)
                .remove();
        }

        if(!toInsert.isEmpty()){
            List<GroupRole> entities = toInsert.stream()
                .map(roleId -> new GroupRole(groupId, roleId))
                .toList();
            this.saveBatch(entities);
        }
    }

    @Override
    public void removeByGroupId(@NotBlank String id) {
        this.lambdaUpdate()
            .eq(GroupRole::getGroupId, id)
            .remove();
    }

}
