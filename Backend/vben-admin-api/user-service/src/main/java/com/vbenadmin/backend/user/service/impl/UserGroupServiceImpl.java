package com.vbenadmin.backend.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.user.entity.UserGroup;
import com.vbenadmin.backend.user.mapper.UserGroupMapper;
import com.vbenadmin.backend.user.service.IUserGroupService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

    private final UserGroupMapper userGroupMapper;

    @Override
    public void removeByUserId(String userId) {
        int result = userGroupMapper.removeByUserId(userId);

        if (result == 0) {
            // 没有数据可以删除
            return;
        }

        if (result < 0) {
            // 说明是数据库异常
            throw new BizException(50001, "关联表删除错误");
        }
    }

    @Override
    public void bindGroupWithUsers(String groupId, List<String> userIds) {
        List<UserGroup> entities = userIds.stream()
                .map(userId -> new UserGroup(userId, groupId))
                .toList();
        this.saveBatch(entities);
    }

}
