package com.vbenadmin.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.user.models.request.GroupQueryRequest;
import com.vbenadmin.backend.user.models.vo.GroupInfoVO;
import com.vbenadmin.backend.user.service.IGroupService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 用户组表 前端控制器
 * </p>
 *
 * @author maihehe
 * @since 2025-12-17
 */
@RestController
@RequestMapping("/sys/group")
@RequiredArgsConstructor
public class GroupController {
    
    private final IGroupService groupService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponseVO<GroupInfoVO>>> getGroupList(@Valid @RequestBody GroupQueryRequest request){
        PageResponseVO<GroupInfoVO> pageVO = groupService.getGroupListByRequest(request);
        return ResponseEntity.ok(ApiResponse.success(pageVO));
    }
    
}
