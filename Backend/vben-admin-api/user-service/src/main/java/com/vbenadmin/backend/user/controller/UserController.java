package com.vbenadmin.backend.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import com.vbenadmin.backend.user.models.request.UserCreateRequest;
import com.vbenadmin.backend.user.models.request.UserQueryRequest;
import com.vbenadmin.backend.user.models.request.UserUpdateRequest;
import com.vbenadmin.backend.user.models.vo.UserInfoVO;
import com.vbenadmin.backend.user.models.vo.UserProfileVO;
import com.vbenadmin.backend.user.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<UserProfileVO>> getUserProfile() {
        UserProfileVO vo = userService.getUserProfile();
        return ResponseEntity.ok(ApiResponse.success(vo));
    }

    @GetMapping("/system/list")
    public ResponseEntity<ApiResponse<List<UserInfoVO>>> getUserList(UserQueryRequest request) {
        List<UserInfoVO> voList = userService.getUserListByRequest(request);
        return ResponseEntity.ok(ApiResponse.success(voList));
    }

    @GetMapping("/system/all")
    public ResponseEntity<ApiResponse<List<UserInfoVO>>> getAllUserList() {
        List<UserInfoVO> voList = userService.getAllUserList();
        return ResponseEntity.ok(ApiResponse.success(voList));
    }

    @PutMapping("/system/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable("id") String id,
            @RequestBody UserUpdateRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/system/user")
    public ResponseEntity<ApiResponse<UserInfoVO>> createUser(@RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
