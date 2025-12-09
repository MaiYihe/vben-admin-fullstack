package com.vbenadmin.backend.commonrpc.rpc;

import java.util.List;

public interface IRbacRpcService {
    List<String> getAccessCodes(String userId);
}
