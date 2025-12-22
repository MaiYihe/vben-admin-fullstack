package com.vbenadmin.backend.user.service.impl;

import org.springframework.stereotype.Service;

import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.user.mapper.UserDepartmentMapper;
import com.vbenadmin.backend.user.service.IUserDepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDepartmentServiceImpl implements IUserDepartmentService{

    private final UserDepartmentMapper userDepartmentMapper;

    @Override
    public void removeByUserId(String userId) {
        int result = userDepartmentMapper.removeByUserId(userId);

        if(result == 0){
            return;
        }

        if (result < 0){
            // 说明是数据库异常
            throw new BizException(50001, "关联表删除错误");
        }
    }
    
}
