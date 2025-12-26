package com.vbenadmin.backend.rbac.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.rbac.entity.RoleResource;

/**
 * <p>
 * 角色-资源节点关系表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
public interface IRoleResourceService extends IService<RoleResource> {
    boolean bindByAuthCodes(String roleId, List<String> authCodes);

    boolean updateByAuthCodes(String roleId, List<String> authCodes);

    boolean removeByRoleId(String roleId);
}
