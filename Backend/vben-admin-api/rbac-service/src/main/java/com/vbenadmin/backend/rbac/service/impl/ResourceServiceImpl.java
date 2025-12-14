package com.vbenadmin.backend.rbac.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.rbac.entity.Resource;
import com.vbenadmin.backend.rbac.mapper.ResourceMapper;
import com.vbenadmin.backend.rbac.service.IResourceService;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 系统资源（菜单/页面/按钮/内嵌/外链）表 服务实现类
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    private final ResourceMapper resourceMapper;

    @Override
    public List<String> getResourceIdsByRoleIds(List<String> roleIds) {
        List<String> resouceIds = resourceMapper.selectResourceIdsByRoleIds(roleIds);

        // SQL 查询结构不可能为 null，防御性编程
        if (resouceIds == null || resouceIds.isEmpty()) {
            return Collections.emptyList();
        }

        return resouceIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    @Override
    public List<String> getAuthCodesByResourceIds(List<String> resouceIds) {
        List<String> authCodes = resourceMapper.selectAuthCodesByResourceIds(resouceIds);

        if(authCodes == null || authCodes.isEmpty()){
            return Collections.emptyList();
        }

        return authCodes.stream()
            .filter(Objects::nonNull)
            .distinct()
            .toList();
    }

}
