package com.vbenadmin.backend.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.user.entity.Permission;
import com.vbenadmin.backend.user.mapper.PermissionMapper;
import com.vbenadmin.backend.user.service.IPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-11-16
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
