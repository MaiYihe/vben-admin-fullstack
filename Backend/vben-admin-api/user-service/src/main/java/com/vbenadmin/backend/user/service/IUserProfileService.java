package com.vbenadmin.backend.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserRegisterRequest;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.models.vo.UserProfileVO;

public interface IUserProfileService extends IService<User>{
    List<String> getAuthCodesByUserId(String userId);

    UserInfoDTO getUserInfoByUserName(String username);

    boolean existUser(String username);

    User registerUser(UserRegisterRequest userRegisterRequest);// 直接注册用户

    UserProfileVO getUserProfile();

}
