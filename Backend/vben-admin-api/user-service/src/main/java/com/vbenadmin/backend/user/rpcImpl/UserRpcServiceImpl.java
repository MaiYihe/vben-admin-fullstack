package com.vbenadmin.backend.user.rpcImpl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;

import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserRegisterRequest;
import com.vbenadmin.backend.commonrpc.rpc.IUserRpcService;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.service.IUserService;

import lombok.RequiredArgsConstructor;

@DubboService
@RequiredArgsConstructor
public class UserRpcServiceImpl implements IUserRpcService {
    private final IUserService userService;

    @Override
    public List<String> getAuthCodesByUserId(String userId) {
        return userService.getAuthCodesByUserId(userId);
    }

    @Override
    public UserInfoDTO getUserInfoByUserName(String username) {
        return userService.getUserInfoByUserName(username);
    }

    @Override
    public boolean existUser(String username) {
        return userService.existUser(username);
    }

    @Override
    public String registerUser(UserRegisterRequest registerRequest) {
        User user = userService.registerUser(registerRequest);
        return user.getId();
    }

}
