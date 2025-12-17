package com.vbenadmin.backend.auth.service;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import com.vbenadmin.backend.commonrpc.rpc.IRbacRpcService;

@Service
public class PermissionQueryService {
    @DubboReference
    private IRbacRpcService rbacRpcService;

    public List<String> getAuthCodes(String userId){
        return rbacRpcService.getAuthCodesByUserId(userId);
    }
}
