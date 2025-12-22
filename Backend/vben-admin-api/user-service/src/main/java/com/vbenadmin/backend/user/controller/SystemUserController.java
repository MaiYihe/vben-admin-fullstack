package com.vbenadmin.backend.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.user.models.request.UserCreateRequest;
import com.vbenadmin.backend.user.models.request.UserQueryRequest;
import com.vbenadmin.backend.user.models.request.UserUpdateRequest;
import com.vbenadmin.backend.user.models.vo.UserInfoVO;
import com.vbenadmin.backend.user.service.ISystemUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SystemUserController {

    private final ISystemUserService userService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponseVO<UserInfoVO>>> getUserList(UserQueryRequest request) {
        PageResponseVO<UserInfoVO> pageVO = userService.getUserListByRequest(request);
        var response = ApiResponse.success(pageVO);

        if(pageVO.getItems() == null)
            response.setMessage("当前查询条件下找不到任何条目");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserInfoVO>>> getAllUserList() {
        List<UserInfoVO> voList = userService.getAllUserList();
        return ResponseEntity.ok(ApiResponse.success(voList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable("id") String id,
            @RequestBody UserUpdateRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserInfoVO>> createUser(@RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") String id){
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
