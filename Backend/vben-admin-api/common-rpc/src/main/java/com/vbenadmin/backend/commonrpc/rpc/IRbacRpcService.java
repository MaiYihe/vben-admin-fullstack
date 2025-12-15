package com.vbenadmin.backend.commonrpc.rpc;

import java.util.List;

public interface IRbacRpcService {
    List<String> getAuthCodes(String userId);
    List<String> getRoles(String userId);
}
