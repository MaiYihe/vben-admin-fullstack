package com.vbenadmin.backend.rbac.models.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class RoleInfoVO {
    private String id;
    private String name;
    private Integer status;
    private LocalDateTime createTime;
    private List<String> permissions;
    private String remark;
}
