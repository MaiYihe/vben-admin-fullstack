package com.vbenadmin.backend.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserCreateRequest;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.models.vo.UserProfileVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-11-06
 */
public interface IUserService extends IService<User> {
    UserInfoDTO getUserInfoByUserName(String username);
    boolean existUser(String username);
    User createUser(UserCreateRequest userCreateRequest);

    UserProfileVO getUserProfile();
}
