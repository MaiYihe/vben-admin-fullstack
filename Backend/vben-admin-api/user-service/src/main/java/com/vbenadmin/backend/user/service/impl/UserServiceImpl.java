package com.vbenadmin.backend.user.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonrpc.rpc.IRbacRpcService;
import com.vbenadmin.backend.commonweb.context.UserContext;
import com.vbenadmin.backend.commonweb.security.UserContextHolder;
import com.vbenadmin.backend.user.convert.IUserProfileMapper;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.mapper.UserMapper;
import com.vbenadmin.backend.user.models.dto.LoginUserDTO;
import com.vbenadmin.backend.user.models.vo.UserProfileVO;
import com.vbenadmin.backend.user.service.IUserService;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-11-06
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @DubboReference
    private IRbacRpcService rbacRpcService;
    private final IUserProfileMapper userProfileMapper;
    private final UserMapper userMapper;

    @Override
    public UserProfileVO getUserProfile() {
        
        UserContext userContext = UserContextHolder.get();
        String userId = userContext.getUserId();
        // 获取 loginUserDTO
        LoginUserDTO loginUserDTO = getCurrentLoginUser(userId);
        // 获取 roles
        List<String> roles = rbacRpcService.getRoles(userId);
        // 获取 accessToken
        String accessToken = userContext.getAccessToken();

        // 转换生成 userProfileVO
        return userProfileMapper.toUserProfileVO(loginUserDTO, roles, accessToken);

    }

    private LoginUserDTO getCurrentLoginUser(String userId) {
        LoginUserDTO loginUser = userMapper.selectCurrentLoginUser(userId);
        if(loginUser == null){
            throw new BizException(40401, "登录用户不存在");
        }

        return loginUser;
    }

}
