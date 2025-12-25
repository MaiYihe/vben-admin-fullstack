package com.vbenadmin.backend.rbac.converter;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vbenadmin.backend.rbac.converter.context.RoleRelationContext;
import com.vbenadmin.backend.rbac.entity.Role;
import com.vbenadmin.backend.rbac.models.vo.RoleInfoVO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoleInfoVOConverter {
    @Mapping(target = "permissions", expression = "java(ctx.getPermissions(role.getId()))")
    RoleInfoVO toVO(Role role, @Context RoleRelationContext ctx);

    List<RoleInfoVO> toVOList(List<Role> roles, @Context RoleRelationContext ctx);
}
