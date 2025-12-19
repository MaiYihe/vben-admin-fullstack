package com.vbenadmin.backend.user.rpcImpl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;

import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserRegisterRequest;
import com.vbenadmin.backend.commonrpc.rpc.IUserRpcService;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.service.IUserProfileService;

import lombok.RequiredArgsConstructor;

@DubboService
@RequiredArgsConstructor
public class UserRpcServiceImpl implements IUserRpcService {
    private final IUserProfileService userProfileService;

    @Override
    public List<String> getAuthCodesByUserId(String userId) {
        return userProfileService.getAuthCodesByUserId(userId);
    }

    @Override
    public UserInfoDTO getUserInfoByUserName(String username) {
        return userProfileService.getUserInfoByUserName(username);
    }

    @Override
    public boolean existUser(String username) {
        return userProfileService.existUser(username);
    }

    @Override
    public String registerUser(UserRegisterRequest registerRequest) {
        User user = userProfileService.registerUser(registerRequest);
        return user.getId();
    }

}
