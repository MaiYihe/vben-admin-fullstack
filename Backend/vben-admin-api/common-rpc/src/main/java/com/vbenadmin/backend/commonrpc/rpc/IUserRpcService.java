package com.vbenadmin.backend.commonrpc.rpc;


import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserCreateRequest;


public interface IUserRpcService {
    UserInfoDTO getUserInfoByUserName(String username);

    boolean existUser(String username);

    String createUser(UserCreateRequest userCreateRequest);
    
}
