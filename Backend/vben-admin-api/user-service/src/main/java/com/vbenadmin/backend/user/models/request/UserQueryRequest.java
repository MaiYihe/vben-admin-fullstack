package com.vbenadmin.backend.user.models.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserQueryRequest {
    @NotNull(message = "页数不能为空")
    private Integer page;

    @NotNull(message = "页条目数不能为空")
    private Integer pageSize;

    private String username;
    private String id;
    private String realName;
    private Byte status;

    private String roles;
    private String groups;
    private String remark;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
