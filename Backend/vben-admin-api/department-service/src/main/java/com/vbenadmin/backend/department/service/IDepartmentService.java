package com.vbenadmin.backend.department.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vbenadmin.backend.department.entity.Department;
import com.vbenadmin.backend.department.models.request.DeptCreateRequest;
import com.vbenadmin.backend.department.models.vo.DeptInfoVO;

public interface IDepartmentService extends IService<Department> {

    List<DeptInfoVO> getAllDeptList();

    void createDeptByRequest(DeptCreateRequest request);
}
