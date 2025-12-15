package com.vbenadmin.backend.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.models.dto.LoginUserDTO;

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
}
