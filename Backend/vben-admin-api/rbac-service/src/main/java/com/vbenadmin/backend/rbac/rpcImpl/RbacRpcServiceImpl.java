
package com.vbenadmin.backend.rbac.rpcImpl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;

import com.vbenadmin.backend.commonrpc.models.dto.UserRoleDTO;
import com.vbenadmin.backend.commonrpc.rpc.IRbacRpcService;
import com.vbenadmin.backend.rbac.service.IResourceService;
import com.vbenadmin.backend.rbac.service.IRoleService;

import lombok.RequiredArgsConstructor;


@DubboService
@RequiredArgsConstructor
public class RbacRpcServiceImpl implements IRbacRpcService{

    private final IResourceService resourceService;
    private final IRoleService roleService;

    @Override
    public List<String> getAuthCodesByUserId(String userId) {
        List<String> roleIds = roleService.getRoleIdsByUserId(userId);
        return resourceService.getAuthCodesByRoleIds(roleIds);
    }

    @Override
    public List<String> getRoleNamesByUserId(String userId) {
        return roleService.getRoleNamesByUserId(userId);
    }

    @Override
    public List<UserRoleDTO> getUserRolesByUserIds(List<String> userIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRolesByUserIds'");
    }

}
