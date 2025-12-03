package com.vbenadmin.backend.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.user.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author maihehe
 * @since 2025-11-16
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> getCodesByRoleIds(@Param("roleIds") List<Long> roleIds);
}

