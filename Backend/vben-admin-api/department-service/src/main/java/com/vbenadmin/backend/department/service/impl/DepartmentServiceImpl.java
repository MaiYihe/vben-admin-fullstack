package com.vbenadmin.backend.department.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.department.converter.DeptConverter;
import com.vbenadmin.backend.department.converter.DeptInfoConverter;
import com.vbenadmin.backend.department.entity.Department;
import com.vbenadmin.backend.department.mapper.DepartmentMapper;
import com.vbenadmin.backend.department.models.request.DeptCreateRequest;
import com.vbenadmin.backend.department.models.vo.DeptInfoVO;
import com.vbenadmin.backend.department.service.IDepartmentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    private final DeptInfoConverter deptInfoConverter;
    private final DeptConverter deptConverter;

    @Override
    public List<DeptInfoVO> getAllDeptList() {
        List<Department> depts = this.list();
        if (depts.isEmpty()) {
            return List.of();
        }
        return deptInfoConverter.toVOList(depts);
    }

    @Override
    public void createDeptByRequest(DeptCreateRequest request) {
        if (existDept(request.getName()))
            throw new BizException(40901, "部门已存在");

        Department dept = deptConverter.toEntity(request);
        this.save(dept);
    }

    private boolean existDept(String DeptName){
        return this.lambdaQuery()
            .eq(Department::getName,DeptName)
            .count() > 0;
    }
}
