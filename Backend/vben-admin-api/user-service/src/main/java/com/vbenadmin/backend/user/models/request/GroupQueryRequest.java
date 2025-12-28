package com.vbenadmin.backend.user.models.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GroupQueryRequest {
    @NotNull(message = "页数不能为空")
    private Integer page;
    @NotNull(message = "页条目数不能为空")
    private Integer pageSize;

    private String name;
    private String code;
    private Boolean status;
    private List<String> roles;

    private String remark;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
