package com.vbenadmin.backend.commonrpc.rpc;

import java.util.List;

import com.vbenadmin.backend.commonrpc.models.dto.UserRoleDTO;

public interface IRbacRpcService {
    List<String> getAuthCodesByUserId(String userId);
    List<String> getRoleNamesByUserId(String userId);
    List<UserRoleDTO> getUserRolesByUserIds(List<String> userIds);
}
