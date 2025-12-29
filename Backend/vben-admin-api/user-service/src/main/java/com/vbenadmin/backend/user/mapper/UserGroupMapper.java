package com.vbenadmin.backend.user.mapper;

import org.apache.ibatis.annotations.Delete;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.user.entity.UserGroup;

public interface UserGroupMapper extends BaseMapper<UserGroup>{

    @Delete("DELETE FROM sys_user_group WHERE user_id = #{userId}")
    int removeByUserId(String userId);

}
