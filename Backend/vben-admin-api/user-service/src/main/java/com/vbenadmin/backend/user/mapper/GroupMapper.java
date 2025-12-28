package com.vbenadmin.backend.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.models.dto.GroupRoleDTO;
import com.vbenadmin.backend.user.models.dto.GroupUserCountDTO;


/**
 * <p>
 * 用户组表 Mapper 接口
 * </p>
 *
 * @author maihehe
 * @since 2025-12-17
 */
public interface GroupMapper extends BaseMapper<Group> {
    List<String> selectGroupIdsByRoles(@Param("roles") List<String> roles);

    List<GroupRoleDTO> selectGroupRolesByGroupIds(List<String> groupIds);

    List<GroupUserCountDTO> countUsersByGroupIds(List<String> groupIds);
}

