package com.vbenadmin.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import com.vbenadmin.backend.user.models.vo.UserProfileVO;
import com.vbenadmin.backend.user.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<UserProfileVO>> getUserProfile(){
        UserProfileVO vo = userService.getUserProfile();
        return ResponseEntity.ok(ApiResponse.success(vo));
    }
}
