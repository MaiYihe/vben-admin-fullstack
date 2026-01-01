package com.vbenadmin.backend.rbac.models.vo;

import java.util.ArrayList;
import java.util.List;

import com.vbenadmin.backend.rbac.enums.ResourceType;

public record ResourceInfoVO(
        String id,
        String pid,
        String name,
        Integer status,
        ResourceType type,
        String path,
        String component,
        String authCode,
        MetaVO meta,
        List<ResourceInfoVO> children) {

    public ResourceInfoVO {
        if (children == null) {
            children = new ArrayList<>();
        }
        if (meta == null) {
            meta = MetaVO.EMPTY;
        }
    }
}
