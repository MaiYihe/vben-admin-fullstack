package com.vbenadmin.backend.department.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeptCreateRequest {
    @NotNull(message = "部门名不能为空")
    private String name;

    private String pid;
    private Boolean status;
    private String remark;
}
