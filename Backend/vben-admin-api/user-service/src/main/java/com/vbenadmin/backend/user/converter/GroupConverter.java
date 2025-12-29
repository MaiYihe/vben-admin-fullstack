package com.vbenadmin.backend.user.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.models.request.GroupCreateRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface GroupConverter {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createTime",ignore = true)
    Group toEntity(GroupCreateRequest request);
}
