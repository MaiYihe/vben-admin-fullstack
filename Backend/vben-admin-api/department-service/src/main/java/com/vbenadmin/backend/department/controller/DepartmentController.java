package com.vbenadmin.backend.department.controller;

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
import com.vbenadmin.backend.department.models.request.DeptCreateRequest;
import com.vbenadmin.backend.department.models.request.DeptUpdateRequest;
import com.vbenadmin.backend.department.models.vo.DeptInfoVO;
import com.vbenadmin.backend.department.service.IDepartmentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/system/dept")
@AllArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DeptInfoVO>>> getAllDeptList() {
        List<DeptInfoVO> voList = departmentService.getAllDeptList();
        return ResponseEntity.ok(ApiResponse.success(voList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createDept(@Valid @RequestBody DeptCreateRequest request) {
        departmentService.createDeptByRequest(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateDept(@PathVariable String id,
            @Valid @RequestBody DeptUpdateRequest request) {
        departmentService.updateDeptByRequest(id, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
