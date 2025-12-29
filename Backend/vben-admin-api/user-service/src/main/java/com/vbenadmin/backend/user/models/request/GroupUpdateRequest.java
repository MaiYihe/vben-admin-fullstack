package com.vbenadmin.backend.user.models.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GroupUpdateRequest {
    @NotNull(message = "用户组名不能为空")
    private String name;
    @NotNull(message = "用户组编码不能为空")
    private String code;
    private Boolean status;
    private String remark;

    private List<String> roleIds;
    private List<String> userIds;
}
