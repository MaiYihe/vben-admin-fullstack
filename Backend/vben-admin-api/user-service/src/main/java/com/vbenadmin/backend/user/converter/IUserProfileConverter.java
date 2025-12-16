package com.vbenadmin.backend.user.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vbenadmin.backend.user.models.dto.LoginUserDTO;
import com.vbenadmin.backend.user.models.vo.UserProfileVO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface IUserProfileConverter {
    @Mapping(source = "dto.id", target = "userId")
    UserProfileVO toUserProfileVO(
            LoginUserDTO dto,
            List<String> roles,
            String accessToken);
}
