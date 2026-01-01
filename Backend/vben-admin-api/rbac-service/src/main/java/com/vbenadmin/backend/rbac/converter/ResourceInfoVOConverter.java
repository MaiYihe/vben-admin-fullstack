package com.vbenadmin.backend.rbac.converter;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbenadmin.backend.commoncore.exception.BizException;
import com.vbenadmin.backend.rbac.entity.Resource;
import com.vbenadmin.backend.rbac.models.vo.MetaVO;
import com.vbenadmin.backend.rbac.models.vo.ResourceInfoVO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ResourceInfoVOConverter {

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "meta", expression = "java(parseMeta(resource.getMeta(),objectMapper))")
    ResourceInfoVO toVO(Resource resource, @Context ObjectMapper objectMapper);

    List<ResourceInfoVO> toVOList(List<Resource> resources, @Context ObjectMapper objectMapper);

    default MetaVO parseMeta(String metaJson, ObjectMapper objectMapper) {
        if (metaJson == null) {
            return MetaVO.EMPTY;
        }
        try {
            return objectMapper.readValue(metaJson, MetaVO.class);
        } catch (Exception e) {
            throw new BizException(50000, "资源元数据 meta 解析失败");
        }
    }
}
