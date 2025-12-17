package com.vbenadmin.backend.user.converter;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.vbenadmin.backend.commonrpc.models.dto.UserInfoDTO;
import com.vbenadmin.backend.user.entity.User;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserInfoDTOConverter {

    UserInfoDTO toDTO(User user);    
}
