package com.vbenadmin.backend.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.user.entity.GroupRole;

/**
 * <p>
 * 用户组-角色关系表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-29
 */
public interface IGroupRoleService extends IService<GroupRole> {

    void bindGroupWithRoles(String groupId, List<String> roleIds);

}
