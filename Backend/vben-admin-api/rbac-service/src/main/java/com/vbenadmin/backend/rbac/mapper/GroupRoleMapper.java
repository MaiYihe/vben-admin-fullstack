package com.vbenadmin.backend.rbac.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface GroupRoleMapper {
    @Delete("Delete From sys_group_role WHERE role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") String roleId);
}
