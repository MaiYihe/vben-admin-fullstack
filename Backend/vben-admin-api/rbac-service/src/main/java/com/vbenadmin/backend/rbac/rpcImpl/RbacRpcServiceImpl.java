
package com.vbenadmin.backend.rbac.rpcImpl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;

import com.vbenadmin.backend.commonrpc.rpc.IRbacRpcService;
import com.vbenadmin.backend.rbac.service.IRbacService;

import lombok.RequiredArgsConstructor;


@DubboService
@RequiredArgsConstructor
public class RbacRpcServiceImpl implements IRbacRpcService{

    private final IRbacService rbacService;

    @Override
    public List<String> getAccessCodes(String userId) {
        return rbacService.getAccessCodesByUserId(userId);
    }

}
