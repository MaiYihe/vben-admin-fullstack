package com.vbenadmin.backend.user.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.user.converter.UserConverter;
import com.vbenadmin.backend.user.converter.UserInfoVOConverter;
import com.vbenadmin.backend.user.converter.context.RoleGroupContext;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.mapper.UserMapper;
import com.vbenadmin.backend.user.models.dto.UserGroupDTO;
import com.vbenadmin.backend.user.models.dto.UserRoleDTO;
import com.vbenadmin.backend.user.models.request.UserCreateRequest;
import com.vbenadmin.backend.user.models.request.UserQueryRequest;
import com.vbenadmin.backend.user.models.request.UserUpdateRequest;
import com.vbenadmin.backend.user.models.vo.UserInfoVO;
import com.vbenadmin.backend.user.service.ISystemUserService;
import com.vbenadmin.backend.user.service.IUserGroupService;
import com.vbenadmin.backend.user.service.IUserRoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-11-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl extends ServiceImpl<UserMapper, User> implements ISystemUserService {

    private final UserMapper userMapper;
    private final UserInfoVOConverter userInfoVOConverter;
    private final UserConverter userConverter;
    private final IUserGroupService userGroupService;
    private final IUserRoleService userRoleService;

    @Override
    public PageResponseVO<UserInfoVO> getUserListByRequest(UserQueryRequest request) {

        // 查询 List<User>
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

        // 扩展查询条件来连接角色和组
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            wrapper.in(User::getId, userMapper.selectUserIdsByRoles(request.getRoles()));
        }
        if (request.getGroups() != null && !request.getGroups().isEmpty()) {
            wrapper.in(User::getId, userMapper.selectUserIdsByGroups(request.getGroups()));
        }

        // 关键：先根据 wrapper 筛选过滤，然后返回 page
        IPage<User> userPage = userMapper.selectPage(page, wrapper);
        List<User> users = userPage.getRecords();

        if (users.isEmpty()) {
            return new PageResponseVO<>(null, 0);
        }

        // MapStruct
        RoleGroupContext ctx = getRoleGroupCtx(users);
        List<UserInfoVO> userInfoVOs = userInfoVOConverter.toVOList(users, ctx);

        return new PageResponseVO<UserInfoVO>(userInfoVOs, userPage.getTotal());
    }

    @Override
    public List<UserInfoVO> getAllUserList() {
        List<User> users = userMapper.selectList(null);
        if (users.isEmpty()) {
            return List.of();
        }
        RoleGroupContext ctx = getRoleGroupCtx(users);
        return userInfoVOConverter.toVOList(users, ctx);
    }

    // 查询到角色-用户组上下文，方便映射到 UserInfoVO
    private RoleGroupContext getRoleGroupCtx(List<User> users) {
        // 拿到 userIds，从而去其他表寻找关联字段
        List<String> userIds = users.stream()
                .map(User::getId)
                .toList();

        // 2. 查询得到 groupMap
        List<UserGroupDTO> groupRows = userMapper.selectUserGroupsByUserIds(userIds);
        Map<String, List<String>> groupMap = groupRows.stream()
                .collect(Collectors.groupingBy(
                        UserGroupDTO::getUserId,
                        Collectors.mapping(
                                UserGroupDTO::getGroupName,
                                Collectors.toList())));

        // 3. 查询得到 roleMap
        List<UserRoleDTO> roleRows = userMapper.selectUserRolesByUserIds(userIds);
        Map<String, List<String>> roleMap = roleRows.stream()
                .collect(Collectors.groupingBy(
                        UserRoleDTO::getUserId,
                        Collectors.mapping(
                                UserRoleDTO::getRoleName,
                                Collectors.toList())));

        return new RoleGroupContext(roleMap, groupMap);
    }

    @Override
    public void updateUser(String userId, UserUpdateRequest request) {
        if (userId == null)
            throw new BizException(40000, "userId 为空，无法更新");

        User user = this.getById(userId);
        if (user == null)
            throw new BizException(40401, "用户不存在");

        log.debug("尝试更新 userId 为 {} 的用户", userId);
        LambdaUpdateWrapper<User> uw = Wrappers.lambdaUpdate(User.class)
                .eq(User::getId, userId)
                .set(request.getUsername() != null, User::getUsername, request.getUsername())
                .set(request.getRealName() != null, User::getRealName, request.getRealName())
                .set(request.getStatus() != null, User::getStatus, request.getStatus())
                .set(request.getRemark() != null, User::getDescription, request.getRemark());

        boolean updated = this.update(uw);
        if (!updated)
            throw new BizException(50001, "用户信息更新失败");
    }

    @Override
    public void createUser(UserCreateRequest userCreateRequest) {
        if (userCreateRequest == null)
            throw new BizException(40000, "创建请求为空");

        if (userCreateRequest.getUsername() == null)
            throw new BizException(40000, "创建请求中没有用户名");

        if (existUser(userCreateRequest.getUsername()))
            throw new BizException(40901, "用户已存在");

        User user = userConverter.toEntity(userCreateRequest);

        // 管理员创建用户：给一个默认密码
        String defaultPassword = "123456";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(defaultPassword));

        boolean saved = this.save(user);

        if (!saved)
            throw new BizException(50001, "创建失败，未知错误");
    }

    private boolean existUser(String username) {
        return this.lambdaQuery()
                .eq(User::getUsername, username)
                .count() > 0;
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        if (userId == null)
            throw new BizException(40000, "未携带 userId");

        User user = this.getById(userId);
        if (user == null)
            throw new BizException(40401, "用户不存在");

        // 安全保护 1：禁止删超级管理员
        if (user.getUsername() == "root") {
            throw new BizException(40000, "不能删除超级管理员");
        }

        // 安全保护 2：只能删禁用用户（非常常见）
        if (user.getStatus() == 1) {
            throw new BizException(40000, "请先禁用用户，再进行删除");
        }

        // 级联清理（中间表）
        userRoleService.removeByUserId(userId);
        userGroupService.removeByUserId(userId);

        boolean removed = this.removeById(userId);

        if (!removed)
            throw new BizException(50001, "删除用户失败，未知错误");
    }

}
