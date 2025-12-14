
package com.vbenadmin.backend.rbac.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vbenadmin.backend.rbac.service.IRbacService;
import com.vbenadmin.backend.rbac.service.IResourceService;
import com.vbenadmin.backend.rbac.service.IRoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RbacService implements IRbacService {

    private final IRoleService roleService;
    private final IResourceService resourceService;

    @Override
    public List<String> getAuthCodesByUserId(String userId) {
        List<String> roleIds = roleService.getRoleIdsByUserId(userId);
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> resourceIds = resourceService.getResourceIdsByRoleIds(roleIds);

        return resourceService.getAuthCodesByResourceIds(resourceIds);
    }

}
