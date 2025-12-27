package com.vbenadmin.codegenerator.service.impl;

import com.vbenadmin.codegenerator.entity.UserDepartment;
import com.vbenadmin.codegenerator.mapper.UserDepartmentMapper;
import com.vbenadmin.codegenerator.service.IUserDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
