package com.vbenadmin.backend.department.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.department.entity.UserDepartment;

/**
 * <p>
 * 用户-部门联表 服务类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-27
 */
public interface IUserDepartmentService extends IService<UserDepartment> {
    void removeByDeptId(String id);
}
