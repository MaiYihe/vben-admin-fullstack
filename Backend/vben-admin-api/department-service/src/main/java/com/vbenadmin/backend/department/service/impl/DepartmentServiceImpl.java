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
}
