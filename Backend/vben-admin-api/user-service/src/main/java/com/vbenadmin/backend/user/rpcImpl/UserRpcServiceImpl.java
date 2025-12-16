package com.vbenadmin.backend.user.rpcImpl;

import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserCreateRequest;
import com.vbenadmin.backend.commonrpc.rpc.IUserRpcService;
import com.vbenadmin.backend.user.converter.IUserConverter;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.service.IUserService;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
@RequiredArgsConstructor
public class UserRpcServiceImpl implements IUserRpcService {
    private final IUserService userService;
    private final IUserConverter userConverter;

    @Override
    public UserInfoDTO getUserInfoByUserName(String username) {
        return userService.lambdaQuery()
                .eq(User::getUsername, username)
                .oneOpt()
                .map(userConverter::toUserInfoDTO)
                .orElse(null);

    }

    @Override
    public boolean existUser(String username) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername, username)
                .oneOpt()
                .orElse(null);

        return user != null;
    }

    @Override
    public String createUser(UserCreateRequest userCreateRequest) {
        // 创建 User 实体
        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());

        // 保存用户
        boolean success = userService.save(user);
        // 保存失败处理
        if (!success) {
            throw new BizException(50001, "用户保存失败");
        }

        return user.getId();
    }

}
