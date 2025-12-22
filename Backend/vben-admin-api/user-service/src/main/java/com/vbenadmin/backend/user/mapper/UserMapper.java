package com.vbenadmin.backend.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.models.dto.LoginUserDTO;
import com.vbenadmin.backend.user.models.dto.UserGroupDTO;
import com.vbenadmin.backend.user.models.dto.UserRoleDTO;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author maihehe
 * @since 2025-11-06
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("""
            SELECT DISTINCT res.auth_code
            FROM sys_resource res
            JOIN sys_role_resource rr ON res.id = rr.resource_id
            JOIN sys_group_role gr ON rr.role_id = gr.role_id
            JOIN sys_user_group ug ON gr.group_id = ug.group_id
            WHERE ug.user_id = #{userId}
            AND res.auth_code IS NOT NULL;
            """)
    List<String> getAuthCodesByUserId(@Param("userId")String userId);

    @Select("""
            SELECT ro.name
            FROM sys_role ro
            JOIN sys_group_role gr ON ro.id = gr.role_id
            JOIN sys_user_group ug ON gr.group_id = ug.group_id
            WHERE ug.user_id = #{userId}
            """)
    List<String> getRoleNamesByUserId(@Param("userId") String userId);

    @Select("""
            SELECT
                id,
                username,
                real_name AS realName,
                avatar,
                description,
                home_path AS homePath
            FROM sys_user WHERE id = #{userId}
            """)
    LoginUserDTO selectCurrentLoginUser(@Param("userId") String userId);

    List<String> selectUserIdsByRoles(@Param("roles") List<String> roles);

    List<String> selectUserIdsByGroups(@Param("groups") List<String> groups);

    List<UserRoleDTO> selectUserRolesByUserIds(@Param("userIds") List<String> userIds);

    List<UserGroupDTO> selectUserGroupsByUserIds(@Param("userIds") List<String> userIds);
}
