package com.vbenadmin.backend.rbac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.rbac.entity.RoleResource;

/**
 * <p>
 * 角色-资源节点关系表 Mapper 接口
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    int insertByAuthCodes(
            @Param("roleId") String roleId,
            @Param("authCodes") List<String> authCodes);

    @Select("""
                SELECT rr.resource_id
                FROM sys_role_resource rr
                WHERE rr.role_id = #{roleId}
            """)
    List<String> getResourceIdsByRoleId(@Param("roleId") String roleId);

    int deleteByRoleIdAndResourceIds(
            @Param("roleId") String roleId,
            @Param("resourceIds") List<String> resourceIds);

    int insertByRoleIdAndResourceIds(
            @Param("roleId") String roleId,
            @Param("resourceIds") List<String> resourceIds);
}
