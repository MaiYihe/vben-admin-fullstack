package com.vbenadmin.backend.user.service.impl;

import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.user.mapper.UserRoleMapper;
import com.vbenadmin.backend.user.service.IUserRoleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRoleServiceImpl implements IUserRoleService{

    private final UserRoleMapper userRoleMapper;

    @Override
    public void removeByUserId(String userId) {
        int result = userRoleMapper.removeByUserId(userId);

        if(result == 0){
            return;
        }

        if (result < 0){
            // 说明是数据库异常
            throw new BizException(50001, "关联表删除错误");
        }
    }
    
}
