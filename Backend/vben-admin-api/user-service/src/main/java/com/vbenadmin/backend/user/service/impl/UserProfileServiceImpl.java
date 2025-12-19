package com.vbenadmin.backend.user.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserRegisterRequest;
import com.vbenadmin.backend.commonweb.context.UserContext;
import com.vbenadmin.backend.commonweb.security.UserContextHolder;
import com.vbenadmin.backend.user.converter.UserInfoDTOConverter;
import com.vbenadmin.backend.user.converter.UserProfileVOConverter;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.mapper.UserMapper;
import com.vbenadmin.backend.user.models.dto.LoginUserDTO;
import com.vbenadmin.backend.user.models.vo.UserProfileVO;
import com.vbenadmin.backend.user.service.IUserProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl extends ServiceImpl<UserMapper, User> implements IUserProfileService {

    private final UserProfileVOConverter userProfileVOConverter;
    private final UserMapper userMapper;
    private final UserInfoDTOConverter userInfoDTOConverter;

    @Override
    public List<String> getAuthCodesByUserId(String userId) {
        if (userId == null)
            return Collections.emptyList();
        return userMapper.getAuthCodesByUserId(userId);
    }

    @Override
    public UserProfileVO getUserProfile() {
        UserContext userContext = UserContextHolder.get();
        log.debug("[getUserProfile] get UserContext = {}", userContext);
        String userId = userContext.getUserId();
        // 获取 loginUserDTO
        LoginUserDTO loginUserDTO = getCurrentLoginUser(userId);
        log.debug("[getUserProfile] get loginUserDTO = {}", loginUserDTO);
        // 获取 roles
        List<String> roleNames = userMapper.getRoleNamesByUserId(userId);
        log.debug("[getUserProfile] get roleNames = {}", roleNames);
        // 获取 accessToken
        String accessToken = userContext.getAccessToken();

        // 转换生成 userProfileVO
        return userProfileVOConverter.toVO(loginUserDTO, roleNames, accessToken);

    }

    private LoginUserDTO getCurrentLoginUser(String userId) {
        LoginUserDTO loginUser = userMapper.selectCurrentLoginUser(userId);
        if (loginUser == null) {
            throw new BizException(40401, "登录用户不存在");
        }
        return loginUser;
    }

    @Override
    public UserInfoDTO getUserInfoByUserName(String username) {
        return Optional.ofNullable(
                this.lambdaQuery()
                        .eq(User::getUsername, username)
                        .one())
                .map(userInfoDTOConverter::toDTO)
                .orElse(null);
    }

    @Override
    public boolean existUser(String username) {
        return this.lambdaQuery()
                .eq(User::getUsername, username)
                .count() > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User registerUser(UserRegisterRequest userCreateRequest) {
        // 创建 User 实体
        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());

        // 保存用户
        boolean success = this.save(user);
        // 保存失败处理
        if (!success) {
            throw new BizException(50001, "用户保存失败");
        }
        return user;
    }
}
