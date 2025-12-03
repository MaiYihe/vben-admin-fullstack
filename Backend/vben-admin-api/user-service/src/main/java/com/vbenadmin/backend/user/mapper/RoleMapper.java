package com.vbenadmin.backend.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.user.entity.Role;

import java.util.List;


/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author maihehe
 * @since 2025-11-16
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Long> getRoleIdsByUserId(Long userId);
}

