package com.vbenadmin.backend.user.converter;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vbenadmin.backend.user.converter.context.UserRelationContext;
import com.vbenadmin.backend.user.entity.User;
import com.vbenadmin.backend.user.models.vo.UserInfoVO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserInfoVOConverter {

    @Mapping(target = "remark", source = "user.description")
    @Mapping(target = "roles", expression = "java(ctx.getRoles(user.getId()))")
    @Mapping(target = "groups", expression = "java(ctx.getGroups(user.getId()))")
    UserInfoVO toVO(
            User user,
            @Context UserRelationContext ctx);

    List<UserInfoVO> toVOList(
            List<User> users,
            @Context UserRelationContext ctx);

}
