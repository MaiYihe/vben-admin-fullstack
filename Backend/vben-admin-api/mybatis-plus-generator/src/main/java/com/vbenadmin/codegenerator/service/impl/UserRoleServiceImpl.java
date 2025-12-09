package com.vbenadmin.codegenerator.service.impl;

import com.vbenadmin.codegenerator.entity.UserRole;
import com.vbenadmin.codegenerator.mapper.UserRoleMapper;
import com.vbenadmin.codegenerator.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关系表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
