package com.vbenadmin.backend.user.rpcImpl;

import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserCreateRequest;
import com.vbenadmin.backend.commonrpc.rpc.IUserRpcService;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.mapper.PermissionMapper;
import com.vbenadmin.backend.user.mapper.RoleMapper;
import com.vbenadmin.backend.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;


@DubboService
@RequiredArgsConstructor
public class UserRpcServiceImpl implements IUserRpcService {
    private final IUserService userService;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    @Override
    public UserInfoDTO getPasswordByUserName(String username) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername,username)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
        // .orElse(null)→ 从 Optional<User> 中取出真正的 User 对象；
        // 如果 Optional 为空，则返回 null

        if(user == null) {
            return null;
        }

        return UserInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public boolean existUser(String username) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername,username)
                .oneOpt()
                .orElse(null);

        return user != null;
    }

    @Override
    public Long createUser(UserCreateRequest userCreateRequest) {
        // 创建 User 实体
        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());

        // 保存用户
        boolean success = userService.save(user);

        // 保存失败处理
        if(!success) {
            throw new BizException(50001,"用户保存失败");
        }

        return user.getId();
    }

    @Override
    public List<String> getAccessCodes(Long userId) {
        // 查询用户角色 ID
        List<Long> roleIds = roleMapper.getRoleIdsByUserId(userId);

        // 查询角色权限代码
        return permissionMapper.getCodesByRoleIds(roleIds);
    }
}
