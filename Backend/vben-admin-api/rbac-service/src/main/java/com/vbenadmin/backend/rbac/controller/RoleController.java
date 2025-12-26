package com.vbenadmin.backend.rbac.controller;

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
import com.vbenadmin.backend.commonweb.models.vo.PageResponseVO;
import com.vbenadmin.backend.rbac.models.request.RoleCreateRequest;
import com.vbenadmin.backend.rbac.models.request.RoleQueryRequest;
import com.vbenadmin.backend.rbac.models.request.RoleUpdateRequest;
import com.vbenadmin.backend.rbac.models.vo.RoleInfoVO;
import com.vbenadmin.backend.rbac.service.IRoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system/role")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponseVO<RoleInfoVO>>> getRoleList(@Valid RoleQueryRequest request) {
        PageResponseVO<RoleInfoVO> pageVO = roleService.getRoleListByRequest(request);
        var response = ApiResponse.success(pageVO);

        if (pageVO.getItems() == null)
            response.setMessage("当前查询条件下找不到任何条目");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RoleInfoVO>>> getAllRoleList() {
        List<RoleInfoVO> voList = roleService.getAllRoleList();
        return ResponseEntity.ok(ApiResponse.success(voList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createRole(@Valid @RequestBody RoleCreateRequest roleCreateRequest) {
        roleService.createRole(roleCreateRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateRole(@PathVariable("id") String id,
            @Valid @RequestBody RoleUpdateRequest request) {
        roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
