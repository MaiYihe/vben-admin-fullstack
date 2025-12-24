package com.vbenadmin.backend.rbac.models.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleQueryRequest {
    @NotNull(message = "页数不能为空")
    private Integer page;
    @NotNull(message = "页条目数不能为空")
    private Integer pageSize;
    
    private String name;
    private String id;
    private String remark;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Integer status;
}
