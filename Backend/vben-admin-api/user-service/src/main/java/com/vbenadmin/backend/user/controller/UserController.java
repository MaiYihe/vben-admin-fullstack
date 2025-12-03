package com.vbenadmin.backend.user.controller;


import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import com.vbenadmin.backend.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

//    @GetMapping("/info")
//    public ApiResponse<>
}
