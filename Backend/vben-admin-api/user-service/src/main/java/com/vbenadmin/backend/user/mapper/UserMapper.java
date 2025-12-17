package com.vbenadmin.backend.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.models.dto.LoginUserDTO;
import com.vbenadmin.backend.user.models.request.UserQueryRequest;
import com.vbenadmin.backend.user.models.vo.UserInfoVO;

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

    // 查询用户信息及其角色和用户组，并分页
    IPage<UserInfoVO> selectUserListByRequest(Page<?> page, @Param("request") UserQueryRequest request);
}
