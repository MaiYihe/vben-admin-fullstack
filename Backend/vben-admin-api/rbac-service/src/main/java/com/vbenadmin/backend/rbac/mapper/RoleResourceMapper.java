package com.vbenadmin.backend.rbac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

    void insertByAuthCodes(@Param("roleId") String roleId,@Param("authCodes") List<String> authCodes);

}

