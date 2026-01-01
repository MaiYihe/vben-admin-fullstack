package com.vbenadmin.backend.rbac.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vbenadmin.backend.commoncore.models.response.ApiResponse;
import com.vbenadmin.backend.rbac.models.vo.ResourceInfoVO;
import com.vbenadmin.backend.rbac.service.IResourceService;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 系统资源（菜单/页面/按钮/内嵌/外链）表 前端控制器
 * </p>
 *
 * @author maihehe
 * @since 2025-12-08
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/system/resource")
public class ResourceController {

    private final IResourceService resourceService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ResourceInfoVO>>> getAllResourceList(){
        List<ResourceInfoVO> voList = resourceService.getAllResourceList();
        return ResponseEntity.ok(ApiResponse.success(voList));
    }
}
