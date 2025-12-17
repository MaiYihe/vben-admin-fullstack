package com.vbenadmin.backend.user.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.dto.UserRoleDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserCreateRequest;
import com.vbenadmin.backend.commonrpc.rpc.IRbacRpcService;
import com.vbenadmin.backend.commonweb.context.UserContext;
import com.vbenadmin.backend.commonweb.security.UserContextHolder;
import com.vbenadmin.backend.user.converter.UserInfoDTOConverter;
import com.vbenadmin.backend.user.converter.UserInfoVOConverter;
import com.vbenadmin.backend.user.converter.UserProfileVOConverter;
import com.vbenadmin.backend.user.converter.context.RoleGroupContext;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.mapper.UserMapper;
import com.vbenadmin.backend.user.models.dto.LoginUserDTO;
import com.vbenadmin.backend.user.models.dto.UserGroupDTO;
import com.vbenadmin.backend.user.models.request.UserQueryRequest;
import com.vbenadmin.backend.user.models.vo.UserInfoVO;
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
    private final IUserProfileConverter userProfileConverter;
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
        return userProfileConverter.toUserProfileVO(loginUserDTO, roles, accessToken);

    }

    @Override
    public List<UserInfoVO> getUserListByRequest(UserQueryRequest request) {

        // 1. 查询 List<User>
        // 分页查询，使用 MyBatis Plus 的分页功能
        Page<User> page = new Page<>(request.getPage(), request.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(request.getUsername() != null, User::getUsername, request.getUsername())
                .eq(request.getId() != null, User::getId, request.getId())
                .like(request.getRealName() != null, User::getRealName, request.getRealName())
                .eq(request.getStatus() != null, User::getStatus, request.getStatus())
                .ge(request.getStartTime() != null, User::getCreateTime, request.getStartTime())
                .le(request.getEndTime() != null, User::getCreateTime, request.getEndTime())
                .orderByDesc(User::getCreateTime);

        IPage<User> userPage = userMapper.selectPage(page, wrapper);
        List<User> users = userPage.getRecords();

        if (users.isEmpty()) {
            return List.of();
        }
        // 拿到 userIds，从而去其他表寻找关联字段
        List<String> userIds = users.stream()
                .map(User::getId)
                .toList();

        // 2. 远程调用，查询得到 roleMap
        List<UserRoleDTO> roleRows = rbacRpcService.getUserRolesByUserIds(userIds);
        Map<String, List<String>> roleMap = roleRows.stream()
                .collect(Collectors.groupingBy(
                        UserRoleDTO::getUserId,
                        Collectors.mapping(
                                UserRoleDTO::getRoleName,
                                Collectors.toList())));

        // 3. 查询得到 groupMap
        List<UserGroupDTO> groupRows = groupService.getGroupsByUserIds(userIds);
        Map<String, List<String>> groupMap = groupRows.stream()
                .collect(Collectors.groupingBy(
                        UserGroupDTO::getUserId,
                        Collectors.mapping(
                                UserGroupDTO::getGroupName,
                                Collectors.toList())));

        RoleGroupContext ctx = new RoleGroupContext(roleMap, groupMap);
        return userInfoVOConverter.toVOList(users, ctx);
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
    public User createUser(UserCreateRequest userCreateRequest) {
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
