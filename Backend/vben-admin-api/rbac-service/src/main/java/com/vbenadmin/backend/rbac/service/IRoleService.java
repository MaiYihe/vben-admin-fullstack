package com.vbenadmin.backend.rbac.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.rbac.entity.Role;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
public interface IRoleService extends IService<Role> {
    List<String> getRoleIdsByUserId(String userId);
}
