package com.vbenadmin.backend.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.user.entity.GroupRole;
import com.vbenadmin.backend.user.mapper.GroupRoleMapper;
import com.vbenadmin.backend.user.service.IGroupRoleService;

import java.util.List;

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

}
