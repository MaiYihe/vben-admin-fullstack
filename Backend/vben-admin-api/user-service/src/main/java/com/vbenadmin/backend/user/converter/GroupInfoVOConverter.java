package com.vbenadmin.backend.user.converter;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vbenadmin.backend.user.converter.context.GroupRelationContext;
import com.vbenadmin.backend.user.entity.Group;
import com.vbenadmin.backend.user.models.vo.GroupInfoVO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface GroupInfoVOConverter {
    
    @Mapping(target = "roles", expression = "java(ctx.getRoles(group.getId()))") 
    @Mapping(target = "userCount", expression = "java(ctx.getUsersCount(group.getId()))")
    GroupInfoVO toVO(
            Group group,
            @Context GroupRelationContext ctx);

    List<GroupInfoVO> toVOList(
            List<Group> groups,
            @Context GroupRelationContext ctx);
}
