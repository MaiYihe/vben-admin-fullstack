package com.vbenadmin.backend.rbac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.rbac.entity.Role;
import com.vbenadmin.backend.rbac.models.dto.RolePermissionDTO;



/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<RolePermissionDTO> selectRolePermissionsByRoleIds(
            @Param("roleIds") List<String> roleIds);
}

