package com.vbenadmin.backend.department.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.department.entity.UserDepartment;
import com.vbenadmin.backend.department.mapper.UserDepartmentMapper;
import com.vbenadmin.backend.department.service.IUserDepartmentService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-部门联表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-27
 */
@Service
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment> implements IUserDepartmentService {

    @Override
    public void removeByDeptId(String deptId) {
        this.lambdaUpdate()
            .eq(UserDepartment::getDepartmentId,deptId)
            .remove();
    }

}
