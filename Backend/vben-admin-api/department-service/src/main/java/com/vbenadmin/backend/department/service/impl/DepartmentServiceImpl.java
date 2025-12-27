package com.vbenadmin.backend.department.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.department.converter.DeptConverter;
import com.vbenadmin.backend.department.converter.DeptInfoConverter;
import com.vbenadmin.backend.department.entity.Department;
import com.vbenadmin.backend.department.mapper.DepartmentMapper;
import com.vbenadmin.backend.department.models.request.DeptCreateRequest;
import com.vbenadmin.backend.department.models.request.DeptUpdateRequest;
import com.vbenadmin.backend.department.models.vo.DeptInfoVO;
import com.vbenadmin.backend.department.service.IDepartmentService;
import com.vbenadmin.backend.department.service.IUserDepartmentService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    private final DeptInfoConverter deptInfoConverter;
    private final DeptConverter deptConverter;
    private final IUserDepartmentService userDeptService;

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

    private boolean existDept(String DeptName) {
        return this.lambdaQuery()
                .eq(Department::getName, DeptName)
                .count() > 0;
    }

    @Override
    public void updateDeptByRequest(String deptId, DeptUpdateRequest request) {
        Department dept = this.getById(deptId);
        if (dept == null)
            throw new BizException(40000, "部门不存在");

        log.debug("尝试更新 deptId 为 {} 的用户", deptId);
        this.lambdaUpdate()
                .eq(Department::getId, deptId)
                .set(request.getName() != null, Department::getName, request.getName())
                .set(request.getPid() != null, Department::getPid, request.getPid())
                .set(request.getStatus() != null, Department::getStatus, request.getStatus())
                .set(request.getRemark() != null, Department::getRemark, request.getRemark())
                .update();
    }

    @Override
    @Transactional
    public void deleteDeptById(String id) {

        Department dept = this.getById(id);
        if (dept == null)
            throw new BizException(40401, "部门不存在");
        userDeptService.removeByDeptId(id);

        this.removeById(id);
    }
}
