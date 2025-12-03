package com.vbenadmin.backend.commonrpc.rpc;


import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserCreateRequest;

import java.util.List;

public interface IUserRpcService {
    UserInfoDTO getPasswordByUserName(String username);

    boolean existUser(String username);

    Long createUser(UserCreateRequest userCreateRequest);

    List<String> getAccessCodes(Long userId);
}