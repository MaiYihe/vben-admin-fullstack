package com.vbenadmin.backend.user.mapper;

import org.apache.ibatis.annotations.Delete;

public interface UserDepartmentMapper {

    @Delete("DELETE FROM sys_user_department WHERE user_id = #{userId}")
    int removeByUserId(String userId);
}
