package com.vbenadmin.backend.user.mapper;

import org.apache.ibatis.annotations.Delete;

public interface UserGroupMapper {

    @Delete("DELETE FROM sys_user_group WHERE user_id = #{userId}")
    int removeByUserId(String userId);

}
