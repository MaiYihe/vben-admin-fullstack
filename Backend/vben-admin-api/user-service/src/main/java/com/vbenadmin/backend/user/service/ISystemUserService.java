package com.vbenadmin.backend.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.models.request.UserCreateRequest;
import com.vbenadmin.backend.user.models.request.UserQueryRequest;
import com.vbenadmin.backend.user.models.request.UserUpdateRequest;
import com.vbenadmin.backend.user.models.vo.UserInfoVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-11-06
 */
public interface ISystemUserService extends IService<User> {

    List<UserInfoVO> getUserListByRequest(UserQueryRequest request);

    List<UserInfoVO> getAllUserList();

    void updateUser(String userId, UserUpdateRequest request);

    void createUser(UserCreateRequest userCreateRequest);
}
