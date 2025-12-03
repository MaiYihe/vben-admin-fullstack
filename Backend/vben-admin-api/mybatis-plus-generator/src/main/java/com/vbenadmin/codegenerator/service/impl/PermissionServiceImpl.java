package com.vbenadmin.codegenerator.service.impl;

import com.vbenadmin.codegenerator.entity.Permission;
import com.vbenadmin.codegenerator.mapper.PermissionMapper;
import com.vbenadmin.codegenerator.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
