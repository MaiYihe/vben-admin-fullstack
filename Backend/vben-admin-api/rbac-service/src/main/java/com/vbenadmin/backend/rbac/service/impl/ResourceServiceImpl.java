package com.vbenadmin.backend.rbac.service.impl;

import java.util.List;

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

    @Override
    public List<String> getIdsByAuthCodes(List<String> authCodes) {
        if(authCodes == null || authCodes.isEmpty())
            return List.of();

        return this.lambdaQuery()
            .in(Resource::getAuthCode, authCodes)
            .list()
            .stream()
            .map(Resource::getId)
            .toList();
    }
}
