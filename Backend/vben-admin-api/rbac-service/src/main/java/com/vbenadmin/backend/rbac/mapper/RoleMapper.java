package com.vbenadmin.backend.rbac.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.rbac.entity.Role;

import io.lettuce.core.dynamic.annotation.Param;


/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT role_id FROM sys_user_role WHERE user_id = #{userId}")
    List<String> getRoleIdsByUserId(@Param("userId") String userId);
}

