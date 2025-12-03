package com.vbenadmin.backend.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.user.entity.Role;
import com.vbenadmin.backend.user.mapper.RoleMapper;
import com.vbenadmin.backend.user.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-11-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
