package com.vbenadmin.backend.department.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vbenadmin.backend.department.entity.Department;
import com.vbenadmin.backend.department.models.request.DeptCreateRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DeptConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    Department toEntity(DeptCreateRequest request);
}
