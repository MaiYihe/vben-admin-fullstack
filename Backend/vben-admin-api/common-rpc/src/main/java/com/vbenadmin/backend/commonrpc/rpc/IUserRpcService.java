package com.vbenadmin.backend.commonrpc.rpc;



import java.util.List;

import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.commonrpc.models.request.UserRegisterRequest;


public interface IUserRpcService {
    List<String> getAuthCodesByUserId(String userId);

    UserInfoDTO getUserInfoByUserName(String username);

    boolean existUser(String username);

    String registerUser(UserRegisterRequest userRegisterRequest);
    
}
