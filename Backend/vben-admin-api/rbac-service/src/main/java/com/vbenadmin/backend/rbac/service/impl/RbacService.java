
package com.vbenadmin.backend.rbac.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vbenadmin.backend.rbac.service.IRbacService;
import com.vbenadmin.backend.rbac.service.IResourceService;
import com.vbenadmin.backend.rbac.service.IRoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RbacService implements IRbacService {

    private final IRoleService roleService;
    private final IResourceService resourceService;

    @Override
    public List<String> getAuthCodesByUserId(String userId) {
        log.debug("[RBAC] 根据 userId 查询 roleIds");
        List<String> roleIds = roleService.getRoleIdsByUserId(userId);
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        log.debug("[RBAC] 根据 roleIds 查询 resourceIds");
        List<String> resourceIds = resourceService.getResourceIdsByRoleIds(roleIds);
        log.debug("[RBAC] resourceIds = {}", resourceIds);

        List<String> authCodes = resourceService.getAuthCodesByResourceIds(resourceIds);
        log.debug("[RBAC] authCodes = {}", authCodes);

        return authCodes;
    }

}
