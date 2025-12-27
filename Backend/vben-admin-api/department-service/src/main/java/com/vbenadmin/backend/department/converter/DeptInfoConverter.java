package com.vbenadmin.backend.department.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.vbenadmin.backend.department.entity.Department;
import com.vbenadmin.backend.department.models.vo.DeptInfoVO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DeptInfoConverter {

    DeptInfoVO toVO(Department dept);

    List<DeptInfoVO> toVOList(List<Department> depts);
}
