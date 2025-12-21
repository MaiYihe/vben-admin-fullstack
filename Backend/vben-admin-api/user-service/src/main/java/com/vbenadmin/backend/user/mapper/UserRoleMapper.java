package com.vbenadmin.backend.user.mapper;

import org.apache.ibatis.annotations.Delete;

public interface UserRoleMapper {

    @Delete("DELETE FROM user_role WHERE user_id = #{userId}")
    int removeByUserId(String userId);
}
